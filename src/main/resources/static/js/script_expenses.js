        function selectAll() {
        const checkboxes = document.querySelectorAll('input[type="checkbox"]');
        const allChecked = document.querySelector('input[type="checkbox"]:checked');
        let isAllSelected = true;
        checkboxes.forEach(function(checkbox) {
            if (!checkbox.checked) {
                isAllSelected = false;
            }
        });

        checkboxes.forEach(function(checkbox) {
            if (isAllSelected) {
                checkbox.checked = false;
            } else {
                checkbox.checked = true;
            }
        });
    }
   console.log("1")