'use strict';

function Tools(){
    this.timer = undefined;
    this.isPrd = location.host=="www.ccpourtoujours.com";
}

/*Tools.prototype.doRequest = function(options, header){
    var prefix;     //正式测试环境接口路径不同
    options.showLoading = options.showLoading || false;  //展示加载圈
    options.onceEle = options.onceEle || null;           //一些限制点击一次的按钮，比如保存按钮    
    options.url = options.url || '';
    options.params = options.params || {};
    options.hideToast = options.hideToast || false;
    options.allResp = options.allResp || false;
    header = header || {};
    
    if(/http/i.test(options.url)){
        prefix = '';
    } else {
        prefix = '/adminjson/';
    }
    var loginUrl = location.protocol+"//"+location.host+"/login.html";
    
    var loadingTimer = options.showLoading && window.loading && setTimeout(function(){
        window.loading.show();
    }, 150);
    if(options.onceEle){
        if(options.onceEle.attr('wait'))
            return;
        options.onceEle.attr('wait', true);
    }
    
    var dtd = $.Deferred(), self = this;;
    $.ajax({
        type: 'post',
        url: prefix + options.url,
        headers: header,
        dataType: 'json',
        data: JSON.stringify(options.params),
        timeout: 20000,
        success: function(data){
            if(data.errorCode==0){            //成功
                if(options.allResp)
                    dtd.resolve(data);
                else
                    dtd.resolve(data.body);

            } else if(data.errorCode==1){     //统一未登录错误标识
                if(!!window.clearInfo){                 //index.html
                    window.clearInfo();                    
                    location.href = loginUrl;
                } else if(!!window.top.clearInfo){      //frame
                    window.top.clearInfo();
                    window.top.location.href = loginUrl;
                }                                       //login
                dtd.reject("登录失败");
            } else {    //错误
                if(!options.hideToast){
                    self.toastFail(data.msg);
                }
                dtd.reject(data.msg);
            }
        },
        error: function(jqXHR, textStatus, errorThrown){
            if(!options.hideToast){                
                self.toastFail(JSON.stringify(textStatus));
            }
            dtd.reject(JSON.stringify(textStatus));
        },
        complete: function(){
            if(options.showLoading && window.loading){
                clearTimeout(loadingTimer);
                window.loading.hide();
            }
            if(options.onceEle){
                options.onceEle.removeAttr('wait');
            }
        }
    })
    return dtd.promise();
}*/

/*Tools.prototype.setCookie = function (cook, expiredays, path) {      //按天数设置, encodeURIComponent加密需要服务器端解密, expire和max-age都是设置过期时间，max-age更新一些
    var path = path || '';
    for(var i in cook){
        if(expiredays===undefined){     //未设置当作session cookie
            document.cookie = i+'='+encodeURIComponent(cook[i])+';'+path;
        } else {
            if(expiredays<0)
                document.cookie = i+'='+encodeURIComponent(cook[i])+"; max-age=-1;"+path;     //max-age负数，仅在窗口生效
            else if(expiredays==0)
                document.cookie = i+'='+encodeURIComponent(cook[i])+"; max-age=0;"+path;      //为0删除cookie
            else
                document.cookie = i+'='+encodeURIComponent(cook[i])+"; max-age="+expiredays*24*60*60+';'+path;       //按秒计算
        }
    }
}

Tools.prototype.getCookie = function(key) {
    var matchs = document.cookie.match(new RegExp('\\S*'+key+'=([^;]*)', 'i'));
    if(!!matchs && matchs.length>1){
        return decodeURIComponent(matchs[1]);
    }
    return false;
}*/

Tools.prototype.setStorage = function(key, value) {
    if(window.localStorage) {
        if (typeof value == 'object') {
            localStorage.setItem(key, JSON.stringify(value));
        } else {
            localStorage.setItem(key, value);
        }
    } else {
        window.key = value;
    }
}

Tools.prototype.getStorage = function(key) {
    if(window.localStorage) {
        var item = localStorage.getItem(key);
        if (item != null) {     //存在这个
            try {
                return JSON.parse(item);
            } catch (e) {
                return item;
            }
        }
        return false;
    } else {
        return window.key;
    }
}

Tools.prototype.params2Str = function(params){
    var str = [];
    for(var key in params){
        str.push(key, '=', encodeURIComponent(params[key]), '&');
    }
    return str.join('').slice(0, -1);
}

Tools.prototype.str2Params = function(str){
    var result = {};
    var paarr = str.split('&');
    for(var i=0,ilen=paarr.length; i<ilen; i++){
        var temp = paarr[i].split('=');
        result[temp[0]] = temp[1];
    }
    return result;
}
Tools.prototype.getCacheData = function(options){    //对于所有rp。缓存一些请求的数据避免重复请求
    var self = this;
    options.key = options.key || '';
    options.reqOption = options.reqOption || {};
    options.succCall = options.succCall || function(){};
    options.forceRefresh = options.forceRefresh || false;

    window[options.key] = window[options.key] || { data:null, succCallQueue:[] };
    var cacheData = window[options.key].data;

    if(!options.forceRefresh && cacheData) {
        options.succCall(cacheData)
        return;
    }
    if(window[options.key].succCallQueue.length > 0){       //有相同请求正在进行
        window[options.key].succCallQueue.push(options.succCall);
        return;
    }
    window[options.key].succCallQueue.push(options.succCall);           //存储到callback队列中
    $.when(self.doRequest(options.reqOption))
    .done(function(data){
        window[options.key].data = data;
        var sc;
        while(!!(sc = window[options.key].succCallQueue.pop()))
            sc(data);
    });
}
Tools.prototype.loadScript = function(options){
    options.src = options.src || undefined;
    if(!options.src) 
        return;
    options.nodefer = options.nodefer || false;     //默认加defer
    options.notimestamp = options.notimestamp || false;     //默认加时间戳
    var script = document.createElement('script');
    if(!options.nodefer) {
        script.defer = 'defer';
    }
    var ressrc = '';
    if(!options.notimestamp) {
        if(/\?/i.test(options.src))
            ressrc += options.src + '&t=' + Date.now();
        else {
            ressrc += options.src + '?t=' + Date.now();
        }
    }
    script.src = ressrc;
    document.head.appendChild(script);
}
/*/!**
 * toaster组件
 *!/
Tools.prototype.toastSuccess = function(text){
    var item = [];
    item.push('<div id="alert_success" style="position: fixed; z-index:10000; width: 340px; left: 50%; margin-left: -170px; top: 100px; opacity:0; -webkit-transform:translateY(40px); -webkit-transition:all 0.3s ease-out 0s;" class="alert alert-success alert-dismissible" role="alert">');
    item.push('  <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>');
    item.push('  <strong>', text, '</strong>');  
    item.push('</div>');
    $('body').append(item.join(''));
    setTimeout(function(){
        $("#alert_success").css({
            'opacity': 1,
            '-webkit-transform': 'translateY(0)'
        });
    }, 100);
    clearTimeout(this.timer);    
    this.timer = setTimeout(function(){
        $('.alert-success').remove();
    }, 2000);
}
Tools.prototype.toastFail = function(text){
    var item = [];
    item.push('<div id="alert_danger" style="position: fixed; z-index:10000; width: 340px; left: 50%; margin-left: -170px; top: 100px; opacity:0; -webkit-transform:translateY(40px); -webkit-transition:all 0.3s ease-out 0s;" class="alert alert-danger alert-dismissible" role="alert">');
    item.push('  <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>');
    item.push('  <strong>', text, '</strong>');  
    item.push('</div>');
    $('body').append(item.join(''));
    setTimeout(function(){
        $("#alert_danger").css({
            'opacity': 1,
            '-webkit-transform': 'translateY(0)'
        });
    }, 100);
    clearTimeout(this.timer);
    this.timer = setTimeout(function(){
        $('.alert-danger').remove();
    }, 2000);
}*/

/**
 *  毫秒转化为2017-06-08格式 
 */
Tools.prototype.dateString = function(time, hasZero){
    var d = new Date(time);
    var y = d.getFullYear();
    if(!!hasZero) {
        var m = d.getMonth()+1 < 10 ? '0'+(d.getMonth()+1) : (d.getMonth()+1);
        var dd = d.getDate() < 10 ? '0'+d.getDate() : d.getDate();
    } else {
        m = d.getMonth() + 1;
        dd = d.getDate();
    }
    return y+'-'+m+'-'+dd;
}

/**
 * 将2017-06-08格式字符串转化为年月日数字
 */
Tools.prototype.dateString2Num = function(str) {
    var ar = str.split('-');
    var s = "";
    ar.forEach(function(v, i) {
        s += Number(v) < 10 ? '0'+Number(v) : Number(v);
    });
    return Number(s);
};

/**
 * 加载圈
 */
function Loading(){
    this.tmp = '';
    this.tmp += '<div id="ld_w" class="ld_w">';
    this.tmp += '    <i class="ld_icon"></i>';
    this.tmp += '</div>';
}
Loading.prototype.show = function(){
    if($('#ld_w').length>0){
        return;
    }
    $('body').append(this.tmp);
}
Loading.prototype.hide = function(){
    $('#ld_w').remove();
}
