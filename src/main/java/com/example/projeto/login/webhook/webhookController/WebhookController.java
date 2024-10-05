package com.example.projeto.login.webhook.webhookController;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebhookController {

    private static final Logger LOGGER = Logger.getLogger(WebhookController.class.getName());

    @PostMapping("/webhook")
    public void handleWebhook(@RequestBody String payload) {
        LOGGER.info("Recebido webhook com payload: " + payload);
        executeDeployScript();
    }

    private void executeDeployScript() {
        try {
            String scriptPath = "/home/webhook/deploy.sh";

            ProcessBuilder processBuilder = new ProcessBuilder("bash", scriptPath);
            processBuilder.inheritIO();
            Process process = processBuilder.start();

            int exitCode = process.waitFor();  // Espera o script terminar
            if (exitCode == 0) {
                LOGGER.info("Script de deploy executado com sucesso.");
            } else {
                LOGGER.severe("Falha na execução do script de deploy. Código de saída: " + exitCode);
            }
        } catch (IOException | InterruptedException e) {
            LOGGER.log(Level.SEVERE, "Erro ao executar o script de deploy.", e);
            Thread.currentThread().interrupt();  // Restaura o estado de interrupção após capturar InterruptedException
        }
    }
}
