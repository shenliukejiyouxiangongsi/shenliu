

var userId = $("#userId").val();
var orderId = $("#orderId").val();
var evId = $("#id").val();
init();
function init(){


    // var phone = getvl('phone');
    // var userName = getvl('userName');
    // var id = getvl("id")
    // var orderNo =getvl('orderNo');
    //
    // var taskId ="";
    // var phoneYys="";
    // var orderId = getvl('orderId');
    // var userId = getvl('userId');
    //加载订单信息
    loadOrders();
    //加载个人信息
    loadMyEssay();


    //加载数据魔盒
    loadShujumohe();
    //查询联系人信息
    loadUserContact(1);

    selectBlack();

    loadXinyanLeida();

    //通话记录
    loadLocalUserContact();

    loadUserLocalMessage();
}

function isMathc(m){
    if(m==0){
        return '匹配';
    }
    return '不匹配';
}
function getBelongArea(belongArea){
    if(belongArea==null){
        return '未知归属地';
    }
    return belongArea;
}
/**
 * 查询并加载个人信息
 * @returns
 */
function loadMyEssay() {
    $.ajax({
        url: ctx + "biz/authInfo/selectUserAuthInfo?id=" + userId,
        type: "get",
        dataType: 'json',
        contentType: "application/json;charset=utf-8",
        success: function(data) {
            if(data.code == web_status.SUCCESS) {
                var da = data.data.userAuthDetail;
                //个人信息
                $("#userName").html(da.userName);
                $("#identityNum").html(da.identityNum);
                $("#age").html(GetAge(da.identityNum));
                $("#marriage").html(da.marriage);
                $("#diploma").html(da.diploma);
                $("#email").html(da.email);
                $("#qq").html(da.qq);
                $("#weixin").html(da.weixin);
                $("#confidence").html(da.confidence);
                $("#zhiye").html(da.zhiye);
                $("#shebao").html(da.shebao);
                $("#zhiwu").html(da.zhiwu);

                $("#bankPhone").html(da.bankPhone);

                $("#address").html(da.address);
                $("#channelName").html(data.data.user.channelName);
                $("#idcardAddress").html(da.idcardAddress);


                //身份认证信息
                $('#bankName').html(da.bankName);
                $('#bankNum').html(da.bankNum);

                //$("#contactsNameFirst").html(da.contactsNameFirst);
                //$("#contactsRelationFirst").html(da.contactsRelationFirst);
                $("#contactsPhoneFirst").html(da.contactsPhoneFirst + '('+getBelongArea(data.data.phoneMatches[0].belongArea)+')');

                $("#phoneMatchFirst").html(isMathc(data.data.phoneMatches[0].phoneMatch));
                $("#callMatchFirst").html(isMathc(data.data.phoneMatches[0].callMatch));



                //$("#contactsNameSecond").html(da.contactsNameSecond);
               // $("#contactsRelationSecond").html(da.contactsRelationSecond );
                $("#contactsPhoneSecond").html(da.contactsPhoneSecond  + '('+getBelongArea(data.data.phoneMatches[1].belongArea)+')');
                $("#phoneMatchSecond").html(isMathc(data.data.phoneMatches[1].phoneMatch));
                $("#callMatchSecond").html(isMathc(data.data.phoneMatches[1].callMatch));

                $("#idFront").attr("src",da.identityFront);
                $("#idBack").attr("src",da.identityBack);
                $("#faceUrl").attr("src",da.faceUrl);
            }

        },
        error: function() {
            /* Act on the event */
        }
    });

}


function showIdentityInfo(){
    layer.open({
        type: 1 //Page层类型
        ,area: ['1000px', '700px']
        ,title: '身份证信息'
        ,shade: 0.6 //遮罩透明度
        ,maxmin: false //允许全屏最小化
        ,anim: 1 //0-6的动画形式，-1不开启
        ,content: $("#identityInfo")
    });

}


/**
 * 查询并加载数据魔盒的信息
 * @returns
 */

function loadShujumohe(){
    $.ajax({
        url: ctx + "biz/authInfo/selectYYSByUserId?id=" + userId,
        type: "GET",
        dataType: 'json',
        contentType: "application/json;charset=utf-8",
        success: function(data) {
            if(data.code == web_status.SUCCESS) {
                var contact = data.data.contact;
                loadContact(contact);
                loadSource(data.data.source);
                loadContactDetail(data.data.contactDetial);
                //loadContactBlack(data.data.black);
                $("#contactCount").html(data.data.contactCount);
                $("#sixMonthCallCount").html(data.data.sixMonthCallCount +'(占比 '+(data.data.sixMonthCallCount/data.data.contactCount)*100+'%)');
                $("#vaildCallCount").html(data.data.vaildCallCount);
                $("#vaildContactCount").html(data.data.vaildContactCount);

                    $("#yysUserMobile").html(data.data.mobileInfo.userMobile);
                    $("#yysMobileNetTime").html(data.data.mobileInfo.mobileNetTime+"("+data.data.mobileInfo.mobileNetAge+")");
                    $("#yysUserName").html(data.data.mobileInfo.realName);
            } else {
                alert(data.msg);
            }
        },
        error: function() {

        }
    });
}

function selectBlack(){
    $.ajax({
        url: ctx + "biz/authInfo/selectBlack?userId=" + userId,
        type: "GET",
        dataType: 'json',
        contentType: "application/json;charset=utf-8",
        success: function(data) {
            if(data.code == web_status.SUCCESS) {
                var xinyanBlack = data.data.authXinyanBlack;
                if(xinyanBlack && xinyanBlack!=null){
                    $("#currentlyOverdue").html(xinyanBlack.currentlyOverdue);
                    $("#maxOverdueDays").html(xinyanBlack.maxOverdueDays);
                    $("#maxOverdueAmt").html(xinyanBlack.maxOverdueAmt);
                    $("#latestOverdueTime").html(xinyanBlack.latestOverdueTime);
                    $("#currentlyPerformance").html(xinyanBlack.currentlyPerformance);
                    $("#accExc").html(xinyanBlack.accExc);
                    $("#accSleep").html(xinyanBlack.accSleep);
                }
            } else {
                alert(data.msg);
            }
        },
        error: function() {

        }
    });
}




/**
 * 联系人次数最多的20人
 * @param contactDetails
 * @returns
 */
function loadContactDetail(contactDetails){
    if(contactDetails==null){
        return null;
    }
    $("#contactDetailTop").html("");
    var thisList = "";
    for(var i =0;i<contactDetails.length;i++){
        var n = contactDetails[i];
        thisList +=
            '<tr class="footable-even" style="display: table-row;">' +
            '<td class="footable-visible">' + parseInt(i+1) + '</td>' +
            '<td class="footable-visible">' + n.contactNumber + '</td>' +
            '<td class="footable-visible">' + mobileConvert(n.mobileContactname)+ '</td>' +
            '<td class="footable-visible">' + areaConvert(n.contactArea) + '</td>' +
            '<td class="footable-visible">' + n.callCount6month + '</td>' +
            '<td class="footable-visible">' + n.callCountActive6month + '次('+parseInt(n.callTimeActive6month / n.callCountActive6month)+'秒)</td>' +
            '<td class="footable-visible">' + n.callCountPassive6month + '次('+parseInt(n.callTimePassive6month / n.callCountPassive6month)+'秒)</td>' +
            '<td class="footable-visible">' + parseFloat(n.callTimeActive6month/60).toFixed(2) + '</td>' +
            '<td class="footable-visible">' + parseFloat(n.callTimePassive6month/60).toFixed(2) + '</td>' +
            '</tr>';
    }

    $('#contactDetailTop').append(thisList);
}

function mobileConvert(mobile){
    if(mobile==null || mobile == undefined){
        return "<span style='font-weight:bold'>无数据</span>";
    }
    var str = mobile.charAt((mobile.length-1));
    if(str=='/' ){

        return mobile.substr(0,mobile.length-1) ;
    }
    return mobile;

}


function areaConvert(area){
    if(area==null || area == undefined){
        return "未知归属地";
    }
    return area;
}

function loadSource(s){
    if(s==null){
        return;
    }
    $("#baseInfoScore").html(s.baseInfoScore);
    $("#riskContactInfoScore").html(s.riskContactInfoScore);
    $("#billInfoScore").html(s.callInfoScore);
    $("#totalScore").html(s.totalScore);
}

/**
 * 联系人风险分析
 * @param contact
 * @returns
 */
function loadContact(c){
    if(c==null){
        return ;
    }
    $("#callCount1month").html(c.callCount1month);
    $("#callCount3month").html(c.callCount3month);
    $("#callCount6month").html(c.callCount6month);

    $("#callCountActive3month").html(c.callCountActive3month);
    $("#callCountActive6month").html(c.callCountActive6month);

    $("#callCountPassive3month").html(c.callCountPassive3month);
    $("#callCountPassive6month").html(c.callCountPassive6month);

    $("#callTime1month").html(c.callTime1month);
    $("#callTime3month").html(c.callTime3month);
    $("#callTime6month").html(c.callTime6month);

    $("#contactCount1month").html(c.contactCount1month);
    $("#contactCount3month").html(c.contactCount3month);
    $("#contactCount6month").html(c.contactCount6month);

    $("#msgCount1month").html(c.msgCount1month);
    $("#msgCount3month").html(c.msgCount3month);
    $("#msgCount6month").html(c.msgCount6month);
}




/**
 * 查询并加载订单信息
 * @returns
 */
function loadOrders() {
    $.ajax({
        url: ctx + "biz/authInfo/selectOrderById?id=" + orderId,
        type: "GET",
        dataType: 'json',
        contentType: "application/json;charset=utf-8",
        success: function(data) {
            if(data.code == web_status.SUCCESS) {
                var o = data.data;
                if(o==null){
                    return ;
                }
                $("#orderNo").html(o.orderNo);
                $("#totalMoney").html(o.evaluationPrice);
                $("#needPayMoney").html(o.evaluationPrice);
                $("#jobName").html(o.phoneType);
                $("#tdScore").html(o.tdScore);
                $("#xinyan").html(o.xinyan);
                $("#loanAddress").html(o.loanAddress);
                var aliRiskTag = '';
                if(o.aliRiskTag=='md030'){
                    aliRiskTag= redFont('高逾期风险');
                }else if(o.aliRiskTag=='md020'){
                    aliRiskTag= redFont('中逾期风险');
                }else if(o.aliRiskTag=='md010'){
                    aliRiskTag= '低逾期风险';
                }else if(o.aliRiskTag=='md000'){
                    aliRiskTag= '暂未检测到逾期风险';
                }else{
                    aliRiskTag = '阿里风险名单查询异常';
                }

                $("#aliRiskTag").html(aliRiskTag);
                if(o.appType!=null){
                    if(o.appType==1){
                        $("#deviceType").html("IOS");
                    }else{
                        $("#deviceType").html("Andriod");
                    }
                }
            } else {
                alert(data.msg);
            }
        },
        error: function() {

        }
    });
}

function loadXinyanLeida() {
    $.ajax({
        url: ctx + "biz/authInfo/selectXinyanLdByOrderId?orderId=" + orderId,
        type: "GET",
        dataType: 'json',
        contentType: "application/json;charset=utf-8",
        success: function(data) {
            if(data.code == web_status.SUCCESS) {
                var o = data.data;
                if(o==null){
                    return ;
                }
                $("#loansCount").html(o.loansCount);
                $("#loansSettleCount").html(o.loansSettleCount);

                $("#loansOverdueCount").html(redFont(o.loansOverdueCount));
                $("#loansLatestTime").html(o.loansLatestTime);
                $("#latestOneMonthSuc").html(o.latestOneMonthSuc);
                $("#latestOneMonthFail").html(redFont(o.latestOneMonthFail));
                $("#historySucFee").html(o.historySucFee);
                $("#historyFailFee").html(redFont(o.historyFailFee));
                $("#latestOneMonth").html(o.latestOneMonth);
                $("#latestThreeMonth").html(o.latestThreeMonth);
                $("#latestSixMonth").html(o.latestSixMonth);

            } else {
                alert(data.msg);
            }
        },
        error: function() {

        }
    });
}

function redFont(val){
    if(val==null){
        return val;
    }
    return '<span style="color:red;">'+val+'</span>';
}


/**
 * 加载本地通话记录
 */
function loadLocalContactList() {

}

/**
 * 加载本地短信内容
 */
function loadLocalSmsList() {

}


function loadUserContact(phoneNumber){

    $('#user-contact-bootstrap-table').bootstrapTable({
        url: ctx + "biz/authInfo/selectUserContactByUserId?userId=" + userId+"&phoneNumber"+phoneNumber,                                   // 请求后台的URL（*）
        contentType: "application/x-www-form-urlencoded",   // 编码类型
        method: 'get',                                     // 请求方式（*）
        cache: false,                                       // 是否使用缓存
        striped: true,                                  // 是否显示行间隔色
        pagination: true,   // 是否显示分页（*）
        sortStable: true,                                   // 设置为 true 将获得稳定的排序
        pageNumber: 1,                                      // 初始化加载第一页，默认第一页
        pageSize: 10,                                       // 每页的记录行数（*）
        pageList: [10, 25, 50],                             // 可供选择的每页的行数（*）
        escape: false,                                    // 转义HTML字符串
        iconSize: 'outline',                                // 图标大小：undefined默认的按钮尺寸 xs超小按钮sm小按钮lg大按钮
        toolbar: '#toolbar',                                // 指定工作栏
        queryParams: $.table.queryParams,                       // 传递参数（*）
        sidePagination: "server",                          // 启用服务端分页
        columns: [
            {
                title: '序号',
                field: '',
                align: 'center',
                formatter: function (value, row, index) {
                    var options = $("#userLocalCallList").bootstrapTable('getOptions');
                    return options.pageSize * (options.pageNumber - 1) + index + 1;
                }
            },
            {
                field: 'name',
                title: '通讯录姓名'
            }, {
                field: 'phone',
                title: '通讯录号码'
            }, {
                field: 'contactArea',
                title: '归属地'
            }, {
                field: 'callCount',
                title: '6个月内通话次数'
            }]
    });

    // $.ajax({
    //     url: ctx + "biz/authInfo/selectUserContactByUserId?userId=" + userId+"&pageNo="+pageNo+"&pageSize=20&phoneNumber="+phoneNumber,
    //     type: "GET",
    //     dataType: 'json',
    //     contentType: "application/json;charset=utf-8",
    //     success: function(data) {
    //         if(data.code == web_status.SUCCESS) {
    //             var o = data.data.list;
    //             if(o!=null){
    //                 $('#thislist').html("");
    //             }
    //             var thislist="";
    //             $.each(data.data.list, function(i, n) {
    //                 thislist =thislist+ '<tr class="footable-even" style="display: table-row;">' +
    //                     '<td class="footable-visible">' +parseInt(i+1) +'</td>' +
    //                     '<td class="footable-visible">' + n.name + '</td>' +
    //                     '<td class="footable-visible">' + n.phone + '</td>' +
    //                     '<td class="footable-visible">' + n.contactArea + '</td>' +
    //                     '<td class="footable-visible">' + n.callCount+ '</td>' +
    //                     '</tr>';
    //             });
    //             $('#thislist').html(thislist);
    //             $("#pager").pager({
    //                 pagenumber: data.data.pageNum,
    //                 pagecount: data.data.pages,
    //                 totalcount: data.data.total,
    //                 buttonClickCallback: UserContactPageClick
    //             });
    //         } else {
    //             alert(data.msg);
    //         }
    //     },
    //     error: function() {
    //
    //     }
    // });
}


// UserContactPageClick = function(pageclickednumber) {
//
//
//
//     loadUserContact(pageclickednumber);
// }



function loadLocalUserContact(pageNo,phoneNumber){

    $('#userLocalCallList').bootstrapTable({
        url:  ctx + "biz/authInfo/selectUserLocalCallByUserId?userId=" + userId,                                   // 请求后台的URL（*）
        contentType: "application/x-www-form-urlencoded",   // 编码类型
        method: 'get',                                     // 请求方式（*）
        cache: false,                                       // 是否使用缓存
        striped: true,                                  // 是否显示行间隔色
        pagination: true,   // 是否显示分页（*）
        sortStable: true,                                   // 设置为 true 将获得稳定的排序
        pageNumber: 1,                                      // 初始化加载第一页，默认第一页
        pageSize: 10,                                       // 每页的记录行数（*）
        pageList: [10, 25, 50],                             // 可供选择的每页的行数（*）
        escape: false,                                    // 转义HTML字符串
        iconSize: 'outline',                                // 图标大小：undefined默认的按钮尺寸 xs超小按钮sm小按钮lg大按钮
        toolbar: '#toolbar',                                // 指定工作栏
        queryParams: $.table.queryParams,                       // 传递参数（*）
        sidePagination: "server",                          // 启用服务端分页
        columns: [
            {
                title: '序号',
                field: '',
                align: 'center',
                formatter: function (value, row, index) {
                    var options = $("#userLocalCallList").bootstrapTable('getOptions');
                    return options.pageSize * (options.pageNumber - 1) + index + 1;
                }
             },
            {
                field: 'callType',
                title: '类型',
                formatter: function (value, row, index) {
                    if(value==1){
                        return "来电";
                    }else{
                        return "去电";
                    }
                }
            }, {
                field: 'callName',
                title: '姓名'
            }, {
                field: 'callTime',
                title: '时间'
            }, {
                field: 'callPhone',
                title: '联系号码'
            }, {
                field: 'callDuration',
                title: '通话时长'
            }]
    });
}



function loadUserLocalMessage(){

    $('#userLocalCallMessage').bootstrapTable({
        url:  ctx + "biz/authInfo/selectUserLocalMessageByUserId?userId=" + userId,                                   // 请求后台的URL（*）
        contentType: "application/x-www-form-urlencoded",   // 编码类型
        method: 'get',                                     // 请求方式（*）
        cache: false,                                       // 是否使用缓存
        striped: true,                                  // 是否显示行间隔色
        pagination: true,   // 是否显示分页（*）
        pageNumber: 1,                                      // 初始化加载第一页，默认第一页
        pageSize: 10,                                       // 每页的记录行数（*）
        pageList: [10, 25, 50],                             // 可供选择的每页的行数（*）
        escape: false,                                    // 转义HTML字符串
        iconSize: 'outline',                                // 图标大小：undefined默认的按钮尺寸 xs超小按钮sm小按钮lg大按钮
        toolbar: '#toolbar',                                // 指定工作栏
        sidePagination: "server",                          // 启用服务端分页
        queryParams: $.table.queryParams,                       // 传递参数（*）
        columns: [
            {
                title: '序号',
                field: '',
                width :50,
                align: 'center',
                formatter: function (value, row, index) {
                    var options = $("#userLocalCallMessage").bootstrapTable('getOptions');
                    return options.pageSize * (options.pageNumber - 1) + index + 1;
                }
            },
            {
                field: 'mesType',
                width :50,
                title: '类型',formatter: function (value, row, index) {
                    if(value==1){
                        return "发送";
                    }else{
                        return "接收";
                    }
                }
            }, {
                field: 'mesContent',
                title: '短信类容',
                width :1000,
            }, {
                field: 'pushTime',
                title: '发送时间',
                width :150,
            }]
    });
}








/**
 * 查看同盾运营商信息
 * @returns
 */
function showTongdunYysInfo(){
    $.ajax({
        url: ctx + "biz/authInfo/getTongdunInfo?userId=" + userId,
        type: "GET",
        dataType: 'json',
        contentType: "application/json;charset=utf-8",
        success: function(data) {
            if(data.code == web_status.SUCCESS) {
                var url = 'https://report.shujumohe.com/report/';
                if(data.data.taskId!=null){
                    url = url+data.data.taskId;
                    if(data.data.token!=null){
                        url = url+"/"+data.data.token;
                    }
                    // var tempwindow=window.open('_blank');
                    // console.log(url);
                    // tempwindow.location=url;
                    $.modal.openFull("运营商报告", url, '800', '600');
                }

            } else{
                alert('未获取到同盾的运营商报告，请从同盾后台查看');
            }
        },
        error: function() {

        }
    });

}

function tbMsg() {

    var appId = "858a17fe-3572-4a9d-aba9-b2d1ab4cc088";
    var tid = "";
    $.ajax({
        url: ctx + "biz/authInfo/getTid?userId=" + userId ,
        type: "get",
        dataType: 'json',
        contentType: "application/json;charset=utf-8",
        success: function(data) {
            if(data.data!=null && data.data.taskId){
                tid = data.data.taskId;
                var new_window = window.open("","","width=1000,height=800");
                new_window.location = "http://fc.tcredit.com/reports/dsReportPage?tid="+tid+"&appId="+appId;
            }else{
                alert("用户淘宝未认证");
            }

        },
        error: function() {

            alert("error");
        }
    });

}

function searchList() {

    var params = $("#user-contact-bootstrap-table").bootstrapTable('getOptions');
    params.queryParams = function(params) {
        var search = {};

        search.phoneNumber = $("#phoneNumber").val();
        search.pageSize = params.limit;
        search.pageNum = params.offset / params.limit + 1;
        search.searchValue = params.search;
        search.orderByColumn = params.sort;
        search.isAsc = params.order;
        return search;
    }
    $("#user-contact-bootstrap-table").bootstrapTable('refresh', params);

}



function GetAge(identityCard) {
    var len = (identityCard + "").length;
    if (len == 0) {
        return 0;
    } else {
        if ((len != 15) && (len != 18))//身份证号码只能为15位或18位其它不合法
        {
            return 0;
        }
    }
    var strBirthday = "";
    if (len == 18)//处理18位的身份证号码从号码中得到生日和性别代码
    {
        strBirthday = identityCard.substr(6, 4) + "/" + identityCard.substr(10, 2) + "/" + identityCard.substr(12, 2);
    }
    if (len == 15) {
        strBirthday = "19" + identityCard.substr(6, 2) + "/" + identityCard.substr(8, 2) + "/" + identityCard.substr(10, 2);
    }
    //时间字符串里，必须是“/”
    var birthDate = new Date(strBirthday);
    var nowDateTime = new Date();
    var age = nowDateTime.getFullYear() - birthDate.getFullYear();
    //再考虑月、天的因素;.getMonth()获取的是从0开始的，这里进行比较，不需要加1
    if (nowDateTime.getMonth() < birthDate.getMonth() || (nowDateTime.getMonth() == birthDate.getMonth() && nowDateTime.getDate() < birthDate.getDate())) {
        age--;
    }
    return age;
}


