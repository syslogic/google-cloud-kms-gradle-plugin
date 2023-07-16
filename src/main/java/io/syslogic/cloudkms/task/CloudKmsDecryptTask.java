package io.syslogic.cloudkms.task;

import org.gradle.api.tasks.TaskAction;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

/**
 * Abstract Decrypt {@link BaseTask}
 *
 * @author Martin Zeitler
 */
abstract public class CloudKmsDecryptTask extends BaseTask {

    /**
     * The default {@link TaskAction}.
     * @throws IOException when the size of plaintextFile cannot be determined.
     */
    @TaskAction
    public void run() throws IOException {

        List<String> ciphertextFiles = getCiphertextFiles().get();
        List<String> plaintextFiles = getPlaintextFiles().get();

        for (int i=0; i < ciphertextFiles.size(); i++) {
            File ciphertextFile = new File(ciphertextFiles.get(i));
            File plaintextFile = new File(plaintextFiles.get(i));
            if (ciphertextFile.exists() && ciphertextFile.canRead()) {
                this.stdOut("ciphertextFile found: " + ciphertextFile.getAbsolutePath());

                if (plaintextFile.exists() && plaintextFile.canWrite()) {
                    this.stdOut("plaintextFile found: " + plaintextFile.getAbsolutePath());
                    if (Files.size(plaintextFile.toPath()) == 0) {
                        this.stdOut("plaintextFile empty: " + plaintextFile.getAbsolutePath());
                    } else {
                        this.stdErr("> plaintextFile not empty: " + plaintextFile.getAbsolutePath());
                        this.stdErr("> won't overwrite; exiting by default.");
                        return; // only overwrite empty files.
                    }
                }
            } else {
                this.stdErr("> ciphertextFile not found: " + ciphertextFile.getAbsolutePath());
                return;
            }
        }

        StringBuilder result = new StringBuilder();
        for (int i=0; i < ciphertextFiles.size(); i++) {
            String cmd = "kms decrypt";
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
