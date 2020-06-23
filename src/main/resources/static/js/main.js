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
    var username = $("#login-un").val();
    var password = $("#login-pwd").val();
    console.log(username + " " + password);
    if (username && password) {
        $.ajax({
            type: "POST",
            url: "/user/login",
            data: {
                username: username,
                password: password
            },
            success: function (res) {
                if(res=="success"){
                    window.location.href="/file";
                }else{
                    alert("账号或密码错误！");
                    window.location.reload();
                }
            },
        })
    }
});

$("#for-sigin").click(function () {
    var username = $("#sigin-un").val();
    var password = $("#sigin-pwd").val();
    console.log(username + " " + password);
    if (username && password) {
        $.ajax({
            type: "POST",
            url: "/user/register",
            data: {
                username: username,
                password: password
            },

        })
    }
});