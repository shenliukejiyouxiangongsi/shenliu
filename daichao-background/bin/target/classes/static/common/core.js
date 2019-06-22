/**
 * 查看贷前报告
 * @param evId
 */
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

/**
 * 查看认证信息
 * @param id
 */
function viewAuthInfo(id) {
    var url = ctx + "biz/authInfo/viewTabInfo/"+id;
    $.modal.openFull("认证信息", url, '800', '600');
}

/**
 * 还款记录
 * @param orderId
 */
function rePayRecord(orderId) {
    var url = ctx + "biz/repayRecord/"+orderId;
    $.modal.open("查看还款信息", url, '800', '600');
}

/**
 * 查看备注信息
 * @param orderId
 */
function remarkInfo(orderId) {
    var url = ctx + "biz/remarkInfo/"+orderId;
    $.modal.open("备注信息", url, '800', '600');
}

/**
 * 查看合同
 * @param id
 */
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

/**
 * 线下还款确认页面
 * @param orderId
 * @param payType
 */
function showNeedPayMoney(orderId,payType) {
    var url = ctx + "biz/orders/viewRepayOrderConfirm/"+orderId+"/"+payType;
    $.modal.open("线下还款确认", url, '400', '200');
}

/**
 * 减免滞纳金
 * @param orderId
 */
function reduceOrder(orderId){
    var url = ctx + "biz/orders/reduceOrder/"+orderId;
    $.modal.open("减免罚息", url, '400', '200');
}


function showUserOrders(userId){
    var url = ctx+"biz/orders/showUserOrders/"+userId;
    $.modal.open("减免罚息", url, '1000', '600');
}