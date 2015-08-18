jQuery.fn.autoZoomLoadImage = function(width, height) {
  return this.each(function() {
    var t = $(this);
    var src = $(this).attr("src");
    var img = new Image();
    img.src = src;
    //自动缩放图片
    var autoScaling = function() {
        if (img.width > 0 && img.height > 0) {
          if (img.width / img.height >= width / height) {
            if (img.width > width) {
              t.width(width);
              t.height((img.height * width) / img.width);
            }
            else {
              t.width(img.width);
              t.height(img.height);
            }
          }
          else {
            if (img.height > height) {
              t.height(height);
              t.width((img.width * height) / img.height);
            }
            else {
              t.width(img.width);
              t.height(img.height);
            }
          }
        }
    };
    //处理ff下会自动读取缓存图片
    if (img.complete) {
      autoScaling();
      return;
    }
    $(this).attr("src", "");
    t.hide();
    $(img).load(function() {
      autoScaling();
      t.attr("src", this.src);
      t.show();
    });
  });
};