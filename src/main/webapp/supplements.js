var permission = new Boolean(false);

function loadSupplements() {
    const xhr = new XMLHttpRequest();
    xhr.open('GET', 'protected/supplements');
    xhr.onload = function () {
        if (this.status == OK) {
            let stuffs = JSON.parse(this.responseText);
            let account = stuffs[0];
            permission = account.permission;
            let supplements = stuffs[1];
            const tbodyEl = document.createElement('tbody');
            tbodyEl.setAttribute('class', 'buy-cells');


            for (let i = 0; i < supplements.length; i++) {
                const supplement = supplements[i];

                const imageTdEl = document.createElement('td');
                imageTdEl.classList.add('default-cell');
                imageTdEl.innerHTML = '<img src="' + supplement.img + '" width="150" height="150">';

                const nameTdEl = document.createElement('td');
                nameTdEl.classList.add('default-cell');
                nameTdEl.textContent = supplement.name;

                const typeTdEl = document.createElement('td');
                typeTdEl.classList.add('default-cell');
                typeTdEl.textContent = supplement.supplement_type;

                const priceTdEl = document.createElement('td');
                priceTdEl.classList.add('default-cell');
                priceTdEl.textContent = supplement.price;

                const buttonBuyEl = document.createElement('button');
                buttonBuyEl.textContent = "Buy";
                buttonBuyEl.setAttribute('id', 'buy-supplement' + supplement.id);

                buttonBuyEl.dataset.supplementBuyId = supplement.id;
                buttonBuyEl.addEventListener('click', onBuyButtonClicked);

                buttonBuyEl.dataset.supplementPrice = supplement.price;

                const buttonOneTdEl = document.createElement('td');
                buttonOneTdEl.appendChild(buttonBuyEl);
                buttonOneTdEl.setAttribute('id', 'buy-supplement-' + supplement.id);

                const trEl = document.createElement('tr');
                trEl.setAttribute('id', 'row-supplement-id-' + supplement.id);
                trEl.appendChild(imageTdEl);
                trEl.appendChild(nameTdEl);
                trEl.appendChild(typeTdEl);
                trEl.appendChild(priceTdEl);
                trEl.appendChild(buttonOneTdEl);
                tbodyEl.appendChild(trEl);
                supplementsContentDivEl.innerHTML = '';
                supplementsContentDivEl.appendChild(tbodyEl);
            }
        }
    }
    console.log(permission);
    if(permission){
        showContents(['supplements-content','back-to-profile-content', 'admin-content']);
    }else{
        showContents(['supplements-content','back-to-profile-content']);
    }

    xhr.send();

}

function onBuyButtonClicked() {
    const supplementBuyId = this.dataset.supplementBuyId;

    const params = new URLSearchParams();
    params.append('supplementBuyId', supplementBuyId);

    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onSupplementBuyResponse);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('POST', 'protected/inventory');

    xhr.send(params);
}

function onSupplementBuyResponse() {
    if (this.status === OK) {
        const message = JSON.parse(this.responseText);
        alert(message.message);
        loadSupplements();
    if(permission){
        showContents(['supplements-content','back-to-profile-content', 'admin-content']);
    }else{
        showContents(['supplements-content','back-to-profile-content']);
    }
    }else if (this.status === INTERNAL_SERVER_ERROR){
        const json = JSON.parse(this.responseText);{
            alert(`${json.message}`);
        }
    }
    else {
        onOtherResponse(supplementsContentDivEl, this);
    }
}
