package cn.itcast.bos.action.base;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

public class BaseAction<T> extends ActionSupport implements ModelDriven<T>{

	protected T model;
	@Override
	public T getModel() {
		return model;
	}
	public BaseAction(){
		//this.getClass()--> cn.itcast.bos.action.base.AreaAction
		Type type = this.getClass().getGenericSuperclass(); //父类的参数化类型  BaseAction<Area>
		ParameterizedType ptype = (ParameterizedType) type;
		Type[] types = ptype.getActualTypeArguments();   //真实的参数 <Area>
		Class<T> classs = (Class<T>) types[0];
		try {
			model = classs.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
	protected int page;
	protected int rows;
	public void setPage(int page) {
		this.page = page;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}
	
	public void javaToJson(Map map,String [] strings){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=utf-8");
		JsonConfig jsonconfig = new JsonConfig();
		jsonconfig.setExcludes(strings);
		String string = JSONObject.fromObject(map,jsonconfig).toString();
		try {
			response.getWriter().write(string);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void javaToJson(List list,String [] strings){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=utf-8");
		JsonConfig jsonconfig = new JsonConfig();
		jsonconfig.setExcludes(strings);
		String string = JSONArray.fromObject(list,jsonconfig).toString();
		try {
			response.getWriter().write(string);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Map ajaxReturn(boolean success,String message){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("success", success);
		map.put("message",message);
		return map;
	}
	
}
