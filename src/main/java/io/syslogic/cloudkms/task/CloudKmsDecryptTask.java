package io.syslogic.cloudkms.task;

import org.gradle.api.tasks.TaskAction;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

/**
 * Abstract Decrypt {@link BaseTask}.
 * Note: This is meant to run remotely, in order to decrypt relevant files.
 *
 * @author Martin Zeitler
 */
abstract public class CloudKmsDecryptTask extends BaseTask {

    /**
     * The default {@link TaskAction}.
     * `ciphertextFiles` are the input for this task.
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
                this.stdOut("> cipher-text found: " + ciphertextFile.getAbsolutePath());
                if (Files.size(ciphertextFile.toPath()) == 0) {
                    this.stdErr("> cipher-text exists, but is empty: " + ciphertextFile.getAbsolutePath());
                    this.stdErr("> there's no input; exiting.");
                    return;
                }

                if (plaintextFile.exists() && plaintextFile.canWrite()) {
                    if (Files.size(plaintextFile.toPath()) > 0) {
                        this.stdErr("> plain-text exists and is not empty: " + plaintextFile.getAbsolutePath());
                        this.stdErr("> won't overwrite; exiting.");
                        return; // only overwrite empty files.
                    }
                }
            } else {
                this.stdErr("> cipher-text not found: " + ciphertextFile.getAbsolutePath());
                this.stdErr("> there's no input; exiting.");
                return;
            }
        }

        String params = this.getParams();
        StringBuilder result = new StringBuilder();
        for (int i=0; i < ciphertextFiles.size(); i++) {
            String cmd = "kms decrypt";
            cmd += " --plaintext-file=" + plaintextFiles.get(i);
            cmd += " --ciphertext-file=" + ciphertextFiles.get(i);
            cmd += params;
            result.append(this.execute(cmd));
        }
        this.stdOut(result.toString());
    }
}
