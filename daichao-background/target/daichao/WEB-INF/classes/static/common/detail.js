$(function(){
    console.log(JSON.parse(data))
    var dad = JSON.parse(data).data
    console.log(dad)
    var da = JSON.parse(dad.riskItems).result_desc.ANTIFRAUD.risk_items;
    // console.log(da)
    // $("#reportNumber").text(dad.reportNumber)
    // $("#createTime").text(dad.validDate)
    $("#name").text(dad.idName);
    $("#phone").text(dad.phoneNo)
    $("#idNo").text(dad.idNo)
    $('#age').text(dad.age+"岁")

    $("#loanApplyNum").text(dad.loanApplyNum==null?"0":dad.loanApplyNum)
    $("#accExc").text(dad.accExc==null?"0":dad.accExc)
    // if(!dad.accExc==null||!dad.accExc==0){
    //     var div = '<div>'+
    //         '<p>异常还款机构</p>'+
    //         ' <p>'+dad.accExc+'</p>'+
    //         '</div>'
    //     $('#loanApplyNumDiv').after(div)
    // }
    $("#accSleep").text(dad.accSleep==null?"0":dad.accSleep)
    // if(!dad.accSleep==null||!dad.accSleep==0){
    //     var div = '<div>'+
    //         '<p>睡眠机构</p>'+
    //         ' <p>'+dad.accSleep+'</p>'+
    //         '</div>'
    //     $('#loanApplyNumDiv').after(div)
    // }
    $("#currentlyOverdue").text(dad.currentlyOverdue==null?"0":dad.currentlyOverdue)
    // if(!dad.currentlyOverdue==null||!dad.currentlyOverdue==0){
    //     var div = '<div>'+
    //         '<p>逾期机构数</p>'+
    //         ' <p>'+dad.currentlyOverdue+'</p>'+
    //         '</div>'
    //     $('#loanApplyNumDiv').after(div)
    // }
    $("#maxOverdueDays").text(dad.maxOverdueDays==null?"0":dad.maxOverdueDays)
    // if(!dad.maxOverdueDays==null||!dad.maxOverdueDays==0){
    //     var div = '<div>'+
    //         '<p>最长逾期天数</p>'+
    //         ' <p>'+dad.maxOverdueDays+'</p>'+
    //         '</div>'
    //     $('#loanApplyNumDiv').after(div)
    // }
    $("#maxOverdueAmt").text(dad.maxOverdueAmt==null?"0":dad.maxOverdueAmt)
    // if(!dad.maxOverdueAmt==null||!dad.maxOverdueAmt=="0"){
    //     console.log(dad.maxOverdueAmt)
    //     var div = '<div>'+
    //         '<p>最大逾期金额</p>'+
    //         ' <p>'+dad.maxOverdueAmt+'</p>'+
    //         '</div>'
    //     $('#loanApplyNumDiv').after(div)
    // }
    $("#latestOverdueTime").text(dad.latestOverdueTime==null?"0":dad.latestOverdueTime)
    // if(!dad.latestOverdueTime==null||!dad.latestOverdueTime==0){
    //     var div = '<div>'+
    //         '<p>最近逾期时间</p>'+
    //         ' <p>'+dad.latestOverdueTime+'</p>'+
    //         '</div>'
    //     $('#loanApplyNumDiv').after(div)
    // }
    $("#blackPlatNum").html(dad.blackPlatNum>0?"<span class='red'>"+dad.blackPlatNum+"</span>":"无")
    $.each(da,function(i,n){
        if(n.risk_name=="身份证命中法院失信名单"||n.risk_name=="身份证命中信贷逾期名单"
            ||n.risk_name=="身份证命中犯罪通缉名单"||n.risk_name=="身份证命中欠款公司法人代表名单"
            ||n.risk_name=="身份证对应人存在助学贷款欠费历史"||n.risk_name=="身份证命中车辆租赁违约名单"||n.risk_name=="身份证命中法院执行名单"||n.risk_name=="手机号命中欠款公司法人代表名单"
        ){
            var div ='<div class="row ">'+
                '<div class="col-sm-9 ">'+ n.risk_name+'</div>'+
                '<div class="col-sm-3 " style="color: red">命中</div>'+
                '</div>'
            $('#anti').append(div)
        }else if(n.risk_name=="身份证命中高风险关注名单"||n.risk_name=="身份证命中欠税公司法人代表名单"||n.risk_name=="身份证命中信贷逾期后还款名单"
            ||n.risk_name=="身份证命中法院结案名单"||n.risk_name=="身份证_姓名命中法院结案模糊名单"||n.risk_name=="身份证命中故意违章乘车名单"||n.risk_name=="身份证命中欠税名单"
            ||n.risk_name=="身份证_姓名命中信贷逾期模糊名单"||n.risk_name=="身份证_姓名命中法院执行模糊名单"||n.risk_name=="手机号命中高风险关注名单"
            ||n.risk_name=="身份证_姓名命中法院失信模糊名单"||n.risk_name=="手机号命中车辆租赁违约名单"||n.risk_name=="手机号命中信贷逾期后还款名单"||n.risk_name=="手机号命中虚假号码库"
            ||n.risk_name=="手机号命中通信小号库"||n.risk_name=="手机号命中诈骗骚扰库"||n.risk_name=="手机号疑似乱填"||n.risk_name=="手机号命中信贷逾期名单"||n.risk_name=="3个月内申请人身份证关联父亲手机数过多"||n.risk_name=="3个月内申请人在多个平台被放款_不包含本合作方"){
            var div ='<div class="row ">'+
                '<div class="col-sm-9 ">'+ n.risk_name+'</div>'+
                '<div class="col-sm-3 " style="color: orange">命中</div>'+
                '</div>'
            $('#anti').append(div)
        }else if(n.risk_name=="身份证命中中风险关注名单"||n.risk_name=="身份证命中低风险关注名单"||n.risk_name=="手机号命中中风险关注名单"
            ||n.risk_name=="手机号命中低风险关注名单"||n.risk_name=="身份证归属地位于高风险较为集中地区"
            ||n.risk_name=="1个月内同一个手机号码申请被拒次数大于等于4"
        ){
            var div ='<div class="row ">'+
                '<div class="col-sm-9 ">'+ n.risk_name+'</div>'+
                '<div class="col-sm-3 " style="color: red">命中</div>'+
                '</div>'
            $('#anti').append(div)
        }else if(n.risk_name=="申请人信息命中中风险关注名单"){
            if(n.risk_detail[0].description=="身份证命中中风险关注名单"){
                var div ='<div class="row ">'+
                    '<div class="col-sm-9 ">'+ n.risk_detail[0].description+'</div>'+
                    '<div class="col-sm-3 " >命中</div>'+
                    '</div>'
                $('#anti').append(div)
            }
            if(n.risk_detail[0].description=="手机命中低风险关注名单"){
                var div ='<div class="row ">'+
                    '<div class="col-sm-9 ">'+ n.risk_detail[0].description+'</div>'+
                    '<div class="col-sm-3 " >命中</div>'+
                    '</div>'
                $('#anti').append(div)
            }
            if(n.risk_detail[0].description=="手机命中中风险关注名单"){
                var div ='<div class="row ">'+
                    '<div class="col-sm-9 ">'+ n.risk_detail[0].description+'</div>'+
                    '<div class="col-sm-3" >命中</div>'+
                    '</div>'
                $('#anti').append(div)
            }

        }else if(n.risk_name=="3个月内身份证关联多个申请信息"||n.risk_name=="3个月内申请信息关联多个身份证"||n.risk_name=="7天内设备或身份证或手机号申请次数过多"){
            var  detail=n.risk_detail[0].frequency_detail_list;
            if(detail){
                var div ='<div class="row ">'+
                    '<div class="col-sm-9 ">'+ n.risk_name+'</div>'+
                    '<div class="col-sm-3 ">'+ n.risk_detail[0].frequency_detail_list[0].count+'</div>'+
                    '</div>'
                $('#anti').append(div)
            }

        }else if(n.risk_name=="3个月内申请人手机号作为联系人手机号出现的次数大于等于2"||n.risk_name=="3个月内申请人手机号作为联系人手机号出现的次数过多"||n.risk_name=="3个月内申请人身份证作为联系人身份证出现的次数大于等于2"){
            var  detail=n.risk_detail[0].frequency_detail_list;
            if(detail) {
                var div = '<div class="row ">' +
                    '<div class="col-sm-9 ">' + n.risk_name + '</div>' +
                    '<div class="col-sm-3 ">' + n.risk_detail[0].frequency_detail_list[0].count + '</div>' +
                    '</div>'
                $('#anti').append(div)
            }
        }else if(n.risk_name=="3个月内申请人在多个平台申请借款"||n.risk_name=="1个月内申请人在多个平台申请借款"||n.risk_name=="7天内申请人在多个平台申请借款"){
            var divs="<ul style='display: none' id='showHidden"+i+"'>" ;
            var riskData = n.risk_detail[0].platform_detail;
            $.each(riskData,function(i,n){
                divs+='<li><div  class="row " ><div class="col-sm-1"></div><div class="col-sm-8 ">'+n.industry_display_name+'</div><div class="col-sm-3 ">'+n.count+'</div></div></li>'
            })
            divs+="</ul>"
            var div ='<div class="row " id="doAction'+i+'"><a>'+
                '<div class="col-sm-9 " style="color: coral">'+ n.risk_name+'</div>'+
                '<div class="col-sm-3 " style="color: red">'+n.risk_detail[0].platform_count+'</div>'+
                // <span class="glyphicon glyphicon-menu-down"></span>
                '</a></div>'+divs
              //  '<div><div  class="collapseDiv">'+divs +'</div></div>'
            $('#anti').append(div);
             $( '#doAction'+i ).click(function() {
                 $( "#showHidden"+i ).toggle("slow");
               });
        }else if(n.risk_name=='1个月内设备或身份证或手机号申请次数过多'){
            var oneData = n.risk_detail[0].frequency_detail_list;
            $.each(oneData,function(i,n) {
                var div ='<div class="row ">'+
                    '<div class="col-sm-9 ">'+ n.note+'</div>'+
                    '<div class="col-sm-3 ">'+ n.count+'</div>'+
                    '</div>'
                $('#anti').append(div)
            })
        }else if(n.risk_name=='申请人信息命中低风险关注名单'){
            if(n.risk_detail[0].grey_list_details[0].risk_level=="中"){
                var div ='<div class="row ">'+
                    '<div class="col-sm-9 ">'+ n.risk_detail[0].description+'</div>'+
                    '<div class="col-sm-3 " style="color: #FF9900">命中</div>'+
                    '</div>'
            }else if(n.risk_detail[0].grey_list_details[0].risk_level=="高"){
                var div ='<div class="row ">'+
                    '<div class="col-sm-9 ">'+ n.risk_detail[0].description+'</div>'+
                    '<div class="col-sm-3 " style="color: #FF0000">命中</div>'+
                    '</div>'
            }else{
                var div ='<div class="row ">'+
                    '<div class="col-sm-9 ">'+ n.risk_detail[0].description+'</div>'+
                    '<div class="col-sm-3 ">命中</div>'+
                    '</div>'
            }
            $('#anti').append(div)
        }
    })
})