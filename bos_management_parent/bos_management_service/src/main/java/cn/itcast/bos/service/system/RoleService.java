package cn.itcast.bos.service.system;

import java.util.List;

import cn.itcast.bos.domain.system.Role;
import cn.itcast.bos.domain.system.User;

public interface RoleService {

	void save(Role model, String menuIds, String permissionIds);

	List<Role> findAll();

	List<Role> findByUser(User user);

}
