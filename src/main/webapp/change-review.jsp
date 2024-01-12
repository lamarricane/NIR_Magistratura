<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="java.util.List"%>
<%@ page import="model.Review"%>
<html>
    <head>
        <title> Reviews </title>

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
                var insertWebUsersNames;
                var updateWebUsersNames;

				function setInsertWebUsers(input) {
					let result = "";
					getInsertNames(input);
					for (var i = 0; i < insertWebUsersNames.length; i++) {
						result += "<option>" + insertWebUsersNames[i] + "</option>";
					}
					document.getElementById("insertElements").innerHTML = result;
				}

                function getInsertNames(input) {
                    if (input != "") {
                	    $.ajax({
                            type: "GET",
                            url: "autofill?autofillWebUsers=" + input,
                            async: false,
                            dataType: "json",
                            success: function (data) {
                                insertWebUsersNames = data;
                            }
    				    });
    				}
                }

				function setUpdateWebUsers(input) {
					let result = "";
					getUpdateNames(input);
					for (var i = 0; i < updateWebUsersNames.length; i++) {
						result += "<option>" + updateWebUsersNames[i] + "</option>";
					}
					document.getElementById("updateElements").innerHTML = result;
				}

                function getUpdateNames(input) {
                    if (input != "") {
                	    $.ajax({
                            type: "GET",
                            url: "autofill?autofillWebUsers=" + input,
                            async: false,
                            dataType: "json",
                            success: function (data) {
                                updateWebUsersNames = data;
                            }
    				    });
    				}
                }
            </script>
    </head>
<body style="background-image: url('change_book.jpg');">
<%
    List<Review> reviews = (List) request.getAttribute("reviews");
%>
    <div class="container">
        <div class="row" align="center">
            <table class="table_dark">
                <thread>
                    <tr>
                        <th>ID Review</th>
                        <th>Book</th>
                        <th>Text</th>
                        <th>Web User</th>
                        <th>Publication Date</th>
                    </tr>
                </thread>
                <body>
                    <% for(Review review : reviews) { %>
                        <tr>
                            <td style="text-align:center"> <%= review.getId() %> </td>
                              <td style="text-align:center"> <%= review.getBook().getName() %> </td>
                            <td style="text-align:center"> <%= review.getText() %> </td>
                              <td style="text-align:center"> <%= review.getWebUser().getName() %> </td>
                            <td style="text-align:center"> <%= review.getPublicationDate() %> </td>
                        <tr>
                    <% } %>
                </body>
            </table>
        </div>
        <div class="row">
            <form method="post" action="review">
                <div class="text-field">
                        <div class ="form-group">
                            <label>Delete review by id:</label>
                            <input type="text" name="deleteReviewById" class="text-field__input" placeholder="id">
                        </div>
                        <div class ="form-group">
                            <label>Update review with id:</label>
                            <input type="text" name="updateReviewById" class="text-field__input" placeholder="id">
                        </div>
                        <div class ="form-group">
                            <label>New data:</label>
                            <input list="listUpdateBooks" type="text" id="updateBook" name="updateBook" class="text-field__input" placeholder="new book">
                            <input type="text" name="updateText" class="text-field__input" placeholder="new review text">
                            <input list="listUpdateWebUsers" type="text" id="updateWebUser" name="updateWebUser" class="text-field__input" placeholder="new web user">
                            <input type="text" name="updatePublicationDate" class="text-field__input" placeholder="new publication_date">
                            <script>
							    var input1 = document.getElementById("updateWebUser");
								input1.oninput = function () {
								    setUpdateWebUsers(input1.value);
								}
							</script>
                            <datalist id="listUpdateWebUsers">
                                <div id="updateElements"></div>
                            </datalist>
                            <script>
							    var input2 = document.getElementById("updateBook");
								input2.oninput = function () {
								    setUpdateBooks(input2.value);
								}
							</script>
                            <datalist id="listUpdateBooks">
                                <div id="updateElements"></div>
                            </datalist>
                        </div>
                        <div class ="form-group">
                            <label>Insert review:</label>
                            <input list="listInsertBooks" type="text" name="insertBook" id="insertBook" class="text-field__input" placeholder="book">
                            <input type="text" name="insertText" class="text-field__input" placeholder="review text">
                            <input list="listInsertWebUsers" type="text" name="insertWebUser" id="insertWebUser" class="text-field__input" placeholder="web user">
                            <input type="text" name="insertPublicationDate" class="text-field__input" placeholder="publication_date">
                            <script>
							    var input11 = document.getElementById("insertWebUser");
								input11.oninput = function () {
								    setInsertWebUsers(input11.value);
								}
							</script>
                            <datalist id="listInsertWebUsers">
                                <div id="insertElements"></div>
                            </datalist>
                            <script>
							    var input22 = document.getElementById("insertBook");
								input22.oninput = function () {
								    setInsertBooks(input22.value);
								}
							</script>
                            <datalist id="listInsertBooks">
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