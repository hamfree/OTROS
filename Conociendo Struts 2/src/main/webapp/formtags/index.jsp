<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Bienvenido - Etiquetas de Formulario Struts 2</title>
</head>
<body>
	<h1>¡Bienvenido a Struts 2!</h1>
	<p>
		<a href='<s:url action="editar" />'>Edite su información</a>
	</p>
	<p>
		<a href='<s:url action="index" />'>Volver al Inicio</a>
	</p>
</body>
</html>