package cn.itcast.bos.service.base.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.base.CourierDao;
import cn.itcast.bos.dao.base.FixedAreaDao;
import cn.itcast.bos.dao.base.SubAreaDao;
import cn.itcast.bos.dao.base.TakeTimeDao;
import cn.itcast.bos.domain.base.Courier;
import cn.itcast.bos.domain.base.FixedArea;
import cn.itcast.bos.domain.base.SubArea;
import cn.itcast.bos.domain.base.TakeTime;
import cn.itcast.bos.service.base.FixedAreaService;

@Service
@Transactional
public class FixedAreaServiceImpl implements FixedAreaService {

	@Autowired
	private FixedAreaDao dao;
	@Autowired
	private SubAreaDao sdao;
	@Autowired
	private CourierDao cdao;
	@Autowired
	private TakeTimeDao tdao;

	@Override
	public void save(FixedArea model) {
		dao.save(model);
	}

	@Override
	public Page findByPage(Pageable pageable) {
		return dao.findAll(pageable);
	}

	@Override
	public void save(FixedArea model, String ids) {
		model = dao.save(model);
//		保存定区和分区的关联管理
//		FixedArea
		String[] sIds = ids.split(",");
		for (int i = 0; i < sIds.length; i++) {
			SubArea subArea = sdao.findOne(sIds[i]);
			subArea.setFixedArea(model);
		}
	}

	@Override
	public void associationCourierToFixedArea(String id, int courierId, int takeTimeId) {
//		t_fixedarea_courier 添加一条数据
//		fixedarea 维护方
//		courier  被维护方
		FixedArea fixedarea = dao.findOne(id);
		Courier courier = cdao.findOne(courierId);
		fixedarea.getCouriers().add(courier);
//		修改T_COURIER中的C_TAKETIME_ID
		TakeTime takeTime = tdao.findOne(takeTimeId);
		courier.setTakeTime(takeTime);
		
	} 
}
