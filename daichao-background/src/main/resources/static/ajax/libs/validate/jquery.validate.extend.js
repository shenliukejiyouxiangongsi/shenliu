/*this is basic form validation using for validation person's basic information author:Clara Guo data:2017/07/20*/
$(document).ready(function(){
	$.validator.setDefaults({
		submitHandler: function(form) {
			form.submit();
		}
	});
	//手机号码验证身份证正则合并：(^\d{15}$)|(^\d{17}([0-9]|X)$)
	jQuery.validator.addMethod("isPhone",function(value,element){
		var length = value.length;
		var phone=/^1[3|4|5|6|7|8|9][0-9]\d{8}$/;
		return this.optional(element)||(length == 11 && phone.test(value));
	},"请填写正确的11位手机号");
	//电话号码验证
	jQuery.validator.addMethod("isTel",function(value,element){
		var tel = /^(0\d{2,3}-)?\d{7,8}$/g;//区号3,4位,号码7,8位
		return this.optional(element) || (tel.test(value));
	},"请填写正确的座机号码");
	//姓名校验
	jQuery.validator.addMethod("isName",function(value,element){
		var name=/^[\u4e00-\u9fa5]{2,6}$/;
		return this.optional(element) || (name.test(value));
	},"姓名只能用汉字,长度2-4位");
	//校验用户名
	jQuery.validator.addMethod("isUserName",function(value,element){
		var userName=/^[a-zA-Z0-9]{2,13}$/;
		return this.optional(element) || (userName).test(value);
	},'请输入数字或者字母,不包含特殊字符');

	//校验身份证
	jQuery.validator.addMethod("isIdentity",function(value,element){
		var id= /^(\d{15}$|^\d{18}$|^\d{17}(\d|X))$/;
		return this.optional(element) || (id.test(value));
	},"请输入正确的15或18位身份证号,末尾为大写X");
	//校验出生日期
	jQuery.validator.addMethod("isBirth",function(value,element){
		var birth = /^(19|20)\d{2}-(1[0-2]|0?[1-9])-(0?[1-9]|[1-2][0-9]|3[0-1])$/;
		return this.optional(element) || (birth).test(value);
	},"出生日期格式示例2000-01-01");
	//校验新旧密码是否相同
	jQuery.validator.addMethod("isdiff",function(){
		var p1=$("#pwdOld").val();
		var p2=$("#pwdNew").val();
		if(p1==p2){
			return false;
		}else{
			return true;
		}
	});
	//校验新密码和确认密码是否相同
	jQuery.validator.addMethod("issame",function(){
		var p3=$("#confirm_password").val();
		var p4=$("#pwdNew").val();
		if(p3==p4){
			return true;
		}else{
			return false;
		}
	});

    //校验数字是否在0-1之间
	jQuery.validator.addMethod("isRate",function(value,element){
		var userName=/^(0(\.\d{1,2})?|1(\.0{1,2})?)$/;
		return this.optional(element) || (userName).test(value);
	},'输入数字必须为0-1内最多两位小数点的小数');

	//校验数字是否在0-100之间
	jQuery.validator.addMethod("isProportion",function(value,element){
		var userName=/^[0-9]$|^100$|^[1-9][0-9]$/;
		return this.optional(element) || (userName).test(value);
	},'输入数字必须为0-100内的整数');

	//校验是否是数字
	jQuery.validator.addMethod("isNumber",function(value,element){
		var id= /^\d{1,}$/;
		return this.optional(element) || (id.test(value));
	},"请输入数字");

	//校验是否是金额
	jQuery.validator.addMethod("isMoney",function(value,element){
		var id= /^\d{1,}$|^\d{1,}(\.\d{1,})?(w|W|万)$/;
		return this.optional(element) || (id.test(value));
	},"请输入正确金额");
	//校验产品最高金额不能小于最低金额
	jQuery.validator.addMethod("isMaxMoney",function(){
		var m1=$("#minMoney").val();
		var m2=$("#maxMoney").val();
		var reg=/^\d{1,}(\.\d{1,})?(w|W|万)$/;
		if(reg.test(m1)){
			m1=m1.slice(0,m1.length-1)*10000
		}
		if(reg.test(m2)){
			// console.log(m2.length+"=="+m2.slice(0,(m2.length-1)))
			m2=m2.slice(0,(m2.length-1))*10000
		}
		// console.log(parseInt(m1)+"="+parseInt(m2))
		if(parseInt(m1)<=parseInt(m2)){
			return true;
		}else{
			 return false;
		}
		},"最高金额不能小于最低金额");
    //校验最迟还款时间不小于最早还款时间
	jQuery.validator.addMethod("isMaxTime",function(){
		var t1=$("#outtimeBegin").val();
		var t2=$("#outtimeEnd").val();
		if(parseInt(t1)<=parseInt(t2)){
			return true;
		}else{
			 return false;
		}
		},"最迟还款时间不小于最早还款时间");

    //产品名称不超过10个字
	jQuery.validator.addMethod("isPname",function(value,element){
		var id= /^.{1,10}$/;
		return this.optional(element) || (id.test(value));
	},"产品名称不超过10个字");

    //产品描述不超过20个字
	jQuery.validator.addMethod("isDescribation",function(value,element){
		var id= /^.{1,20}$/;
		return this.optional(element) || (id.test(value));
	},"不超20字");

    //不超过30个字
	jQuery.validator.addMethod("isChar",function(value,element){
		var id= /^.{0,30}$/;
		return this.optional(element) || (id.test(value));
	},"不超30字");

    //产品重名校验
	jQuery.validator.addMethod("checkName",function(value,element){
       var pName=$("#pName").val();
       var beforePName=$("#beforePName").val();
       var isTrue=true;
       //alert(pName+"==="+beforePName)
       if(beforePName =="" || pName!= beforePName){
          $.ajax({
                //cache : true,
                type : "POST",
                url : prefix + "/checkName",
                data : {
                    "pName": pName,
                },
                async : false,
                success : function(data) {
                    if(data.code==0){
                        isTrue = false;
                    }else{
                        isTrue = true;
                    }
                   // console.log(data)
                }
            })


       }
       return isTrue;

	},"重名");

    //校验产品推荐添加
	jQuery.validator.addMethod("isRecommend",function(value,element){
       var pId=$("#pId option:selected").val();
       var rType=$("#rType option:selected").val();

       var isTrue=true;
       $.ajax({
           //cache : true,
           type : "POST",
            url : prefix + "/checkRecommend",
            data : {
                     "pId": pId,
                     "rType": rType,
                   },
            async : false,
            success : function(data) {
                         if(data.code==0){
                              isTrue = false;
                         }else{
                              isTrue = true;
                         }
                          // console.log(data)
                    }
            })



       return isTrue;

	},"该类型产品推荐已存在，请直接修改");

    //app审核添加验证版本不能重复
	jQuery.validator.addMethod("checkAppVersion",function(value,element){
       var marketId=$("input[name='marketId']").val();
       var appVersion = $("input[name='appVersion']").val();
       var appType = $("input[name='marketType']").val();

       var isTrue=true;
       //alert(pName+"==="+beforePName)
       $.ajax({
               //cache : true,
               type : "POST",
               url : prefix + "/checkAppVersion",
               data : {
                     "marketId": marketId,
                     "appVersion": appVersion,
                     "appType":appType
               },
               async : false,
               success : function(data) {
                    if(data.code==0){
                         isTrue = false;
                    }else{
                         isTrue = true;
                    }
                     //console.log(data)
               }
       })
       return isTrue;

	},"该渠道该类型的app版本已存在");

	//校验商品折扣金额不能大于商品金额
	jQuery.validator.addMethod("isDisCountMoney",function(){
		var m1=$("#mercPrice").val();
		var m2=$("#discountPrice").val();
		if(parseFloat(m1)>=parseFloat(m2)){
			return true;
		}else{
			return false;
		}
	},"商品折扣金额不能大于商品金额");

	//校验数字小数点后是否在两位以内
	jQuery.validator.addMethod("isPrice",function(value,element){
		var price=/^(([1-9][0-9]*)|(([0]\.\d{1,2}|[1-9][0-9]*\.\d{1,2})))$/;
		return this.optional(element) || (price).test(value);
	},'输入数字为整数或最多保留两位小数点');

	//校验渠道名不能为中文和特殊符号
	jQuery.validator.addMethod("isLoginName",function(value,element){
		var loginName=/^[A-Za-z0-9]+$/;
		return this.optional(element) || (loginName).test(value);
	},'输入内容不能包含中文和特殊字符');

	//校验基础信息表单
	$("#basicInfoForm").validate({
		errorElement:'span',
		errorClass:'help-block error-mes',
		rules:{
			name:{
				required:true,
				isName:true
			},
			sex:"required",
			birth:"required",
            mobile:{
				required:true,
				isPhone:true
			},
			email:{
				required:true,
				email:true
			}
		},
		messages:{
			name:{
				required:"请输入中文姓名",
				isName:"姓名只能为汉字"
			},
			sex:{
				required:"请输入性别"
			},
			birth:{
				required:"请输入出生年月"
			},
            mobile:{
				required:"请输入手机号",
				isPhone:"请填写正确的11位手机号"
			},
			email:{
				required:"请输入邮箱",
				email:"请填写正确的邮箱格式"
			}
		},
	
		errorPlacement:function(error,element){
			element.next().remove();
			element.closest('.gg-formGroup').append(error);
		},
		
		highlight:function(element){
			$(element).closest('.gg-formGroup').addClass('has-error has-feedback');
		},
		success:function(label){
			var el = label.closest('.gg-formGroup').find("input");
			el.next().remove();
			label.closest('.gg-formGroup').removeClass('has-error').addClass("has-feedback has-success");
			label.remove();
		},
		submitHandler:function(form){
			alert("保存成功!");
		}
	});


	//校验修改密码表单
	$("#modifyPwd").validate({
		onfocusout: function(element) { $(element).valid()},
		 debug:false, //表示校验通过后是否直接提交表单
		 onkeyup:false, //表示按键松开时候监听验证
		rules:{
			pwdOld:{
				required:true,
				minlength:6
			},
            pwdNew:{
			   required:true,
			   minlength:6,
			   isdiff:true,
			   //issame:true,
		   },
			confirm_password:{
			  required:true,
			  minlength:6,
			  issame:true,
			}
		  
		   },
		messages:{
			 	pwdOld : {
					 required:'必填',
					 minlength:$.validator.format('密码长度要大于6')
				},
            	pwdNew:{
				   required:'必填',
				   minlength:$.validator.format('密码长度要大于6'),
				   isdiff:'原密码与新密码不能重复',
				  
			   },
				confirm_password:{
				   required:'必填',
				   minlength:$.validator.format('密码长度要大于6'),
				   issame:'新密码要与确认新密码一致',
				}
		
		},
		errorElement:"mes",
		errorClass:"gg-star",
		errorPlacement: function(error, element) 
		{ 
			element.closest('.gg-formGroup').append(error);

		}
	});
});