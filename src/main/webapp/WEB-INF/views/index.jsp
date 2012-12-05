<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" session="false" %><%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %><%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %><%@taglib prefix="spring" uri="http://www.springframework.org/tags" %><%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%><%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<spring:eval expression="@applicationProps['application.version']" var="applicationVersion"/><spring:url value="/resources-{applicationVersion}" var="resourceUrl"><spring:param name="applicationVersion" value="${applicationVersion}"/></spring:url>
<!DOCTYPE html>
<!--[if lt IE 7]>      <html class="no-js lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]>         <html class="no-js lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]>         <html class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!--> <html class="no-js"> <!--<![endif]-->
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
        <title><fmt:message key="welcome.title"/></title>
        <meta name="description" content="">
        <meta name="viewport" content="width=device-width">
        <link rel="stylesheet" href="${resourceUrl}/css/normalize.css">
        <link rel="stylesheet" href="${resourceUrl}/css/main.css">
        <script src="${resourceUrl}/js/vendor/modernizr-2.6.2.min.js"></script>
    </head>
    <body>
        <!--[if lt IE 7]>
            <p class="chromeframe">You are using an <strong>outdated</strong> browser. Please <a href="http://browsehappy.com/">upgrade your browser</a> or <a href="http://www.google.com/chromeframe/?redirect=true">activate Google Chrome Frame</a> to improve your experience.</p>
        <![endif]-->

        <!-- Add your site or application content here -->
        <p>Hello world! This is HTML5 Boilerplate.</p>
        <h1>
		<fmt:message key="welcome.title"/>
		</h1>
		<p>
			Locale = ${pageContext.response.locale}
		</p>
		<hr>	
		<ul>
			<li> <a href="?locale=en_us">us</a> |  <a href="?locale=en_gb">gb</a> | <a href="?locale=es_es">es</a> | <a href="?locale=de_de">de</a> </li>
		</ul>
		<ul>
			<li><a href="account">@Controller Example</a></li>
		</ul>

        <script src="//ajax.googleapis.com/ajax/libs/jquery/1.8.2/jquery.min.js"></script>
        <script>window.jQuery || document.write('<script src="${resourceUrl}/js/vendor/jquery-1.8.2.min.js"><\/script>')</script>
        <script src="${resourceUrl}/js/plugins.js"></script>
        <script src="${resourceUrl}/js/main.js"></script>

        <!-- Google Analytics: change UA-XXXXX-X to be your site's ID. -->
        <script>
            var _gaq=[['_setAccount','UA-XXXXX-X'],['_trackPageview']];
            (function(d,t){var g=d.createElement(t),s=d.getElementsByTagName(t)[0];
            g.src=('https:'==location.protocol?'//ssl':'//www')+'.google-analytics.com/ga.js';
            s.parentNode.insertBefore(g,s)}(document,'script'));
        </script>
    </body>
</html>
