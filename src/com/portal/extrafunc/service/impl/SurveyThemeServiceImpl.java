 package com.portal.extrafunc.service.impl;
 
 import com.portal.extrafunc.dao.SurveyThemeDao;
 import com.portal.extrafunc.entity.SurveyItem;
 import com.portal.extrafunc.entity.SurveyTheme;
 import com.portal.extrafunc.service.QuestionDetailService;
 import com.portal.extrafunc.service.QuestionnaireService;
 import com.portal.extrafunc.service.SurveyDetailService;
 import com.portal.extrafunc.service.SurveyThemeService;
 import com.portal.usermgr.entity.User;
 import java.util.Iterator;
 import java.util.List;
 import java.util.Map;
 import java.util.Set;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.data.domain.Page;
 import org.springframework.stereotype.Service;
 import org.springframework.transaction.annotation.Transactional;
 
 @Service
 @Transactional
 public class SurveyThemeServiceImpl
   implements SurveyThemeService
 {
   private SurveyThemeDao dao;
   private QuestionnaireService naireService;
   private QuestionDetailService questionDetailService;
   private SurveyDetailService surveyDetailService;
 
   @Transactional(readOnly=true)
   public Page<SurveyTheme> getPage(Integer naireId, int pageNo, int pageSize)
   {
     return this.dao.getPage(naireId, pageNo, pageSize);
   }
   @Transactional(readOnly=true)
   public List<SurveyTheme> getList(Integer naireId) {
     return this.dao.getList(naireId);
   }
   @Transactional(readOnly=true)
   public SurveyTheme findById(Integer id) {
     SurveyTheme entity = (SurveyTheme)this.dao.findById(id);
     return entity;
   }
 
   public SurveyTheme save(SurveyTheme bean, Integer naireId, Integer showType1, Integer showType2, String[] names, Integer[] votes, Integer[] prioritys)
   {
     bean.setNaire(this.naireService.findById(naireId));
     if (bean.getSurveyType().equals(SurveyTheme.NORMAL)) {
       bean.setShowType(showType1);
       if ((names != null) && (names.length > 0))
         for (int i = 0; i < names.length; i++)
           bean.addToItems(names[i], votes[i], prioritys[i]);
     }
     else
     {
       bean.setShowType(showType2);
     }
     bean.init();
     this.dao.save(bean);
     return bean;
   }
 
   public SurveyTheme update(SurveyTheme bean, Integer showType1, Integer showType2, String[] names, Integer[] votes, Integer[] prioritys)
   {
     bean = (SurveyTheme)this.dao.update(bean);
     if (bean.getItems() != null) {
       bean.getItems().clear();
       if (bean.getSurveyType().equals(SurveyTheme.NORMAL)) {
         bean.setShowType(showType1);
         bean.setMaxlength(null);
         if ((names != null) && (names.length > 0)) {
           for (int i = 0; i < names.length; i++) {
             bean.addToItems(names[i], votes[i], prioritys[i]);
           }
         }
       }
     }
     if (bean.getSurveyType().equals(SurveyTheme.WRITED)) {
       bean.setShowType(showType2);
       bean.setTotalCount(null);
     }
     return bean;
   }
 
   public void voteSurvey(Integer qId, Map<String, String> m, Map<String, String[]> ml, String ip, User user)
   {
     this.questionDetailService.save(this.naireService.findById(qId), user, ip);
     if (m != null) {
       Set key = m.keySet();
       for (Iterator it = key.iterator(); it.hasNext(); ) {
         String s = (String)it.next();
         SurveyTheme st = findById(Integer.valueOf(Integer.parseInt(s)));
         if (st.getSurveyType().equals(SurveyTheme.NORMAL)) {
           if (st.getItems() != null) {
             for (SurveyItem si : st.getItems()) {
               if (si.getName().equals(m.get(s)))
                 si.setVotes(Integer.valueOf(si.getVotes().intValue() + 1));
             }
           }
         }
         else {
           this.surveyDetailService.save((String)m.get(s), st, user);
         }
       }
     }
     if (ml != null)
       saveMapList(ml, ip, user);
   }
 
   private void saveMapList(Map<String, String[]> ml, String ip, User user)
   {
     Set key = ml.keySet();
     for (Iterator it = key.iterator(); it.hasNext(); ) {
       String s = (String)it.next();
       SurveyTheme st = findById(Integer.valueOf(Integer.parseInt(s)));
       if ((!st.getSurveyType().equals(SurveyTheme.NORMAL)) || 
         (st.getItems() == null)) continue;
       for (SurveyItem si : st.getItems())
         for (String ss : (String[])ml.get(s))
           if (si.getName().equals(ss))
             si.setVotes(Integer.valueOf(si.getVotes().intValue() + 1));
     }
   }
 
   public SurveyTheme deleteById(Integer id)
   {
     SurveyTheme bean = (SurveyTheme)this.dao.deleteById(id);
     return bean;
   }
 
   public SurveyTheme[] deleteByIds(Integer[] ids) {
     SurveyTheme[] beans = new SurveyTheme[ids.length];
     int i = 0; for (int len = ids.length; i < len; i++) {
       beans[i] = deleteById(ids[i]);
     }
     return beans;
   }
 
   @Autowired
   public void setDao(SurveyThemeDao dao)
   {
     this.dao = dao;
   }
   @Autowired
   public void setNaireService(QuestionnaireService naireService) {
     this.naireService = naireService;
   }
 
   @Autowired
   public void setQuestionDetailService(QuestionDetailService questionDetailService) {
     this.questionDetailService = questionDetailService;
   }
   @Autowired
   public void setSurveyDetailService(SurveyDetailService surveyDetailService) {
     this.surveyDetailService = surveyDetailService;
   }
 }


 
 
 