<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Conociendo Struts 2 - Bienvenido</title>
<s:head />
</head>
<body>
	<h1>¡Bienvenido a Struts 2!</h1>
	<h2>Tutorial de Struts 2
		(https://struts.apache.org/getting-started/)</h2>
	<ol>
		<li><p>
				El típico primer ejemplo en cualquier tutorial de Informática: <a
					href="<s:url action='hello'/>">Hola Mundo</a>
			</p></li>
		<li><p>
				Ahora con parámetros:
				<s:url action="hello" var="helloLink">
					<s:param name="userName">Bruce Phillips</s:param>
				</s:url>
				<a href="${helloLink}">Hola Bruce Phillips</a>
			</p></li>
		<li><p>Formulario básico. Obtenga su propio saludo personal
				rellenando y enviando este formulario.</p> <s:form action="hello"
				cssStyle="text-align:left;width:50%;border: 2px double black;">
				<s:textfield name="userName" label="Su nombre" />
				<s:submit value="Submit" />
			</s:form></li>
		<li><p>Usando formularios en Struts.</p>
			<p>
				<s:url action="registerInput" var="registerInputLink" />
				<a href="${registerInputLink}">Por favor regístrese</a> para nuestro
				sorteo.
			</p></li>
		<li><p>Internacionalización (I18N). Registro en español.</p>
			<p>
				<s:url action="registerInput" var="registerInputLinkES">
					<s:param name="request_locale">es</s:param>
				</s:url>
				<a href="${registerInputLinkES}">Por favor, regístrese</a> para
				nuestro sorteo
			</p></li>
		<li><p>Etiquetas para Formulario</p>
			<p>
				<s:url action="inicio" var="enlaceInicio" />
				<a href="${enlaceInicio}">Acceso a registro</a>
			</p></li>
		<li><p>Validación XML en Formularios</p>
			<p>
				<s:url action="indiceFormXmlValidation" var="enlaceInicio2" />
				<a href="${enlaceInicio2}">Acceso a registro con validación</a>
			</p></li>
	</ol>

	<h3>Depuración</h3>
	<ul>
		<li><a href='<s:url action="index" namespace="config-browser" />'>Lanza
				el navegador de configuración (depuracion)</a></li>

		<li><s:url action="index" var="indexLink">
				<s:param name="debug">browser</s:param>
			</s:url>
			<p>
				<a href="${indexLink}">Carga esta página con depuración</a>
			</p></li>
		<s:debug />
	</ul>
	<hr />
	<s:text name="contact" />
</body>
</html>