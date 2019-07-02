


//https://fir.im/wh6t?utm_source=fir&utm_medium=qr&openId=oGB0Cj-qTbkAhNqK4j9bCj-wgO3s
var urlcore = 'https://www.drjjdh.cn:8381';
// var urlcore = 'http://dcapi.hzyoudai.com';
//苹果手机下载地址
var ios = "https://fir.im/rk89"
// 安卓下载地址
var android = "https://fir.im/tfdq"

var urlhref = window.location.href;
var channelName = getname(urlhref).channelName;
var shellId = getname(urlhref).shellId;


function getname (url) {
    if(!/\?/.test(url)) {
        // 判断是否有?存在,即是判定键值对是否存在,
        // 在正则中?有特殊含义代表出现0或1次,所以用"\"进行转义,将其转为普通字符
        return null;
    }
    var index = url.indexOf("?") + 1;
    var str = url.substr(index);
    var arr = str.split("&");
    // var arr = url.split("?")[1].split("&");
    //上面的三句实现的效果可以并为一句
    var o = {};
    for(var i = 0; i < arr.length; i++) {
        // 之所以再次使用split方法是因为数组元素还是字符串,
        // 数组本身是没有split语法的
        var newarr = arr[i].split("=");
        o[newarr[0]] = newarr[1];
    }
    return o;
}
//图片验证码
function picCode(){
    $.ajax({
        url: url+'/api/user/create/pictureCode',
        type: "get",
        dataType: 'json',
        success:function(data){
            console.log(data)
        	$('#picCode').attr('src','data:image/jpeg;base64,'+ data.data.base64)
        },
        error:function() {
            /* Act on the event */
        }
    });
}

function is_weixn_qq(){
	var ua = navigator.userAgent.toLowerCase();
	if(ua.match(/MicroMessenger/i)=="micromessenger") {
		return "weixin";
	} else if (ua.match(/QQ/i) == "qq") {
		return "QQ";
		}
	return false;
	} 
		//ip地址
        function ip(e){
            if(shellId==undefined){
                shellId=0
            }
            var ip=returnCitySN["cip"];
            
            var url;
            if(channelName!=undefined){
                url=urlcore+"/api/userRecord/put?ip="+ip+"&channelName="+channelName+"&shellId="+shellId;
            }else {
                url=urlcore+"/api/userRecord/put?ip="+ip;
            }
            $.ajax({
                type: "GET",
                url:  url, // 这个就是不同于当前域的一个URL地址，这里单纯演示，所以同域
                success: function (data) {
                    //   console.log(data)
                    //   console.log(typeof(data))
                    var dad = JSON.parse(data)
                    if(dad.success==false){
                        // $("#pop").css("display","block")
                        $("#registerBut").attr("disabled","disabled")
                        $("#registerBut").css("background","#cacaca")
                        if(e==1){
                            alert("该渠道已关闭")
                        }
                        
                    }
                }, error: function(){
                
            }
            });
        }