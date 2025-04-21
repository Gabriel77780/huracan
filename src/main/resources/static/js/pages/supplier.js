
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
      swal(data.message, {
        icon: "success",
      });
      loadSupplier();
      hideSuppliersFormContainer();
    }
  })
  .catch(error => console.error("Error creating or updating supplier:", error));

});


function editSupplier(id) {

  showSuppliersFormContainer();

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

  swal({
    title: "Está seguro de que desea borrar el cliente?",
    text: "Una vez eliminado, no podrá recuperar este cliente!",
    icon: "warning",
    buttons: true,
    dangerMode: true,
  })
  .then((willDelete) => {
    if (willDelete) {

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

          swal(data.message, {
            icon: "success",
          });

          loadSupplier();
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
          <td class="text-center"><button class="btn btn-primary btn-sm" onclick="editSupplier(${supplier.id})"><i class="fas fa-list"></i></button></td>
          <td class="text-center"><button class="btn btn-danger btn-sm" onclick="deleteSupplier(${supplier.id})"><i class="fas fa-trash"></i></button></td>
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

function showSuppliersFormContainer() {
  const form = document.getElementById('suppliersFormContainer');
  form.style.display = 'block';
}

hideSuppliersFormContainer();
loadSupplier();