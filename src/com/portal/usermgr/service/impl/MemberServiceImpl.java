 package com.portal.usermgr.service.impl;
 
 import com.portal.usermgr.dao.MemberDao;
 import com.portal.usermgr.entity.Group;
 import com.portal.usermgr.entity.Member;
 import com.portal.usermgr.entity.User;
 import com.portal.usermgr.service.GroupService;
 import com.portal.usermgr.service.MemberService;
 import com.portal.usermgr.service.UserService;
 import java.sql.Timestamp;
 import java.util.Set;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.data.domain.Page;
 import org.springframework.stereotype.Service;
 import org.springframework.transaction.annotation.Transactional;
 
 @Service
 @Transactional
 public class MemberServiceImpl
   implements MemberService
 {
   private MemberDao dao;
   private UserService userService;
   private GroupService groupService;
 
   @Transactional(readOnly=true)
   public Page<Member> getPage(int pageNo, int pageSize)
   {
     Page page = this.dao.getPage(pageNo, pageSize);
     return page;
   }
 
   @Transactional(readOnly=true)
   public Page<Member> getPage(String key, Integer siteId, Integer groupId, String sortname, String sortorder, int pageNo, int pageSize) {
     return this.dao.getPage(key, siteId, groupId, sortname, sortorder, pageNo, 
       pageSize);
   }
   @Transactional(readOnly=true)
   public int getNoCheckMemberCount() {
     return this.dao.getNoCheckMemberCount();
   }
   @Transactional(readOnly=true)
   public Member findById(Integer id) {
     Member entity = (Member)this.dao.findById(id);
     return entity;
   }
 
   public Member registerMember(User user, Member member, String ip, Integer groupId)
   {
     member.setRegisterIp(ip);
     member.setLastLoginIp(ip);
     if (groupId != null) {
       member.addToGroups(this.groupService.findById(groupId));
     }
     this.userService.save(user);
     member.setUser(user);
     save(member);
     user.setMember(member);
     return member;
   }
 
   public Member updateMember(User user, Member member, Integer groupId, Integer siteId)
   {
     if (findById(user.getId()) != null) {
       member = update(member);
     } else {
       member.setUser(user);
       member = save(member);
       user.setMember(member);
     }
     this.userService.update(user);
     if ((groupId != null) && 
       (!groupId.equals(member.getGroup(siteId).getId()))) {
       member.getGroups().remove(member.getGroup(siteId));
       member.addToGroups(this.groupService.findById(groupId));
     }
 
     return member;
   }
 
   public void updateLoginInfo(User user, String lastLoginIp) {
     Member member = findById(user.getId());
     if (member != null) {
       member.setLastLoginIp(lastLoginIp);
       member.setLastLoginTime(new Timestamp(System.currentTimeMillis()));
       if (member.getRegisterIp() == null) {
         member.setRegisterIp(lastLoginIp);
       }
       member.setLoginCount(Integer.valueOf(member.getLoginCount().intValue() + 1));
     }
   }
 
   public Member save(Member bean) {
     bean.init();
     this.dao.save(bean);
     return bean;
   }
 
   public Member update(Member bean) {
     bean = (Member)this.dao.update(bean);
     return bean;
   }
 
   public Member updatePass(Integer memberId, String password) {
     User user = this.userService.updatePass(memberId, password);
     return user.getMember();
   }
 
   public Member deleteById(Integer id) {
     Member bean = (Member)this.dao.deleteById(id);
     this.userService.deleteById(id);
     return bean;
   }
 
   public Member[] deleteByIds(Integer[] ids) {
     Member[] beans = new Member[ids.length];
     int i = 0; for (int len = ids.length; i < len; i++) {
       beans[i] = deleteById(ids[i]);
     }
     return beans;
   }
 
   @Autowired
   public void setDao(MemberDao dao)
   {
     this.dao = dao;
   }
   @Autowired
   public void setUserService(UserService userService) {
     this.userService = userService;
   }
   @Autowired
   public void setGroupService(GroupService groupService) {
     this.groupService = groupService;
   }
 }


 
 
 