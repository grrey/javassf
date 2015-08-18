(function ($)
{	
    /*搜索框 收缩/展开*/
    $(".searchtitle .togglebtn").live('click',function(){ 
        if($(this).hasClass("togglebtn-down")) $(this).removeClass("togglebtn-down");
        else $(this).addClass("togglebtn-down");
        var searchbox = $(this).parent().nextAll("div.searchbox:first");
        searchbox.slideToggle('fast');
    });

})(jQuery);

function showMenu(objid,divid) {
	var cityObj = $("#"+objid);
	var cityOffset = $("#"+objid).offset();
	$("#"+divid).css({left:cityOffset.left + "px", top:cityOffset.top + cityObj.outerHeight() + "px"}).slideDown("fast");

	$("body").bind("mousedown", function(event){
		if (!(event.target.id == objid || event.target.id == divid || $(event.target).parents("#"+divid).length>0)) {
			$("#"+divid).fadeOut("fast");
			//$("body").unbind("mousedown", onBodyDown);
		}
	});
}

function showMenuset(objid,divid,lefts,tops) {
	var cityObj = $("#"+objid);
	var cityOffset = $("#"+objid).offset();
	$("#"+divid).css({left:cityOffset.left+lefts + "px", top:cityOffset.top+tops + cityObj.outerHeight() + "px"}).slideDown("fast");

	$("body").bind("mousedown", function(event){
		if (!(event.target.id == objid || event.target.id == divid || $(event.target).parents("#"+divid).length>0)) {
			$("#"+divid).fadeOut("fast");
			//$("body").unbind("mousedown", onBodyDown);
		}
	});
}