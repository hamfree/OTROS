<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Struts 2 Etiquetas de Formulario con Validación -
	Bienvenida</title>
</head>
<body>
	<h1>¡Bienvenida a Struts 2!</h1>
	<p>
		<a href='<s:url action="editarFormulario" />'>Edite su información</a>
	</p>
	<p>
		<a href='<s:url action="index" />'>Volver al Inicio</a>
	</p>
</body>
</html>