const backendUrl = 'http://localhost:8080/auth';  // Backend URL

// Register user
document.getElementById('register-form')?.addEventListener('submit', async function (event) {
    event.preventDefault();

    const email = document.getElementById('email').value;
    const password = document.getElementById('password').value;
    const fullName = document.getElementById('fullName').value;  // Capture full name for registration

    const response = await fetch(`${backendUrl}/signup`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            email: email,
            password: password,
            fullName: fullName   // Include full name in request body
        }),
        credentials: 'include'
    });

    if (response.ok) {
        alert('Registration successful! You can now sign in.');
        window.location.href = 'signin.html';  // Redirect to signin page after successful signup
    } else {
        alert('Registration failed.');
    }
});

// Sign-in user
document.getElementById('signin-form')?.addEventListener('submit', async function (event) {
    event.preventDefault();

    const email = document.getElementById('email').value;
    const password = document.getElementById('password').value;

    const response = await fetch(`${backendUrl}/login`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            email: email,
            password: password
        })
    });

    const data = await response.json();

    if (response.ok && data.token) {
        localStorage.setItem('jwtToken', data.token);  // Store JWT token on successful login
        alert('Login successful!');
        window.location.href = 'index.html';  // Redirect to homepage
    } else {
        alert('Login failed.');
    }
});

// Function to fetch authenticated user details
async function fetchUserDetails() {
    const token = localStorage.getItem('jwtToken'); // Get the JWT token from local storage

    if (token) {
        const response = await fetch(`http://localhost:8080/users/me`, {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${token}` // Include JWT token in the Authorization header
            }
        });

        if (response.ok) {
            const user = await response.json();
            // Display user details on the page
            document.getElementById('user-fullname').innerText = `Full Name: ${user.fullName}`; // Assuming your User entity has a 'fullName' property
            document.getElementById('user-email').innerText = `Email: ${user.email}`; // Assuming your User entity has an 'email' property
        } else {
            console.error('Failed to fetch user details');
        }
    } else {
        console.error('No token found, please log in');
        window.location.href = 'signin.html'; // Redirect to sign-in page if no token is found
    }
}

// Call the function when the page loads
document.addEventListener('DOMContentLoaded', fetchUserDetails);

// Logout user
document.getElementById('logout')?.addEventListener('click', function () {
    localStorage.removeItem('jwtToken');  // Remove JWT token from storage
    window.location.href = 'signin.html';  // Redirect to sign-in page
});
