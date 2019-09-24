function onAddButtonClicked() {
    const adminFormEl = document.forms['admin-form'];

    const nameInputEl = adminFormEl.querySelector('input[name="name"]');
    const supplementTypeInputEl = adminFormEl.querySelector('input[name="supplement_type"]');
    const imgInputEl = adminFormEl.querySelector('input[name="img"]');
    const priceInputEl = adminFormEl.querySelector('input[name="price"]');
    const premiumInputEl = adminFormEl.querySelector('select[name="permission"]')[0].value;


    const name = nameInputEl.value;
    const supplement_type = supplementTypeInputEl.value;
    const img = imgInputEl.value;
    const price = priceInputEl.value;
    var premium = (premiumInputEl == 'true');

    const params = new URLSearchParams();
    params.append('name', name);
    params.append('supplement_type', supplement_type);
    params.append('img', img);
    params.append('price', price);
    params.append('premium',premium);

    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onAddRespond);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('POST', 'protected/supplements');
    xhr.send(params);
}

function onAddRespond() {
    if (this.status === OK) {
        const supplement = JSON.parse(this.responseText);
        alert('Sehr gut comrade!');
        showContents(['supplements-content','back-to-profile-content', 'admin-content']);
    } else {
     onOtherResponse(document.getElementById('register-form'), this);
    }
}

