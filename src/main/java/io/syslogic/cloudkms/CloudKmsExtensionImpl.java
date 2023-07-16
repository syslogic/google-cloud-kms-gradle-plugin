package io.syslogic.cloudkms;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Public API for Gradle build scripts.
 * @author Martin Zeitler
 */
@SuppressWarnings("unused")
public class CloudKmsExtensionImpl implements CloudKmsExtension {
    private List<String> ciphertextFiles;
    private List<String> plaintextFiles;
    private String kmsLocation = null;
    private String kmsKeyring = null;
    private String kmsKey = null;

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
     * <code>cloudKms {kmsLocation = "global"}</code>
     * @param value the key-ring location.
     */
    public void setKmsLocation(@NotNull String value) {
        this.kmsLocation = value;
    }

    /**
     * Define the Google Cloud KMS key-ring name.
     * <br><br>
     * <code>cloudKms {kmsKeyring = "android-gradle"}</code>
     * @param value the key-ring name.
     */
    public void setKmsKeyring(@NotNull String value) {
        this.kmsKeyring = value;
    }

    /**
     * Define the Google Cloud KMS key name.
     * <br><br>
     * <code>cloudKms {kmsKey = "default"}</code>
     * @param value the key name.
     */
    public void setKmsKey(@NotNull String value) {
        this.kmsKey = value;
    }

    @Override
    public List<String> getCiphertextFiles() {
        return this.ciphertextFiles;
    }

    @Override
    public List<String> getPlaintextFiles() {
        return this.plaintextFiles;
    }

    /** @return Google Cloud KMS key-ring location. */
    @Override
    public String getKmsLocation() {
        return this.kmsLocation;
    }

    /** @return Google Cloud KMS key-ring name. */
    @Override
    public String getKmsKeyring() {
        return this.kmsKeyring;
    }

    /** @return Google Cloud KMS key name. */
    @Override
    public String getKmsKey() {
        return this.kmsKey;
    }
}
