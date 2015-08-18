function fTrim(str){return str.replace(/(^\s*)|(\s*$)/g,"");}
function chkCheckBox(objNam)
{
	var obj = document.getElementsByName(objNam);
	var objLen= obj.length;
	var objYN;
	var i;
	objYN=false;
	for (i = 0;i< objLen;i++)
	{
		if (obj[i].checked==true)
		{
			objYN= true;
			break;
		}
	}
	return objYN;
}
function isEmail(s) {
	if (s.length > 100)	return false;
	if (s.indexOf("'")!=-1 || s.indexOf("/")!=-1 || s.indexOf("\\")!=-1 || s.indexOf("<")!=-1 || s.indexOf(">")!=-1) return false;
    s = s.replace('(', '');
    s = s.replace(')', '');
    s = s.replace('（', '');
    s = s.replace('）', '');
	var regu = "^(([0-9a-zA-Z]+)|([0-9a-zA-Z]+[_.0-9a-zA-Z-]*[_.0-9a-zA-Z]+))@([a-zA-Z0-9-]+[.])+([^\.]+)$";
	var result=true;
	var arrayStr = s.split(";");
	var i = 0;
	for (;i<arrayStr.length;i++){
		if (s.charAt(0)==" "){
			result=false;
			break;
		}
		if (s.charAt(s.length-1)==" "){
			result=false;
			break;
		}
		var re = new RegExp(regu);
		if (arrayStr[i].search(re) == -1){
			result=false;
			break;
		}
	}
	return result;
}
function isDate(str){		 
    var reg = /^(\d+)-(\d{1,2})-(\d{1,2})$/; 
    var r = str.match(reg); 
    if(r==null)return false; 
    r[2]=r[2]-1; 
    var d= new Date(r[1], r[2],r[3]); 
    if(d.getFullYear()!=r[1])return false; 
    if(d.getMonth()!=r[2])return false; 
    if(d.getDate()!=r[3])return false;  
    return true;
}
function isNumber(s) {
	var regu = "^([0-9]*)$";
	var re   = new RegExp(regu);
	if (s.search(re) != -1)
		return true;
	else
		return false;
}
function isPhone(s){
	if (s.length < 1) return false;

	var tmpChar = "0123456789-/";

	for (i = 0; i < s.length; i++) {
		ch = s.charAt(i);
		for(j = 0; j < tmpChar.length; j++) {
			if (ch == tmpChar.charAt(j))
				break;
			if (j == tmpChar.length - 1)
				return false;
		}
	}
	return true;
}
function isURL(s) {
	if(ischinese(s))
	{
		return false;
	}
	else
	{
		if(s.indexOf(".")==-1 || s.indexOf("http://")==-1)
		{
			return false;
		}
		else
			return true;
	}
		
}
//判断是否含有中文字
function ischinese(s){
    var ret=false;

    for(var i=0;i<s.length;i++){
        if(s.charCodeAt(i)>=256){			
			ret=true;
			break;
		}
	}

    return ret;
}
function isValidAccountPass(s) 
{
	if (s.length == 0) return false;
	var regu = "^[0-9A-Za-z_-]*$";
	var re   = new RegExp(regu);
	if (s.search(re) != -1)
		return true;
	else
    {
         return false;
    }
}
//读取字符串长度，其中一个汉字占两个字符
function len(s){
	var length = 0;
	var tmpArr = s.split("");

	for (i = 0; i < tmpArr.length; i++){
		if (tmpArr[i].charCodeAt(0) < 299)
			length ++;
		else
			length += 2;
	}
	return length;
}

function checkall(){
	var m=document.TheForm;
	if(m.cList.length>1){
		for(j=0;j<m.cList.length;j++)
		m.cList[j].checked=true;
	}else{
		m.cList.checked=true;
	}
}
function clearall(){
	var m=document.TheForm;
	if(m.cList.length>1){
		for(j=0;j<m.cList.length;j++)
		m.cList[j].checked=false;
	}else{
		m.cList.checked=false;
	}
}
function inputQJ(str,flag){
	var i;
	var result='';
	if (str.length<=0) {return "";} 
	for(i=0;i<str.length;i++){
		str1=str.charCodeAt(i);
		if(str1<125&&!flag){
			result+=String.fromCharCode(str.charCodeAt(i)+65248);
		}else{
			result+=String.fromCharCode(str.charCodeAt(i)-65248);
		}
	}
	return result;
}

//日期选择框
function fShowCalendar(objNam,objValue){
	var showx = event.screenX + event.offsetX;
	var showy = event.screenY + event.offsetY - 18;
	retval = window.showModalDialog("/Lib/20090101/Scripts/DatetimeDialog.htm",objValue, "dialogWidth:250px;dialogHeight:200px;dialogLeft:" + showx + "px;dialogTop:" + showy + "px;status:no;directories:yes;scrollbars:no;Resizable=no;help=no;");
	if(retval!= null)
	{
		objNam.value = retval;
	}
}
function selOnChange(obj1, obj2){
    var m=document.thisForm;
    if(typeof(obj1.options[obj1.selectedIndex])=="unknown" ||
        typeof(obj2.options[obj2.selectedIndex])=="unknown"){
        retrun ;
    }
    var idx1 = obj1.selectedIndex;
    var idx2 = obj2.selectedIndex;

    if (idx1 > idx2) {
        obj2.selectedIndex = idx1;
    }
}
function copyToClipBoard(){   
	 var clipBoardContent="";   
	 clipBoardContent+=document.title;   
	 clipBoardContent+="\n";   
	 clipBoardContent+=this.location.href; //获取地址   
	 window.clipboardData.setData("Text",clipBoardContent);   
	 alert("复制成功！您可以将本页推荐给你QQ、MSN或者论坛上的朋友阅读！");   
}   
/*更新验证码*/
function changecode(idname,imgurl){
	document.getElementById(idname).src=imgurl+"?id="+Math.floor(Math.random()*100+1);
}