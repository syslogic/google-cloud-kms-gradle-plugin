package io.syslogic.cloudkms.task;

import org.gradle.api.tasks.TaskAction;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

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
        File plaintextFile = new File(getPlaintextFile().get());
        if (plaintextFile.exists() && plaintextFile.canRead()) {
            this.stdOut("plaintextFile found: " + plaintextFile.getAbsolutePath());
        } else {
            this.stdErr("> plaintextFile not found: " + plaintextFile.getAbsolutePath());
        }

        File ciphertextFile = new File(getCiphertextFile().get());
        if (ciphertextFile.exists() && ciphertextFile.canWrite()) {
            this.stdOut("ciphertextFile found: " + ciphertextFile.getAbsolutePath());
            if ( Files.size(ciphertextFile.toPath()) == 0) {
                this.stdOut("ciphertextFile empty: " + plaintextFile.getAbsolutePath());
            } else {
                this.stdErr("> ciphertextFile not empty: " + ciphertextFile.getAbsolutePath());
                this.stdErr("> won't overwrite; exiting by default.");
                return; // only overwrite empty files.
            }
        } else {
            this.stdErr("> ciphertextFile not found: " + ciphertextFile.getAbsolutePath());
        }

        String cmd = "kms encrypt";
        cmd += " --plaintext-file=" + getPlaintextFile().get();
        cmd += " --ciphertext-file=" + getCiphertextFile().get();
        cmd += " --location=" + getKmsLocation().get();
        cmd += " --keyring=" + getKmsKeyring().get();
        cmd += " --key=" + getKmsKey().get();
        String result = this.execute(cmd);
        this.stdOut(result);
    }
}
