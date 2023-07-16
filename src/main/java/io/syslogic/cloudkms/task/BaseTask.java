package io.syslogic.cloudkms.task;

import org.gradle.api.DefaultTask;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;
import org.gradle.internal.os.OperatingSystem;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Abstract {@link BaseTask}
 *
 * @author Martin Zeitler
 */
abstract public class BaseTask extends DefaultTask {

    /** Task Input `ciphertextFile`. */
    @Input
    abstract public Property<String> getCiphertextFile();

    /** Task Input `plaintextFile`. */
    @Input
    abstract public Property<String> getPlaintextFile();

    /** Task Input `kmsLocation`. */
    @Input
    abstract public Property<String> getKmsLocation();

    /** Task Input `kmsKeyring`. */
    @Input
    abstract public Property<String> getKmsKeyring();

    /** Task Input `kmsKey`. */
    @Input
    abstract public Property<String> getKmsKey();

    /** The absolute path to the gcloud command. */

    boolean configure() {
        return true;
    }

    void stdOut(@NotNull String value) {
        System.out.println(value);
    }

    void stdErr(@NotNull String value) {
        System.err.println(value);
    }

    @NotNull
    private String getCloudCli() {

        String line;
        StringBuilder stdOut = new StringBuilder();
        String cmd = "which gcloud"; // Linux
        if (OperatingSystem.current().isWindows()) {
            cmd = "powershell.exe (Get-Command gcloud).Path";
        }

        try {
            Process process = Runtime.getRuntime().exec(cmd);
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            while ((line = reader.readLine()) != null) {stdOut.append(line);}
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return stdOut.toString();
    }

    @NotNull
    protected String execute(String command) {
        String cmd;
        String gcloud = this.getCloudCli();
        if (OperatingSystem.current().isWindows()) {
            cmd = "powershell.exe \"" + gcloud + " " + command + "\"";
        } else {
            cmd = gcloud + " " + command;
        }
        this.stdOut("> " + cmd);
        StringBuilder stdOutput = new StringBuilder();
        StringBuilder stdError = new StringBuilder();
        String line;

        try {
            Process process = Runtime.getRuntime().exec(cmd);

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            while ((line = reader.readLine()) != null) {stdOutput.append(line);}
            if (! stdOutput.isEmpty()) {this.stdOut("> " + stdOutput);}

            BufferedReader error = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            while ((line = error.readLine()) != null) {stdError.append(line);}
            if (! stdError.isEmpty()) {this.stdErr("> " + stdError);}
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return stdOutput.toString();
    }
}
