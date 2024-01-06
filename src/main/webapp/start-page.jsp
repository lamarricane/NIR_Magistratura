<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="java.util.List"%>
<%@ page import="model.Book"%>
<%@ page import="model.Author"%>

<html>
<form>
    <head>
        <title> Добро пожаловать в каталог книг </title>
        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@ page isELIgnored="false"%>
        <link rel="stylesheet" type="text/css" href="style.css"/>
    </head>

<body style="background-image: url('start_page.jpg');">
<%
    List<Author> authors = (List) request.getAttribute("authors");
    List<Book> books = (List) request.getAttribute("books");
%>
<div class="container">
    <div class="row" align="center">
        <table class="table_dark">
            <thread>
            <tr>
                <th>ID Author</th>
                <th>Name</th>
                <th>Birth Date</th>
            </tr>
            </thread>
            <body>
                  <% for(Author author : authors) { %>
                     <tr>
                        <td style="text-align:center"> <%= author.getId() %> </td>
                        <td style="text-align:center"> <%= author.getName() %> </td>
                        <td style="text-align:center"> <%= author.getBirthDate() %> </td>
                     <tr>
                  <% } %>
            </body>
        </table>
        <div class="buttons" style="text-align: center;">
            <p>
                <a href="author" class="button7">Change authors</a>
            </p>
        </div>
    </div>
    <div class="row" align="center">
        <table class = "table_dark">
            <thread>
            <tr>
                <th>Author Name</th>
                <th>Book Name</th>
                <th>Genre</th>
                <th>Number of Pages</th>
                <th>Publishing House id</th>
                <th>Year of Publishing</th>
            </tr>
            </thread>
            <body>
                  <% for(Book book : books) { %>
                     <tr>
                        <td style="text-align:center"> <%= book.getAuthor().getName() %> </td>
                        <td style="text-align:center"> <%= book.getName() %> </td>
                        <td style="text-align:center"> <%= book.getGenre() %> </td>
                        <td style="text-align:center"> <%= book.getNumberOfPages() %> </td>
                        <td style="text-align:center"> <%= book.getPublishingHouseId() %> </td>
                        <td style="text-align:center"> <%= book.getYearOfPublishing() %> </td>
                     <tr>
                  <% } %>
            </body>
        </table>
        <div class="buttons" style="text-align: center;">
            <p>
                <a href="book" class="button7">Change books</a>
            </p>
        </div>
    </div>
</div>

</body>
</form>
</html>
