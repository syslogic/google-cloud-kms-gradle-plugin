package io.syslogic.cloudkms.task;

import org.gradle.api.tasks.TaskAction;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

/**
 * Abstract Encrypt {@link io.syslogic.cloudkms.task.BaseTask}
 *
 * @author Martin Zeitler
 */
abstract public class CloudKmsEncryptTask extends BaseTask {

    /**
     * The default {@link TaskAction}.
     * @throws IOException when the size of ciphertextFile cannot be determined.
     */
    @TaskAction
    public void run() throws IOException {

        List<String> plaintextFiles = getPlaintextFiles().get();
        List<String> ciphertextFiles = getCiphertextFiles().get();

        for (int i=0; i < plaintextFiles.size(); i++) {
            File plaintextFile = new File(plaintextFiles.get(i));
            File ciphertextFile = new File(ciphertextFiles.get(i));
            if (plaintextFile.exists() && plaintextFile.canRead()) {
                this.stdOut("plaintextFile found: " + plaintextFile.getAbsolutePath());

                if (ciphertextFile.exists() && ciphertextFile.canWrite()) {
                    this.stdOut("ciphertextFile found: " + ciphertextFile.getAbsolutePath());
                    if (Files.size(ciphertextFile.toPath()) == 0) {
                        this.stdOut("ciphertextFile empty: " + ciphertextFile.getAbsolutePath());
                    } else {
                        this.stdErr("> ciphertextFile not empty: " + ciphertextFile.getAbsolutePath());
                        this.stdErr("> won't overwrite; exiting by default.");
                        return; // only overwrite empty files.
                    }
                }
            } else {
                this.stdErr("> plaintextFile not found: " + plaintextFile.getAbsolutePath());
                return;
            }
        }

        StringBuilder result = new StringBuilder();
        for (int i=0; i < ciphertextFiles.size(); i++) {
            String cmd = "kms encrypt";
            cmd += " --plaintext-file=" + plaintextFiles.get(i);
            cmd += " --ciphertext-file=" + ciphertextFiles.get(i);
            cmd += " --location=" + getKmsLocation().get();
            cmd += " --keyring=" + getKmsKeyring().get();
            cmd += " --key=" + getKmsKey().get();
            result.append(this.execute(cmd));
        }
        this.stdOut(result.toString());
      }
}
