package cn.itcast.bos.service.base.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.base.AreaDao;
import cn.itcast.bos.domain.base.Area;
import cn.itcast.bos.service.base.AreaService;
import cn.itcast.bos.utils.PinYin4jUtils;

@Service
@Transactional
public class AreaServiceImpl implements AreaService {

	@Autowired
	private AreaDao dao;

	@Override
	public void importXls(FileInputStream fileInputStream) {
//		1、获取workbook对象
		try {
		HSSFWorkbook book = new HSSFWorkbook(fileInputStream);
        HSSFSheet sheet = book.getSheetAt(0);
        int lastRowNum = sheet.getLastRowNum();
        Area area  = null;
        List<Area> list = new ArrayList<Area>();
        for (int i = 1; i <= lastRowNum; i++) {
      	  HSSFRow row = sheet.getRow(i);
      	  String id = row.getCell(0).getStringCellValue();
      	  String province = row.getCell(1).getStringCellValue();
      	  String city = row.getCell(2).getStringCellValue();
      	  String district = row.getCell(3).getStringCellValue();
      	  String postcode = row.getCell(4).getStringCellValue();
      	  area = new Area();
      	  area.setId(id);
      	  area.setProvince(province);
      	  area.setCity(city);
      	  area.setDistrict(district);
      	  area.setPostcode(postcode);
      	  
	      province = province.substring(0, province.length()-1);
	      city = city.substring(0, city.length()-1);
	      district = district.substring(0, district.length()-1);
	      //	      城市编码
	      String citycode = PinYin4jUtils.hanziToPinyin(city,"");
	      area.setCitycode(citycode);
	        //          简码
	      String[] headByString = PinYin4jUtils.getHeadByString(province+city+district);
	      String shortcode = StringUtils.join(headByString);
	      area.setShortcode(shortcode);
	      	 list.add(area);
	    }
        dao.save(list);
		} 
        catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Page<Area> findByPage(Pageable pageable) {
		return dao.findAll(pageable);
	}

	@Override
	public List<Area> findAll() {
		return dao.findAll();
	}

	@Override
	public List<Area> findByQ(String q) {
//		select t.*, t.rowid from T_AREA t  where c_citycode like lower('%?%') 
//		  or  c_shortcode like upper('%?%')
		return dao.findByCitycodeLikeOrShortcodeLike("%"+q.toLowerCase()+"%","%"+q.toUpperCase()+"%");
	}
	
	
}
