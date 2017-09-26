package cn.itcast.bos.action.base;

import java.io.IOException;
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

import cn.itcast.bos.domain.base.Standard;
import cn.itcast.bos.service.base.StandardService;


@Controller
@ParentPackage("struts-default")
@Namespace("/")
@Scope("prototype")
public class StandardAction extends BaseAction<Standard> {
	
	@Autowired
	private StandardService service;
	
	
//	@Action(value="standardAction_save",results={@Result(name="success",type="redirect",location="/pages/base/standard.html"),
//			@Result(name="error",type="redirect",location="/pages/base/error.html")})
	@Action("standardAction_save")
	public void save() throws IOException{
		try {
			service.save(model);
			javaToJson(ajaxReturn(true, "操作成功"), null);
		} catch (Exception e) {
			javaToJson(ajaxReturn(false, "操作失败"), null);
		}
		
	}
	
	@Action("standardAction_findByPage")
	public void findByPage(){
		Pageable pageable = new  PageRequest(page-1, rows);
		Page page = service.findByPage(pageable);
//		拼装datagrid分页时需要的数据格式
//		{"total":100,"rows":[{},{},{}]}
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("total", page.getTotalElements());
		map.put("rows", page.getContent());
		javaToJson(map, null);
	}
	
	
	@Action("standardAction_findAll")
	public void findAll(){
		List<Standard> list=service.findAll();
		javaToJson(list, null);
	}

}
