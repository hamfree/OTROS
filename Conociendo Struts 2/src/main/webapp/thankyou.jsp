<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Registro exitoso</title>
</head>
<body>
	<h3>
		<s:text name="thankyou" />
	</h3>
	<p>
		Su información de registro:
		<s:property value="personaBean" />
	</p>
	<p>
		<a href="<s:url action='index' />">Regresar a la página principal</a>.
	</p>
</body>
</html>