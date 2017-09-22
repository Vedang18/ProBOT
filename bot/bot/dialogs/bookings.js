var builder = require('botbuilder');
var logger = require('../../log4js').logger;
var prorigoRest = require('../prorigoRest');

var lib = new builder.Library('booking');

// lib.dialog('/ShowBookingStatus',[
//     function(session,args,next){
//     session.sendTyping();
//     var intent = args.intent;
//     var  = builder.EntityRecognizer.findEntity(intent.entities, 'Note.Title');

//     prorigoRest.getAllBookings(function(json){
//         var bookingMessage = createBookingMessage(session, json);
//         session.send(bookingMessage);
//         session.endDialog();
//     }, function(err){
//         logger.error(err);
//         session.endDialog('something_went_wrong');
//     },{userId : 'test', channelId: 'skype'});
// }]).triggerAction({
//     matches: 'ShowBookingStatus'
// });


lib.dialog('/CancelBooking',[function(session,args,next){
    session.sendTyping();
    prorigoRest.getMyBookings(function(jsonBody){
        if (jsonBody.length == 0){
            var showBookingsMessage = "You have no reserved room"
            session.send(showBookingsMessage)
        }else{
            var room_list = [];
            for (var i=0; i<jsonBody.length; i++){
                room_list[i] = jsonBody[i].room + "  "+ jsonBody[i].date + "  " + jsonBody[i].fromTime + " to " + jsonBody[i].toTime;
                }
                session.dialogData.room_list = jsonBody;
            builder.Prompts.choice(session, "Which room do you want to cancel?", room_list, {listStyle: 3})
            }
    }, function(err){
        logger.error(err);
        session.endDialog('something_went_wrong');
    },{userId : 'test', channelId: 'skype'});
},function(session,results){
    var meeting = session.dialogData.room_list[results.response.index];
    session.dialogData.meeting = meeting;
    var promptMsg1 = "Are you sure you want to cancel ";
    var promptMsg2 = meeting.room +" on " + meeting.date + " from " + meeting.fromTime + " to " + meeting.toTime + " ?";
    var promptMsg = promptMsg1 + promptMsg2;
    builder.Prompts.choice(session,promptMsg,["Yes","No"], {listStyle : 3});
    },
    function(session, results){
        session.dialogData.ans = results.response.entity;
        if(session.dialogData.ans == "Yes"){
            prorigoRest.cancelBookings(function(){
                session.endDialog("Booking deleted successfully!");
            }, function(){
                session.endDialog('something_went_wrong');
            },{user: {userId : 'test', channelId: 'skype'}, meeting: session.dialogData.meeting});
        }else{
            session.endDialog();
        }
    }    
    ]).triggerAction({
    matches: 'CancelRoom'
});

     
lib.dialog('/bookRoom', [
    function(session, args, next){
        session.sendTyping();
        parseBookingEntities(args.intent);
        session.dialogData.bookingInfo = {};
        next();
    }, function(session, results){
        if(!session.dialogData.bookingInfo.roomName){
            builder.Prompts.choice(session, 'Which room do you want to book?', ['Conf Room 4', 'Conf Room 1', 'Meeting room 3'], {listStyle : 3});
        }
    }, function(session, results){
        // do rest API to LUIS to determine the room
        session.dialogData.bookingInfo.roomName = results.response.entity;
        if(!session.dialogData.bookingInfo.bookingFromDate){
            builder.Prompts.time(session, 'Enter the time of booking');
        }
    },function(session, results){
        session.dialogData.bookingInfo.bookingFromDate = results.response.entity;
        if(!session.dialogData.bookingInfo.bookingPurpose){
            builder.Prompts.text(session, 'Enter purpose of booking');
        }
    }, function(session, results){
        session.dialogData.bookingInfo.bookingPurpose = results.response.entity;
        session.endDialog();
    }
    // },function(session, results){
    //     session.dialogData.bookingInfo.
    // }
]).triggerAction({
    matches: 'BookRoom'
});

lib.dialog('/attendees', [

]);

function createBookingMessage(session, bookingJson){
    var bookingMessageText = '';
    if(bookingJson.length == 0){
        bookingMessageText = 'No bookings';
    } else {
        bookingJson.forEach(function(booking){
            bookingMessageText += '* Meeting in ' + booking.room + ' on ' + booking.date +' from ' 
            + booking.fromTime + ' to ' + booking.toTime + ' for ' + booking.reason + ' with ' + booking.attendees + '\n\n';
        });
    }
    
    var bookingMessage = new builder.Message(session);
    bookingMessage.text(bookingMessageText).textFormat('markdown');
    return bookingMessage;
}

function parseBookingEntities(intent){
    // 4:00 to 5:00 pm on next monday
    var roomEntity = builder.EntityRecognizer.findEntity(intent.entities, 'builtin.datetimeV2.datetimerange');
    // next 2 days
    var dateRange = builder.EntityRecognizer.findEntity(intent.entities, 'builtin.datetimeV2.daterange');
    // today/tomorrow
    var dateEntity = builder.EntityRecognizer.findEntity(intent.entities, 'builtin.datetimeV2.date');
    // 4pm to 5pm
    var timeRange = builder.EntityRecognizer.findEntity(intent.entities, 'builtin.datetimeV2.timerange');
    // 4:00
    var timeEntity = builder.EntityRecognizer.findEntity(intent.entities, 'builtin.datetimeV2.time');
    // 1 hour
    var durationEntity = builder.EntityRecognizer.findEntity(intent.entities, 'builtin.datetimeV2.duration');
    // cr1
    var roomEntity = builder.EntityRecognizer.findEntity(intent.entities, 'builtin.datetimeV2.room');
    
    var bookingRoom = getBookingRoom(roomEntity);
    var bookingStartTime = null;
    var bookinsEndTime = null;
    var bookingStartDate = null;
    var bookingEndDate = null;
}

function getBookingRoom(roomEntity){
    var room = null;
    if(roomEntity && roomEntity.resolution && roomEntity.resolution.values){
        room = roomEntity.resolution.values[0];
    }
    return room;
}

// Export createLibrary() function
module.exports.createLibrary = function () {
    return lib.clone();
};