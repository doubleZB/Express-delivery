package cn.itcast.bos.service.base;

import java.io.FileInputStream;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import cn.itcast.bos.domain.base.Area;

public interface AreaService {

	void importXls(FileInputStream fileInputStream);

	Page<Area> findByPage(Pageable pageable);

	List<Area> findAll();

	List<Area> findByQ(String q);

}
