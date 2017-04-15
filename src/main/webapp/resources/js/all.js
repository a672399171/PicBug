// 登录
function login() {
    $.post('/login', {
        schoolNum: $('#schoolNum').val(),
        password: $('#password').val()
    }, function (data) {
        if (data.success) {
            window.location = '/';
        } else {
            alert(data.error);
        }
    }, 'JSON')
}

// 格式化json
function formatJson() {
    var obj = JSON.parse($('#text').val());
    var str = JSON.stringify(obj, undefined, 2);
    $('#text').val(str);
}

// 更新配置
function updateConfig() {
    $.post('/updateConfig', {
        json: $('#text').val(),
        key: $('#key').val()
    }, function (data) {
        if (data.success) {
            alert("success");
        } else {
            alert(data.error);
        }
    }, 'JSON');
}

// 创建xhr
function createXhr() {
    var xmlhttp;
    if (window.XMLHttpRequest) {// code for IE7+, Firefox, Chrome, Opera, Safari
        xmlhttp = new XMLHttpRequest();
    } else {// code for IE6, IE5
        xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
    }
    return xmlhttp;
}

// 清空
function clearText() {
    $('#text').val('');
}

// 复制到剪切板
function copy(obj) {
    $(obj).zclip({
        path: 'http://cdn.bootcss.com/zclip/1.1.2/ZeroClipboard.swf',
        copy: function () {
            return $('#text').val();
        }
    });
}

// 追加日志
function appendLog(data) {
    // console.log(data);
    var str = moment().format('HH:mm:ss') + '  ';
    if (data.success === true) {
        str += "success";
    } else if (data.success == false) {
        str += "error:" + data.error;
    } else {
        str += data.area + ',size:' + data.imgList.length;
    }
    str += '<br>';
    $('#log p').append(str);
}

function toTop() {
    $('body').animate({scrollTop: '0px'}, 800);
}
