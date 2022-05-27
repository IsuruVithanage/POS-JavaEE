//Customer----------------------------------------
// Save Customer Button
$("#btnSaveCust").click(function () {

    // remove all the row click events added before
    $("#custTable>tr").off("click");

    $("#confirmSaveCust").off("click");

    $("#confirmSaveCust").click(function () {
        console.log("start");
        saveCustomer();
        clearAllCustTxt();
        loadAllCustomers();
        generateCustID();
        swal("Success!", "Customer has been saved!", "success");
    });


});

// Clear Customer Button
$("#btnClearCust").click(function () {
    clearAllCustTxt();
    generateCustID();

});

//Set Delete button
$("#btnDeleteCust").click(function () {
    $("#confirmdeleteCust").off("click");

    $("#confirmdeleteCust").click(function () {
        deleteCustomer($("#txtCustID").text());
        clearAllCustTxt();
        loadAllCustomers();
        generateCustID();

    });


});
/*Clear the text fields*/
function clearAllCustTxt() {
    $('#txtCustName,#txtCustAddress,#txtCustSalary').val("");
/*    $('#txtCustName,#txtCustAddress,#txtCustSalary').css('border', '2px solid #ced4da');*/
/*    $('#txtCustName').focus();
    $("#btnSaveCust").attr('disabled', true);*/
    loadAllCustomers();
/*    $("#lblcusid,#lblcusname,#lblcusaddress,#lblcussalary").text("");
    $("#btnSaveCust").html("Save Customer");
    $("#btnSaveCust").attr("class", "btn btn-outline-primary");*/
}

/*Save Customer*/
function saveCustomer() {
    console.log("save");
    //gather customer information/**/
   /* let customerID = $("#txtCustID").text();
    let customerName = $("#txtCustName").val();
    let customerAddress = $("#txtCustAddress").val();
    let customerSalary = $("#txtCustSalary").val();*/
    /*
        if (isExists(customerID)){
            for (var i of customerDB) {
                if (customerID===i.getCustID()){
                    i.setCustName(customerName);
                    i.setCustAddress(customerAddress);
                    i.setCustSalary(customerSalary);

                    return;
                }
            }

        }else {*/
    /*       //create Object
           /!*console.log(true);*!/
           var customerObject = new Customer(customerID, customerName, customerAddress, customerSalary);
           customerDB.push(customerObject);*/
    var cusOb = {
        id: $("#txtCustID").text(),
        name: $("#txtCustName").val(),
        address: $("#txtCustAddress").val(),
        salary: $("#txtCustSalary").val()
    }

    $.ajax({
        url: "http://localhost:8080/backend/customer",
        method: "POST",
        contentType: "application/json", //request content type json
        data: JSON.stringify(cusOb),
        success: function (res) {
            if (res.status === 200) {
                alert(res.message);
                console.log(cusOb);
                //loadAllCustomers();
            } else {
                alert(res.data);
                console.log(cusOb);
            }
        },
        error: function (ob, textStatus, error) {
            alert(textStatus);
            console.log(ob.responseText);
            console.log(cusOb);
        }
    });

}

//Check the
/*function isExists(id) {
    for (var i of customerDB) {
        if (id === i.getCustID()) {
            return true;
        }
    }
    return false;

}*/

/*Delete Customer*/
function deleteCustomer(id) {

    $.ajax({
        url:"http://localhost:8080/backend/customer?CusID="+id,
        method:"DELETE",
        //data:data,// application/x-www-form-urlencoded
        success:function (res){
            if (res.status === 200) {
                alert(res.message);
                loadAllCustomers();
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

/*Display customer in the Table*/
function loadAllCustomers() {
    $("#custTableBody").empty();

    $.ajax({
        url: "http://localhost:8080/backend/customer?option=GETALL",
        method: "GET",
        success: function (res) {
            if (res.status === 200) {
                for (const customer of res.data) {
                    let row = `<tr><td>${customer.id}</td><td>${customer.name}</td><td>${customer.address}</td><td>${customer.salary}</td></tr>`;
                    $("#custTableBody").append(row);

                }

                bindClickEvents();
            } else {
                alert(res.data);
                console.log(cusOb);
            }
        },
        error: function (ob, textStatus, error) {
            alert(textStatus);
            console.log(ob.responseText);
            console.log(cusOb);
        }
    });


}

//bind the events to the table rows after the row was added
function bindClickEvents(){
    $("#custTableBody>tr").click(function (){
        //Get values from the selected row
        let cusID= $(this).children(":eq(0)").text();
        let cusName = $(this).children(":eq(1)").text();
        let cusAddress = $(this).children(":eq(2)").text();
        let cusTP = $(this).children(":eq(3)").text();

        // set values for the input fields
        $("#txtCustID").text(cusID);
        $("#txtCustName").val(cusName);
        $("#txtCustAddress").val(cusAddress);
        $("#txtCustSalary").val(cusTP);
    });
}

// search customer
$("#btnSearchCust").click(function () {
    var searchID = $("#txtSearchCust").val();

    var response = searchCustomer(searchID);
    if (response) {
        $("#txtCustID").text(response.getCustID());
        $("#txtCustName").val(response.getCustName());
        $("#txtCustAddress").val(response.getCustAddress());
        $("#txtCustSalary").val(response.getCustSalary());
    } else {
        clearAllCustTxt();
        alert("No Such a Customer");
    }
});

//Serach Customer
function searchCustomer(id) {
    for (let i = 0; i < customerDB.length; i++) {
        if (customerDB[i].getCustID() == id) {
            return customerDB[i];
        }
    }
}

//Genereate Customer ID
function generateCustID() {
    $.ajax({
        url: "http://localhost:8080/backend/customer?option=GENERATEID",
        method: "GET",
        success: function (res) {
            if (res.status === 200) {
                $("#txtCustID").text(res.data);
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
/*const cusNameRegEx = /^[A-z ]{5,20}$/;
const cusAddressRegEx = /^[A-z]{7,}$/;
const cusSalaryRegEx = /^[0-9]{1,}$/;


$('#txtCustName,#txtCustAddress,#txtCustSalary').on('keydown', function (eventOb) {
    if (eventOb.key == "Tab") {
        eventOb.preventDefault(); // stop execution of the button
    }
});

$('#txtCustName,#txtCustAddress,#txtCustSalary').on('blur', function () {
    formCustValid();
});

//focusing events
$("#txtCustName").on('keyup', function (eventOb) {
    setCustButton();
    if (eventOb.key == "Enter") {
        checkIfCustValid();
    }

});


$("#txtCustAddress").on('keyup', function (eventOb) {
    setCustButton();
    if (eventOb.key == "Enter") {
        checkIfCustValid();
    }
});

$("#txtCustSalary").on('keyup', function (eventOb) {
    setCustButton();
    if (eventOb.key == "Enter") {
        checkIfCustValid();
    }
});
// focusing events end
$("#btnSaveCust").attr('disabled', true);


function formCustValid() {
    var cusName = $("#txtCustName").val();
    if (cusNameRegEx.test(cusName)) {
        $("#txtCustName").css('border', '2px solid green');
        $("#lblcusname").text("");
        var cusAddress = $("#txtCustAddress").val();
        if (cusAddressRegEx.test(cusAddress)) {
            var cusSalary = $("#txtCustSalary").val();
            var resp = cusSalaryRegEx.test(cusSalary);
            $("#txtCustAddress").css('border', '2px solid green');
            $("#lblcusaddress").text("");
            if (resp) {
                $("#txtCustSalary").css('border', '2px solid green');
                $("#lblcussalary").text("");
                return true;
            } else {
                $("#txtCustSalary").css('border', '2px solid red');
                $("#lblcussalary").text("Cus Salary is a required field : Pattern 100.00 or 100");
                return false;
            }
        } else {
            $("#txtCustAddress").css('border', '2px solid red');
            $("#lblcusaddress").text("Cus Address is a required field : Mimum 7");
            return false;
        }
    } else {
        $("#txtCustName").css('border', '2px solid red');
        $("#lblcusname").text("Cus Name is a required field : Mimimum 5, Max 20, Spaces Allowed");
        return false;
    }
}

function checkIfCustValid() {
    var cusName = $("#txtCustName").val();
    if (cusNameRegEx.test(cusName)) {
        $("#txtCustAddress").focus();
        var cusAddress = $("#txtCustAddress").val();
        if (cusAddressRegEx.test(cusAddress)) {
            $("#txtCustSalary").focus();
            var cusSalary = $("#txtCustSalary").val();
            var resp = cusSalaryRegEx.test(cusSalary);
            if (resp) {

            } else {
                $("#txtCustSalary").focus();
            }
        } else {
            $("#txtCustAddress").focus();
        }
    } else {
        $("#txtCustName").focus();
    }
}

function setCustButton() {
    let b = formCustValid();
    if (b) {
        $("#btnSaveCust").attr('disabled', false);
    } else {
        $("#btnSaveCust").attr('disabled', true);
    }
}

$('#btnSaveCust').click(function () {
    checkIfCustValid();
});*/
//validation ended