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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
     * Task Input `kmsKeyPath`.
     * @return path of the key.
     */
    @Input
    abstract public Property<String> getKmsKeyPath();

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

    private static final String KMS_KEY_PATH_PATTERN = "^projects/(.*)/locations/(.*)/keyRings/(.*)/cryptoKeys/(.*)$";

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

    /** @return gcloud parameters, either from keyPath or from location/keyStore/key */
    @Input
    protected String getParams() {
        String param = null;
        boolean found = false;
        if (! getKmsKeyPath().get().isEmpty()) {
            Pattern p = Pattern.compile(KMS_KEY_PATH_PATTERN);
            Matcher matcher = p.matcher(getKmsKeyPath().get());
            while (matcher.find()) {
                param = " --location=" + matcher.group(2);
                param += " --keyring=" + matcher.group(3);
                param += " --key=" + matcher.group(4);
                found = true;
            }
        }

        if (! found) {
            param = " --location=" + getKmsLocation().get();
            param += " --keyring=" + getKmsKeyring().get();
            param += " --key=" + getKmsKey().get();
        }
        return param;
    }
}
