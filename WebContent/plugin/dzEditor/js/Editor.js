Editor = function() {
	this.init();
}

Editor.prototype.init = function() {
	//菜单名称
	Editor.menuName=['_fontname','_fontsize','_forecolor','_Pnnk','_email','_image'];
	Editor.menu=[];
	Editor.popup=[];
	Editor.editor = $('#_editor_textarea');
	Editor.txtarea = Editor.editor[0];
	Editor.file = $('#_editor_file');
	Editor.fileContainer = $('#_file_container');
	Editor.fileSelector = $('#_file_selecter');
	//控制栏（按钮）
	$('#_editor_controls a').each(function(){
		$(this).addClass('_controls_normal');
		$(this).mouseover(function(){
			$(this).toggleClass('_controls_hover',true);
			$(this).toggleClass('_controls_normal',false);
		});
		$(this).mouseout(function(){
			$(this).toggleClass('_controls_hover',false)
			$(this).toggleClass('_controls_normal',true);
		});
	});
	//弹出菜单
	for(var i=0;i<Editor.menuName.length;i++) {
		var n = Editor.menuName[i];
		var Lname= '#'+n+'_pop';
		var m = $('#'+n+'_menu');
		var p = $(Lname);
		Editor.menu[i] = m;
		Editor.popup[i] = p;
		var offset = m.offset();
		var top = offset.top+m.outerHeight()+'px';
		var left = offset.left+'px';
		p.css({top:top,left:left});
		m.attr('popup',Lname);
		m.click(function(){
			var poLname = $(this).attr('popup');
			$(poLname).toggle();
			Editor.closeMenu($(poLname));
		});		
		$(Lname+' ._input_cancel').each(function(){
			$(this).attr('popup',Lname).click(function() {
				var poLname = $(this).attr('popup');
				$(poLname).hide();	
			})
		});
	}
	//字体
	$('#_fontname_pop li').each(function(){
		var j = $(this);
		var font = $.trim(j.text());
		j.css('font-family',font);
		j.click(function(){
			Editor.bbstyle('[font='+font+']','[/font]');
			$('#_fontname_pop').hide();
		});
	});
	//字体大小
	$('#_fontsize_pop li').each(function(){
		var j = $(this);
		var size = j.attr('fontsize');
		j.css('font-size',size);
		j.click(function(){
			Editor.bbstyle('[size='+size+']','[/size]');
			$('#_fontsize_pop').hide();
		});
	});
	//字体颜色
	$('#_forecolor_pop div').each(function(){
		var j = $(this);
		var p = j.parent();
		var col = p.attr('col');
		j.css('background-color',col);
		j.addClass('_forecolor_normal');
		j.mouseover(function(){
			j.toggleClass('_forecolor_hover',true);
			j.toggleClass('_forecolor_normal',false);
		});
		j.mouseout(function(){
			j.toggleClass('_forecolor_hover',false);
			j.toggleClass('_forecolor_normal',true);
		});
		j.click(function(){
			Editor.bbstyle('[color='+col+']','[/color]');
			$('#_forecolor_pop').hide();
		});
	});
	//链接
	//var link_pop = $('#_link_pop ');
}
Editor.closeMenu = function(p) {
	for(var i=0;i<Editor.popup.length;i++) {
		if(p.attr('id')!=Editor.popup[i].attr('id')) {
			Editor.popup[i].hide();
		}
	}
}
Editor.emoticon = function(text) {
   text = '' + text;
   Editor.bbstyle(text,'');
}
Editor.insertUrl = function(url,label,type) {
	if(!url || url=='') {
		return;
	}
	label = label || url;
	Editor.bbstyle('['+type+'='+url+']','[/'+type+']',label);
}
Editor.bbstyle = function(bbopen,bbclose,m) {
	var txtarea = Editor.txtarea;
	if (document.selection){
		//IE
		txtarea.focus();
		sel = document.selection.createRange();
		var txt = sel.text;
		if(m) {
			txt = m+txt;
			txt = txt.replace(/\n/g,'\n'+m);
		}
		sel.text = bbopen+txt+bbclose;
		txtarea.focus();
//		sel.moveStart('character', -txt.length-bbclose.length);
//		sel.moveEnd('character', -bbclose.length);
//		sel.select();
	}else if (txtarea.selectionStart || txtarea.selectionStart == '0') {
		//Mozilla-Netscape
		var startPos = txtarea.selectionStart;
		var endPos = txtarea.selectionEnd;
		var cursorPos = endPos;
		var scrollTop = txtarea.scrollTop;
		if (startPos != endPos) {
			var txt = txtarea.value.substring(startPos, endPos);
			if(m) {
				txt = m+txt;
				txt = txt.replace(/\n/g,'\n'+m);
			}
			txtarea.value = txtarea.value.substring(0, startPos)
				+ bbopen
				+ txt
				+ bbclose
				+ txtarea.value.substring(endPos, txtarea.value.length);
			cursorPos += bbopen.length + bbclose.length;
		}else {
			txtarea.value = txtarea.value.substring(0, startPos)
				+ bbopen+''+bbclose
				+ txtarea.value.substring(endPos, txtarea.value.length);
			cursorPos = startPos + bbopen.length+bbclose.length+1;
		}
		txtarea.focus();
		txtarea.selectionStart = cursorPos;
		txtarea.selectionEnd = cursorPos;
		txtarea.scrollTop = scrollTop;
	}else {
		txtarea.value += bbopen+''+bbclose;
		txtarea.focus();
	}
}
Editor.locaPnndex = 1;
Editor.selectFile = function() {
	var f = Editor.file;
	//检查是否选择了文件
	var name = f.val();
	if(name=='') {
		return;
	}
	var container = Editor.fileContainer;
	container.append(f);
	//复制一个file放至原处
	Editor.file=f.clone();
	Editor.fileSelector.append(Editor.file);
	//修改属性
	var index = Editor.locaPnndex++;
	f.attr('id','');
	f.attr('name','attachment');
	f.css('display','none');
	name = name.replace(/\\/g,'/');
	var filename = name.substring(name.lastIndexOf('/')+1,name.length);
	var url = "<div>";
	url += "[<a href='javascript:void(0)' onclick='$(this).parent().remove();'>删除</a>]";
	url += "[<a href='javascript:void(0)' onclick='Editor.emoticon(\"[localimg]"+index+"[/localimg]\")'>插入</a>]";
	url += "["+index+"]";
	url += "<a href='javascript:void(0)' onclick='Editor.emoticon(\"[localimg]"+index+"[/localimg]\")' onmouseover='Editor.showImage(this);' onmouseout='$(this).next(\"div\").hide()'>"+filename+"</a>";
	url += "<div style='display:none;position:absolute;' class='_editor_local_img' id='divid'><img src='file:///"+name+"' id='imgid'/><input name='code' type='hidden' value='"+index+"'/></div>";
	url += "</div>";
	container.append(url);
}
Editor.imageSize=300;
Editor.showImage = function(o) {
	var j = $(o);
	var imgc = j.next().show();
	if(!imgc.attr('position')) {
		var img = imgc.children('img');
		var h = img.height();
		var w = img.width();
		var size = Editor.imageSize;
		var max = h > w ? h : w;
		if(max>size) {
			var zoom = size/max;
			var hmin = h*zoom;
			var wmin = w*zoom;
			img.attr('width',wmin);
			img.attr('height',hmin);
		}
		var offset = j.offset();
		var top = offset.top - imgc.outerHeight() - 150;
		var left = offset.left - imgc.outerWidth() + 20;
		imgc.css('top',top);
		imgc.css('left',left);
		imgc.attr('position','ok');
	}
}

function setImagePreview(file) {
	var docObj = file;
	var imgObjPreview = document.getElementById("imgid");
	if (docObj) {
		imgObjPreview.style.display = 'block';
		imgObjPreview.style.width = '110px';
		imgObjPreview.style.height = '140px';
		// imgObjPreview.src = docObj.files[0].getAsDataURL();
		// 火狐7以上版本不能用上面的getAsDataURL()方式获取，需要一下方式
		imgObjPreview.src = window.URL.createObjectURL(docObj);
	} else {
		// IE下，使用滤镜
		docObj.select();
		var imgSrc = document.selection.createRange().text;
		var localImagId = document.getElementById("divid");
		// 必须设置初始大小
		localImagId.style.width = "110px";
		localImagId.style.height = "140px";
		// 图片异常的捕捉，防止用户修改后缀来伪造图片
		try {
			localImagId.style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale)";
			localImagId.filters
					.item("DXImageTransform.Microsoft.AlphaImageLoader").src = imgSrc;
		} catch (e) {
			
		}
		imgObjPreview.style.display = 'none';
		document.selection.empty();
	}
	return true;
}