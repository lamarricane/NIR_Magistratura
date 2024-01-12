<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="java.util.List"%>
<%@ page import="model.Publisher"%>
<html>
    <head>
        <title> Publishers </title>
        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@ page isELIgnored="false"%>
        <link rel="stylesheet" type="text/css" href="style.css"/>
            <style>
            .text-field {
              margin-bottom: 1rem;
            }

            .text-field__label {
              display: block;
              margin-bottom: 0.25rem;
              margin: 1px 1px 1px 1px;
            }

            .text-field__input {
              margin: 2px 2px 2px 2px;
              display: block;
              width: 20%;
              height: calc(2.25rem + 2px);
              padding: 0.375rem 0.75rem;
              font-family: inherit;
              font-size: 1rem;
              font-weight: 300;
              line-height: 1.5;
              color: #212529;
              background-color: #fff;
              background-clip: padding-box;
              border: 1px solid #bdbdbd;
              border-radius: 0.25rem;
              transition: border-color 0.15s ease-in-out, box-shadow 0.15s ease-in-out;
            }
            button.button7 {
              font-weight: 700;
              color: white;
              text-decoration: none;
              padding: .8em 1em calc(.8em + 3px);
              border-radius: 3px;
              background: rgb(64,199,129);
              box-shadow: 0 -3px rgb(53,167,110) inset;
              transition: 0.2s;
            }
            button.button7:hover { background: rgb(53, 167, 110); }
            button.button7:active {
              background: rgb(33,147,90);
              box-shadow: 0 3px rgb(33,147,90) inset;
            }
            .row {
            	white-space: normal;
            	display: inline-block;
            	width: 50%;
            	vertical-align: top;
            	margin-right: 5%;
            	font-size: 14px;
            }
            .form-group {
                display: flex;
                flex-direction: row;
                justify-content: center;
                align-items: center;
                margin: 3px 3px 0 0;
                width: 90%;
                height: 7%;
            }
            label {
                font: 14pt Impact;
                color: black;
            }
            </style>
    </head>
<body style="background-image: url('change_publisher.jpg');">
<%
    List<Publisher> publishers = (List) request.getAttribute("publishers");
%>
    <div class="container">
        <div class="row" align="center">
            <table class="table_dark">
                <thread>
                    <tr>
                        <th>ID Publisher</th>
                        <th>Name</th>
                        <th>Location</th>
                    </tr>
                </thread>
                <body>
                    <% for(Publisher publisher : publishers) { %>
                        <tr>
                            <td style="text-align:center"> <%= publisher.getId() %> </td>
                            <td style="text-align:center"> <%= publisher.getName() %> </td>
                            <td style="text-align:center"> <%= publisher.getLocation() %> </td>
                        <tr>
                    <% } %>
                </body>
            </table>
        </div>
        <div class="row">
            <form method="post" action="publisher">
                <div class="text-field">
                    <div class ="form-group">
                        <label>Delete publisher with name: </label>
                        <input type="text" name="deleteName" class="text-field__input" placeholder="name">
                    </div>
                    <div class ="form-group">
                        <label>Update publisher with name:</label>
                        <input type="text" name="updateName" class="text-field__input" placeholder="name">
                        <input type="text" name="newName" class="text-field__input" placeholder="new name">
                        <input type="text" name="newLocation" class="text-field__input" placeholder="new location">
                    </div>
                    <div class ="form-group">
                        <label class="text-field__label">Insert publisher:</label>
                        <input type="text" name="insertName" class="text-field__input" placeholder="name">
                         <input type="text" name="insertLocation" class="text-field__input" placeholder="location">
                    </div>
                    <p align="center">
                        <button a href="http://localhost:8080/" class="button7" type="submit" >Save changes</button>
                        <a href="/" class="button7">Back</a>
                    </p>
                </div>
            </form>
        </div>
    </div>
</body>
</html>
