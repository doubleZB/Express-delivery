package cn.itcast.bos.service.base;

import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.List;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import cn.itcast.bos.domain.base.SubArea;

public interface SubAreaService {

	void save(SubArea model);

	Page<SubArea> findByPage(Pageable pageable);

	List<SubArea> findAll();
	
	void exportXls(FileInputStream in, OutputStream outputStream);

	List findChart();

}
