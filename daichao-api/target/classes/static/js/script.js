
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
            // console.log(phone.length);
            // console.log(phone.length > 10);

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
                    // data:data,
                    // dataType: 'json',
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
                                    // location.href=android;
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
function tiaoxiazai(){
    var u = navigator.userAgent;

    if (u.indexOf('iPhone') > -1) {//苹果手机
        //苹果手机下载跳转地址
        window.location.href = ios;
        // $(".yindao").css("display","block")


    }else{
        //安卓手机下载地址
        window.location.href = android;
    }


}

//点击注册

function but(){

    var phone = $('#phone').val();
    var code = $('#code').val();
    var blackBox = _fmOpt.getinfo();
    var password = $('#password').val();


    // console.log(code,codeMun)
    // console.log(password)
    // if(code==0||!code==codeMun){
    //     alert("验证码错误")
    //     return
    // }

    if(!phone){
        mui.alert( "请输入手机号");
        return
    }
    if(!code){
        mui.alert( "请输入验证码");
        return
    }

    let queryConfig = {
        phone:phone,
        code:code,
        channelName:channelName,
        equipmentFlag:2,
    };

    var arr = setQueryConfig(queryConfig).split('&')
    let stringA =  setQueryConfig(getJsonData(arr.sort()))
    var natureStr = hex_md5(stringA + "&serverAPI="+ nature);


    // var ua = navigator.userAgent.toLowerCase();//获取判断用的对象
// if (ua.match(/MicroMessenger/i) == "micromessenger") {
//     //在微信中打开
//     // $('#codeBtn').text('weixin')
//     var sourceBrowser = "wx_browser "
// }else if(ua.match(/QQ/i) == "qq"){
//     //QQ中打开
//     // $('#codeBtn').text('qq')
//     var sourceBrowser= "qq_browser"
// }else{
//     var sourceBrowser = ""
// }
    

$.ajax({
    url:  urlcore + "/api/user/registerPhoneCodeV2?phone="+phone+"&code="+code+"&channelName="+channelName+"&equipmentFlag=2"+"&sign="+natureStr,
    type: "get",
    // dataType: 'json',
    contentType: "application/json;charset=utf-8",
    // xhrFields:{withCredentials:true},
    success:function(data){

        //data.msg =  "该手机号已经注册过，请直接登录";
        if (data.success == true) {
            // document.getElementById("modal").style.visibility="visible";
            var u = navigator.userAgent;
            if (u.indexOf('Android') > -1 || u.indexOf('Linux') > -1) {
                //安卓手机下载地址
                location.href=android;
            }else  if (u.indexOf('iPhone') > -1) {//苹果手机
                //苹果手机下载跳转地址
                window.location.href = ios;

            }else{
                //安卓手机下载地址
                // location.href=android;
                window.location.href = ios;
            }

        } else {
    // $("#password").attr("placeholder",data.msg);
            // 已经注册直接跳转

            // mui.alert( data.msg);


            if (data.msg == "该手机号已经注册过，请直接登录"){
                var u = navigator.userAgent;
                if (u.indexOf('Android') > -1 || u.indexOf('Linux') > -1) {
                    //安卓手机下载地址
                    location.href=android;
                }else  if (u.indexOf('iPhone') > -1) {//苹果手机
                    //苹果手机下载跳转地址
                    window.location.href = ios;

                }else{
                    //安卓手机下载地址
                    // location.href=android;
                    window.location.href = ios;
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



$(function(){
    ip(0)
    // if(is_weixn_qq()=="QQ"){
	// 	$.ajax({
	// 		type:"get",
	// 		url:urlcore+"/api/dictionaries/selectOneByType?name=qqChannelSwitch",
	// 		dataType: 'json',
	// 		contentType: "application/json;charset=utf-8",
	// 		xhrFields:{withCredentials:true},
	// 		success:function(msg){
	// 			if(msg.success==true){
	// 				if(msg.msg==0){
	// 					$("#registerBut").attr("disabled","disabled")
	// 					$("#registerBut").css("background","#cacaca")
	// 					alert("该渠道已关闭!")
	// 					ip(0)
	
						
	// 				}else{
	// 					_czc.puship(1)
	// 				}
	// 			}else{
    //                 ip(0)
    //             }
				
    //         },
    //         error:function(){
    //             ip(0)
    //         }
	// 	})
	// 	console.log(isQuDao("qqChannelSwitch"))
	//    }else if(is_weixn_qq()=="weixin"){
	// 	$.ajax({
	// 		type:"get",
	// 		url:urlcore+"/api/dictionaries/selectOneByType?name=wxChannelSwitch",
	// 		dataType: 'json',
	// 		contentType: "application/json;charset=utf-8",
	// 		xhrFields:{withCredentials:true},
	// 		success:function(msg){
	// 			if(msg.success==true){
	// 				if(msg.msg==0){
	// 					$("#registerBut").attr("disabled","disabled")
	// 					$("#registerBut").css("background","#cacaca")
	// 					alert("该渠道已关闭!")
	// 					ip(0)
						
	// 				}else{
	// 						   //ip地址
	// 					ip(1)
	// 				}
	// 			}else{
    //                 ip(0)
    //             }
				
    //         },
    //         error:function(){
    //             ip(0)
    //         }
	// 	})
	//    }else{
	// 		ip(1)
	//    }
         
      
    $("#tiaoxiazai").click(function(){
       
        var u = navigator.userAgent;


            if (u.indexOf('iPhone') > -1) {//苹果手机
                //苹果手机下载跳转地址
                window.location.href = ios;
                // $(".yindao").css("display","block")
            }else{
                //安卓手机下载地址
                window.location.href = android;
            }
    })
    $("#xiazaiBut").click(function(){
        // $(".yindao").css("display","none")
        var u = navigator.userAgent;
            if (u.indexOf('iPhone') > -1) {//苹果手机
                //苹果手机下载跳转地址
                window.location.href = ios;

            }else{
                //安卓手机下载地址
                window.location.href = android;
            }
    })

    // $(".codeBut").click(function(){
    //     alert("sss")
    //     var phone = $('#phone').val();
    //     // console.log(phone.length);
    //     // console.log(phone.length > 10);
    //     if (phone.length==11){ 
    //         var md5 = hex_md5("phone="+phone+"YDCODE9812#!1")
    //         var data = JSON.stringify({
    //             phone:phone,
    //             validateSign:md5
    //         })
    //         $.ajax({
    //             type:"POST",
    //             url:urlcore+"/api/user/getPhoneCodewebV2",
    //             data:data,
    //             dataType: 'json',
    //             contentType: "application/json;charset=utf-8",
    //             success:function(res){
    //                 // console.log(res)
    //                 if(res.success==true){
    //                     mui.alert("验证码发送成功");
    //                     code =res.msg;
    //                     var time
    //                     $(this).css("pointer-events","none")
    //                     // $(this).attr('disabled',"true")
    //                     var i = 60;
    //                     time= setInterval(function(){
    //                         if(i>0){
    //                             i--
    //                         $(".codeBut").html(i+"秒");
    //                         }else{
    //                             clearInterval(time);
    //                             $(".codeBut").text("发送验证码");
    //                             $(".codeBut").css("pointer-events","auto")
    //                         }
                            
    //                     },1000)
                        
    //                 }else{
    //                     mui.alert(res.msg);
    //                 }
    //             }
    //         })
    // 　  }else{
    //         mui.alert("请输入正确的手机号")
    //     }

    // })
    // $(".but").click(function(){
    //     var code = $("#code").val()
    //     var phone = $('#phone').val();
    //     var password = $("#password").val()
    //     // console.log(code,codeMun)
    //     // console.log(password)
    //     // if(code==0||!code==codeMun){
    //     //     alert("验证码错误")
    //     //     return
    //     // }
    //     if(!phone){
    //         mui.alert( "请输入手机号");
    //         return
    //     }
    //     if(!code){
    //         mui.alert( "请输入验证码");
    //         return
    //     }
    //     if(!password){
    //         mui.alert( "请输入密码");
    //         return
    //     }
    //     var phone = $('#phone').val();
	// var code = $('#code').val();
	// var blackBox = _fmOpt.getinfo(); 
	// var password = $('#password').val();
	// var ua = navigator.userAgent.toLowerCase();//获取判断用的对象
	// if (ua.match(/MicroMessenger/i) == "micromessenger") {
	// 	//在微信中打开
	// 	// $('#codeBtn').text('weixin')
	// 	var sourceBrowser = "wx_browser "
	// }else if(ua.match(/QQ/i) == "qq"){
	// 	//QQ中打开
	// 	// $('#codeBtn').text('qq')
	// 	var sourceBrowser= "qq_browser"
	// }else{
	// 	var sourceBrowser = ""
	// }
		

	// $.ajax({
	// 	url:  urlcore + "/api/user/registerPhoneCodeV2?phone="+phone+"&tokenId="+"&type=web"+"&code="+code+"&password="+password+"&channelName="+channelName+"&blackBox="+blackBox+"&sourceBrowser="+sourceBrowser,
	// 	type: "post",
	// 	dataType: 'json',
	// 	contentType: "application/json;charset=utf-8",
	// 	xhrFields:{withCredentials:true},
	// 	success:function(data){
	// 		if (data.success == true) {
	// 			// document.getElementById("modal").style.visibility="visible";
	// 			var u = navigator.userAgent;       
	// 			if (u.indexOf('iPhone') > -1) {//苹果手机
	// 				//苹果手机下载跳转地址
	// 				window.location.href = ios;
					
	// 			}else{
	// 				//安卓手机下载地址
	// 				location.href=android;
	// 			}
	// 		} else {
	// 	// $("#password").attr("placeholder",data.msg);
    //             mui.alert( data.msg);
	// 		}

	// 	},
	// 	error: function() {
	// 		mui.toast("error");
	// 	}

	// });

    //     // $.ajax({
    //     //     type:"get",
    //     //     url:url+"/api/user/registerPhoneCodeV2",
    //     //     headers: {
    //     //         proKey: "yddcApp"
    //     //     },
    //     //     data:{
    //     //         phone:phone,
    //     //         password:password,
    //     //         code:codeMun,
    //     //         equipmentFlag:2,
    //     //         channelName:channelName
    //     //     },
    //     //     success:function(res){
                
    //     //         if(res.code=="SUCCESS"){
    //     //             var u = navigator.userAgent; 
    //     //             if (u.indexOf('iPhone') > -1) {//苹果手机
    //     //                 //苹果手机下载跳转地址
    //     //                 window.location.href = xaizai;
    //     //                 // $(".yindao").css("display","block")
    //     //             }else{
    //     //                 //安卓手机下载地址
    //     //                     window.location.href = android;
    //     //             }
    //     //         }else{
    //     //             mui.alert( res.msg);
    //     //         }
    //     //     }
    //     // })


    // })
   

})