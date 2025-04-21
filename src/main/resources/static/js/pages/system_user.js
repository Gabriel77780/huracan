
document.getElementById('systemUserForm').addEventListener('submit', function(event) {
  event.preventDefault();

  const formData = new FormData(this);

  const systemUserId = formData.get('id');

  const systemUserData = {

    id: systemUserId ? formData.get('id') : null,
    name: formData.get('name'),
    email: formData.get('email'),
    password: formData.get('password'),
    role: formData.get('role'),

  };

  fetch('/system-user', {
    method: systemUserId ? "PUT" : "POST",
    headers: {
      'Content-Type': 'application/json',
      [header]: token
    },
    body: JSON.stringify(systemUserData)
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

      loadSystemUser();
      hideSystemUserFormContainer();
    } else {

      Swal.fire({
        icon: "error",
        text: data.message,
        confirmButtonText: "Aceptar",
        confirmButtonColor: "black",
      });

    }
  })
  .catch(error => console.error("Error creating or updating systemUser:", error));

});


function editSystemUser(id) {

  showSystemUserFormContainer();

  fetch(`/system-user/${id}`)
  .then(response => response.json())
  .then(systemUser => {
    document.getElementById("id").value = systemUser.id;
    document.getElementById("name").value = systemUser.name;
    document.getElementById("email").value = systemUser.email;
    document.getElementById("password").value = systemUser.password;
    document.getElementById("role").value = systemUser.role;
  })
  .catch(error => console.error("Error loading systemUser:", error));
}

function deleteSystemUser(id) {

  Swal.fire({
    title: "Está seguro de que desea borrar el usuario del sistema?",
    text: "Una vez eliminado, no podrá recuperar este usuario del sistema!",
    icon: "warning",
    showCancelButton: true,
    confirmButtonColor: "black",
    cancelButtonColor: "black",
    confirmButtonText: "Aceptar",
    cancelButtonText: "Cancelar",
  })
  .then((result) => {
    if (result.isConfirmed) {

      fetch(`/system-user/${id}`, {
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

          loadSystemUser();
        } else {
          Swal.fire({
            icon: "error",
            text: data.message,
            confirmButtonText: "Aceptar",
            confirmButtonColor: "black",
          });
        }

      })
      .catch(error => console.error("Error deleting systemUser:", error));

    }
  });

}

function loadSystemUser() {
  fetch("/system-user/all")
  .then(response => response.json())
  .then(systemUsers => {
    const tableBody = document.getElementById("systemUserTableBody");
    tableBody.innerHTML = "";

    systemUsers.forEach(systemUser => {
      const row = document.createElement("tr");
      row.innerHTML = `
          <td>${systemUser.name}</td>
          <td>${systemUser.email}</td>
          <td class="text-center"><button class="btn btn-dark btn-sm" onclick="editSystemUser(${systemUser.id})"><i class="fas fa-list"></i></button></td>
          <td class="text-center"><button class="btn btn-dark btn-sm" onclick="deleteSystemUser(${systemUser.id})"><i class="fas fa-trash"></i></button></td>
        `;
      tableBody.appendChild(row);
    });

  })
  .catch(error => console.error("Error loading systemUsers:", error));
}

function hideSystemUserFormContainer() {
  const form = document.getElementById('systemUserFormContainer');
  form.style.display = 'none';
}

function showSystemUserFormContainer() {
  const form = document.getElementById('systemUserFormContainer');
  form.style.display = 'block';
}

hideSystemUserFormContainer();
loadSystemUser();