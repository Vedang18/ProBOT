var moment = require('moment');

function welcomeAfterLongTime(session) {
    if (session.userData && session.userData.lastConversationTime) {
        // greet user if he comes back after 1 week
        var lastTime = moment(session.userData.lastConversationTime).add(1 * 7 * 24 * 60, 'm');
        if (moment().isAfter(lastTime)) {
            session.send('Hey! Welcome back. Long time no see.');
        }
    }

    session.userData.lastConversationTime = moment();
}

module.exports = {
    welcomeAfterLongTime: welcomeAfterLongTime
}