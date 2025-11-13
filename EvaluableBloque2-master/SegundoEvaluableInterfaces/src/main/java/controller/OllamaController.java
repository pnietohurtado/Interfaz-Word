package controller;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class OllamaController {
    
    public String Traduccion(String message) {
        String[] urls = {
            "http://188.78.176.33:11434/api/generate"
        };
        
        for (String url : urls) {
            System.out.println("Intentando conectar a: " + url);
            String result = tryConnection(url, message);
            if (result != null) {
                return result;
            }
        }
        return "Error: No se pudo conectar al servidor";
    }
    
    private String tryConnection(String url, String message) {
        String prompt = "Traduce al inglés: " + message;
        
        String jsonInputString = String.format(
            "{\"model\": \"deepseek-r1:1.5b\", \"prompt\": \"%s\", \"stream\": false}",
            prompt.replace("\"", "\\\"")
        );

        System.out.println("JSON enviado: " + jsonInputString);

        try {
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");
            con.setConnectTimeout(15000); // Aumentado a 15 segundos
            con.setReadTimeout(45000);    // Aumentado a 45 segundos
            con.setDoOutput(true);

            System.out.println("Conectando...");
            
            // Escribir JSON
            try (OutputStream os = con.getOutputStream()) {
                byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
                System.out.println("JSON enviado al servidor");
            }

            System.out.println("Esperando respuesta...");
            
            // Leer respuesta
            int status = con.getResponseCode();
            System.out.println("Código de respuesta: " + status);
            
            try (BufferedReader br = new BufferedReader(
                new InputStreamReader(
                    status >= 200 && status < 300 ? con.getInputStream() : con.getErrorStream(),
                    StandardCharsets.UTF_8
                )
            )) {
                StringBuilder response = new StringBuilder();
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine);
                }
                
                String result = response.toString();
                /*
                System.out.println("Respuesta completa del servidor:");
                System.out.println(result);
                */
                // Extraer solo el texto de la respuesta
                String respuesta = extractResponseText(result); 
                System.out.println(respuesta);
                return respuesta;
            }

        } catch (SocketTimeoutException e) {
            System.out.println("Timeout conectando a: " + url + " - " + e.getMessage());
            return null;
        } catch (ConnectException e) {
            System.out.println("Conexión rechazada a: " + url + " - " + e.getMessage());
            return null;
        } catch (Exception e) {
            System.out.println("Error con " + url + ": " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
    
    private String extractResponseText(String jsonResponse) {
        try {
            // Buscar el campo "response" en el JSON
            if (jsonResponse.contains("\"response\":")) {
                int start = jsonResponse.indexOf("\"response\":\"") + 12;
                int end = jsonResponse.indexOf("\"", start);
                if (start > 11 && end > start) {
                    return jsonResponse.substring(start, end);
                }
            }
            return jsonResponse;
        } catch (Exception e) {
            return jsonResponse;
        }
    }
}