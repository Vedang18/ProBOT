
$(document).ready(function () {
    $("#alert-msg").hide();
    disableSpinner();
    var form = $('#login-form');
    $.validator.messages.required = ''; // Disables 'This field is required.' on validation
    if (getUrlParameter('userName')) {
        $("#page-title").html("Update Password")
        $("#username").attr("disabled", "");
        $("#username").val(getUrlParameter('userName'));
    }
    form.submit(function (event) {
        event.stopPropagation();
        event.preventDefault();
        if (form.valid()) {
            enableSpinner();
            var data = {};
            data.username = $("#username").val();
            data.password = $("#password").val();
            data.channelId = getUrlParameter('channelId');
            data.userId = getUrlParameter('userId');
            data.address = getUrlParameter('address')
            $.post('/login', data, function (data, status) {
                disableSpinner();
                if (status === 'success') {
                    ShowAlertSuccess();
                } else {
                    ShowAlertDanger();
                }
            }).fail(function (err) {
                disableSpinner();
                ShowAlertDanger();
            });
        }
        form.addClass('was-validated');
    });
    function ShowAlertSuccess() {
        $("#login-form-container").hide();
        $("#alert-msg").show();
        $("#alert-msg").removeClass("alert-danger");
        $("#alert-msg").addClass("alert-success");
        $("#alert-msg").html("User credentials saved successfully. You can continue to use the bot.");
    }
    function ShowAlertDanger() {
        $("#alert-msg").show();
        $("#login-form-container").show();
        $("#alert-msg").removeClass("alert-success");
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

    function enableSpinner() {
        $("#submit").attr('disabled', 'disabled');
        $("#spinner").show();
    }
    function disableSpinner() {
        $("#submit").removeAttr('disabled');
        $("#spinner").hide();
    }
});
