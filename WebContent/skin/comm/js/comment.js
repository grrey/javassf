var art;

function ajaxcomreply(id){
	$("#parentId").val(id);
	art = $.dialog({title:'回复评论:',content:$('#replycomment').html(),padding:0,lock: true});
}

function replyComment(id){
	if($("#repcontent").val()==""){
		alert("评论内容不能为空！");
		return;
	}
	$.post("comment.jsp",{
		docId : id,
		parentId:$("#parentId").val(),
		content : $("#repcontent").val()
	},function(data){
		if(data.status==0){
			ajaxref($("#parentId").val());
		}else if(data.status==-2){
			alert("文档不存在！");
		}else if(data.status==-3){
			alert("该文档禁止评论！");
		}else{
			alert("该文档需登录才可以评论！");
		}
	},"json");
	art.close();
}

function commentSubmit(id){
	if($("#comcontent").val()==""){
		alert("评论内容不能为空！");
		return;
	}
	$.post("comment.jsp",{
		docId : id,
		content : $("#comcontent").val()
	},function(data){
		if(data.status==0){
			alert("评论成功！");
			$("#comcontent").val("");
			ajaxhref(1);
		}else if(data.status==-2){
			alert("文档不存在！");
		}else if(data.status==-3){
			alert("该文档禁止评论！");
		}else{
			alert("该文档需登录才可以评论！");
		}
	},"json");
}

function ajaxref(parentId){
	$.get("commentListByPre.jsp",{
		parentId : parentId,
		pageNo : 1,
		count : 50
	},function(data){
	    $("#topicHtml"+parentId).html(data);
	});
}

function commentUp(id){
	$.get("commentUps.jsp",{
		commentId : id
	},function(data){
		var ups = parseInt($("#ups"+id).html())+1;
	    $("#zc"+id).html("已支持<span>["+ups+"]</span>");
	    $("#zc"+id).removeAttr("href");
	    $("#zc"+id).removeAttr("onclick");
	},"json");
}

function checkval(){
	if($("input[name='q']").val()=="请输入关键词"){
		 alert("请输入关键词!");
	   	 $("input[name='q']").focus();
	   	 return false;
	}
	return true;
}