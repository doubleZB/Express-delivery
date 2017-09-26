package cn.itcast.bos.action.base;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;

import cn.itcast.bos.domain.base.Courier;
import cn.itcast.bos.service.base.CourierService;

@Controller
@ParentPackage("struts-default")
@Namespace("/")
@Scope("prototype")
public class CourierAction extends BaseAction<Courier> {

	@Autowired
	private CourierService service;
	
	@Action("courierAction_save")
	public void save() throws IOException{
		try {
			service.save(model);
			javaToJson(ajaxReturn(true, "操作成功"), null);
		} catch (Exception e) {
			javaToJson(ajaxReturn(false, "操作失败"), null);
		}
	}
	
	@Action("courierAction_findByPage")
	public void findByPage(){
//		条件查询
//		model.getCourierNum();//工号
//		model.getStandard().getName();  收派标准的名称
//		model.getType(); //类型
//		model.getCompany()  //公司
		
//		Specification  相当于 DetachedCriteria
//		DetachedCriteria dc = DetachedCriteria.forClass(Courier.class);
//		if(StringUtils.isNotBlank(model.getCourierNum())){
//			dc.add(Restrictions.like("courierNum", model.getCourierNum()));
//		}
		
//		model.getCourierNum();//工号
//		model.getStandard().getName();  收派标准的名称
//		model.getType(); //类型
//		model.getCompany()  //公司
		Specification<Courier> specification =new Specification<Courier>() {
			@Override
			public Predicate toPredicate(Root<Courier> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
//				Root<Courier> root 查询的主体对象  看作 courier,
//				CriteriaQuery<?> query,  组装条件
//				CriteriaBuilder cb    组装条件  相当于 Restrictions
//				model.getCourierNum();//工号
				
//				准备一个list集合，为了接收组装的条件
				List<Predicate> list = new ArrayList<Predicate>();
				if(StringUtils.isNotBlank(model.getCourierNum())){
					Predicate predicate1 = cb.like(root.get("coutierNum").as(String.class), "%"+model.getCourierNum()+"%");
					list.add(predicate1);
				}
//				model.getType(); //类型
				if(StringUtils.isNotBlank(model.getType())){
					Predicate predicate2 = cb.like(root.get("type").as(String.class), "%"+model.getType()+"%");
					list.add(predicate2);
				}
//				model.getCompany()  //公司
				if(StringUtils.isNotBlank(model.getCompany())){
					Predicate predicate3 = cb.like(root.get("company").as(String.class), "%"+model.getCompany()+"%");
					list.add(predicate3);
				}
//				model.getStandard().getName();  收派标准的名称
				if(model.getStandard()!=null&&StringUtils.isNotBlank(model.getStandard().getName())){
//					select c.* from courier c inner join standard s where c.standardId=s.id where s.name=?
					Join<Object, Object> join = root.join("standard"); //当做standard对象
					Predicate predicate4 = cb.like(join.get("name").as(String.class), "%"+model.getStandard().getName()+"%");
					list.add(predicate4);
				}
				
//				判断list的长度，如果长度为0代表一个条件也没有组装直接return null
				if(list.size()==0){
					return null;
				}
//				list转成数组再 转成对象
				Predicate[] predicates = new Predicate[list.size()];
				predicates = list.toArray(predicates);
				return cb.and(predicates);
			}
		};
		
		
		Pageable pageable = new  PageRequest(page-1, rows);
		Page page = service.findByPage(specification,pageable);
//		拼装datagrid分页时需要的数据格式
//		{"total":100,"rows":[{},{},{}]}
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("total", page.getTotalElements());
		map.put("rows", page.getContent());
//		设置在转json时排除的字段
		javaToJson(map, new String[]{"fixedAreas"});
	}
	
	private String ids;//前端传来的需要作废的ids
	public void setIds(String ids) {
		this.ids = ids;
	}
	@Action("courierAction_deleteBatch")
	public void deleteBatch() throws IOException{
		try {
			service.deleteBatch(ids);
			javaToJson(ajaxReturn(true, "操作成功"), null);
		} catch(UnauthorizedException e){
			javaToJson(ajaxReturn(false, "无此权限"), null);
		}catch (Exception e) {
			javaToJson(ajaxReturn(false, "操作失败"), null);
		}
	}
	
	@Action("courierAction_findByDeltagIsNull")
	public void findByDeltagIsNull() throws IOException{
			List<Courier> list = service.findByDeltagIsNull();
			javaToJson(list, new String[]{"fixedAreas"});
	}
	

}
