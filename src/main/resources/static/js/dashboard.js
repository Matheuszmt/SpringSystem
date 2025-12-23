// Verificar autenticação
if (!Storage.isAuthenticated()) {
    window.location.href = 'login.html';
}

// Mostrar email do usuário
const user = Storage.getUser();
if (user) {
    document.getElementById('userEmail').textContent = `Email: ${user.email}`;
}

// Logout
document.getElementById('logoutBtn').addEventListener('click', () => {
    Storage.removeUser();
    window.location.href = 'login.html';
});