$(function () {
    $.ajax({
        type: "GET",
        url: "/file-operator",
        success: function (res) {
            for (let i = 0; i < res.length; i++) {
                let filename = res[i].originalFilename;
                let md5 = res[i].md5;
                addfile(filename, md5);
            }
        },
        error: function () {
            alert("加载历史文件失败！");
        }
    })
});

$("#upload").click(function () {
    let files = $('#file').prop('files');
    if (files[0]) {
        var filename = files[0].name;
        var data = new FormData();
        data.append('file', files[0]);
        $.ajax({
            url: '/file-operator',
            type: 'POST',
            data: data,
            cache: false,
            processData: false,
            contentType: false,
            success(res) {
                addfile(filename, res);
            },
            error() {
                alert("请上传文件大小10MB以内的文件！");
            }
        })
    }
});

$("#file").change(function () {
    $("#upload").click();
});


$("#choose").click(function () {
    $("#file").click();
});

function addfile(filename, md5) {
    let item = "<div class='file'><a href='/file-operator/download/" + md5 + "'>" + filename + "</a></div>"
    $("#filebody").prepend(item);
}