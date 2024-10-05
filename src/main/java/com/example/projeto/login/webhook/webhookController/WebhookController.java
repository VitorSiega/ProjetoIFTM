package com.example.projeto.login.webhook.webhookController;

import java.io.IOException;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebhookController {

    private static final String SECRET = "vige0284"; // Defina seu segredo aqui

    @PostMapping("/webhook")
    public void handleWebhook(@RequestBody String payload,
            @RequestHeader(value = "X-Hub-Signature", required = false) String signature) throws InterruptedException, IOException {
        // Validação do segredo (se estiver usando)
        if (!isValidSignature(signature)) {
            // Retorne uma resposta de erro (401 Unauthorized ou 403 Forbidden)
            throw new RuntimeException("Invalid signature");
        }

        // Processar o payload recebido
        executeDeployScript();
    }

    private boolean isValidSignature(String signature) {
        // Adicione a lógica para validar a assinatura com seu segredo
        return SECRET.equals(signature);
    }

    private void executeDeployScript() throws InterruptedException, IOException {

        String scriptPath = "/home/webhook/deploy.sh";

        ProcessBuilder processBuilder = new ProcessBuilder("bash", scriptPath);
        processBuilder.inheritIO();
        Process process = processBuilder.start();
        process.waitFor();

    }
}
