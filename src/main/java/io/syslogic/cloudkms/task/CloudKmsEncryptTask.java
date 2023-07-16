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
     * `plaintextFiles` are the input for this task.
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

                this.stdOut("> plain-text found: " + plaintextFile.getAbsolutePath());
                if (Files.size(plaintextFile.toPath()) == 0) {
                    this.stdErr("> plain-text exists, but is empty: " + plaintextFile.getAbsolutePath());
                    this.stdErr("> there's no input; exiting.");
                    return;
                }

                if (ciphertextFile.exists() && ciphertextFile.canWrite()) {
                    if (Files.size(ciphertextFile.toPath()) > 0) {
                        this.stdErr("> cipher-text exists and is not empty: " + ciphertextFile.getAbsolutePath());
                        this.stdErr("> won't overwrite; exiting.");
                        return; // only overwrite empty files.
                    }
                }
            } else {
                this.stdErr("> plain-text not found: " + plaintextFile.getAbsolutePath());
                this.stdErr("> there's no input; exiting.");
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
            result.append(this.execute(cmd)).append("\n");
        }
        this.stdOut(result.toString());
      }
}
