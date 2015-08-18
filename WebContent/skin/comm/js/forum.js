var art;

function sp(id){
    $("#postsId").val(id);
	art = $.dialog({title:'屏蔽',content:$('#shieldPosts').html(),padding:0,lock: true});
}

function tt(id){
    $("#topthemeId").val(id);
	art = $.dialog({title:'置顶',content:$('#topTheme').html(),padding:0,lock: true});
}

function light(id){
    $("#lightthemeId").val(id);
	art = $.dialog({title:'高亮',content:$('#lightTheme').html(),padding:0,lock: true});
}

function lock(id){
    $("#lockthemeId").val(id);
	art = $.dialog({title:'锁定',content:$('#lockTheme').html(),padding:0,lock: true});
}

function sts(){
	if($("input[name='ids']:checked").length==0){
		alert("请选择要操作的主题!");
		return;
	}
	$("input[name='ids']:checked").each(function(i){
		$("#postsId").after("<input type='hidden' name='themeId' value='"+$(this).val()+"'/>");
	});
    $("#postsId").remove();
    $("#shieldForm").attr("action","shieldTheme.jsp");
	art = $.dialog({title:'屏蔽',content:$('#shieldPosts').html(),padding:0,lock: true});
}

function tts(){
	if($("input[name='ids']:checked").length==0){
		alert("请选择要操作的主题!");
		return;
	}
	$("input[name='ids']:checked").each(function(i){
		$("#topthemeId").after("<input type='hidden' name='themeId' value='"+$(this).val()+"'/>");
	});
    $("#topthemeId").remove();
	art = $.dialog({title:'置顶',content:$('#topTheme').html(),padding:0,lock: true});
}

function lights(){
	if($("input[name='ids']:checked").length==0){
		alert("请选择要操作的主题!");
		return;
	}
	$("input[name='ids']:checked").each(function(i){
		$("#lightthemeId").after("<input type='hidden' name='themeId' value='"+$(this).val()+"'/>");
	});
    $("#lightthemeId").remove();
	art = $.dialog({title:'高亮',content:$('#lightTheme').html(),padding:0,lock: true});
}

function locks(){
	if($("input[name='ids']:checked").length==0){
		alert("请选择要操作的主题!");
		return;
	}
	$("input[name='ids']:checked").each(function(i){
		$("#lockthemeId").after("<input type='hidden' name='themeId' value='"+$(this).val()+"'/>");
	});
    $("#lockthemeId").remove();
	art = $.dialog({title:'锁定',content:$('#lockTheme').html(),padding:0,lock: true});
}

function sq(){
    $("#ligcolor").blur();
}