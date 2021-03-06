(function($) {
	$.fn.floatAd = function(options) {
		var defaults = {
			imgSrc : "",
			url : "",
			id : "",
			openStyle : 1,
			speed : 10
		};
		var options = $.extend(defaults, options);
		var _target = options.openStyle == 1 ? "target='_blank'" : '';
		var html = "<div id='float_ad"+options.id+"' style='position:absolute;left:0px;top:0px;z-index:1000000;cleat:both;'>";
		html += "  <a href='" + options.url + "' " + _target + "><img src='"
				+ options.imgSrc + "' border='0' class='float_ad_img'/></a>";
		html += "<a href='javascript:void(0);' onclick='$(\"#float_ad"+options.id+"\").remove();'>关闭</a></div>";
		$('body').append(html);
		function init() {
			var x = 0, y = 0;
			var xin = true, yin = true;
			var step = 1;
			var delay = 10;
			var obj = $("#float_ad"+options.id);
			obj.find('img.float_ad_img').load(function() {
				var float = function() {
					var L = T = 0;
					var OW = obj.width();
					var OH = obj.height();
					var DW = $(document).width();
					var DH = $(document).height();
					x = x + step * (xin ? 1 : -1);
					if (x < L) {
						xin = true;
						x = L
					}
					if (x > DW - OW - 1) {
						xin = false;
						x = DW - OW - 1
					}
					y = y + step * (yin ? 1 : -1);
					if (y > DH - OH - 1) {
						yin = false;
						y = DH - OH - 1
					}
					if (y < T) {
						yin = true;
						y = T
					}
					var left = x;
					var top = y;
					obj.css({
						'top' : top,
						'left' : left
					})
				};
				var itl = setInterval(float, options.speed);
				$('#float_ad'+options.id).mouseover(function() {
					clearInterval(itl)
				});
				$('#float_ad'+options.id).mouseout(function() {
					itl = setInterval(float, options.speed)
				})
			})
		}
		init()
	}
})(jQuery);