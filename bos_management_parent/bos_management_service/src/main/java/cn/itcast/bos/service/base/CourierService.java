package cn.itcast.bos.service.base;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import cn.itcast.bos.domain.base.Courier;

public interface CourierService {

	void save(Courier model);

	Page findByPage(Pageable pageable);

	void deleteBatch(String ids);

	Page findByPage(Specification<Courier> specification, Pageable pageable);

	List<Courier> findByDeltagIsNull();

}
