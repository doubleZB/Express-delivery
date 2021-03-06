package cn.itcast.bos.action.system;

import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.aspectj.internal.lang.annotation.ajcDeclareAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.itcast.bos.action.base.BaseAction;
import cn.itcast.bos.domain.system.Role;
import cn.itcast.bos.service.system.RoleService;
@Controller
@ParentPackage("struts-default")
@Namespace("/")
@Scope("prototype")
public class RoleAction extends BaseAction<Role> {
	
	@Autowired
	private RoleService service;

	private String menuIds;
	private String permissionIds;
	public void setMenuIds(String menuIds) {
		this.menuIds = menuIds;
	}

	public void setPermissionIds(String permissionIds) {
		this.permissionIds = permissionIds;
	}

	@Action("roleAction_save")
	public void save(){
		try {
			service.save(model,menuIds,permissionIds);
			javaToJson(ajaxReturn(true, ""), null);
		} catch (Exception e) {
			javaToJson(ajaxReturn(false, "操作失败"), null);
			e.printStackTrace();
		}
	}
	@Action("roleAction_findAll")
	public void findAll(){
		List<Role> list = service.findAll();
		javaToJson(list, new String[]{"users","permissions","menus"});
	}
}
