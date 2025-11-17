// Adding interactivity to input fields
    document.addEventListener('DOMContentLoaded', function() {
        const inputs = document.querySelectorAll('.form-input');

        inputs.forEach(input => {
            // Adding a focus effect
            input.addEventListener('focus', function() {
                this.parentElement.querySelector('.input-icon').style.color = '#667eea';
            });

            // Return to normal color when focus is lost
            input.addEventListener('blur', function() {
                this.parentElement.querySelector('.input-icon').style.color = '#888';
            });
        });
    });