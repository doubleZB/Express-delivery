package cn.itcast.bos.service.base;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import cn.itcast.bos.domain.base.Standard;

public interface StandardService {

	void updateOperator(String string);

	void save(Standard model);

	Page findByPage(Pageable pageable);

	List<Standard> findAll();

}
