<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<shiro:hasPermission name="courier-search">
  <button>查询</button>
</shiro:hasPermission>

<shiro:hasPermission name="courier-add">
  <button>新增</button>
</shiro:hasPermission>

<shiro:hasRole name="courier">
  <button>修改</button>
</shiro:hasRole>

<shiro:hasRole name="admin">
  <button>删除</button>
</shiro:hasRole>
</body>
</html>