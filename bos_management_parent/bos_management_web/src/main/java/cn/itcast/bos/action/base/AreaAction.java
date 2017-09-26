package cn.itcast.bos.action.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import cn.itcast.bos.domain.base.Area;
import cn.itcast.bos.service.base.AreaService;
@Controller
@ParentPackage("struts-default")
@Namespace("/")
@Scope("prototype")
public class AreaAction extends BaseAction<Area> {

	private File myFile;
	public File getMyFile() {
		return myFile;
	}

	public void setMyFile(File myFile) {
		this.myFile = myFile;
	}

	@Autowired
	private AreaService service;
	
	@Action("areaAction_importXls")
	public void importXls() throws FileNotFoundException{
		service.importXls(new FileInputStream(myFile));
	}
	
	
	

	@Action("areaAction_findByPage")
	public void findByPage(){
		Pageable pageable = new PageRequest(page-1, rows);
		Page<Area> page = service.findByPage(pageable);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", page.getTotalElements());
		map.put("rows", page.getContent());
		javaToJson(map, new String[]{"subareas"});
	}
	
	private String q;
	public void setQ(String q) {
		this.q = q;
	}

	@Action("areaAction_findAll")
	public void findAll(){
		System.out.println("传过来的参数q："+q);
		List<Area> list = null;
		if(q==null){
		list  = service.findAll();
		}else{
		 list  = service.findByQ(q);
		}
		
		javaToJson(list, new String[]{"subareas"});
	}

	
	
}
