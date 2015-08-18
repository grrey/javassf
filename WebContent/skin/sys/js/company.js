function addFairs(id,name,base){
	$("#cname").html(name);
	$("#fairsId").val("");
	$("#showcase").val("");
    $.ligerDialog.open({
    	title:"企业加入招聘会",
    	width:300,
    	target: $("#target1"),
    	buttons: [ { 
    		text: '确定', 
    		onclick: function (item, dialog) {
    			if($("#fairsId").val()!=null&&$("#showcase").val()!=""){
    			$.ajax({
    				type : "POST",
    				url : base+"/admin/javassf/companyfairs/o_ajaxSave.do",
    				dataType : "json",
    				data : {
    					companyId : id,
    					fairsId:$("#fairsId").val(),
    					showcase:$("#showcase").val(),
    					showTime:$("input[name='showTime']:checked").val()
    				},
    				success : function(data) {
    					if(data.status==1){
    						$.ligerDialog.success('添加成功!');
    					}else if(data.status==-1){
    						$.ligerDialog.error('该企业会员已经加入该招聘会中！');
    					}else{
    						$.ligerDialog.error('没有选择企业会员或者招聘会，添加失败!');
    					}
    					dialog.hide();
    				}
    			});
    			}else{
    				if($("#fairsId").val()==null){
    					$.ligerDialog.error('请选择招聘会');
    				}else{
    				    $.ligerDialog.error('请填写展台号');
    				}
    			}
    			}
    	}]
    });
}

function addVip(id,name,base){
	$("#cname1").html(name);
	$("#typeId").val("");
    $.ligerDialog.open({
    	title:"设置VIP会员",
    	width:300,
    	target: $("#target2"),
    	buttons: [ { 
    		text: '确定', 
    		onclick: function (item, dialog) {
    			$.ajax({
    				type : "POST",
    				url : base+"/admin/javassf/companyinfo/o_ajaxAdd.do",
    				dataType : "json",
    				data : {
    					companyId : id,
    					typeId:$("#typeId").val()
    				},
    				success : function(data) {
    					if(data.status==1){
    						$.ligerDialog.success('设置成功!');
    					}else{
    						$.ligerDialog.error('没有选择企业会员，设置失败!');
    					}
    					dialog.hide();
    				}
    			});
    			}
    	}]
    });
}

function updatePassword(id,name,base){
	$("#cname2").html(name);
    $.ligerDialog.open({
    	title:"修改密码",
    	width:300,
    	target: $("#target3"),
    	buttons: [ { 
    		text: '确定', 
    		onclick: function (item, dialog) {
    			if($("#password").val()!=""){
    			if($("#password").val()==$("#repassword").val()){
    			$.ajax({
    				type : "POST",
    				url : base+"/admin/javassf/companyinfo/o_passwordUpdate.do",
    				dataType : "json",
    				data : {
    					userId : id,
    					password:$("#password").val()
    				},
    				success : function(data) {
    					if(data.status==1){
    						$.ligerDialog.success('密码修改成功!');
    					}else{
    						$.ligerDialog.error('该企业会员不存在，修改失败!');
    					}
    					dialog.hide();
    				}
    			});
    			}else{
        			$.ligerDialog.error('2次密码输入不一致!');
        		}
    			}else{
    				$.ligerDialog.error('密码不能为空!');
    			}
    		}
    	}]
    });
}