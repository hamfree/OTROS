<?xml version="1.0" encoding="UTF-8" ?>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Actualización exitosa</title>
</head>
<body>
	<h1>Información Actualizada</h1>
	<p>
		Su información:<br />
		<s:property value="personaBean" />
	</p>
	<p>
		<a href="<s:url action='index' />">Volver a la página de inicio</a>.
	</p>
</body>
</html>