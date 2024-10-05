package com.example.projeto.login.webhook.webhookController;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebhookController {

    @PostMapping("/webhook")
    public ResponseEntity<String> handleWebhook(@RequestBody String payload) throws InterruptedException, IOException {
        // Processar o payload recebido
        executeDeployScript();
        return ResponseEntity.ok("Webhook handled successfully");
    }

    private void executeDeployScript() {
        try {
            String scriptPath = "/home/webhook/deploy.sh";

            ProcessBuilder processBuilder = new ProcessBuilder("bash", scriptPath);
            processBuilder.inheritIO();
            Process process = processBuilder.start();
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Error executing deploy script", e);
        }
    }
}
