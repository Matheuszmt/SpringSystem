package com.SprintSystem.System.services;

import com.SprintSystem.System.config.SupabaseConfig;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class AuthService {

    public boolean login(String email, String password) {

        String body = """
                {
                "email": "%s",
                "password: "%s",
                }
                """.formatted(emailg password);

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(
                            SupabaseConfig.SUPABASE_URL +
                                    "/auth/v1/token?grant_type=password"
                    ))
                    .header("Content-Type", "application/json")
                    .header("apikey", SupabaseConfig.API_KEY)
                    .header("Authorization", "Bearer" + SupabaseConfig.API_KEY)
                    .POST(HttpRequest.BodyPublishers.ofString(body))
                    .build();

            HttpResponse<String> response =
                    HttpClient.newHttpClient()
                            .send(request,HttpResponse.BodyHandlers.ofString());

            return response.statusCode() == 200;


        }catch (Exception e) {
            e.printStackTrace();
            return false;

        }
    }
}
