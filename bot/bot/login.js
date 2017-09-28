
$(function () {
    $('#submit').click(function (e) {
        e.preventDefault();
        console.log('select_link clicked');
        var data = {};
        data.channelId = $("#channelId").val();
        data.userId = $("#userId").val();
        data.username = $("#username").val();
        data.password = $("#password").val();
        data.addressString = $("#addressId").val();

        $.ajax({
            type: 'POST',
            data: JSON.stringify(data),
            contentType: 'application/json',
            url: appUrl + '/login',
            success: function (data) {
                if (data.responseText == "User added") {
                    alert("User logged In sucessfully");
                }
                else {
                    alert("Unauthorised Access");
                }
            },
            error: function (err) {
                if (error.responseText == "Unauthorised") {
                    alert("Unauthorised Access");
                }
            }
        });
    });
});               