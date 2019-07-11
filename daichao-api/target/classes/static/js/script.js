
    _fmOpt = {
        partner: "zhyc",
        appName: "zhyc_web",
        token: "zhyc" + "-" + new Date().getTime() + "-"+ Math.random().toString(16).substr(2),
        fmb: true,
        getinfo: function(){
            return "e3Y6ICIyLjUuMCIsIG9zOiAid2ViIiwgczogMTk5LCBlOiAianMgbm90IGRvd25sb2FkIn0=";
        }};
    var cimg = new Image(1,1);
    cimg.onload = function() {
        _fmOpt.imgLoaded = true;
    };
    cimg.src = "https://fp.fraudmetrix.cn/fp/clear.png?partnerCode=zhyc&appName=zhyc_web&tokenId=" + _fmOpt.token;
    var fm = document.createElement('script'); fm.type = 'text/javascript'; fm.async = true;
    fm.src = ('https:' == document.location.protocol ? 'https://' : 'http://') + 'static.fraudmetrix.cn/v2/fm.js?ver=0.1&t=' + (new Date().getTime()/3600000).toFixed(0);
    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(fm, s);


    function codeBut(){
        var phone = $('#phone').val();
        if (phone.length==11){
                var md5 = hex_md5(phone+"YDCODE9812#!1")
                var queryConfig = {
                    phone:phone,
                    validateSign:md5,
                };

            var arr = setQueryConfig(queryConfig).split('&')
            let stringA =  setQueryConfig(getJsonData(arr.sort()))
            var natureStr = hex_md5(stringA + "&serverAPI="+ nature);

                $.ajax({
                    type:"get",
                    url:urlcore+"/api/user/getRegisterCode?phone="+phone+"&validateSign="+md5 +"&sign="+natureStr ,
                    contentType: "application/json;charset=utf-8",
                    success:function(res){
                        // console.log(res)
                        if(res.success==true){
                            mui.alert(res.msg);
                            code =res.msg;
                            var time
                            $(this).css("pointer-events","none")
                            // $(this).attr('disabled',"true")
                            var i = 60;
                            time= setInterval(function(){
                                if(i>0){
                                    i--
                                $(".codeBut").html(i+"秒");
                                }else{
                                    clearInterval(time);
                                    $(".codeBut").text("发送验证码");
                                    $(".codeBut").css("pointer-events","auto")
                                }
                                
                            },1000)
                            
                        }else{
                            if (res.msg == "该手机号已经注册过，请直接登录"){
                                var u = navigator.userAgent;
                                if (u.indexOf('Android') > -1 || u.indexOf('Linux') > -1) {
                                    //安卓手机下载地址
                                    location.href=android;
                                }else  if (u.indexOf('iPhone') > -1) {//苹果手机
                                    //苹果手机下载跳转地址
                                    window.location.href = ios;

                                }else{
                                    //安卓手机下载地址
                                    window.location.href = ios;
                                }
                            }else {
                                mui.alert( res.msg);
                            }
                            // mui.alert(res.msg);
                        }
                    }
                })
        　  }else{
                mui.alert("请输入正确的手机号")
            }
    }



//点击注册

function but(){

    var phone = $('#phone').val();
    var code = $('#code').val();
    var blackBox = _fmOpt.getinfo();
    var password = $('#password').val();

    if(!phone){
        mui.alert( "请输入手机号");
        return
    }
    if(!code){
        mui.alert( "请输入验证码");
        return
    }

    var u = navigator.userAgent, app = navigator.appVersion;
    //ios 终端
    var isiOSAll =  !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/);
    var isiOS = u.indexOf('iPhone') > -1 || u.indexOf('iPad') > -1 ;
    var isAndroid = u.indexOf("Android") > -1 || u.indexOf("Linux") > -1;

    var equipmentFlag;

    // 安卓手机
    if (isAndroid) {
        equipmentFlag = 1;
    }else  if(u.indexOf('Mac') > -1){
        equipmentFlag = 2;
    }else if(u.indexOf('iPhone') > -1 || u.indexOf('iPad') > -1){//苹果手机
        equipmentFlag = 0;
    }else{
        equipmentFlag = 2;
    }

    let queryConfig = {
        phone:phone,
        code:code,
        channelName:channelName,
        equipmentFlag:equipmentFlag,
    };

    var arr = setQueryConfig(queryConfig).split('&')
    let stringA =  setQueryConfig(getJsonData(arr.sort()))
    var natureStr = hex_md5(stringA + "&serverAPI="+ nature);
    

$.ajax({
    url:  urlcore + "/api/user/registerPhoneCodeV2?phone="+phone+"&code="+code+"&channelName="+channelName+"&equipmentFlag="+equipmentFlag+"&sign="+natureStr,
    type: "get",
    contentType: "application/json;charset=utf-8",
    success:function(data){

        // iPhone anz 
        //data.msg =  "该手机号已经注册过，请直接登录";
        var u = navigator.userAgent, app = navigator.appVersion;
        var isiOS = u.indexOf('iPhone') > -1 || u.indexOf('iPad') > -1;
        var isAndroid = u.indexOf("Android") > -1 || u.indexOf("Linux") > -1;
    
        if (data.success == true) {
            // document.getElementById("modal").style.visibility="visible";
          
            if (isAndroid) {
                //安卓手机下载地址
                location.href=android;

            }else  if (isiOS) {//苹果手机
                //苹果手机下载跳转地址
                window.location.href = ios;  

            }else {
                window.location.href = android;
            }

        } else {
            // 已经注册直接跳转
            if (data.msg == "该手机号已经注册过，请直接登录"){
                
                if (isAndroid) {
                    //安卓手机下载地址
                    location.href = android;

                }else  if (isiOS) {//苹果手机
                    //苹果手机下载跳转地址
                    window.location.href = ios;
                }else {
                    window.location.href = android;
                }
            }else {
                mui.alert( data.msg);
            }
        }

    },
    error: function() {
        mui.toast("error");
    }

});
}
