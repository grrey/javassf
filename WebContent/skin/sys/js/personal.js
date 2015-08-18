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
    				url : base+"/admin/javassf/personalInfo/o_passwordUpdate.do",
    				dataType : "json",
    				data : {
    					userId : id,
    					password:$("#password").val()
    				},
    				success : function(data) {
    					if(data.status==1){
    						$.ligerDialog.success('密码修改成功!');
    					}else{
    						$.ligerDialog.error('该个人会员不存在，修改失败!');
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