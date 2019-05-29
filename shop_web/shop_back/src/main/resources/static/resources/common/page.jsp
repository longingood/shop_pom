<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<!--  1.引入样式文件 -->
<link rel="stylesheet" href="js/layui/css/layui.css" media="all">
</head>
<!-- 2.引入js文件 -->
<script src="js/layui/layui.js"></script>
<body>
	<!-- 这个div专门用来 显示分页导航条-->
	<div id="test1"></div>
	<script>

// 初始化的分页导航条
layui.use('laypage', function(){
  var laypage = layui.laypage;
	laypage.render({
	    elem: 'test1' // 显示分页导航条元素的id
	    ,count: "${page.totalElements}" // 总条数
	    ,limit:"${page.size}" // 每页显示5条
  		,curr:"${page.number+1}" // 当前页
	    ,layout: ['prev', 'page', 'next', 'limit','count']// 布局
	    ,jump: function(obj, first){ // 
		        //首次不执行
		        if(!first){
		        	location.href="${url}pageNum="+obj.curr+"&pageSize="+obj.limit; //UserServlet/getUserPage
		        }
	       	}
	  });
});
</script>
</body>
</html>