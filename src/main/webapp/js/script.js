// Check if user is authenticated
function checkAuth() {
    // In a real application, this would check with the server
    // For demo purposes, we'll simulate authentication
    const username = localStorage.getItem('username');
    
    if (username) {
        document.getElementById('username').textContent = username;
    } else {
        // If not on login page, redirect to login
        if (!window.location.pathname.endsWith('login.html')) {
            window.location.href = 'login.html';
        }
    }
}

// Handle login form
document.addEventListener('DOMContentLoaded', function() {
    const loginForm = document.getElementById('login-form');
    if (loginForm) {
        loginForm.addEventListener('submit', function(e) {
            e.preventDefault();
            
            const username = document.getElementById('username').value;
            const password = document.getElementById('password').value;
            
            // Simple validation
            if (!username || !password) {
                showError('Please enter both username and password');
                return;
            }
            
            // In a real application, this would be an AJAX request
            // For demo purposes, we'll simulate a successful login
            if (username === 'admin' && password === 'admin123') {
                localStorage.setItem('username', username);
                window.location.href = 'dashboard.html';
            } else {
                showError('Invalid username or password');
            }
        });
        
        // Check for error parameter in URL
        const urlParams = new URLSearchParams(window.location.search);
        if (urlParams.get('error') === '1') {
            showError('Invalid username or password');
        }
    }
});

// Show error message
function showError(message) {
    const errorElement = document.getElementById('error-message');
    if (errorElement) {
        errorElement.textContent = message;
        errorElement.style.display = 'block';
        
        // Hide after 5 seconds
        setTimeout(() => {
            errorElement.style.display = 'none';
        }, 5000);
    }
}

// Logout function
function logout() {
    localStorage.removeItem('username');
    window.location.href = 'login.html';
}