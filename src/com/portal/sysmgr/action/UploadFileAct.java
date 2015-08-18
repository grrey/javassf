package com.portal.sysmgr.action;

import com.javassf.basic.upload.FileRepository;
import com.javassf.basic.utils.ResponseUtils;
import com.portal.sysmgr.entity.Site;
import com.portal.sysmgr.utils.ContextTools;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class UploadFileAct {

	@Autowired
	private FileRepository fileRepository;

	@RequestMapping({ "/kind/o_upload_file.do" })
	public void kinduploadImg(@RequestParam(value = "imgFile", required = false) MultipartFile file, HttpServletRequest request, HttpServletResponse response) throws JSONException {
		JSONObject json = new JSONObject();
		Site site = ContextTools.getSite(request);
		String fileUrl = this.fileRepository.uploadFile(file, site.getUploadPath());
		json.put("error", 0);
		json.put("url", "../.." + fileUrl);
		ResponseUtils.renderText(response, json.toString());
	}

	@RequestMapping({ "/o_upload_attach.do" })
	public void uploadAttachment(@RequestParam(value = "attachFile", required = false) MultipartFile file, String attachmentNum, HttpServletRequest request, HttpServletResponse response) throws JSONException {
		Site site = ContextTools.getSite(request);
		String fileUrl = this.fileRepository.uploadFile(file, site.getUploadPath());
		ResponseUtils.renderText(response, fileUrl);
	}

	@RequestMapping({ "/o_upload_img.do" })
	public void uploadImg(@RequestParam(value = "imgFile", required = false) MultipartFile file, HttpServletRequest request, HttpServletResponse response) throws JSONException {
		Site site = ContextTools.getSite(request);
		String fileUrl = this.fileRepository.uploadFile(file, site.getUploadPath());
		ResponseUtils.renderText(response, fileUrl);
	}
}
