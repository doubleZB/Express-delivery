package cn.itcast.bos.service.base.impl;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.base.CourierDao;
import cn.itcast.bos.domain.base.Courier;
import cn.itcast.bos.service.base.CourierService;

@Service
@Transactional
public class CourierServiceImpl implements CourierService {
	@Autowired
	private CourierDao dao;

	@Override
	public void save(Courier model) {
		dao.save(model);
		
	}

	@Override
	public Page findByPage(Pageable pageable) {
		return dao.findAll(pageable);
	}


//	@RequiresPermissions("courier-delete")
	public void deleteBatch(String ids) {
//		使用此方法时判断当前登录人是否有 courier-delete权限
		Subject subject = SecurityUtils.getSubject();
		if(!subject.isPermitted("courier-delete")){
			throw new UnauthorizedException();
		}
		
//		ids="1,2,3"
		String[] cids = ids.split(",");
		for (int i = 0; i < cids.length; i++) {
//			dao.delete(Integer.parseInt(cids[i]));  //此方法是物理删除
			dao.updateDelTag(Integer.parseInt(cids[i]));  //作废操作是修改courier对象中的deltag字段
		}
	}

	@Override
	public Page findByPage(Specification<Courier> specification, Pageable pageable) {
		return dao.findAll(specification, pageable);
	}

	@Override
	public List<Courier> findByDeltagIsNull() {
		return dao.findByDeltagIsNull();
	}
}
