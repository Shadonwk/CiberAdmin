<%@ page contentType="text/html;charset=ISO-8859-1" %>
<html>
<head>
   <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"/>
   <title>Inicio de sesión</title>
</head>
<body>
<div id="login" class="login" align="center">
    <g:if test="${flash.message}">
        <div class="errors">${flash.message}</div>

    </g:if>
    <form action="${postUrl}" method="POST" id="loginForm" class="cssform" autocomplete="off">
        <fieldset id="datosLogin" class="grupoCatalogo">
            <table id="tableLogin">
                <tbody><tr>
                    <td><label>Nombre:</label></td>
                    <td><g:textField id="username" name="j_username" value="admin"></g:textField></td>
                </tr>
                <tr>
                    <td><label>Contrasena: </label></td>
                    <td><g:passwordField id="password" name="j_password" value="admin"></g:passwordField></td>
                </tr>
                </tbody></table>
        </fieldset>
        <input type="submit" id="submit" title="Aceptar" value="Iniciar">
    </form>
</div>

<script type="text/javascript">
    <!--
    (function() {
        document.forms['loginForm'].elements['j_username'].focus();
    })();
    // -->  
</script>
</body>
</html>