function onInventoryButtonClicked() {
    const xhr = new XMLHttpRequest();
    xhr.open('GET', 'protected/inventory');
    xhr.onload = function () {
        if (this.status === OK) {
            let inventory = JSON.parse(this.responseText);
            let output = '';
            for (var i in inventory) {
                output += '<div id="inventory-content" class="content">' +
                    '<ul>' +
                    '<li><img src="' + inventory[i].img + '" width="150" height="150"></li>' +
                    '<li>Name: ' + inventory[i].name + '</li>' +
                    '<li>Type: ' + inventory[i].supplement_type + '</li>' +
                    '<li>Amount: ' + inventory[i].counter + '</li>' +
                    '</ul>' +
                    '</div>';
            }

            document.getElementById('inventory-content').innerHTML = output;
        }
    }
    showContents(['back-to-profile-content', 'inventory-content']);
    xhr.send();
}
