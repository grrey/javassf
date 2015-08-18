function AddFavorite(sURL, sTitle){
    try{
		window.external.addFavorite(sURL, sTitle);
    }
    catch (e){
        try{
            window.sidebar.addPanel(sTitle, sURL, "");
        }
        catch (e){
            alert("加入收藏失败，请使用Ctrl+D进行添加");
        }
    }
}
function SetHome(obj,vrl){
        try{
                obj.style.behavior='url(#default#homepage)';obj.setHomePage(vrl);
        }
        catch(e){
                if(window.netscape) {
                        try {
                                netscape.security.PrivilegeManager.enablePrivilege("UniversalXPConnect");
                        }
                        catch (e) {
                                alert("此操作被浏览器拒绝！\n请在浏览器地址栏输入'about:config'并回车\n然后将 [signed.applets.codebase_principal_support]的值设置为'true',双击即可。");
                        }
                        var prefs = Components.classes['@mozilla.org/preferences-service;1'].getService(Components.interfaces.nsIPrefBranch);
                        prefs.setCharPref('browser.startup.homepage',vrl);
                 }
        }
}

function xxk(a,b){
	$("[id^='xxkc_"+a+"']").hide();
	$("[id^='xxk_"+a+"']").attr("class","normal");
	$("#xxkc_"+a+b).show();
	$("#xxk_"+a+b).attr("class","curr");
}

function xxkm(a,b){
	$("[id^='xxkc_"+a+"']").hide();
	$("[id^='xxkm_"+a+"']").hide();
	$("[id^='xxk_"+a+"']").attr("class","normal");
	$("#xxkc_"+a+b).show();
	$("#xxkm_"+a+b).show();
	$("#xxk_"+a+b).attr("class","curr");
}