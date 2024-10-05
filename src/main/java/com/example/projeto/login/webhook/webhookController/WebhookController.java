package com.example.projeto.login.webhook.webhookController;

import java.io.IOException;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebhookController {

    @PostMapping("/webhook")
    public void handleWebhook(@RequestBody String payload) {
        executeDeployScript();
    }

    private void executeDeployScript() {
        try {
            String scriptPath = "/home/webhook/deploy.sh";

            ProcessBuilder processBuilder = new ProcessBuilder("bash", scriptPath);
            processBuilder.inheritIO();
            Process process = processBuilder.start();
            process.waitFor();
        } catch (IOException | InterruptedException e) {
        }
    }
}
