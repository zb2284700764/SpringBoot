var AjaxError = function () {

    return {
        // 初始化各个函数及对象
        init: function () {

        },

        // 显示或者记录错误
        unauthorized: function(code, message) {
            if(code == "-998"){
                // window.location.href = url_403;
                alert(message);
            }
        }

    };

}();

jQuery(document).ready(function() {
    AjaxError.init();
});