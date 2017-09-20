var builder = require('botbuilder');
var logger = require('../../log4js').logger;
var prorigoRest = require('../prorigoRest');

var lib = new builder.Library('booking');

lib.dialog('/ShowBookingStatus',[function(session,args,next){
    session.sendTyping();
    prorigoRest.getAllBookings(function(json){
        var bookingMessage = createBookingMessage(session, json);
        session.send(bookingMessage);
        session.endDialog();
    }, function(err){
        logger.error(err);
        session.endDialog('something_went_wrong');
    });
}]).triggerAction({
    matches: 'ShowBookingStatus'
});

function createBookingMessage(session, bookingJson){
    var bookingMessageText = '';
    if(bookingJson.length == 0){
        bookingMessageText = 'No bookings';
    } else {
        bookingJson.forEach(function(booking){
            bookingMessageText += '* Meeting in ' + booking.room + ' on ' + booking.date +' from ' 
            + booking.fromTime + ' to ' + booking.toTime + ' for ' + reason + ' with ' + attendees + '\n\n';
        });
    }
    
    var bookingMessage = new builder.Message(session);
    bookingMessage.text(bookingMessageText).textFormat('markdown');
    return bookingMessage;
}

// Export createLibrary() function
module.exports.createLibrary = function () {
    return lib.clone();
};