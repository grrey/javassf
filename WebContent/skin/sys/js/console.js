Console={};
Console.f_heightChanged =function(options){
	if (tab)
		tab.addHeight(options.diff);
	if (accordion && options.middleHeight - 24 > 0)
		accordion.setHeight(options.middleHeight - 24);
};