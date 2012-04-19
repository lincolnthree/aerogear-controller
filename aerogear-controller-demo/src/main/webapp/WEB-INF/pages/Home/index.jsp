<%@page pageEncoding="UTF-8" %>
<html>
    <body>
        <p>hello, index!</p>
        <p>maybe you should try the <a href="delorean">delorean page</a></p>

        <p>
            <form action="cars" method="post">
                <ul>
                    <li><label>Color:</label><input type="text" name="car.color"/></li>
                    <li><label>Brand:</label><input type="text" name="car.brand"/></li>
                    <li><input type="submit"/></li>
                </ul>
            </form>
        </p>
    </body>
</html>