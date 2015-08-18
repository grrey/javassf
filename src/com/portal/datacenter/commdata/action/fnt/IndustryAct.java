package com.portal.datacenter.commdata.action.fnt;

import com.javassf.basic.utils.ResponseUtils;
import com.portal.datacenter.commdata.service.IndustryService;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndustryAct
{

  @Autowired
  private IndustryService industryService;

  @RequestMapping(value={"/industryChild.jsp"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  public void industryChild(Integer industryId, HttpServletRequest request, HttpServletResponse response, ModelMap model)
    throws JSONException
  {
    List induList = this.industryService.getIndustryChild(industryId);
    JSONObject json = new JSONObject(ResponseUtils.listToJson(induList));
    ResponseUtils.renderJson(response, json.toString());
  }
}




