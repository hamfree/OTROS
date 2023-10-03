<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<s:head />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Struts 2 Etiquetas de Formulario con Validación - Editar
	Persona</title>
</head>
<body>
	<h1>Actualize la información</h1>
	<p>Use el formulario de abajo para editar su información.</p>
	<s:form action="salvarFormulario" method="post">
		<s:textfield key="personaBean.nombre" />
		<s:textfield key="personaBean.apellidos" />
		<s:textfield key="personaBean.correo" />
		<s:textfield key="personaBean.telefono" />
		<s:textfield key="personaBean.edad" />
		<s:select key="personaBean.deporte" list="deportes" />
		<s:radio key="personaBean.genero" list="generos" />
		<s:select key="personaBean.residencia" list="estados" listKey="abreviaturaEstado"
			listValue="nombreEstado" />
		<s:checkbox key="personaBean.mayorde21" />
		<s:checkboxlist key="personaBean.modelosCoche" list="modelosCocheDisponibles" />
		<s:submit key="submit" />
	</s:form>

</body>
</html>