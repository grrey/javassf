package com.portal.sysmgr.shiro;

import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.portal.sysmgr.entity.Site;
import com.portal.sysmgr.utils.ContextTools;
import com.portal.usermgr.entity.User;
import com.portal.usermgr.service.UserService;

/**
 * 自定义的Realm类
 */
public class PortalRealm extends AuthorizingRealm {
	private UserService userService;
	
	/** 
     * 验证当前登录的Subject 
     * @see 该方法的调用时机为login()方法中执行Subject.login()时 
     */  
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
		User user = this.userService.findByUsername(token.getUsername());
		if (user != null) {
			return new SimpleAuthenticationInfo(user.getUsername(), user.getPassword(), getName());
		}
		return null;
	}

	/** 
     * 为当前登录的Subject授予角色和权限 
     * @see  该方法的调用时机为需授权资源被访问时 
     * @see  并且每次访问需授权资源时都会执行该方法中的逻辑,这表明本例中默认并未启用AuthorizationCache 
     * @see  个人感觉若使用了Spring3.1开始提供的ConcurrentMapCache支持,则可灵活决定是否启用AuthorizationCache 
     * @see  比如说这里从数据库获取权限信息时,先去访问Spring3.1提供的缓存,而不使用Shior提供的AuthorizationCache 
     */  
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		//获取当前登录的用户名
		String username = (String) principals.fromRealm(getName()).iterator().next();
		if (!StringUtils.isBlank(username)) {
			User user = this.userService.findByUsername(username);
			Site site = ContextTools.getSite();
			if ((user != null) && (site != null)) {
				SimpleAuthorizationInfo auth = new SimpleAuthorizationInfo();
				if (user.getAdmin() != null) {
					Set perms = user.getAdmin().getPerms(site.getId());
					if (!CollectionUtils.isEmpty(perms)) {
						auth.setStringPermissions(perms);
					}
					return auth;
				}
				auth.setObjectPermissions(null);
				return auth;
			}
		}

		return null;
	}

	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
}
