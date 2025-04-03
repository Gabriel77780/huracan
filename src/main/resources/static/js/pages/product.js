
document.getElementById('productForm').addEventListener('submit', function(event) {
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

  fetch('/product', {
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
      loadProducts();
      hideProductFormContainer();
    }
  })
  .catch(error => {
    document.getElementById('responseMessage').innerHTML = `<div class="alert alert-danger">Error al crear el producto.</div>`;
  });

});


function editProduct(id) {

  showProductFormContainer();

  fetch(`/product/${id}`)
  .then(response => response.json())
  .then(product => {
    document.getElementById("id").value = product.id;
    document.getElementById("name").value = product.name;
    document.getElementById("description").value = product.description;
    document.getElementById("price").value = product.price;
    document.getElementById("stock").value = product.stock;
    document.getElementById("category").value = product.category;
  })
  .catch(error => console.error("Error loading product:", error));
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

      fetch(`/product/${id}`, {
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

          loadProducts();
        }

      })
      .catch(error => {
        alert("Error al eliminar el producto.");
      });

    }
  });

}

function loadProducts() {
  fetch("/product/all")
  .then(response => response.json())
  .then(products => {
    const tableBody = document.getElementById("productTableBody");
    tableBody.innerHTML = "";

    products.forEach(product => {
      const row = document.createElement("tr");
      row.innerHTML = `
          <td>${product.name}</td>
          <td>${product.price.toFixed(2)}</td>
          <td>${product.stock}</td>
          <td><button class="btn btn-warning btn-sm" onclick="editProduct(${product.id})">Actualizar</button></td>
          <td><button class="btn btn-danger btn-sm" onclick="deleteProduct(${product.id})">Eliminar</button></td>
        `;
      tableBody.appendChild(row);
    });

  })
  .catch(error => console.error("Error loading products:", error));
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
loadProducts();