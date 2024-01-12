<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="java.util.List"%>
<%@ page import="model.Book"%>
<html>
    <head>
        <title> Books </title>

	    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
	    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.0/umd/popper.min.js"></script>

        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@ page isELIgnored="false"%>
        <link rel="stylesheet" type="text/css" href="style.css"/>
            <style>
            .text-field {
              margin-bottom: 1rem;
              border: 3px
            }

            .text-field__label {
              border: 3px
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
              font-weight: 100;
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
            <script>
                var insertAuthorsNames;
                var updateAuthorsNames;

				function setInsertAuthors(input) {
					let result = "";
					getInsertNames(input);
					for (var i = 0; i < insertAuthorsNames.length; i++) {
						result += "<option>" + insertAuthorsNames[i] + "</option>";
					}
					document.getElementById("insertElements").innerHTML = result;
				}

                function getInsertNames(input) {
                    if (input != "") {
                	    $.ajax({
                            type: "GET",
                            url: "autofill?autofillAuthors=" + input,
                            async: false,
                            dataType: "json",
                            success: function (data) {
                                insertAuthorsNames = data;
                            }
    				    });
    				}
                }

				function setUpdateAuthors(input) {
					let result = "";
					getUpdateNames(input);
					for (var i = 0; i < updateAuthorsNames.length; i++) {
						result += "<option>" + updateAuthorsNames[i] + "</option>";
					}
					document.getElementById("updateElements").innerHTML = result;
				}

                function getUpdateNames(input) {
                    if (input != "") {
                	    $.ajax({
                            type: "GET",
                            url: "autofill?autofillAuthors=" + input,
                            async: false,
                            dataType: "json",
                            success: function (data) {
                                updateAuthorsNames = data;
                            }
    				    });
    				}
                }
            </script>
            <script>
                var insertPublishersNames;
                var updatePublishersNames;

				function setInsertPublishers(input) {
					let result = "";
					getInsertNames(input);
					for (var i = 0; i < insertPublishersNames.length; i++) {
						result += "<option>" + insertPublishersNames[i] + "</option>";
					}
					document.getElementById("insertElements").innerHTML = result;
				}

                function getInsertNames(input) {
                    if (input != "") {
                	    $.ajax({
                            type: "GET",
                            url: "autofill?autofillPublishers=" + input,
                            async: false,
                            dataType: "json",
                            success: function (data) {
                                insertPublishersNames = data;
                            }
    				    });
    				}
                }

				function setUpdatePublishers(input) {
					let result = "";
					getUpdateNames(input);
					for (var i = 0; i < updatePublishersNames.length; i++) {
						result += "<option>" + updatePublishersNames[i] + "</option>";
					}
					document.getElementById("updateElements").innerHTML = result;
				}

                function getUpdateNames(input) {
                    if (input != "") {
                	    $.ajax({
                            type: "GET",
                            url: "autofill?autofillPublishers=" + input,
                            async: false,
                            dataType: "json",
                            success: function (data) {
                                updatePublishersNames = data;
                            }
    				    });
    				}
                }
            </script>
    </head>
<body style="background-image: url('change_book.jpg');">
<%
    List<Book> books = (List) request.getAttribute("books");
%>
    <div class="container">
        <div class="row" align="center">
            <table class="table_dark">
                <thread>
                    <tr>
                        <th>ID Book</th>
                        <th>Name</th>
                        <th>Genre</th>
                        <th>Author</th>
                        <th>Publisher</th>
                        <th>Number of pages</th>
                        <th>Year of publishing</th>
                    </tr>
                </thread>
                <body>
                    <% for(Book book : books) { %>
                        <tr>
                            <td style="text-align:center"> <%= book.getId() %> </td>
                            <td style="text-align:center"> <%= book.getName() %> </td>
                            <td style="text-align:center"> <%= book.getGenre() %> </td>
                            <td style="text-align:center"> <%= book.getAuthor().getName() %> </td>
                            <td style="text-align:center"> <%= book.getPublisher().getName() %> </td>
                            <td style="text-align:center"> <%= book.getNumberOfPages() %> </td>
                            <td style="text-align:center"> <%= book.getYearOfPublishing() %> </td>
                        <tr>
                    <% } %>
                </body>
            </table>
        </div>
        <div class="row">
            <form method="post" action="book">
                <div class="text-field">
                        <div class ="form-group">
                            <label>Delete book with Name:</label>
                            <input type="text" name="deleteName" class="text-field__input" placeholder="name">
                        </div>
                        <div class ="form-group">
                            <label>Update book with name:</label>
                            <input type="text" name="updateName" class="text-field__input" placeholder="name">
                        </div>
                        <div class ="form-group">
                            <label>New data:</label>
                            <input type="text" name="newName" class="text-field__input" placeholder="new name">
                            <input type="text" name="updateGenre" class="text-field__input" placeholder="new genre">
                            <input list="listUpdateAuthors" type="text" id="updateAuthor" name="updateAuthor" class="text-field__input" placeholder="new author">
                            <input list="listUpdatePublishers" type="text" id="updatePublisher" name="updatePublisher" class="text-field__input" placeholder="new publisher">
                            <input type="text" name="updateNumberOfPages" class="text-field__input" placeholder="new number of pages">
                            <input type="text" name="updateYearOfPublishing" class="text-field__input" placeholder="new year of publishing">
                            <script>
							    var input1 = document.getElementById("updateAuthor");
								input1.oninput = function () {
								    setUpdateAuthors(input1.value);
								}
							</script>
                            <datalist id="listUpdateAuthors">
                                <div id="updateElements"></div>
                            </datalist>
                            <script>
							    var input1 = document.getElementById("updatePublisher");
								input1.oninput = function () {
								    setUpdatePublishers(input1.value);
								}
							</script>
                            <datalist id="listUpdatePublishers">
                                <div id="updateElements"></div>
                            </datalist>
                        </div>
                        <div class ="form-group">
                            <label>Insert book:</label>
                            <input type="text" name="insertName" class="text-field__input" placeholder="name">
                            <input type="text" name="insertGenre" class="text-field__input" placeholder="genre">
                            <input list="listInsertAuthors" type="text" name="insertAuthor" id="insertAuthor" class="text-field__input" placeholder="author">
                            <input list="listInsertPublishers" type="text" name="insertPublisher" id="insertPublisher" class="text-field__input" placeholder="publisher">
                            <input type="text" name="insertNumberOfPages" class="text-field__input" placeholder="number of Pages">
                            <input type="text" name="insertYearOfPublishing" class="text-field__input" placeholder="year of publishing">
                            <script>
							    var input2 = document.getElementById("insertAuthor");
								input2.oninput = function () {
								    setInsertAuthors(input2.value);
								}
                                var input2 = document.getElementById("insertPublisher");
								input2.oninput = function () {
								    setInsertPublishers(input2.value);
								}
							</script>
                            <datalist id="listInsertAuthors">
                                <div id="insertElements"></div>
                            </datalist>
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