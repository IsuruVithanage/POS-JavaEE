//Item-------------------------------
//Save Item
// Save Item Button
$("#btnSaveItem").click(function () {

    $("#confirmSaveItem").off("click");

    $("#confirmSaveItem").click(function () {
        saveItem();
        //clearAllItemTxt();
        loadAllItems();
        //generateItemID();
        swal("Success!", "Item has been saved!", "success");

    });

});

// Clear Item Button
$("#btnClearItem").click(function () {
    clearAllItemTxt();
    generateItemID();

});

/*Clear the text fields*/
function clearAllItemTxt() {
    $('#txtItemName,#txtQTY,#txtPrice').val("");
    //$('#txtItemName,#txtQTY,#txtPrice').css('border', '2px solid #ced4da');
    //$("#btnSaveItem").attr('disabled', true);
    loadAllItems();
    //$("#lblprice,#lblqty,#lblItemName").text("");
}

/*Save Item*/
function saveItem() {
    //gather Item information

    /*if (isExists(itemID)){
        for (var i of itemDB) {
            if (itemID===i.getitemID()){
                i.setitemName(itemName);
                i.setQTY(QTY);
                i.setPrice(price);
                return;
            }
        }

    }else {
        //create Object
        /!*console.log(true);*!/
        var itemObject = new Item(itemID, itemName, QTY, price);
        itemDB.push(itemObject);
    }*/
    var itemOb = {
        id: $("#txtItemID").text(),
        name: $("#txtItemName").val(),
        qty: $("#txtQTY").val(),
        price: $("#txtPrice").val()
    }

    $.ajax({
        url: "http://localhost:8080/backend/item",
        method: "POST",
        contentType: "application/json", //request content type json
        data: JSON.stringify(itemOb),
        success: function (res) {
            if (res.status === 200) {
                alert(res.message);
                //loadAllCustomers();
            } else {
                alert(res.data);
            }
        },
        error: function (ob, textStatus, error) {
            alert(textStatus);
            console.log(ob.responseText);
            console.log(itemOb);
        }
    });
}

//Check the
/*function isExists(id) {
    for (var i of itemDB){
        if (id===i.getitemID()){
            return true;
        }
    }
    return false;

}*/

/*Display Item in the Table*/
function loadAllItems() {
    $("#ItemTableBody").empty();

    $.ajax({
        url: "http://localhost:8080/backend/item?option=GETALL",
        method: "GET",
        success: function (res) {
            if (res.status === 200) {
                for (const item of res.data) {
                    let row = `<tr><td>${item.id}</td><td>${item.name}</td><td>${item.qty}</td><td>${item.price}</td></tr>`;
                    $("#ItemTableBody").append(row);

                }

                bindClickEvents();
            } else {
                alert(res.data);
            }
        },
        error: function (ob, textStatus, error) {
            alert(textStatus);
            console.log(ob.responseText);
        }
    });

}

//bind the events to the table rows after the row was added
function bindClickEvents(){
    $("#ItemTableBody>tr").click(function (){
        //Get values from the selected row
        let itemID= $(this).children(":eq(0)").text();
        let itemName = $(this).children(":eq(1)").text();
        let qty = $(this).children(":eq(2)").text();
        let price = $(this).children(":eq(3)").text();

        // set values for the input fields
        $("#txtItemID").text(itemID);
        $("#txtItemName").val(itemName);
        $("#txtQTY").val(qty);
        $("#txtPrice").val(price);
    });
}

//Set Delete button
$("#btnDeleteItem").click(function () {
    $("#confirmdeleteItem").off("click");

    $("#confirmdeleteItem").click(function () {
        deleteItem($("#txtItemID").text());
        clearAllCustTxt();
        loadAllItems();
        generateItemID();

    });


});

/*Delete Item*/
function deleteItem(id) {

    $.ajax({
        url:"http://localhost:8080/backend/item?ItemID="+id,
        method:"DELETE",
        //data:data,// application/x-www-form-urlencoded
        success:function (res){
            if (res.status === 200) {
                alert(res.message);
                loadAllItems();
            } else if(res.status === 400){
                alert(res.message);
            }else {
                alert(res.data);
            }
        },
        error: function (ob, textStatus, error) {
            alert(textStatus);
            console.log(ob.responseText);
        }
    });


}

// search Item
/*$("#btnSearchItem").click(function () {
    var searchID = $("#txtSearchItem").val();

    var response = searchItem(searchID);
    if (response) {
        $("#txtItemID").text(response.getitemID());
        $("#txtItemName").val(response.getitemName());
        $("#txtQTY").val(response.getQTY());
        $("#txtPrice").val(response.getPrice());
    } else {
        clearAllItemTxt();
        swal("Warning!", "Item not found!", "warning");
    }
});*/

/*function searchItem(id) {
    for (let i = 0; i < itemDB.length; i++) {
        if (itemDB[i].getitemID() === id) {
            return itemDB[i];
        }
    }
}*/

//Genereate Item ID
function generateItemID() {
    $.ajax({
        url: "http://localhost:8080/backend/item?option=GENERATEID",
        method: "GET",
        success: function (res) {
            if (res.status === 200) {
                $("#txtItemID").text(res.data);
            } else {
                alert(res.data);
            }
        },
        error: function (ob, textStatus, error) {
            alert(textStatus);
            console.log(ob.responseText);
        }
    });

}

//Customer validation started
// customer regular expressions
/*const itemNameRegEx = /^[A-z ]{5,20}$/;
const qtyRegEx = /^[0-9]{1,}$/;
const priceRegEx = /^[0-9]{1,}$/;


$('#txtItemName,#txtQTY,#txtPrice').on('keydown', function (eventOb) {
    if (eventOb.key == "Tab") {
        eventOb.preventDefault(); // stop execution of the button
    }
});

$('#txtItemName,#txtQTY,#txtPrice').on('blur', function () {
    formValid();
});

//focusing events
$("#txtItemName").on('keyup', function (eventOb) {
    setButton();
    if (eventOb.key == "Enter") {
        checkIfValid();
    }

});


$("#txtQTY").on('keyup', function (eventOb) {
    setButton();
    if (eventOb.key == "Enter") {
        checkIfValid();
    }
});

$("#txtPrice").on('keyup', function (eventOb) {
    setButton();
    if (eventOb.key == "Enter") {
        checkIfValid();
    }
});
// focusing events end
$("#btnSaveItem").attr('disabled', true);

function formValid() {
    var itenName = $("#txtItemName").val();
    if (itemNameRegEx.test(itenName)) {
        $("#txtItemName").css('border', '2px solid green');
        $("#lblItemName").text("");
        var qty = $("#txtQTY").val();
        if (qtyRegEx.test(qty)) {
            var price = $("#txtPrice").val();
            var resp = priceRegEx.test(price);
            $("#txtQTY").css('border', '2px solid green');
            $("#lblqty").text("");
            if (resp) {
                $("#txtPrice").css('border', '2px solid green');
                $("#lblprice").text("");
                return true;
            } else {
                $("#txtPrice").css('border', '2px solid red');
                $("#lblprice").text("Item Price is a required field : Numerical vale");
                return false;
            }
        } else {
            $("#txtQTY").css('border', '2px solid red');
            $("#lblqty").text("Item QTY is a required field : Numerical vale");
            return false;
        }
    } else {
        $("#txtItemName").css('border', '2px solid red');
        $("#lblItemName").text("Item Name is a required field : Mimimum 5, Max 20, Spaces Allowed");
        return false;
    }
}

function checkIfValid() {
    var itemName = $("#txtItemName").val();
    if (itemNameRegEx.test(itemName)) {
        $("#txtQTY").focus();
        var qty = $("#txtQTY").val();
        if (qtyRegEx.test(qty)) {
            $("#txtPrice").focus();
            var price = $("#txtPrice").val();
            var resp = priceRegEx.test(price);
            if (resp) {

            } else {
                $("#txtPrice").focus();
            }
        } else {
            $("#txtQTY").focus();
        }
    } else {
        $("#txtItemName").focus();
    }
}

function setButton() {
    let b = formValid();
    if (b) {
        $("#btnSaveItem").attr('disabled', false);
    } else {
        $("#btnSaveItem").attr('disabled', true);
    }
}

$('#btnSaveItem').click(function () {
    checkIfValid();
});*/
//validation ended