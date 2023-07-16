package io.syslogic.cloudkms.task;

import org.gradle.api.DefaultTask;
import org.gradle.api.provider.ListProperty;
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

    /**
     * Task Input `ciphertextFiles`.
     * @return paths to the cipher-text files.
     */
    @Input
    abstract public ListProperty<String> getCiphertextFiles();

    /**
     * Task Input `plaintextFiles`.
     * @return paths to the plain-text files.
     */
    @Input
    abstract public ListProperty<String> getPlaintextFiles();

    /**
     * Task Input `kmsLocation`.
     * @return location of the key-ring.
     */
    @Input
    abstract public Property<String> getKmsLocation();

    /**
     * Task Input `kmsKeyring`.
     * @return name of the key-ring.
     */
    @Input
    abstract public Property<String> getKmsKeyring();

    /**
     * Task Input `kmsKey`.
     * @return name of the key.
     */
    @Input
    abstract public Property<String> getKmsKey();

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

    /**
     * It executes the gcloud CLI command in PowerShell or Bash.
     * @param command the sub-command to execute.
     * @return the process input-stream.
     */
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
