package com.portal.datacenter.docdata.action.fnt;

import com.javassf.basic.utils.ResponseUtils;
import com.portal.datacenter.docdata.service.ProgramDownloadService;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.URI;
import java.net.URISyntaxException;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ProgramDownloadAct {
	private static final String CONTENT_TYPE = "text/html; charset=UTF-8";

	@Autowired
	private ServletContext ctx;

	@Autowired
	private ProgramDownloadService programDownloadService;

	@RequestMapping(value = { "/pgdowload.jsp" }, method = { org.springframework.web.bind.annotation.RequestMethod.GET })
	public void programDownload(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		File file = new File(this.ctx.getRealPath("/release/javassf-1.3-final.zip"));
		response.setContentType("text/html; charset=UTF-8");
		response.setContentType("application/x-msdownload");
		response.setHeader("Content-Disposition", "attachment;filename=javassf-1.3-final.zip");
		response.setContentLength((int) file.length());
		HttpClient client = new DefaultHttpClient();
		try {
			HttpGet httpget = new HttpGet(new URI("http://www.javassf.com/pgdowload2.jsp"));
			client.execute(httpget);
		} catch (URISyntaxException e) {
			System.out.println("URL错误或者网络断开!");
		} catch (ClientProtocolException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		this.programDownloadService.updateCount();
		try {
			FileInputStream fis = new FileInputStream(file);
			BufferedInputStream buff = new BufferedInputStream(fis);
			byte[] b = new byte[1024];
			long k = 0L;
			try {
				OutputStream myout = response.getOutputStream();
				while (k < file.length()) {
					int j = buff.read(b, 0, 1024);
					k += j;
					myout.write(b, 0, j);
				}
				myout.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = { "/pgdowload2.jsp" }, method = { org.springframework.web.bind.annotation.RequestMethod.GET })
	public void programDownload2(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws JSONException {
		JSONObject json = new JSONObject();
		this.programDownloadService.updateCount();
		json.put("success", true);
		ResponseUtils.renderJson(response, json.toString());
	}
}
