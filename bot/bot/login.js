
$(function () {
    $('#submit').click(function (e) {
        e.preventDefault();
        console.log('select_link clicked');
        var data = {};
        data.channelId = $("#channelId").val();
        data.userId = $("#userId").val();
        data.username = $("#username").val();
        data.password = $("#password").val();

        $.ajax({
            type: 'POST',
            data: JSON.stringify(data),
            contentType: 'application/json',
            url: 'http://localhost:3978/login',
            success: function (data) {
                alert(data);
            }
        });
    });  
});               