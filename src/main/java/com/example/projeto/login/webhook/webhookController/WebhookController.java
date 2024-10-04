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
            // Define o caminho do script
            String scriptPath = "/home/webhook/deploy.sh";

            // Cria um ProcessBuilder para executar o script
            ProcessBuilder processBuilder = new ProcessBuilder("bash", scriptPath);
            processBuilder.inheritIO(); // Para herdar a entrada e saída do processo
            Process process = processBuilder.start(); // Inicia o processo
            process.waitFor(); // Aguarda a conclusão do processo

            // Aqui você pode adicionar qualquer lógica adicional se necessário
        } catch (IOException | InterruptedException e) {
            // Tratar exceções conforme necessário
            e.printStackTrace();
        }
    }
}
