package cn.itcast.bos.service.system;

import java.util.List;

import cn.itcast.bos.domain.system.Menu;
import cn.itcast.bos.domain.system.User;

public interface MenuService {

	List<Menu> findAll();

	List<Menu> findByPidIsNull();

	void save(Menu model);

	List<Menu> findByUser(User user);

}
