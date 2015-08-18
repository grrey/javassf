Div_Addr_list = "";
Div_Addr_list1 = "";
Div_date_list = ""
Div_hystr_list = ""
Div_jobhystr_list = ""
function Openwin(art_id, wtitle, wwidth, wheight, contexttext) {
	art.dialog({
		padding : 10,
		id : art_id,
		top : 100,
		fixed : false,
		title : wtitle,
		width : wwidth,
		lock : true,
		drag : false,
		resize : false,
		button : [ {
			name : '关闭!'
		} ],
		content : contexttext
	});
}
$(function() {
	$("#address1")
			.click(
					function() {
						if (Div_Addr_list == "") {
							Div_Addr_list = Div_Addr_list
									+ "		  <div id=\"div_addrlist\" class=\"five_li\">";
							Div_Addr_list = Div_Addr_list + "		    <ul>";
							for ( var i = 0; i <= jobarea_v.length - 1; i++) {
								Div_Addr_list = Div_Addr_list
										+ "		      <li><a href='javascript:void(0)' onclick=\"show_s(1,'"
										+ jobarea_v[i] + "','" + jobarea_s[i]
										+ "'," + i + ")\" class='Addr_uf'>"
										+ jobarea_s[i] + "</a></li>";
							}
							Div_Addr_list = Div_Addr_list + "		    </ul>";
							Div_Addr_list = Div_Addr_list + "		  </div>";
						}
						Openwin('show_job_addr', '请选择地点', '500px', '300px',
								Div_Addr_list);
					});
	$("#sinput2")
			.click(
					function() {
						if (Div_date_list == "") {
							Div_date_list = Div_date_list
									+ "		  <div id=\"div_datelist\" class=\"four_li\">";
							Div_date_list = Div_date_list + "		    <ul>";
							for ( var i = 0; i <= pubschedule_show.length - 1; i++) {
								Div_date_list = Div_date_list
										+ "		      <li><a href='javascript:void(0)' onclick=\"Check_ok(2,'"
										+ pubschedule_show[i] + "','"
										+ pubschedule_value[i]
										+ "')\" class='Addr_uf'>"
										+ pubschedule_show[i] + "</a></li>";
							}
							Div_date_list = Div_date_list + "		    </ul>";
							Div_date_list = Div_date_list + "		  </div>";

						}
						Openwin('show_date_time', '请选择时间', '500px', '140px',
								Div_date_list);
					});
	$("#sinput3")
			.click(
					function() {
						if (Div_hystr_list == "") {
							Div_hystr_list = Div_hystr_list
									+ "		  <div id=\"div_addrlist\" class=\"five_li\">";
							Div_hystr_list = Div_hystr_list + "		    <ul>";
							for ( var i = 0; i <= func_s.length - 1; i++) {
								Div_hystr_list = Div_hystr_list
										+ "		      <li><a href='javascript:void(0)' onclick=\"show_s(2,'"
										+ func_v[i] + "','" + func_s[i] + "',"
										+ i + ")\" class='Addr_uf'>"
										+ func_s[i] + "</a></li>";
							}
							Div_hystr_list = Div_hystr_list + "		    </ul>";
							Div_hystr_list = Div_hystr_list + "		  </div>";
						}

						Openwin('show_job_hy', '请选择岗位类别', '600px', '400px',
								Div_hystr_list);
					});
	$("#input1")
			.click(
					function() {
						if (Div_jobhystr_list == "") {
							Div_jobhystr_list = Div_jobhystr_list
									+ "		  <div id=\"div_addrlist\" class=\"three_li\">";
							Div_jobhystr_list = Div_jobhystr_list
									+ "		    <ul>";
							for ( var i = 0; i <= ind_v.length - 1; i++) {
								Div_jobhystr_list = Div_jobhystr_list
										+ "		      <li><a href='javascript:void(0)' onclick=\"Check_ok(4,'"
										+ ind_s[i] + "','" + ind_v[i]
										+ "')\" class='Addr_uf'>" + ind_s[i]
										+ "</a></li>";
							}
							Div_jobhystr_list = Div_jobhystr_list
									+ "		    </ul>";
							Div_jobhystr_list = Div_jobhystr_list
									+ "		  </div>";
						}

						Openwin('show_job_hy_s', '请选择行业类别', '500px', '400px',
								Div_jobhystr_list);
					});

})

function show_s(typeid, job_value, job_area_s, array_ID) {
	tstring = "";
	switch (typeid) {
	case 1:
		if (Div_Addr_list.indexOf('<!---->') > 0) {
			tstring = Div_Addr_list.split('<!---->')[0];
		} else {
			tstring = Div_Addr_list;
		}
		tstring = tstring + "<!----><div class='clear'></div>"
		tstring = tstring + "<h1 class='div_schoose'>请选择城市</h1>"
		tstring = tstring + "<div id=\"city_choose\" class=\"five_li\">"
		tstring = tstring + "<ul>"
		for ( var k = 0; k < subarea_s[array_ID].length; k++) {
			tstring = tstring
					+ "		      <li><a href='javascript:void(0)' onclick=\"Check_ok(1,'"
					+ subarea_s[array_ID][k] + "','" + subarea_v[array_ID][k]
					+ "')\" class='Addr_uf'>" + subarea_s[array_ID][k]
					+ "</a></li>";
		}
		tstring = tstring + "</ul>"
		tstring = tstring + "</div>"
		art.dialog.list['show_job_addr'].content(tstring);
		art.dialog.list['show_job_addr'].position('50%', 100);
		break;
	case 2:
		if (Div_hystr_list.indexOf('<!---->') > 0) {
			tstring = Div_hystr_list.split('<!---->')[0];
		} else {
			tstring = Div_hystr_list;
		}
		tstring = tstring + "<!----><div class='clear'></div>"
		tstring = tstring + "<h1 class='div_schoose'>请选择小类</h1>"
		tstring = tstring + "<div id=\"hy_choose\" class=\"five_li\">"
		tstring = tstring + "<ul>"
		for ( var k = 0; k < subfunc_s[array_ID].length; k++) {
			tstring = tstring
					+ "		      <li><a href='javascript:void(0)' onclick=\"Check_ok(3,'"
					+ subfunc_s[array_ID][k] + "','" + subfunc_v[array_ID][k]
					+ "')\" class='Addr_uf'>" + subfunc_s[array_ID][k]
					+ "</a></li>";
		}
		tstring = tstring + "</ul>"
		tstring = tstring + "</div>"
		art.dialog.list['show_job_hy'].content(tstring);
		art.dialog.list['show_job_hy'].position('50%', 100);
		break;
	}
}

function Check_ok(Formkey, Inserstring, Textvalue) {
	switch (Formkey) {
	case 1:// 选择地址
		//document.getElementById("sinput1").innerHTML = Inserstring;
		document.getElementById("address1").value = Inserstring;
		Div_Addr_list = tstring;
		Div_Addr_list = Div_Addr_list.replace(/Addr_f/g, "Addr_uf");
		Div_Addr_list = Div_Addr_list.replace(Formkey + ",'" + Inserstring
				+ "','" + Textvalue + "')\" class='Addr_uf", Formkey + ",'"
				+ Inserstring + "','" + Textvalue + "')\" class='Addr_f");
		art.dialog.list['show_job_addr'].close();
		break;
	case 2:// 选择时间
		document.getElementById("sinput2").innerHTML = Inserstring;
		document.getElementById("SHPublishDate").value = Textvalue;
		Div_date_list = Div_date_list.replace(/Addr_f/g, "Addr_uf");
		Div_date_list = Div_date_list.replace(Formkey + ",'" + Inserstring
				+ "'," + Textvalue + ")\" class='Addr_uf", Formkey + ",'"
				+ Inserstring + "'," + Textvalue + ")\" class='Addr_f");
		art.dialog.list['show_date_time'].close();
		break;
	case 3:// 选择岗位
		document.getElementById("sinput3").innerHTML = Inserstring;
		document.getElementById("SHJobTypeBox").value = Textvalue;
		Div_hystr_list = tstring;
		Div_hystr_list = Div_hystr_list.replace(/Addr_f/g, "Addr_uf");
		Div_hystr_list = Div_hystr_list.replace(Formkey + ",'" + Inserstring
				+ "','" + Textvalue + "')\" class='Addr_uf", Formkey + ",'"
				+ Inserstring + "','" + Textvalue + "')\" class='Addr_f");
		art.dialog.list['show_job_hy'].close();
		break;
	case 4:// 选择行业
		document.getElementById("input1").innerHTML = Inserstring;
		document.getElementById("SHTrade").value = Textvalue;
		Div_jobhystr_list = Div_jobhystr_list.replace(/Addr_f/g, "Addr_uf");
		Div_jobhystr_list = Div_jobhystr_list.replace(Formkey + ",'"
				+ Inserstring + "','" + Textvalue + "')\" class='Addr_uf",
				Formkey + ",'" + Inserstring + "','" + Textvalue
						+ "')\" class='Addr_f");
		art.dialog.list['show_job_hy_s'].close();
		break;
	}
}
