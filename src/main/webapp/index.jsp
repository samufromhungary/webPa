<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <c:url value="/inventory.js" var="inventoryScriptUrl"/>
    <c:url value="/index.js" var="indexScriptUrl"/>
    <c:url value="/login.js" var="loginScriptUrl"/>
    <c:url value="/register.js" var="registerScriptUrl"/>
    <c:url value="/profile.js" var="profileScriptUrl"/>
    <c:url value="/logout.js" var="logoutScriptUrl"/>
    <c:url value="/supplements.js" var="supplementsScriptUrl"/>
    <c:url value="/back-to-profile.js" var="backToProfileScriptUrl"/>
    <c:url value="/data.js" var="dataScriptUrl"/>
    <c:url value="/admin.js" var="adminScriptUrl"/>
    <link rel="stylesheet" href="style.css">
    <script src="${inventoryScriptUrl}"></script>
    <script src="${indexScriptUrl}"></script>
    <script src="${loginScriptUrl}"></script>
    <script src="${registerScriptUrl}"></script>
    <script src="${profileScriptUrl}"></script>
    <script src="${logoutScriptUrl}"></script>
    <script src="${supplementsScriptUrl}"></script>
    <script src="${backToProfileScriptUrl}"></script>
    <script src="${dataScriptUrl}"></script>
    <script src="${adminScriptUrl}"></script>
    <title>Supplement Webshop</title>
</head>
<body>
<br>
<div>
    <br>
    <div id="logout-content" class="hidden content">
        <button id="logout-button">Logout</button>
    </div>
    <br>
</div>
<div id="login-content" class="content" style="text-align: center">
    <h1>Supplement shop</h1>
    <form id="login-form" onsubmit="return false;" class="login">
        <input type="text" name="email" placeholder=" email">
        <br><br>
        <input type="password" name="password" placeholder=" password">
        <br><br>
        <button id="login-button">Login</button>
    </form>
    <br>
    <button id="toggle-trigger" onclick="togglediv('register-content')">Sign up</button>
</div>
<br>
<div id="register-content" class="hidden content">
    <form id="register-form" onsubmit="return false;" class="register">
        <input type="text" name="email" placeholder=" email">
        <br><br>
        <input type="password" name="password" placeholder="password">
        <br><br>
        <button id="register-button" onclick="togglediv('register-content')">Create account</button>
    </form>
</div>
<br>
<div id="profile-content" class="hidden content">
    <h1>Profile</h1>
    <button id="supplements-button">Supplements</button>
    <button id="inventory-button">Inventory</button>
    <button id="data-button">Profile</button>
</div>
<div id="back-to-profile-content" class="hidden content">
    <button onclick="onBackToProfileClicked();" class="backtoprofile">Back to profile</button>
</div>
</div>
<br>
<div id="supplements-content" class="hidden content"></div>
<br>
<div id="inventory-content" class="hidden content"></div>
<br>
<div id="data-content" class="hidden content">
    <div id="data-content2"></div>
    <form id="change-data-form" onsubmit="return false;">
        <input type="text" name="name" placeholder="name">
        <br><br>
        <input type="text" name="address" placeholder="address">
        <br><br>
        <input type="text" name="phone" placeholder="phone">
        <br><br>
        <input type="text" name="balance" placeholder="balance">
        <br><br>
        <button id="change-data-button">Change data</button>
    </form>
</div>
<div id="admin-content" class="hidden content">
    <form id="admin-form" onsubmit="return false;">
        <br><br>
        <h3>Add a supplement here:</h3>
        <br>
        <input type="text" name="name" placeholder="Name of the supplement"><br>
        <input type="text" name="supplement_type" placeholder="Type of the supplement"><br>
        <input type="url" name="img" placeholder="Picture (direct) link"><br>
        <input type="number" name="price" placeholder="Price of the supplement"><br>
        <select name="permission" id="permissionselect">
            <option value="true">Premium</option>
            <option value="false">Not premium</option>
        </select>
        <button id="admin-button">Add stuff to shop</button>
    </form>
</div>
</body>
</html>
