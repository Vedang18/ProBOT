var builder = require('botbuilder');
var logger = require('../log4js').logger;
var querystring = require('querystring');
var util = require('util');
var prorigoRest = require('./prorigoRest');

var connector = new builder.ChatConnector({
    appId: process.env.MICROSOFT_APP_ID,
    appPassword: process.env.MICROSOFT_APP_PASSWORD
});

var appUrl = process.env.APP_URL;

var DialogLabels = {
    book_room: 'Book a room',
    cancel_booking: 'Cancel room booking',
};

//TODO : give proper msg in Universal Bot also check the logic
var bot = new builder.UniversalBot(connector, [
    function (session) {
        var msg = session.message.text.toLowerCase();
        if (msg == '' || msg == 'hi') {
            session.send('Hello ' + session.message.address.user.name + ', I am ProBOT');
            session.send('welcome_info');

            provideloginIfneeded(session);
        }
    }
]);

var luisAppUrl = process.env.LUIS_MODEL_URL;
bot.recognizer(new builder.LuisRecognizer(luisAppUrl));

bot.library(require('./dialogs/holidays').createLibrary());
bot.library(require('./dialogs/bookings').createLibrary());

bot.dialog('help', function (session) {
    var helpMessageText = "\n* Room Booking \n* Cancel Booking \n* Show Bookings \n* Show Holidays";
    var helpMessage = new builder.Message(session);
    helpMessage.text(helpMessageText).textFormat('markdown');
    session.endDialog("ProBOT is here to help you in: " + helpMessage.data.text);
}
).triggerAction({ matches: "help" })

var room = require('./book-room');

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
        // if (session.message.address.channelId === 'slack') {
        //     if (session.message.sourceEvent.SlackMessage) {
        //         if (session.message.sourceEvent.SlackMessage.type === 'message') {
        //             return;
        //         }
        //     }
        // }
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

function provideloginIfneeded(session) {
    session.sendTyping();
    var channelId = session.message.address.channelId;
    var userId = session.message.address.user.id;
    prorigoRest.findUserByChannelIdAndUserId(function (json) {
        //session.userData.userEntry = json;
        session.endDialog();
    }, function (err) {
        var link = util.format(
            
            '%s/login?userId=%s&channelId=%s',
            appUrl, encodeURIComponent(userId), encodeURIComponent(channelId));
        var msg = new builder.Message(session)
            .attachments([
                new builder.SigninCard(session)
                .text("You must first login to your account.")
                .button("Sign-In", link)
            ]);
            session.send(msg);
            session.send('You can also use the following to register yourself:' + '\n\n' + link);
            session.endDialog();
    }, { userId: userId, channelId: channelId });


}



module.exports = {
    listen: listen,
    beginDialog: beginDialog,
    sendMessage: sendMessage
};