$("#for-login").onclick = function () {
    var username = $("#login-un").val();
    var password = $("#login-pwd").val();
    $.ajax({
        type: "Get",
        url: "",
        data: {
            username: username,
            password: password
        },
        success(res) {

        }
    })
}

$("#for-sigin").onclick = function () {
    var username = $("#sigin-un").val();
    var password = $("#sigin-pwd").val();
    if (username && password) {
        $.ajax({
            type: "Get",
            url: "",
            data: {
                username: username,
                password: password
            },
            success(res) {

            }
        })
    }
}