package com.SprintSystem.System.services;

import com.SprintSystem.System.config.SupabaseConfig;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class UserService {

    private Object userId;

    public boolean createUser(String email, String password) {

        String body = """
                {
                  "email": "%s",
                  "password": "%s"
                }
                """.formatted(email, password);

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(
                            SupabaseConfig.SUPABASE_URL + "/auth/v1/signup"
                    ))
                    .header("Content-Type", "application/json")
                    .header("apikey", SupabaseConfig.API_KEY)
                    .POST(HttpRequest.BodyPublishers.ofString(body))
                    .build();

            HttpResponse<String> response =
                    HttpClient.newHttpClient()
                            .send(request, HttpResponse.BodyHandlers.ofString());

            // 201 ou 200 → usuário criado
            return response.statusCode() == 200 || response.statusCode() == 201;



        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteUserById(String userId) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(
                            SupabaseConfig.SUPABASE_URL + "/auth/v1/admin/users/" + userId
                    ))
                    .header("apikey", SupabaseConfig.SERVICE_ROLE_KEY)
                    .header("Authorization", "Bearer " + SupabaseConfig.SERVICE_ROLE_KEY)
                    .DELETE()
                    .build();

            HttpResponse<String> response = HttpClient.newHttpClient()
                    .send(request, HttpResponse.BodyHandlers.ofString());
            return response.statusCode() == 200 || response.statusCode() == 204;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    public String listUsers() {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(
                            SupabaseConfig.SUPABASE_URL + "/auth/v1/admin/users"
                    ))
                    .header("apikey", SupabaseConfig.SERVICE_ROLE_KEY)
                    .header("Authorization", "Bearer " + SupabaseConfig.SERVICE_ROLE_KEY)
                    .GET()
                    .build();

            HttpResponse<String> response =
                    HttpClient.newHttpClient()
                            .send(request, HttpResponse.BodyHandlers.ofString());

            System.out.println("STATUS: " + response.statusCode());
            return response.body();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
