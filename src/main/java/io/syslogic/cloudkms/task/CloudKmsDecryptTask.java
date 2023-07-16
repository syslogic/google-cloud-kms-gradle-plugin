package io.syslogic.cloudkms.task;

import org.gradle.api.tasks.TaskAction;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

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
        File ciphertextFile = new File(getCiphertextFile().get());
        if (ciphertextFile.exists() && ciphertextFile.canRead()) {
            this.stdOut("ciphertextFile found: " + ciphertextFile.getAbsolutePath());
        } else {
            this.stdErr("> ciphertextFile not found: " + ciphertextFile.getAbsolutePath());
            return;
        }

        File plaintextFile = new File(getPlaintextFile().get());
        if (plaintextFile.exists() && plaintextFile.canWrite()) {
            this.stdOut("plaintextFile found: " + plaintextFile.getAbsolutePath());
            if ( Files.size(plaintextFile.toPath()) == 0) {
                this.stdOut("plaintextFile empty: " + plaintextFile.getAbsolutePath());
            } else {
                this.stdErr("> plaintextFile not empty: " + plaintextFile.getAbsolutePath());
                this.stdErr("> won't overwrite; exiting by default.");
                return; // only overwrite empty files.
            }
        } else {
            this.stdErr("> plaintextFile not found: " + plaintextFile.getAbsolutePath());
        }

        String cmd = "kms decrypt";
        cmd += " --plaintext-file=" + getPlaintextFile().get();
        cmd += " --ciphertext-file=" + getCiphertextFile().get();
        cmd += " --location=" + getKmsLocation().get();
        cmd += " --keyring=" + getKmsKeyring().get();
        cmd += " --key=" + getKmsKey().get();
        String result = this.execute(cmd);
        this.stdOut(result);
    }
}
