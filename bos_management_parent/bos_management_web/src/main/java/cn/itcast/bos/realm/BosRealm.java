package cn.itcast.bos.realm;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import cn.itcast.bos.domain.system.Permission;
import cn.itcast.bos.domain.system.Role;
import cn.itcast.bos.domain.system.User;
import cn.itcast.bos.service.system.PermissionService;
import cn.itcast.bos.service.system.RoleService;
import cn.itcast.bos.service.system.UserService;

public class BosRealm extends AuthorizingRealm {
	@Autowired
    private UserService userService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private PermissionService permissionService;
	/**
	 * 认证
	 */
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken arg0) throws AuthenticationException {
	   System.out.println("********************执行了认证方法");
//	   查询数据库
	   UsernamePasswordToken token = (UsernamePasswordToken) arg0;
	   String username = token.getUsername();
	   String password = new String(token.getPassword());
	   User user = userService.findByUsernameAndPassword(username, password);
	   if(user==null){
		   return null;
	   }else{
//		   principal 主角, credentials 密码, realmName realm的名字
		   SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, password, getName());
		   return info;
	   }
	}
	
	/**
	 * 授权
	 */
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection arg0) {
		 System.out.println("++++++++++++++++++++++++++执行了授权方法");
		 SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		 //告诉了shiro框架 当前登录人拥有的角色
//		 SecurityUtils.getSubject().getPrincipal()
		 User user = (User) arg0.getPrimaryPrincipal();
		 List<Role> list = roleService.findByUser(user);
		 for (Role role : list) {
			 info.addRole(role.getKeyword());
		 }
		 List<Permission> listp= permissionService.findByUser(user);
		 //告诉了shiro框架 当前登录人拥有的权限
		 for (Permission permission : listp) {
			 info.addStringPermission(permission.getKeyword());
		}
		 

		 return info;
	}

}
