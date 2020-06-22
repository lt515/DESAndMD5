$("#login").click(function () {
    $("#for-login").css("display", "");
    $("#login").addClass("action");
    $("#for-sigin").css("display", "none");
    $("#sigin").removeClass("action");
});

$("#sigin").click(function () {
    $("#for-sigin").css("display", "");
    $("#sigin").addClass("action");
    $("#for-login").css("display", "none");
    $("#login").removeClass("action");
});


$("#for-login").click(function () {
    console.log("hahah");
    var username = $("#login-un").val();
    var password = $("#login-pwd").val();
    $.ajax({
        type: "Get",
        url: "/user/login",
        data: {
            username: username,
            password: password
        },
        success(res) {

        }
    })
});

$("#for-sigin").click(function () {
    var username = $("#sigin-un").val();
    var password = $("#sigin-pwd").val();
    if (username && password) {
        $.ajax({
            type: "Get",
            url: "/user/register",
            data: {
                username: username,
                password: password
            },
            success(res) {

            }
        })
    }
});