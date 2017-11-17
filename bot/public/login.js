
$(document).ready(function () {
    $("#alert-msg").hide();
    var form = $('#login-form');
    form.submit(function (event) {
        event.stopPropagation();
        event.preventDefault();
        if (form.valid()) {
            var data = {};
            data.channelId = $("#channelId").val();
            data.userId = $("#userId").val();
            data.username = $("#username").val();
            data.password = $("#password").val();
            data.addressString = $("#addressId").val();
            $.post('/login', JSON.stringify(data), function(data, status){
                if (status === 'success') {
                    ShowAlertSuccess();
                } else {
                    ShowAlertDanger();
                }
            }).fail (function(err) {
                ShowAlertDanger();
            });
        }
        form.addClass('was-validated');
    });
    function ShowAlertSuccess(){
        $("#login-form-container").hide();
        $("#alert-msg").show();
        $("#alert-msg").addClass("alert-success");
        $("#alert-msg").html("User credentials saved successfully. You can continue to use the bot.");
    }
    function ShowAlertDanger(){
        $("#alert-msg").show();
        $("#login-form-container").show();
        $("#alert-msg").addClass("alert-danger");
        $("#alert-msg").html("Could not authenticate with provided username & password.");
    }

});
