var art;
var art1;

function insertArt() {
	art = $.dialog({
		title : '插入新闻:',
		content : $('#insertart').html(),
		padding : "0px 0px"
	});
}

function resultArt() {
	if ($("#artpage").attr("checked")) {
		var h = "[@DocPage callLevel='1' ";
		if ($("#channelId").val() != "") {
			if ($("#channelId").val() != "0") {
				h = h + "cId='" + $("#channelId").val() + "' ";
			} else {
				h = h + "cId=channel.id ";
			}
		}
		var mId = "";
		$("input[name='artmodel']:checked").each(function(i) {
			if (mId != "") {
				mId = mId + "," + $(this).val();
			} else {
				mId = $(this).val();
			}
		});
		if (mId != "") {
			h = h + "mId='" + mId + "' ";
		}
		var tId = "";
		$("input[name='arttype']:checked").each(function(i) {
			if (tId != "") {
				tId = tId + "," + $(this).val();
			} else {
				tId = $(this).val();
			}
		});
		if (tId != "") {
			h = h + "tId='" + tId + "' ";
		}
		if ($("#recommend").attr("checked")) {
			h = h + "recommend='1' ";
		}
		if ($("#artcount").val() != "") {
			h = h + "count='" + $("#artcount").val() + "' ";
		}
		if ($("#showdir").html() != "") {
			h = h + "direction='" + $("#direction").val() + "' ";
		}
		h = h + "orderBy='" + $("#artorder").val() + "' ";
		if ($("#showstyle").val() != "") {
			h = h + "tplName='" + $("#showstyle").val() + "'/]";
		} else {
			h = h + "][#list list as doc][/#list]";
			h = h
					+ "<div class=\"pagebar\">[@Page pageType='1'/]</div>[/@DocPage]";
		}
		insertAtCursor('tplcontent', h);
	} else {
		var h = "[@DocList callLevel='1' ";
		if ($("#channelId").val() != "") {
			if ($("#channelId").val() != "0") {
				h = h + "cId='" + $("#channelId").val() + "' ";
			} else {
				h = h + "cId=channel.id ";
			}
		}
		var mId = "";
		$("input[name='artmodel']:checked").each(function(i) {
			if (mId != "") {
				mId = mId + "," + $(this).val();
			} else {
				mId = $(this).val();
			}
		});
		if (mId != "") {
			h = h + "mId='" + mId + "' ";
		}
		var tId = "";
		$("input[name='arttype']:checked").each(function(i) {
			if (tId != "") {
				tId = tId + "," + $(this).val();
			} else {
				tId = $(this).val();
			}
		});
		if (tId != "") {
			h = h + "tId='" + tId + "' ";
		}
		if ($("#recommend").attr("checked")) {
			h = h + "recommend='1' ";
		}
		if ($("#artcount").val() != "") {
			h = h + "count='" + $("#artcount").val() + "' ";
		}
		if ($("#showdir").html() != "") {
			h = h + "direction='" + $("#direction").val() + "' ";
		}
		h = h + "orderBy='" + $("#artorder").val() + "' ";
		if ($("#showstyle").val() != "") {
			h = h + "tplName='" + $("#showstyle").val() + "'/]";
		} else {
			h = h + "][#list list as doc][/#list][/@DocList]";
		}
		insertAtCursor('tplcontent', h);
	}
	art.close();
}

function showChannel() {
	art1 = $.dialog({
		title : '选择栏目:',
		content : $('#menuContento').html(),
		padding : "0px 0px"
	});
	$.fn.zTree.init($("#channelTree"), setting);
}

function changestyle(){
	if($("#showstyle").val()!="doc_title_dir"&&$("#showstyle").val()!="doc_img_dir"){
		$("#showdir").html("");
	}else{
		$("#showdir").html($("#divdirection").html());
	}
}