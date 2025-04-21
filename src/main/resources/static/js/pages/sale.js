if (typeof customerId === 'undefined') {
  let customerId = 0;
}

if (typeof shoppingCar !== 'undefined') {
  shoppingCar = [];
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
          <td class="text-center"><button class="btn btn-primary btn-sm" onclick="startSale(${customer.id})"><i class="fas fa-cart-plus"></i></button></td>
        `;
      tableBody.appendChild(row);
    });

  })
  .catch(error => console.error("Error loading customers:", error));
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
          <td class="text-center"><button class="btn btn-primary btn-sm" onclick='addProductSaleToShoppingCar(${JSON.stringify(product)})'><i class="fas fa-cart-plus"></i></button></td>
        `;
      tableBody.appendChild(row);
    });

  })
  .catch(error => console.error("Error loading products:", error));
}

function showProductTableContainer() {
  const productTableContainer = document.getElementById("productTableContainer");
  productTableContainer.style.display = "block";
}

function hideProductTableContainer() {
  const productTableContainer = document.getElementById("productTableContainer");
  productTableContainer.style.display = "none";
}

function hideCustomerTableContainer() {
  const customerTableContainer = document.getElementById("customerTableContainer");
  customerTableContainer.style.display = "none";
}

function showClientSaleProductsTableContainer() {
  const clientSaleProductsTableContainer = document.getElementById("clientSaleProductsTableContainer");
  clientSaleProductsTableContainer.style.display = "block";
}

function hideClientSaleProductsTableContainer() {
  const clientSaleProductsTableContainer = document.getElementById("clientSaleProductsTableContainer");
  clientSaleProductsTableContainer.style.display = "none";
}

function startSale(selectedCustomerId) {
  const customerTableContainer = document.getElementById("customerTableContainer");
  customerTableContainer.style.display = "none";

  const clientSaleProductsTableContainer =
      document.getElementById("clientSaleProductsTableContainer");
  clientSaleProductsTableContainer.style.display = "block";

  customerId = selectedCustomerId;

  renderSaleProductsTable();
}

function renderSaleProductsTable() {
  const tbody =
      document.getElementById('clientSaleProductsTableBody');

  if (typeof shoppingCar !== 'undefined') {

    tbody.innerHTML = "";

    shoppingCar.forEach((item, index) => {
      const row = document.createElement('tr');

      row.innerHTML = `
      <td>${item.name}</td>
      <td>${item.price.toFixed(2)}</td>
      <td>${item.quantity}</td>
      <td>${(item.price * item.quantity).toFixed(2)}</td>
      <td class="text-center">
        <button class="btn btn-danger btn-sm" onclick="removeItem(${index})">
          ️<i class="fas fa-trash"></i>
        </button>
      </td>
    `;

      tbody.appendChild(row);
    });


  } else {

    shoppingCar = [];

  }


}

function addProductSaleToShoppingCar(product) {

  swal("Ingrese la cantidad que desea:", {
    content: "input",
  })
  .then((quantity) => {
    const customerTableContainer = document.getElementById(
        "customerTableContainer");
    customerTableContainer.style.display = "none";

    const productTableContainer = document.getElementById(
        "productTableContainer");
    productTableContainer.style.display = "block";

    let uniqueId = Date.now().toString(36) +
        Math.random().toString(36).substring(2);

    shoppingCar.push({
      id: uniqueId,
      productId: product.id,
      name: product.name,
      price: product.price,
      quantity: quantity
    });

    renderSaleProductsTable();

  });
}

function executeSale() {

  if (!customerId || shoppingCar.length === 0) {
    alert("Debe seleccionar un cliente y agregar productos.");
    return;
  }

  const payload = {
    customerId: customerId,
    saleDetailDTOS: shoppingCar.map(item => ({
      productId: item.productId,
      quantity: parseInt(item.quantity),
      price: item.price
    }))
  };

  fetch("/sale", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
      [header]: token
    },
    body: JSON.stringify(payload)
  }).then(response => response.json())
  .then(data => {
    if(data.success){
      swal(data.message, {
        icon: "success",
      });
      shoppingCar = [];
    }
  })
  .catch(error => console.error("Error creating or updating customer:", error));
}

loadCustomer();
loadProducts();

