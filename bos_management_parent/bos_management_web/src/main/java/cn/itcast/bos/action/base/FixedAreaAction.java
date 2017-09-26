package cn.itcast.bos.action.base;

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

import cn.itcast.bos.domain.base.FixedArea;
import cn.itcast.bos.service.base.FixedAreaService;
import cn.itcast.crm.ws.Customer;
import cn.itcast.crm.ws.impl.CustomerService;

@Controller
@ParentPackage("struts-default")
@Namespace("/")
@Scope("prototype")
public class FixedAreaAction extends BaseAction<FixedArea> {

	@Autowired
	private FixedAreaService service;
	
	
	private String ids;
	public void setIds(String ids) {
		this.ids = ids;
	}
	@Action("fixedAreaAction_save")
	public void save(){
		try {
			service.save(model,ids);
			javaToJson(ajaxReturn(true, "操作成功"), null);
		} catch (Exception e) {
			javaToJson(ajaxReturn(false, "操作失败"), null);
			e.printStackTrace();
		}
	}
	private int courierId;
	private int takeTimeId;
	public void setCourierId(int courierId) {
		this.courierId = courierId;
	}
	public void setTakeTimeId(int takeTimeId) {
		this.takeTimeId = takeTimeId;
	}
	@Action("fixedAreaAction_associationCourierToFixedArea")
	public void associationCourierToFixedArea(){
		try {
			service.associationCourierToFixedArea(model.getId(),courierId,takeTimeId);
			javaToJson(ajaxReturn(true, "操作成功"), null);
		} catch (Exception e) {
			javaToJson(ajaxReturn(false, "操作失败"), null);
			e.printStackTrace();
		}
	}
	@Action("fixedAreaAction_findByPage")
	public void findByPage(){
		Pageable pageable = new PageRequest(page-1, rows);
		Page page = service.findByPage(pageable);
		Map<String, Object>  map= new HashMap<String, Object>();
		map.put("total", page.getTotalElements());
		map.put("rows", page.getContent());
		javaToJson(map, new String[]{"subareas","couriers"});
		
	}
	
//	注入crm服务的代理对象
	@Autowired
	private CustomerService crmProxy;
	
//	查询未关联定区的客户
	@Action("fixedAreaAction_findByFixedAreaIdIsNull")
	public void findByFixedAreaIdIsNull(){
		List<Customer> list = crmProxy.findByFixedAreaIdIsNull();
		javaToJson(list, null);
	}
	
//	查询关联指定定区的客户
	@Action("fixedAreaAction_findByFixedAreaId")
	public void findByFixedAreaId(){
		List<Customer> list = crmProxy.findByFixedAreaId(model.getId());
		javaToJson(list, null);
		
	}
	
	private String customerIds;
	public void setCustomerIds(String customerIds) {
		this.customerIds = customerIds;
	}
	//	保存关联指定定区的客户
	@Action("fixedAreaAction_assigncustomerstodecidedzone")
	public void assigncustomerstodecidedzone(){
		try {
			crmProxy.assigncustomerstodecidedzone(model.getId(), customerIds);
			javaToJson(ajaxReturn(true, "操作成功"), null);
		} catch (Exception e) {
			javaToJson(ajaxReturn(false, "操作失败"), null);
			e.printStackTrace();
		}
	}
}
