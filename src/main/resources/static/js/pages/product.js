document.getElementById('productForm').addEventListener('submit', function(event) {
  event.preventDefault();

  // Collect form data
  const formData = new FormData(this);
  // Get CSRF token from the meta tag
  const token = document.querySelector('meta[name="_csrf"]').getAttribute('content');
  const header = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');

  console.log('Token: ', token);
  console.log('Header: ', header);

// Data to send in the request
  const productData = {
    name: formData.get('name'),
    description: formData.get('description'),
    price: parseFloat(formData.get('price')),
    stock: parseInt(formData.get('stock')),
    category: formData.get('category')
  };

  fetch('/product', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      [header]: token // Set CSRF token header dynamically
    },
    body: JSON.stringify(productData)
  })
  .then(response => response.json())
  .then(data => {
    // Display success message
    const message = data.message || "Producto creado exitosamente!";
    document.getElementById('responseMessage').innerHTML = `<div class="alert alert-success">${message}</div>`;
  })
  .catch(error => {
    // Display error message
    document.getElementById('responseMessage').innerHTML = `<div class="alert alert-danger">Error al crear el producto.</div>`;
  });

});