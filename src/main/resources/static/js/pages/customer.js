
document.getElementById('customerForm').addEventListener('submit', function(event) {
  event.preventDefault();

  const formData = new FormData(this);

  const productId = formData.get('id');

  const productData = {
    id: productId ? formData.get('id') : null,
    name: formData.get('name'),
    email: formData.get('email'),
    phone: parseFloat(formData.get('phone'))
  };

  fetch('/customer', {
    method: productId ? "PUT" : "POST",
    headers: {
      'Content-Type': 'application/json',
      [header]: token
    },
    body: JSON.stringify(productData)
  })
  .then(response => response.json())
  .then(data => {
    if(data.success){
      swal(data.message, {
        icon: "success",
      });
      loadCustomer();
      hideCustomerFormContainer();
    }
  })
  .catch(error => console.error("Error creating or updating customer:", error));

});


function editCustomer(id) {

  showCustomerFormContainer();

  fetch(`/customer/${id}`)
  .then(response => response.json())
  .then(customer => {

    console.log(customer)

    document.getElementById("id").value = customer.id;
    document.getElementById("name").value = customer.name;
    document.getElementById("email").value = customer.email;
    document.getElementById("phone").value = customer.phone;
  })
  .catch(error => console.error("Error loading customer:", error));
}

function deleteCustomer(id) {

  swal({
    title: "Está seguro de que desea borrar el cliente?",
    text: "Una vez eliminado, no podrá recuperar este cliente!",
    icon: "warning",
    buttons: true,
    dangerMode: true,
  })
  .then((willDelete) => {
    if (willDelete) {

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

          swal(data.message, {
            icon: "success",
          });

          loadCustomer();
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
          <td><button class="btn btn-primary btn-sm" onclick="editCustomer(${customer.id})">Actualizar</button></td>
          <td><button class="btn btn-danger btn-sm" onclick="deleteCustomer(${customer.id})">Eliminar</button></td>
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