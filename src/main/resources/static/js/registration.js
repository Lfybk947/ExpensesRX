// Adding interactivity to input fields
  document.addEventListener('DOMContentLoaded', function() {
    const inputs = document.querySelectorAll('.form-input');

    inputs.forEach(input => {
      // Adding a ficus effect
      input.addEventListener('focus', function() {
        this.style.borderColor = '#667eea';
        this.style.boxShadow = '0 0 0 3px rgba(102, 126, 234, 0.2)';
      });

      // Reverting to the default style when focus is lost
      input.addEventListener('blur', function() {
        this.style.borderColor = '#ddd';
        this.style.boxShadow = 'none';
      });
    });

    // Validate date of birth (cannot select a future date)
    const birthDateInput = document.getElementById('birthDate');
    if (birthDateInput) {
      const today = new Date().toISOString().split('T')[0];
      birthDateInput.setAttribute('max', today);
    }
  });