function getParameterByName(name, url) {
    if (!url) url = window.location.href;
    name = name.replace(/[\[\]]/g, "\\$&");
    var regex = new RegExp("[?&]" + name + "(=([^&#]*)|&|#|$)"),
        results = regex.exec(url);
    if (!results) return null;
    if (!results[2]) return '';
    return decodeURIComponent(results[2].replace(/\+/g, " "));
}

$(function () {
        var userNameQueryString = getParameterByName('userName');
        $("#userName").text(userNameQueryString);
        $('#submit').click(function (e) {
            e.preventDefault();
            console.log('select_link clicked');
            var data = {};
            data.channelId = $("#channelId").val();
            data.userId = $("#userId").val();
            data.username = $("userName").val();
            data.password = $("#new_password").val();
            data.confirmPassword = $("confirm_password").val();

            if(data.password != data.confirmPassword)
                {
                    alert("Please ensure that password is correct");
                }

            $.ajax({
                type: 'POST',
                data: JSON.stringify(data),
                contentType: 'application/json',
                url: appUrl + '/changePassword',
                success: function (data) {
                    if (data.responseText == "User credentials saved successfully") {
                        alert("Password changed");
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