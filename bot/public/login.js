
$(document).ready(function () {
    $("#alert-msg").hide();
    var form = $('#login-form');
    form.submit(function (event) {
        event.stopPropagation();
        event.preventDefault();
        if (form.valid()) {
            var data = {};
            data.username = $("#username").val();
            data.password = $("#password").val();
            data.channelId = getUrlParameter('channelId');
            data.userId = getUrlParameter('userId');
            data.address = getUrlParameter('address')
            $.post('/login', data, function (data, status) {
                if (status === 'success') {
                    ShowAlertSuccess();
                } else {
                    ShowAlertDanger();
                }
            }).fail(function (err) {
                ShowAlertDanger();
            });
        }
        form.addClass('was-validated');
    });
    function ShowAlertSuccess() {
        $("#login-form-container").hide();
        $("#alert-msg").show();
        $("#alert-msg").addClass("alert-success");
        $("#alert-msg").html("User credentials saved successfully. You can continue to use the bot.");
    }
    function ShowAlertDanger() {
        $("#alert-msg").show();
        $("#login-form-container").show();
        $("#alert-msg").addClass("alert-danger");
        $("#alert-msg").html("Could not authenticate with provided username & password.");
    }
    function getUrlParameter(sParam) {
        var sPageURL = decodeURIComponent(window.location.search.substring(1)),
            sURLVariables = sPageURL.split('&'),
            sParameterName,
            i;

        for (i = 0; i < sURLVariables.length; i++) {
            sParameterName = sURLVariables[i].split('=');

            if (sParameterName[0] === sParam) {
                return sParameterName[1] === undefined ? true : sParameterName[1];
            }
        }
    };
});
