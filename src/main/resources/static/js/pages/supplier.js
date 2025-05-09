
document.getElementById('suppliersForm').addEventListener('submit', function(event) {
  event.preventDefault();

  const formData = new FormData(this);

  const supplierId = formData.get('id');

  const supplierData = {
    id: supplierId ? formData.get('id') : null,
    name: formData.get('name'),
    email: formData.get('email'),
    phone: parseFloat(formData.get('phone'))
  };

  fetch('/supplier', {
    method: supplierId ? "PUT" : "POST",
    headers: {
      'Content-Type': 'application/json',
      [header]: token
    },
    body: JSON.stringify(supplierData)
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
      loadSupplier();
      hideSuppliersFormContainer();
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

});


function editSupplier(id) {

  showSuppliersFormContainer(false);

  fetch(`/supplier/${id}`)
  .then(response => response.json())
  .then(supplier => {
    document.getElementById("id").value = supplier.id;
    document.getElementById("name").value = supplier.name;
    document.getElementById("email").value = supplier.email;
    document.getElementById("phone").value = supplier.phone;
  })
  .catch(error => console.error("Error loading supplier:", error));
}

function deleteSupplier(id) {

  Swal.fire({
    title: "Está seguro de que desea borrar el proveedor?",
    text: "Una vez eliminado, no podrá recuperar este proveedor!",
    icon: "warning",
    showCancelButton: true,
    confirmButtonColor: "black",
    cancelButtonColor: "black",
    confirmButtonText: "Borrar",
    cancelButtonText: "Cancelar",
  })
  .then((result) => {
    if (result.isConfirmed) {

      fetch(`/supplier/${id}`, {
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

          loadSupplier();
        } else {

          Swal.fire({
            icon: "error",
            text: data.message,
            confirmButtonText: "Aceptar",
            confirmButtonColor: "black",
          });

        }

      })
      .catch(error => console.error("Error deleting supplier:", error));

    }
  });

}

function loadSupplier() {
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
          <td class="text-center"><button class="btn btn-dark btn-sm" onclick="editSupplier(${supplier.id})"><i class="fas fa-list"></i></button></td>
          <td class="text-center"><button class="btn btn-dark btn-sm" onclick="deleteSupplier(${supplier.id})"><i class="fas fa-trash"></i></button></td>
        `;
      tableBody.appendChild(row);
    });

  })
  .catch(error => console.error("Error loading suppliers:", error));
}

function hideSuppliersFormContainer() {
  const form = document.getElementById('suppliersFormContainer');
  form.style.display = 'none';
}

function showSuppliersFormContainer(newSupplier) {

  if(newSupplier) {
    document.getElementById("suppliersForm").reset();
    document.getElementById("id").value = null;
  }

  const form = document.getElementById('suppliersFormContainer');
  form.style.display = 'block';
}

hideSuppliersFormContainer();
loadSupplier();