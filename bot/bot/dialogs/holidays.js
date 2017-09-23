var builder = require('botbuilder');
var logger = require('../../log4js').logger;
var prorigoRest = require('../prorigoRest');
var dateFormat = require('dateformat');
var userInfo = require('../index').userInfo;

var lib = new builder.Library('holidays');
lib.dialog('/', [function(session, args, next) {
        session.sendTyping();
        var intent = args.intent;
        var duration = builder.EntityRecognizer.findEntity(intent.entities, 'builtin.datetimeV2.daterange');
        logger.debug(duration);
        if(duration == null){
            prorigoRest.getAllHolidays(function(json){
                var holidayMessage = createHolidayMessage(session, json);
                session.send(holidayMessage);
                session.endDialog();
            }, function(err){
                logger.error(err);
                session.endDialog('something_went_wrong');
            });
        } else {
            var actualDuration = duration.resolution.values[0];
            var thisYear = new Date().getFullYear();
            duration.resolution.values.forEach((val) =>{
                if(val.start.startsWith(thisYear)){
                    actualDuration = val;
                }
            });
            if(!actualDuration.end) {
                var date = dateFormat(actualDuration.start,"dd-mm-yyyy");
                console.log(date);
                prorigoRest.getHolidaysAfter(
                    function(json){
                        var holidayMessage = createHolidayMessage(session, json);
                        session.send(holidayMessage);
                        session.endDialog();
                    },function(err){
                        logger.error(err);
                        session.endDialog('something_went_wrong');
                    }, date);
            }
            else {
                prorigoRest.getHolidays(function(json){
                var holidayMessage = createHolidayMessage(session, json);
                session.send(holidayMessage);
                session.endDialog();
            },function(err){
                logger.error(err);
                session.endDialog('something_went_wrong');
            }, {startDate : actualDuration.start, endDate: actualDuration.end});
        }
    }
        
}]).triggerAction({
    matches:'ShowHolidays'
});

function createHolidayMessage(session, holidayJson){
    var holidayMessageText = '';
    if(holidayJson.length == 0){
        holidayMessageText = 'No holidays';
    } else {
        holidayJson.forEach(function(holiday){
            holidayMessageText += '* ' + holiday.reason + ' on ' + holiday.date + '\n\n';
        });
    }
    var holidayMessage = new builder.Message(session);
    holidayMessage.text(holidayMessageText).textFormat('markdown');
    return holidayMessage;
}

// Export createLibrary() function
module.exports.createLibrary = function () {
    return lib.clone();
};