

function loansReport(evId) {
    $.ajax({
        url: ctx + "biz/authInfo/selectTdDataByOrderId/" + evId,
        type: "get",
        dataType: 'json',
        contentType: "application/json;charset=utf-8",
        async: false,
        success: function (data) {
            if (data.code == web_status.SUCCESS) {
                var content = '[' + data.data + ']';
                var jsonContent = JSON.parse(content);
                $.showReport(jsonContent);
            } else {
                $.modal.alertError(data.msg);
            }

        },
        error: function () {
            /* Act on the event */
            alert("error");
        }
    });
}

function viewAuthInfo(id) {
    var url = ctx + "biz/authInfo/viewTabInfo/"+id;
    $.modal.openFull("认证信息", url, '800', '600');
}
function rePayRecord(orderId) {
    var url = ctx + "biz/repayRecord/"+orderId;
    $.modal.open("查看还款信息", url, '800', '600');
}

function remarkInfo(orderId) {
    var url = ctx + "biz/remarkInfo/"+orderId;
    $.modal.open("备注信息", url, '800', '600');
}

function viewContract(id) {
    $.ajax({
        url: ctx + "biz/evaluation/viewContract/" + id,
        type: "get",
        dataType: 'json',
        contentType: "application/json;charset=utf-8",
        async: false,
        success: function (data) {
            if (data.code == web_status.SUCCESS) {
                var url = data.msg;
                $.modal.openFull("合同", url, '800', '600');
            } else {
                $.modal.alertError(data.msg);
            }
            $.modal.closeLoading();
        },
        error: function () {
            /* Act on the event */
            alert("error");
        }
    });
}

function showNeedPayMoney(orderId,payType) {
    var url = ctx + "biz/orders/viewRepayOrderConfirm/"+orderId+"/"+payType;
    $.modal.open("线下还款确认", url, '400', '200');
}
function reduceOrder(orderId){
    var url = ctx + "biz/orders/reduceOrder/"+orderId;
    $.modal.open("减免罚息", url, '400', '200');
}

// //查看还款记录权限
// var viewRepayRecordFlag =[[${@permission.hasPermi('biz:repayRecord:view')}]]  ;
// //查看催收备注权限标识
// var  viewRemarkFlag=[[${@permission.hasPermi('biz:remarkInfo:view')}]]  ;
// // 查看贷前报告权限标识
// var viewLoanReportFlag = [[${@permission.hasPermi('biz:selectTdDataByOrderId')}]];
// // 查看认证信息权限标识
// var viewAuthInfoFlag =[[${@permission.hasPermi('biz:viewTabInfo')}]];
//
// //查看合同权限标识
// var viewContractFlag =[[${@permission.hasPermi('biz:evaluation:view.Contract')}]]  ;
//
// //线下收款权限
// var outLineRepayOrderFlag = [[${@permission.hasPermi('biz:orders:viewRepayOrderConfirm')}]]  ;
//
// //减免滞纳金
// var reduceOrderFlag = [[${@permission.hasPermi('biz:orders:reduceOrder')}]]  ;