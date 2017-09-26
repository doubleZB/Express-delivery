package cn.itcast.bos.action.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.aspectj.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import cn.itcast.bos.domain.base.SubArea;
import cn.itcast.bos.service.base.SubAreaService;
import cn.itcast.bos.utils.FileUtils;
@Controller
@ParentPackage("struts-default")
@Namespace("/")
@Scope("prototype")
public class SubAreaAction extends BaseAction<SubArea> {
	
	@Autowired
	private SubAreaService service;
	
	@Action("subAreaAction_save")
	public void save(){
		try {
			service.save(model);
			javaToJson(ajaxReturn(true, "操作成功"), null);
		} catch (Exception e) {
			javaToJson(ajaxReturn(false, "操作失败"), null);
			e.printStackTrace();
		}
	}
	
	@Action("subAreaAction_findByPage")
	public void findByPage(){
		Pageable pageable = new PageRequest(page-1, rows);
		Page<SubArea> page = service.findByPage(pageable);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", page.getTotalElements());
		map.put("rows", page.getContent());
		javaToJson(map, new String[]{"subareas","couriers"});
	}
	@Action("subAreaAction_findAll")
	public void findAll(){
		List<SubArea> list = service.findAll();
		javaToJson(list, new String[]{"subareas","couriers"});
	}
	
	@Action("subAreaAction_chart")
	public void chart(){
		List list = service.findChart();
		javaToJson(list, null);
	}
	
	
	@Action("subAreaAction_exportXls")
	public void exportXls() throws Exception{
//		获取导出需要的的模板  File.separator \    /   
		String filePath =ServletActionContext.getServletContext().getRealPath(File.separator)+"template"+File.separator+"subArea.xls";
		FileInputStream in = new FileInputStream(filePath);
		
		HttpServletResponse response = ServletActionContext.getResponse();
		ServletOutputStream outputStream = response.getOutputStream();
//		一个流两个头
//	          一个是mime   application/vnd.ms-excel 可以省略
//		一个是文件的打开方式   inline 在浏览器中打开   attchment
		String agent = ServletActionContext.getRequest().getHeader("User-Agent");
		String  fileName = FileUtils.encodeDownloadFilename("分区数据.xls", agent);
		response.setHeader("content-disposition", "attachment;fileName="+fileName);
		service.exportXls(in,outputStream);
	}

}
