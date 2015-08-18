Admin={};
Admin.leftmenu = function(id) {
	var m = $('#' + id);
	m.children().each(function() {
		$(this).children('a').bind('click', function() {
			$(this).parent().addClass("curr");
			$(this).blur();
			var li = m.focusElement;
			if (li && li!=this) {
				$(li).parent().removeClass("curr");
			}
			m.focusElement=this;
		});
	});
};

Admin.topmenu = function(a,b){
	$("[id^='xxkc_"+a+"']").hide();
	$("[id^='xxk_"+a+"']").attr("class","undis");
	$("#xxkc_"+a+b).show();
	$("#xxk_"+a+b).attr("class","dis");
}

