package io.syslogic.cloudkms;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Public API for Gradle build scripts.
 * Either `kmsKeyPath` or `kmsLocation`, `kmsKeyring` and `kmsKey` are required.
 * @author Martin Zeitler
 */
@SuppressWarnings("unused")
public class CloudKmsExtensionImpl implements CloudKmsExtension {
    private List<String> ciphertextFiles;
    private List<String> plaintextFiles;
    private String kmsKeyPath = null;

    /**
     * Define the path to the encoded keystore file.
     * <br><br>
     * <code>cloudKms {ciphertextFiles = [""]}</code>
     * @param value the absolute path to the encoded keystore file.
     */
    public void setCiphertextFiles(@NotNull List<String> value) {
        this.ciphertextFiles = value;
    }

    /**
     * Define the path to the plain-text keystore file.
     * <br><br>
     * <code>cloudKms {plaintextFiles = [""]}</code>
     * @param value the absolute path to the encoded keystore file.
     */
    public void setPlaintextFiles(@NotNull List<String> value) {
        this.plaintextFiles = value;
    }

    /**
     * Define the Google Cloud KMS key-ring location.
     * <br><br>
     * <code>cloudKms {kmsKeyPath = "global"}</code>
     * @param value the path of the key.
     */
    public void setKmsKeyPath(@NotNull String value) {
        this.kmsKeyPath = value;
    }

    @Override
    public List<String> getCiphertextFiles() {
        return this.ciphertextFiles;
    }

    @Override
    public List<String> getPlaintextFiles() {
        return this.plaintextFiles;
    }

    /** @return Google Cloud KMS key-path. */
    @Override
    public String getKmsKeyPath() {
        return this.kmsKeyPath;
    }
}
