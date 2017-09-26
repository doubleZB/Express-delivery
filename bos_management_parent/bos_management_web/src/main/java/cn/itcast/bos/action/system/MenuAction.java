package cn.itcast.bos.action.system;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.itcast.bos.action.base.BaseAction;
import cn.itcast.bos.domain.system.Menu;
import cn.itcast.bos.domain.system.User;
import cn.itcast.bos.service.system.MenuService;
@Controller
@ParentPackage("struts-default")
@Namespace("/")
@Scope("prototype")
public class MenuAction extends BaseAction<Menu> {
	
	@Autowired
	private MenuService service;
	
	@Action("menuAction_findAll")
	public void findAll(){
		List<Menu> list = service.findAll();
		javaToJson(list,new String[]{"roles","childrenMenus","parentMenu","children"} );
	}
	
	@Action("menuAction_findByPidIsNull")
	public void findByPidIsNull(){
		List<Menu> list = service.findByPidIsNull();
		javaToJson(list,new String[]{"roles","childrenMenus","parentMenu"} );
	}
	@Action("menuAction_findByUser")
	public void findByUser(){
//		获取当前登录人
		User user = (User) SecurityUtils.getSubject().getPrincipal();
		List<Menu> list = service.findByUser(user);
		javaToJson(list,new String[]{"roles","childrenMenus","parentMenu","children"} );
	}
	
	
	@Action("menuAction_save")
	public void save(){
		try {
			service.save(model);
			javaToJson(ajaxReturn(true, "操作成功"), null);
		} catch (Exception e) {
			javaToJson(ajaxReturn(false, "操作失败"), null);
			e.printStackTrace();
		}
	}
}
