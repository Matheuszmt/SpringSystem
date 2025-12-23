// URL base da API - ajuste conforme seu backend
const API_URL = 'http://localhost:8080/api';

// Função para mostrar seção específica
function showSection(sectionId) {
    document.getElementById('menu').style.display = 'none';
    document.querySelectorAll('.form-section').forEach(section => {
        section.style.display = 'none';
    });
    document.getElementById(sectionId).style.display = 'block';

    // Se for listar, carrega os usuários automaticamente
    if (sectionId === 'list') {
        listUsers();
    }
}

// Função para voltar ao menu
function showMenu() {
    document.querySelectorAll('.form-section').forEach(section => {
        section.style.display = 'none';
    });
    document.getElementById('menu').style.display = 'flex';

    // Limpar mensagens
    document.querySelectorAll('.message').forEach(msg => {
        msg.style.display = 'none';
        msg.className = 'message';
    });

    // Limpar formulários
    document.querySelectorAll('form').forEach(form => {
        form.reset();
    });
}

// Função para mostrar mensagem
function showMessage(elementId, message, type) {
    const messageEl = document.getElementById(elementId);
    messageEl.textContent = message;
    messageEl.className = `message ${type}`;
    messageEl.style.display = 'block';
}

// Criar usuário
async function createUser(event) {
    event.preventDefault();

    const email = document.getElementById('createEmail').value;
    const password = document.getElementById('createPassword').value;

    try {
        const response = await fetch(`${API_URL}/users`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ email, password })
        });

        if (response.ok) {
            showMessage('createMessage', 'Usuário criado com sucesso!', 'success');
            document.getElementById('createEmail').value = '';
            document.getElementById('createPassword').value = '';
        } else {
            showMessage('createMessage', 'Erro ao criar usuário!', 'error');
        }
    } catch (error) {
        showMessage('createMessage', 'Erro de conexão com o servidor!', 'error');
        console.error('Erro:', error);
    }
}

// Login
async function loginUser(event) {
    event.preventDefault();

    const email = document.getElementById('loginEmail').value;
    const password = document.getElementById('loginPassword').value;

    try {
        const response = await fetch(`${API_URL}/auth/login`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ email, password })
        });

        if (response.ok) {
            showMessage('loginMessage', 'Login autorizado!', 'success');
        } else {
            showMessage('loginMessage', 'Email ou senha inválidos!', 'error');
        }
    } catch (error) {
        showMessage('loginMessage', 'Erro de conexão com o servidor!', 'error');
        console.error('Erro:', error);
    }
}

// Listar usuários
async function listUsers() {
    const usersList = document.getElementById('usersList');
    usersList.innerHTML = '<p>Carregando...</p>';

    try {
        const response = await fetch(`${API_URL}/users`);

        if (response.ok) {
            const users = await response.json();

            if (users.length === 0) {
                usersList.innerHTML = '<p>Nenhum usuário cadastrado.</p>';
            } else {
                usersList.innerHTML = users.map(user => `
                    <div class="user-item">
                        <strong>ID:</strong> ${user.id}<br>
                        <strong>Email:</strong> ${user.email}
                    </div>
                `).join('');
            }
        } else {
            usersList.innerHTML = '<p class="error">Erro ao listar usuários!</p>';
        }
    } catch (error) {
        usersList.innerHTML = '<p class="error">Erro de conexão com o servidor!</p>';
        console.error('Erro:', error);
    }
}

// Deletar usuário
async function deleteUser(event) {
    event.preventDefault();

    const userId = document.getElementById('deleteId').value;

    try {
        const response = await fetch(`${API_URL}/users/${userId}`, {
            method: 'DELETE'
        });

        if (response.ok) {
            showMessage('deleteMessage', 'Usuário deletado com sucesso!', 'success');
            document.getElementById('deleteId').value = '';
        } else {
            showMessage('deleteMessage', 'Erro ao deletar usuário!', 'error');
        }
    } catch (error) {
        showMessage('deleteMessage', 'Erro de conexão com o servidor!', 'error');
        console.error('Erro:', error);
    }
}