var builder = require('botbuilder');
var logger = require('../log4js').logger;

var prorigoRest = require('./prorigoRest');

var connector = new builder.ChatConnector({
    appId: process.env.MICROSOFT_APP_ID,
    appPassword: process.env.MICROSOFT_APP_PASSWORD
});

var DialogLabels = {
    book_room: 'Book a room',
    cancel_booking: 'Cancel room booking',
};

// This is a dinner reservation bot that uses a waterfall technique to prompt users for input.
var bot = new builder.UniversalBot(connector, [
    function(session){
        var msg = session.message.text.toLowerCase();
        if(msg == '' ||msg == 'hi'){
            session.send('welcome_title');
            session.send('welcome_info');
            session.send('Just type away your requests or queries');
        }
    }
]);

var luisAppUrl = process.env.LUIS_MODEL_URL
bot.recognizer(new builder.LuisRecognizer(luisAppUrl));

// bot.beginDialog('understandUtterance', [
//     function(session){
//         builder.Prompts.text(session, "Just type away your requests or queries");
//     },
//     function(session, results){

//     }
// ]);

bot.dialog('ShowHolidays', [
    function(session, args, next){
        session.sendTyping();
        var intent = args.intent;
        var duration = builder.EntityRecognizer.findEntity(intent.entities, 'builtin.datetimeV2.daterange');
        logger.debug(duration);
        prorigoRest.getAllHolidays().then(function(json){
            var holidayMessage = createHolidayMessage(session, json);
            session.send(holidayMessage);
            session.endDialog();
        }).catch(function(err){
            logger.error(err);
            session.endDialog('something_went_wrong');
        });
    }
]).triggerAction({
    matches:'ShowHolidays'
});

var room = require('./book-room');

bot.dialog('bookrooms',room );

bot.on('error', function (e) {
    console.log('And error ocurred', e);
});

// Enable Conversation Data persistence
bot.set('persistConversationData', true);

// Set default locale
bot.set('localizerSettings', {
    botLocalePath: './bot/locale',
    defaultLocale: 'en'
});



// Trigger secondary dialogs when 'settings' or 'support' is called
bot.use({
    botbuilder: function (session, next) {
        var text = session.message.text.toLowerCase();
        logger.debug(session.message);
        var supportRegex = localizedRegex(session, ['help']);

        if (supportRegex.test(text)) {
            // interrupt and trigger 'help' dialog
            //return session.beginDialog('help:/');
        }

        // continue normal flow
        next();
    }
});

// Send welcome when conversation with bot is started, by initiating the root dialog
bot.on('conversationUpdate', function (message) {
    if (message.membersAdded) {
        message.membersAdded.forEach(function (identity) {
            if (identity.id === message.address.bot.id) {
                bot.beginDialog(message.address, '/');
            }
        });
    }
});

var LocalizedRegexCache = {};
function localizedRegex(session, localeKeys) {
    var locale = session.preferredLocale();
    var cacheKey = locale + ":" + localeKeys.join('|');
    if (LocalizedRegexCache.hasOwnProperty(cacheKey)) {
        return LocalizedRegexCache[cacheKey];
    }

    var localizedStrings = localeKeys.map(function (key) { return session.localizer.gettext(locale, key); });
    var regex = new RegExp('^(' + localizedStrings.join('|') + ')', 'i');
    LocalizedRegexCache[cacheKey] = regex;
    return regex;
}

// Connector listener wrapper to capture site url
var connectorListener = connector.listen();
function listen() {
    return function (req, res) {
        connectorListener(req, res);
    };
}

// Other wrapper functions
function beginDialog(address, dialogId, dialogArgs) {
    bot.beginDialog(address, dialogId, dialogArgs);
}

function sendMessage(message) {
    bot.send(message);
}

function createHolidayMessage(session, holidayJson){
    var holidayMessageText = '';
    holidayJson.forEach(function(holiday){
        holidayMessageText += '* ' + holiday.reason + ' ' + holiday.date + '\n\n';
    });
    var holidayMessage = new builder.Message(session);
    holidayMessage.text(holidayMessageText).textFormat('markdown');
    return holidayMessage;
}

module.exports = {
    listen: listen,
    beginDialog: beginDialog,
    sendMessage: sendMessage
};