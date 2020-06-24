$("#upload").click(function () {
    var files = $('#file').prop('files');
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
    item = "<div class='file'><a href='/file-operator/download/" + md5 + "'>" + filename + "</a></div>"
    $("#filebody").append(item);
}