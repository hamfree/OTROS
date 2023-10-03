<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>¡Hola Mundo!</title>
</head>
<body>
    <!-- Recoge el valor de 'package.properties' en src/main/resources/org/apache/struts/package.properties -->
    <h1>
        <s:text name="greeting" />
    </h1>
    <h2>
        <s:property value="messageStore.message" />
    </h2>
    <p>
        ¡He dicho hola
        <s:property value="contadorSaludos" />
        veces!
    </p>
    <p>
        <s:property value="messageStore" />
    </p>
    <p>
		<a href='<s:url action="index" />'>Volver al Inicio</a>
	</p>
</body>
</html>