function onDataButtonClicked() {

    const xhr = new XMLHttpRequest();
    xhr.addEventListener('error', onNetworkError);
    xhr.open('GET', 'protected/data');
    xhr.onload = function () {
        if (this.status === OK) {
            let data = JSON.parse(this.responseText);
            let output = '<div id="data-content" class="content">' +
                '<ul>' +
                '<li>Name: ' + data.name + '</li>' +
                '<li>Address: ' + data.address + '</li>' +
                '<li>Phone number: ' + data.phone + '</li>' +
                '<li>Balance: ' + data.balance + '</li>' +
                '</ul>' +

                '</div>';
            document.getElementById('data-content2').innerHTML = output;
        }
    }
    showContents(['back-to-profile-content', 'data-content', 'change-data-content']);
    xhr.send();
}

function onChangeDataButtonClicked() {
    const changeDataEl = document.forms['change-data-form'];

    const nameInputEl = changeDataEl.querySelector('input[name="name"]');
    const addressInputEl = changeDataEl.querySelector('input[name="address"]');
    const phoneInputEl = changeDataEl.querySelector('input[name="phone"]');
    const balanceInputEl = changeDataEl.querySelector('input[name="balance"]');


    const name = nameInputEl.value;
    const address = addressInputEl.value;
    const phone = phoneInputEl.value;
    const balance = balanceInputEl.value;

    const params = new URLSearchParams();
    params.append('name', name);
    params.append('address', address);
    params.append('phone', phone);
    params.append('balance', balance);

    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onChangeDataRespond);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('POST', 'protected/data');
    xhr.send(params);
}

function onChangeDataRespond() {
    if (this.status === OK) {
        const changeDataEl = document.forms['change-data-form'];
        const nameInputEl = changeDataEl.querySelector('input[name="name"]');
        const addressInputEl = changeDataEl.querySelector('input[name="address"]');
        const phoneInputEl = changeDataEl.querySelector('input[name="phone"]');
        const balanceInputEl = changeDataEl.querySelector('input[name="balance"]');

        const name = nameInputEl.value;
        const address = addressInputEl.value;
        const phone = phoneInputEl.value;
        const balance = balanceInputEl.value;

        const data = JSON.parse(this.responseText);

        let output = '<div id="data-content" class="content">' +
                        '<ul>' +
                        '<li>Name: ' + name + '</li>' +
                        '<li>Address: ' + address + '</li>' +
                        '<li>Phone number: ' + phone + '</li>' +
                        '<li>Balance: ' + balance + '</li>' +
                        '</ul>' +
                        '</div>';
        document.getElementById('data-content2').innerHTML = output;
        showContents(['back-to-profile-content', 'data-content', 'change-data-content']);
    }
}
