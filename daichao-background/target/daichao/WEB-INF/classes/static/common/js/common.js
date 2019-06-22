/**
 * 通用方法封装处理
 * Copyright (c) 2018 zhuliangjie
 */

$(function() {
	// select2复选框事件绑定
	if ($.fn.select2 !== undefined) {
		$("select.form-control:not(.noselect2)").each(function () {
			$(this).select2().on("change", function () {
				$(this).valid();
			})
		})
	}
	// checkbox 事件绑定
	if ($(".check-box").length > 0) {
	    $(".check-box").iCheck({
	    	checkboxClass: 'icheckbox-blue',
			radioClass: 'iradio-blue',
	    })
	}
	// radio 事件绑定
	if ($(".radio-box").length > 0) {
	    $(".radio-box").iCheck({
	    	checkboxClass: 'icheckbox-blue',
			radioClass: 'iradio-blue',
	    })
	}
	
	// laydate 时间控件绑定


	layui.use('laydate', function() {
		var laydate = layui.laydate;
		var startTime = laydate.render({
			elem: '#startTime',
			// max: $('#endTime').val(),
			max:'nowTime',//默认最大值为当前日期
			theme: 'molv',
			btns: ['now', 'confirm'],
			trigger: 'click',
			done:function(value,date){
				if(value==""){
					var date = new Date()
					startTime.config.max={
						year:date .getFullYear(),
						month:date .getMonth()+1,//关键
						date:date .getDate(),
					}
				} else {
					endTime.config.min={    	    		
						year:date.year,
						month:date.month-1,//关键
						date:date.date,
						};
					}
				}
						
		});
		var endTime = laydate.render({
			elem: '#endTime',
			// min: $('#startTime').val(),
			max:'nowTime',//默认最大值为当前日期
			theme: 'molv',
			btns: ['now', 'confirm'],
			trigger: 'click',
			done:function(value,date){
				if(value==""){	
					startTime.config.max={
						year:0,
						month:0,//关键
						date:0,
					}
				} else{
					startTime.config.max={
						year:date.year,
						month:date.month-1,//关键
						date:date.date,

						}
					}  
				}
				
		});
	});
	
	layui.use('laydate', function() {
		var laydate = layui.laydate;
		var payTimeStart = laydate.render({
			elem: '#payTimeStart',
			// max: $('#endTime').val(),
			max:'nowTime',//默认最大值为当前日期
			theme: 'molv',
			btns: ['now', 'confirm'],
			trigger: 'click',
			done:function(value,date){
				// if(value==""){
				// 	var date = new Date()
				// 	payTimeStart.config.max={
				// 		year:date .getFullYear(),
				// 		month:date .getMonth()+1,//关键
				// 		date:date .getDate(),
				// 	}
				// } else {
				// 	payTimeEnd.config.min={    	    		
				// 		year:date.year,
				// 		month:date.month-1,//关键
				// 		date:date.date,
				// 		};
				// 	}
				}
						
		});
		var payTimeEnd = laydate.render({
			elem: '#payTimeEnd',
			// min: $('#startTime').val(),
			max:'nowTime',//默认最大值为当前日期
			theme: 'molv',
			btns: ['now', 'confirm'],
			trigger: 'click',
			done:function(value,date){
				// if(value==""){	
				// 	payTimeStart.config.max={
				// 		year:0,
				// 		month:0,//关键
				// 		date:0,
				// 	}
				// } else{
				// 	payTimeStart.config.max={
				// 		year:date.year,
				// 		month:date.month-1,//关键
				// 		date:date.date,

				// 		}
				// 	}  
				}
				
		});
	});

	layui.use('laydate', function() {
		var laydate = layui.laydate;
		var needPayTimeStart = laydate.render({
			elem: '#needPayTimeStart',
			// max: $('#endTime').val(),
			// max:'nowTime',//默认最大值为当前日期
			theme: 'molv',
			btns: ['now', 'confirm'],
			trigger: 'click',
			done:function(value,date){
				// if(value==""){
				// 	var date = new Date()
				// 	needPayTimeStart.config.max={
				// 		year:date .getFullYear(),
				// 		month:date .getMonth()+1,//关键
				// 		date:date .getDate(),
				// 	}
				// } else {
				// 	needPayTimeEnd.config.min={    	    		
				// 		year:date.year,
				// 		month:date.month-1,//关键
				// 		date:date.date,
				// 		};
				// 	}
				}
						
		});
		var needPayTimeEnd = laydate.render({
			elem: '#needPayTimeEnd',
			// min: $('#startTime').val(),
			// max:'nowTime',//默认最大值为当前日期
			theme: 'molv',
			btns: ['now', 'confirm'],
			trigger: 'click',
			done:function(value,date){
				// if(value==""){	
				// 	needPayTimeStart.config.max={
				// 		year:0,
				// 		month:0,//关键
				// 		date:0,
				// 	}
				// } else{
				// 	needPayTimeStart.config.max={
				// 		year:date.year,
				// 		month:date.month-1,//关键
				// 		date:date.date,

				// 		}
				// 	}  
				}
				
		});
	});

	layui.use('laydate', function() {
		var laydate = layui.laydate;
		var repaymentTimeStart = laydate.render({
			elem: '#repaymentTimeStart',
			// max: $('#endTime').val(),
			max:'nowTime',//默认最大值为当前日期
			theme: 'molv',
			btns: ['now', 'confirm'],
			trigger: 'click',
			done:function(value,date){
				// if(value==""){
				// 	var date = new Date()
				// 	needPayTimeStart.config.max={
				// 		year:date .getFullYear(),
				// 		month:date .getMonth()+1,//关键
				// 		date:date .getDate(),
				// 	}
				// } else {
				// 	repaymentTimeEnd.config.min={    	    		
				// 		year:date.year,
				// 		month:date.month-1,//关键
				// 		date:date.date,
				// 		};
				// 	}
				}
						
		});
		var repaymentTimeEnd = laydate.render({
			elem: '#repaymentTimeEnd',
			// min: $('#startTime').val(),
			max:'nowTime',//默认最大值为当前日期
			theme: 'molv',
			btns: ['now', 'confirm'],
			trigger: 'click',
			done:function(value,date){
				// if(value==""){	
				// 	repaymentTimeStart.config.max={
				// 		year:0,
				// 		month:0,//关键
				// 		date:0,
				// 	}
				// } else{
				// 	repaymentTimeStart.config.max={
				// 		year:date.year,
				// 		month:date.month-1,//关键
				// 		date:date.date,

				// 		}
				// 	}  
				}
				
		});
	});


	// laydate time-input 时间控件绑定
	// if ($(".select-time").length >= 2) {
	// 	console.log($(".select-time").length)
	// 	console.log($(".select-time"))
	// 	console.log("执行2")
	// 	if($(".select-time").length==3&&$($(".select-time")[2]).hasClass('noTime')){
	// 		console.log('5454')
	// 		layui.use('laydate', function() {
	// 			var laydate = layui.laydate;
	// 			var startTime = laydate.render({
	// 				elem: '#needPayTimeStart',
	// 				// max: $('#endTime').val(),
	// 				// max:'nowTime',//默认最大值为当前日期
	// 				theme: 'molv',
	// 				btns: ['now', 'confirm'],
	// 				trigger: 'click',
	// 				done:function(value,date){
	// 					if(value==""){
	// 						var date = new Date()
	// 						startTime.config.max={
	// 							year:date .getFullYear(),
	// 							month:date .getMonth()+1,//关键
	// 							date:date .getDate(),
	// 						}
	// 					} else {
	// 						endTime.config.min={    	    		
	// 							year:date.year,
	// 							month:date.month-1,//关键
	// 							date:date.date,
	// 							};
	// 						}
	// 					}
								
	// 			});
	// 			var endTime = laydate.render({
	// 				elem: '#needPayTimeEnd',
	// 				// min: $('#startTime').val(),
	// 				// max:'nowTime',//默认最大值为当前日期
	// 				theme: 'molv',
	// 				btns: ['now', 'confirm'],
	// 				trigger: 'click',
	// 				done:function(value,date){
	// 					if(value==""){	
	// 						startTime.config.max={
	// 							year:0,
	// 							month:0,//关键
	// 							date:0,
	// 						}
	// 					} else{
	// 						startTime.config.max={
	// 							year:date.year,
	// 							month:date.month-1,//关键
	// 							date:date.date,
		
	// 							}
	// 						}  
	// 					}
						
	// 			});
	// 		});
	// 	}else{
	// 		layui.use('laydate', function() {
	// 			var laydate = layui.laydate;
	// 			var times = $(".time-input");
	// 			for (var i = 0; i < times.length; i++) {
	// 				var time = times[i];
	// 				laydate.render({
	// 					elem: time,
	// 					theme: 'molv',
	// 					btns: ['now', 'confirm'],
	// 					trigger: 'click',
	// 					max:'nowTime',//默认最大值为当前日期
	// 					done: function(value, date) {
	// 						// console.log("5")
	// 						// console.log(value, date)
	// 					}
						
	// 				});
	// 			}
	// 		});
	// 	}
	// 	layui.use('laydate', function() {
	// 		var laydate = layui.laydate;
	// 		var times = $(".time-input");
	// 		for (var i = 0; i < times.length; i++) {
	// 			var time = times[i];
	// 			laydate.render({
	// 				elem: time,
	// 				theme: 'molv',
	// 				btns: ['now', 'confirm'],
	// 				trigger: 'click',
	// 				max:'nowTime',//默认最大值为当前日期
	// 				done: function(value, date) {
	// 					// console.log("5")
	// 					// console.log(value, date)
	// 				}
					
	// 			});
	// 		}
	// 	});

	// }
	// tree 关键字搜索绑定
	if ($("#keyword").length > 0) {
		$("#keyword").bind("focus", function focusKey(e) {
		    if ($("#keyword").hasClass("empty")) {
		        $("#keyword").removeClass("empty");
		    }
		}).bind("blur", function blurKey(e) {
		    if ($("#keyword").val() === "") {
		        $("#keyword").addClass("empty");
		    }
		    $.tree.searchNode(e);
		}).bind("input propertychange", $.tree.searchNode);
	}
	// 复选框后按钮样式状态变更
	$("#bootstrap-table").on("check.bs.table uncheck.bs.table check-all.bs.table uncheck-all.bs.table", function () {
		var ids = $("#bootstrap-table").bootstrapTable("getSelections");
		$('#toolbar .btn-del').toggleClass('disabled', !ids.length);
		$('#toolbar .btn-edit').toggleClass('disabled', ids.length!=1);;
    });
	// tree表格树 展开/折叠
	var expandFlag = false;
	$("#expandAllBtn").click(function() {
	    if (expandFlag) {
	        $('#bootstrap-tree-table').bootstrapTreeTable('expandAll');
	    } else {
	        $('#bootstrap-tree-table').bootstrapTreeTable('collapseAll');
	    }
	    expandFlag = expandFlag ? false: true;
	})
});

/** 刷新选项卡 */
var refreshItem = function(){
    var topWindow = $(window.parent.document);
	var currentId = $('.page-tabs-content', topWindow).find('.active').attr('data-id');
	var target = $('.xiaodai_iframe[data-id="' + currentId + '"]', topWindow);
    var url = target.attr('src');
    target.attr('src', url).ready();
}

/** 创建选项卡 */
function createMenuItem(dataUrl, menuName) {
    dataIndex = $.common.random(1,100),
    flag = true;
    if (dataUrl == undefined || $.trim(dataUrl).length == 0) return false;
    var topWindow = $(window.parent.document);
    // 选项卡菜单已存在
    $('.menuTab', topWindow).each(function() {
        if ($(this).data('id') == dataUrl) {
            if (!$(this).hasClass('active')) {
                $(this).addClass('active').siblings('.menuTab').removeClass('active');
                $('.page-tabs-content').animate({ marginLeft: ""}, "fast");
                // 显示tab对应的内容区
                $('.mainContent .xiaodai_iframe', topWindow).each(function() {
                    if ($(this).data('id') == dataUrl) {
                        $(this).show().siblings('.xiaodai_iframe').hide();
                        return false;
                    }
                });
            }
            flag = false;
            return false;
        }
    });
    // 选项卡菜单不存在
    if (flag) {
        var str = '<a href="javascript:;" class="active menuTab" data-id="' + dataUrl + '">' + menuName + ' <i class="fa fa-times-circle"></i></a>';
        $('.menuTab', topWindow).removeClass('active');

        // 添加选项卡对应的iframe
        var str1 = '<iframe class="xiaodai_iframe" name="iframe' + dataIndex + '" width="100%" height="100%" src="' + dataUrl + '" frameborder="0" data-id="' + dataUrl + '" seamless></iframe>';
        $('.mainContent', topWindow).find('iframe.xiaodai_iframe').hide().parents('.mainContent').append(str1);

        // 添加选项卡
        $('.menuTabs .page-tabs-content', topWindow).append(str);
    }
    return false;
}

//日志打印封装处理
var log = {
    log: function (msg) {
    	console.log(msg);
    },
    info: function(msg) {
    	console.info(msg);
    },
    warn: function(msg) {
    	console.warn(msg);
    },
    error: function(msg) {
    	console.error(msg);
    }
};

/** 设置全局ajax处理 */
$.ajaxSetup({
    complete: function(XMLHttpRequest, textStatus) {
        if (textStatus == 'timeout') {
            $.modal.alertWarning("服务器超时，请稍后再试！");
            $.modal.closeLoading();
        } else if (textStatus == "parsererror") {
            $.modal.alertWarning("服务器错误，请联系管理员！");
            $.modal.closeLoading();
        }
    }
});
