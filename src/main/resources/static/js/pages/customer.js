
document.getElementById('customerForm').addEventListener('submit', function(event) {
  event.preventDefault();

  const formData = new FormData(this);

  const productId = formData.get('id');

  const productData = {
    id: productId ? formData.get('id') : null,
    name: formData.get('name'),
    description: formData.get('description'),
    price: parseFloat(formData.get('price')),
    stock: parseInt(formData.get('stock')),
    category: formData.get('category')
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
      hideProductFormContainer();
    }
  })
  .catch(error => console.error("Error creating or updating customer:", error));

});


function editProduct(id) {

  showProductFormContainer();

  fetch(`/customer/${id}`)
  .then(response => response.json())
  .then(product => {
    document.getElementById("id").value = product.id;
    document.getElementById("name").value = product.name;
    document.getElementById("description").value = product.description;
    document.getElementById("price").value = product.price;
    document.getElementById("stock").value = product.stock;
    document.getElementById("category").value = product.category;
  })
  .catch(error => console.error("Error loading customer:", error));
}

function deleteProduct(id) {

  swal({
    title: "Está seguro de que desea borrar el producto?",
    text: "Una vez eliminado, no podrá recuperar este producto!",
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
    const tableBody = document.getElementById("productTableBody");
    tableBody.innerHTML = "";

    customers.forEach(customer => {
      const row = document.createElement("tr");
      row.innerHTML = `
          <td>${customer.name}</td>
          <td>${customer.email}</td>
          <td><button class="btn btn-warning btn-sm" onclick="editProduct(${customer.id})">Actualizar</button></td>
          <td><button class="btn btn-danger btn-sm" onclick="deleteProduct(${customer.id})">Eliminar</button></td>
        `;
      tableBody.appendChild(row);
    });

  })
  .catch(error => console.error("Error loading customers:", error));
}

function hideProductFormContainer() {
  const form = document.getElementById('productFormContainer');
  form.style.display = 'none';
}

function showProductFormContainer() {
  const form = document.getElementById('productFormContainer');
  form.style.display = 'block';
}

hideProductFormContainer();
loadCustomer();