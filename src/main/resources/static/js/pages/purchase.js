if (typeof supplierId === 'undefined') {
  let supplierId = 0;
}

if (typeof shoppingCar !== 'undefined') {
  shoppingCar = [];
}

function loadSuppliers() {
  fetch("/supplier/all")
  .then(response => response.json())
  .then(suppliers => {
    const tableBody = document.getElementById("supplierTableBody");
    tableBody.innerHTML = "";

    suppliers.forEach(supplier => {
      const row = document.createElement("tr");
      row.innerHTML = `
          <td>${supplier.name}</td>
          <td>${supplier.email}</td>
          <td class="text-center"><button class="btn btn-dark btn-sm" onclick="startPurchase(${supplier.id})"><i class="fas fa-cart-plus"></i></button></td>
        `;
      tableBody.appendChild(row);
    });

  })
  .catch(error => console.error("Error loading suppliers:", error));
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
          <td class="text-center"><button class="btn btn-dark btn-sm" onclick='addProductPurchaseToShoppingCar(${JSON.stringify(product)})'><i class="fas fa-cart-plus"></i></button></td>
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

function hideSupplierTableContainer() {
  const supplierTableContainer = document.getElementById("supplierTableContainer");
  supplierTableContainer.style.display = "none";
}

function showSupplierPurchaseProductsTableContainer() {
  const supplierPurchaseProductsTableContainer = document.getElementById("supplierPurchaseProductsTableContainer");
  supplierPurchaseProductsTableContainer.style.display = "block";
}

function hideSupplierPurchaseProductsTableContainer() {
  const supplierPurchaseProductsTableContainer = document.getElementById("supplierPurchaseProductsTableContainer");
  supplierPurchaseProductsTableContainer.style.display = "none";
}

function startPurchase(selectedSupplierId) {

  hideSupplierTableContainer();
  showSupplierPurchaseProductsTableContainer();

  supplierId = selectedSupplierId;

  renderPurchaseProductsTable();
}

function renderPurchaseProductsTable() {
  const tbody =
      document.getElementById('supplierPurchaseProductsTableBody');

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
        <button class="btn btn-dark btn-sm" onclick="removePurchaseProduct(${index})">
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

function addProductPurchaseToShoppingCar(product) {

  Swal.fire({
    title: 'Ingrese la cantidad que desea:',
    input: 'text',
    inputPlaceholder: 'Ingrese la cantidad que desea',
    showCancelButton: true,
    confirmButtonColor: "black",
    cancelButtonColor: "black",
    confirmButtonText: "Aceptar",
    cancelButtonText: "Cancelar",
    customClass: {
      input: 'custom-focus-style'
    }
  })
  .then((result) => {

    if (result.isConfirmed) {

      hideSupplierTableContainer()
      showProductTableContainer();

    let uniqueId = Date.now().toString(36) +
        Math.random().toString(36).substring(2);

    shoppingCar.push({
      id: uniqueId,
      productId: product.id,
      name: product.name,
      price: product.price,
      quantity: result.value
    });

    renderPurchaseProductsTable();

    }

  });
}

function executePurchase() {

  if (!supplierId || shoppingCar.length === 0) {
    alert("Debe seleccionar un proveedor y agregar productos.");
    return;
  }

  const payload = {
    supplierId: supplierId,
    purchaseDetailDTOS: shoppingCar.map(item => ({
      productId: item.productId,
      quantity: parseInt(item.quantity),
      price: item.price
    }))
  };

  fetch("/purchase", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
      [header]: token
    },
    body: JSON.stringify(payload)
  }).then(response => response.json())
  .then(data => {
    if(data.success){
      Swal.fire({
        icon: "success",
        text: data.message,
        confirmButtonText: "Aceptar",
        confirmButtonColor: "black",
      });
      shoppingCar = [];

      const purchaseId = data.data;

      hideSupplierPurchaseProductsTableContainer();
      hideProductTableContainer();
      showPurchaseSummaryForm();
      getPurchaseSummaryById(purchaseId);

    } else {
      Swal.fire({
        icon: "error",
        text: data.message,
        confirmButtonText: "Aceptar",
        confirmButtonColor: "black",
      });
    }
  })
  .catch(error => console.error("Error creating or updating supplier:", error));
}

function removePurchaseProduct(index) {
  shoppingCar.splice(index, 1);
  renderPurchaseProductsTable();
}


function getPurchaseSummaryById(purchaseId) {
  fetch(`/purchase/summary/${purchaseId}`, {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json'
    }
  })
  .then(response => response.json())
  .then(result => {
    if (result.success) {
      fillPurchaseSummaryForm(result.data);
    }
  })
  .catch(error => {
    console.error('Error en la comunicación con el servidor:', error);
  });
}

function fillPurchaseSummaryForm(purchaseSummary) {
  document.getElementById('supplierName').value = purchaseSummary.supplierName;
  document.getElementById('totalAmount').value = purchaseSummary.totalAmount;
  document.getElementById('paidDate').value = purchaseSummary.paidDate;
}

function showPurchaseSummaryForm() {
  const purchaseSummaryForm = document.getElementById("purchaseSummaryFormContainer");
  purchaseSummaryForm.style.display = "block";
}

function hideProductTableContainer() {
  const productTableContainer = document.getElementById("productTableContainer");
  productTableContainer.style.display = "none";
}

loadSuppliers();
loadProducts();

