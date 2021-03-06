const OK = 200;
const BAD_REQUEST = 400;
const UNAUTHORIZED = 401;
const NOT_FOUND = 404;
const INTERNAL_SERVER_ERROR = 500;

let loginContentDivEl;
let profileContentDivEl;
let logoutContentDivEl;
let registerContentDivEl;
let supplementsContentDivEl;
let backToProfileContentDivEl;
let inventoryContentDivEl;
let dataContentDivEl;
let adminContentDivEl;

function togglediv(id) {
    const div = document.getElementById(id);
    div.style.display = div.style.display === "none" ? "block" : "none";
}

function newInfo(targetEl, message) {
    newMessage(targetEl, 'info', message);
}

function newError(targetEl, message) {
    newMessage(targetEl, 'error', message);
}

function newMessage(targetEl, cssClass, message) {
    clearMessages();

    const pEl = document.createElement('p');
    pEl.classList.add('message');
    pEl.classList.add(cssClass);
    pEl.textContent = message;

    targetEl.appendChild(pEl);
}

function clearMessages() {
    const messageEls = document.getElementsByClassName('message');
    for (let i = 0; i < messageEls.length; i++) {
        const messageEl = messageEls[i];
        messageEl.remove();
    }
}

function showContents(ids) {
    const contentEls = document.getElementsByClassName('content');
    for (let i = 0; i < contentEls.length; i++) {
        const contentEl = contentEls[i];
        if (ids.includes(contentEl.id)) {
            contentEl.classList.remove('hidden');
        } else {
            contentEl.classList.add('hidden');
        }
    }
}

function removeAllChildren(el) {
    while (el.firstChild) {
        el.removeChild(el.firstChild);
    }
}

function onNetworkError(response) {
    document.body.remove();
    const bodyEl = document.createElement('body');
    document.appendChild(bodyEl);
    newError(bodyEl, 'Network error, please try reloading the page');
}



function onOtherResponse(targetEl, xhr) {
    if (xhr.status === NOT_FOUND) {
        newError(targetEl, 'Not found');
        console.error(xhr);
    } else {
        const json = JSON.parse(xhr.responseText);
        if (xhr.status === INTERNAL_SERVER_ERROR) {
            newError(targetEl, json.message);
        } else if (xhr.status === UNAUTHORIZED || xhr.status === BAD_REQUEST) {
            newError(targetEl, json.message);
        } else {
            newError(targetEl, json.message);
        }
    }
}

function hasAuthorization() {
    return localStorage.getItem('user') !== null;
}

function setAuthorization(user) {
    return localStorage.setItem('user', JSON.stringify(user));
}

function getAuthorization() {
    return JSON.parse(localStorage.getItem('user'));
}

function setUnauthorized() {
    return localStorage.removeItem('user');
}

function onLoad() {
    loginContentDivEl = document.getElementById('login-content');

    profileContentDivEl = document.getElementById('profile-content');

    registerContentDivEl = document.getElementById('register-content');

    logoutContentDivEl = document.getElementById('logout-content');

    supplementsContentDivEl = document.getElementById('supplements-content');

    backToProfileContentDivEl = document.getElementById('back-to-profile-content');

    inventoryContentDivEl = document.getElementById('inventory-content');

    dataContentDivEl = document.getElementById('data-content');

    adminContentDivEl = document.getElementById('admin-content');

    const loginButtonEl = document.getElementById('login-button');
    loginButtonEl.addEventListener('click', onLoginButtonClicked);

    const registerButtonEl = document.getElementById('register-button');
    registerButtonEl.addEventListener('click', onRegisterButtonClicked);

    const logoutButtonEl = document.getElementById('logout-button');
    logoutButtonEl.addEventListener('click', onLogoutButtonClicked);

    const supplementsButtonEl = document.getElementById('supplements-button');
    supplementsButtonEl.addEventListener('click', loadSupplements);

    const inventoryButtonEl = document.getElementById('inventory-button');
    inventoryButtonEl.addEventListener('click', onInventoryButtonClicked);

    const dataButtonEl = document.getElementById('data-button');
    dataButtonEl.addEventListener('click', onDataButtonClicked);

    const changeDataButtonEl = document.getElementById('change-data-button');
    changeDataButtonEl.addEventListener('click', onChangeDataButtonClicked);

    const addButtonEl = document.getElementById('admin-button');
    addButtonEl.addEventListener('click', onAddButtonClicked);


    if (hasAuthorization()) {
        onProfileLoad(getAuthorization());
    }
}

document.addEventListener('DOMContentLoaded', onLoad);
