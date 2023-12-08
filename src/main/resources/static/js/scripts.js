function postLogoutForm() {
    let logoutForm = document.getElementById('logoutForm');
    logoutForm.submit();
}

function preventWhitespace(e) {
    e.target.value = e.target.value.replace(/ /g, "");
}

function searchEmployees() {
    let searchCriteria = document.getElementById('employeeSearch');
    let employeesList = document.getElementById('employeesList');

    fetch('/search/employees?criteria=' + searchCriteria.value).then(
        function (response) {
            return response.json();
        }).then(function (data) {
        if (data.length === 0) {
            employeesList.innerHTML = '<option value="">No employees match the criteria</option>'
            return true;
        }

        let foundEmployees = '<option value="">Select employee (' + data.length + ' found)</option>'
        for (let i = 0; i < data.length; i++) {
            foundEmployees += '<option value="' + data[i].id + '">' + data[i].firstName + ' ' + data[i].lastName + '</option>';
        }
        employeesList.innerHTML = foundEmployees;
        return true;
    }).catch(function (err) {
        console.warn('Something went wrong.', err);
        employeesList.innerHTML = '<option value="">Error occurred while searching, try again</option>';
        return false;
    });
}