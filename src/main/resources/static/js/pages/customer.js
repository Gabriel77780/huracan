
document.getElementById('customerForm').addEventListener('submit', function(event) {
  event.preventDefault();

  const formData = new FormData(this);

  const customerId = formData.get('id');

  const productData = {
    id: customerId ? formData.get('id') : null,
    name: formData.get('name'),
    email: formData.get('email'),
    phone: parseFloat(formData.get('phone'))
  };

  fetch('/customer', {
    method: customerId ? "PUT" : "POST",
    headers: {
      'Content-Type': 'application/json',
      [header]: token
    },
    body: JSON.stringify(productData)
  })
  .then(response => response.json())
  .then(data => {
    if(data.success){

      Swal.fire({
        icon: "success",
        text: data.message,
        confirmButtonText: "Aceptar",
        confirmButtonColor: "black",
      });

      loadCustomer();
      hideCustomerFormContainer();
    } else {

      Swal.fire({
        icon: "error",
        text: data.message,
        confirmButtonText: "Aceptar",
        confirmButtonColor: "black",
      });

    }
  })
  .catch(error => console.error("Error creating or updating customer:", error));

});


function editCustomer(id) {

  showCustomerFormContainer();

  fetch(`/customer/${id}`)
  .then(response => response.json())
  .then(customer => {
    document.getElementById("id").value = customer.id;
    document.getElementById("name").value = customer.name;
    document.getElementById("email").value = customer.email;
    document.getElementById("phone").value = customer.phone;
  })
  .catch(error => console.error("Error loading customer:", error));
}

function deleteCustomer(id) {

  Swal.fire({
    title: "Está seguro de que desea borrar el cliente?",
    text: "Una vez eliminado, no podrá recuperar este cliente!",
    icon: "warning",
    showCancelButton: true,
    confirmButtonColor: "black",
    cancelButtonColor: "black",
    confirmButtonText: "Aceptar",
    cancelButtonText: "Cancelar",
  })
  .then((result) => {
    if (result.isConfirmed) {

      fetch(`/customer/${id}`, {
        method: 'DELETE',
        headers: {
          'Content-Type': 'application/json',
          [header]: token
        }
      })
      .then(response => response.json())
      .then(data => {

        if(data.success) {

          Swal.fire({
            icon: "success",
            text: data.message,
            confirmButtonText: "Aceptar",
            confirmButtonColor: "black",
          });

          loadCustomer();
        } else {
          Swal.fire({
            icon: "error",
            text: data.message,
            confirmButtonText: "Aceptar",
            confirmButtonColor: "black",
          });
        }

      })
      .catch(error => console.error("Error deleting customer:", error));

    }
  });

}

function loadCustomer() {
  fetch("/customer/all")
  .then(response => response.json())
  .then(customers => {
    const tableBody = document.getElementById("customerTableBody");
    tableBody.innerHTML = "";

    customers.forEach(customer => {
      const row = document.createElement("tr");
      row.innerHTML = `
          <td>${customer.name}</td>
          <td>${customer.email}</td>
          <td class="text-center"><button class="btn btn-dark btn-sm" onclick="editCustomer(${customer.id})"><i class="fas fa-list"></i></button></td>
          <td class="text-center"><button class="btn btn-dark btn-sm" onclick="deleteCustomer(${customer.id})"><i class="fas fa-trash"></i></button></td>
        `;
      tableBody.appendChild(row);
    });

  })
  .catch(error => console.error("Error loading customers:", error));
}

function hideCustomerFormContainer() {
  const form = document.getElementById('customerFormContainer');
  form.style.display = 'none';
}

function showCustomerFormContainer() {
  const form = document.getElementById('customerFormContainer');
  form.style.display = 'block';
}

hideCustomerFormContainer();
loadCustomer();