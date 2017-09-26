package cn.itcast.bos.service.base;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import cn.itcast.bos.domain.base.FixedArea;

public interface FixedAreaService {

	void save(FixedArea model);

	Page findByPage(Pageable pageable);

	void save(FixedArea model, String ids);

	void associationCourierToFixedArea(String id, int courierId, int takeTimeId);

}
