package com.SprintSystem.System;

import com.SprintSystem.System.services.AuthService;
import com.SprintSystem.System.services.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class SystemApplication {

	public static void main(String[] args) {

		Scanner scanner = new Scanner(System.in);
		AuthService authService = new AuthService();
		UserService userService = new UserService();

		System.out.println("1 - Criar usuário");
		System.out.println("2 - Login");
		System.out.println("3 - Listar usuários");
		System.out.println("4 - Deletar usuário");
		System.out.print("Escolha: ");
		int option = Integer.parseInt(scanner.nextLine());

		if (option == 1) {

			System.out.print("Email: ");
			String email = scanner.nextLine();
			System.out.print("Senha: ");
			String password = scanner.nextLine();

			if (userService.createUser(email, password)) {
				System.out.println("Usuário criado com sucesso!");
			} else {
				System.err.println("Erro ao criar usuário!");
			}
		}

		if (option == 2) {

			System.out.print("Email: ");
			String email = scanner.nextLine();
			System.out.print("Senha: ");
			String password = scanner.nextLine();

			if (authService.login(email, password)) {
				System.out.println("Login autorizado!");
			} else {
				System.err.println("Email ou senha inválidos!");
			}
		}

		if (option == 3) {
			String usersJson = userService.listUsers();
			if (usersJson != null) {
				System.out.println("Usuários cadastrados:");
				System.out.println(usersJson);
			} else {
				System.err.println("Erro ao listar usuários!");
			}
		}

		if (option == 4) {
			System.out.print("Digite o ID do usuário: ");
			String userId = scanner.nextLine();

			if (userService.deleteUserById(userId)) {
				System.out.println("Usuário deletado com sucesso!");
			} else {
				System.err.println("Erro ao deletar usuário!");
			}
		}

		scanner.close();

	}
}
