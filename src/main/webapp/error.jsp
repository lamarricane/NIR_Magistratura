<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page errorPage="error.jsp" %>
<%@ page isErrorPage="true" %>
<%
   String message = pageContext.getException().getMessage();
%>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Exception page</title>
    </head>
    <form>
        <body style="background-image: url('img.png');">
            <div style="font-size: 20px; text-align: center; padding: 30px; margin: 50px; border: 1px solid black; border-radius: 5px; display: flex; flex-direction: column; justify-content: center;">
                <h2> Attention! </h2>
                <p>Message: <%= message %></p>
            </div>
        </body>
    </form>
</html>
