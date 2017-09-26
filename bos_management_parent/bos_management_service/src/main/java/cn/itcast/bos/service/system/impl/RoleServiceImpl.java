package cn.itcast.bos.service.system.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.system.MenuDao;
import cn.itcast.bos.dao.system.PermissionDao;
import cn.itcast.bos.dao.system.RoleDao;
import cn.itcast.bos.domain.system.Menu;
import cn.itcast.bos.domain.system.Permission;
import cn.itcast.bos.domain.system.Role;
import cn.itcast.bos.domain.system.User;
import cn.itcast.bos.service.system.RoleService;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleDao dao;
	@Autowired
	private MenuDao mdao;
	@Autowired
	private PermissionDao pdao;

	@Override
	public void save(Role model, String menuIds, String permissionIds) {
//		1、保存一个role对象 role表中添加一条数据
		model = dao.save(model);
//		2、role_menu关联表
		if(menuIds!=null&&!menuIds.equals("")){
			String[] mIds = menuIds.split(",");
			for (String string : mIds) {
				Menu menu = mdao.findOne(Integer.parseInt(string));
//				分析得出 menu和role  role是数据的维护方
				model.getMenus().add(menu);
			}
			
		}
//		3、role_premission的关联
		if(permissionIds!=null&&!permissionIds.equals("")){
			String[] pIds = permissionIds.split(",");
			for (String string : pIds) {
				Permission permisson =  pdao.findOne(Integer.parseInt(string));
//				分析 role和Permission   role是数据的维护方
				model.getPermissions().add(permisson);
			}
		}
	}

	@Override
	public List<Role> findAll() {
		return dao.findAll();
	}

	@Override
	public List<Role> findByUser(User user) {
		if(user.getUsername().equals("admin")){
			return dao.findAll();
		}
		return dao.findByUser(user.getId());
	}
}
