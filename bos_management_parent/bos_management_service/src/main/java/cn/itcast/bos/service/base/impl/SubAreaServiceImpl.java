package cn.itcast.bos.service.base.impl;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.UUID;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.base.SubAreaDao;
import cn.itcast.bos.domain.base.SubArea;
import cn.itcast.bos.service.base.SubAreaService;

@Service
@Transactional
public class SubAreaServiceImpl implements SubAreaService {
	@Autowired
	private SubAreaDao dao ;

	@Override
	public void save(SubArea model) {
		if(model.getId()==null){  //保存
			model.setId(UUID.randomUUID().toString());
		}
		dao.save(model);
	}

	@Override
	public Page<SubArea> findByPage(Pageable pageable) {
		return dao.findAll(pageable);
	}

	@Override
	public List<SubArea> findAll() {
		return dao.findAll();
	}

	@Override
	public void exportXls(FileInputStream in,OutputStream out) {
//		导出所有的分区数据
		List<SubArea> list = dao.findAll();
		try {
			HSSFWorkbook book = new HSSFWorkbook(in);
			HSSFSheet sheet = book.getSheetAt(0);
			HSSFCellStyle cellStyle = book.getSheetAt(1).getRow(0).getCell(0).getCellStyle();
			
			int rowIndex = 2;
			HSSFRow row = null;
			HSSFCell cell = null;
			for (SubArea subArea : list) {
				row = sheet.createRow(rowIndex);
				
				cell = row.createCell(0);
				cell.setCellStyle(cellStyle);
				cell.setCellValue(subArea.getId());
				
				cell = row.createCell(1);
				cell.setCellStyle(cellStyle);
				cell.setCellValue(subArea.getArea().getProvince());
				
				cell = row.createCell(2);
				cell.setCellStyle(cellStyle);
				cell.setCellValue(subArea.getArea().getCity());
				
				cell = row.createCell(3);
				cell.setCellStyle(cellStyle);
				cell.setCellValue(subArea.getArea().getDistrict());
				
				cell = row.createCell(4);
				cell.setCellStyle(cellStyle);
				cell.setCellValue(subArea.getKeyWords());
				
				cell = row.createCell(5);
				cell.setCellStyle(cellStyle);
				cell.setCellValue(subArea.getStartNum());
				
				cell = row.createCell(6);
				cell.setCellStyle(cellStyle);
				cell.setCellValue(subArea.getEndNum());
				
				cell = row.createCell(7);
				cell.setCellStyle(cellStyle);
				cell.setCellValue(subArea.getSingle());
				
				cell = row.createCell(8);
				
				cell.setCellValue(subArea.getAssistKeyWords());
				
				rowIndex++;
			}
			book.write(out);
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		
	}

	@Override
	public List findChart() {
		// TODO Auto-generated method stub
		return dao.findChart();
	}

}
