package deploy;

import java.io.File;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebhookController {

    @PostMapping("/webhook")
    public void handleWebhook(@RequestBody String payload) {
        executePullAndBuild();
    }

    private void executePullAndBuild() {
        try {
            ProcessBuilder pullProcessBuilder = new ProcessBuilder("git", "-C", "/home/ProjetoIFTM", "pull");
            pullProcessBuilder.inheritIO();
            Process pullProcess = pullProcessBuilder.start();
            pullProcess.waitFor();

            ProcessBuilder mvnProcessBuilder = new ProcessBuilder("mvn", "-C", "/home/ProjetoIFTM", "clean", "package");
            mvnProcessBuilder.inheritIO();
            Process mvnProcess = mvnProcessBuilder.start();
            mvnProcess.waitFor();

            File newJar = new File("/home/ProjetoIFTM/target/projeto-0.0.1-SNAPSHOT.jar");
            File existingJar = new File("/home/projeto-0.0.1-SNAPSHOT.jar");

            if (newJar.exists()) {
                if (existingJar.delete()) {
                    newJar.renameTo(existingJar);
                }
            }
        } catch (Exception e) {
        }
    }
}
