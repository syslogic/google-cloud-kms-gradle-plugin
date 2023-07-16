package io.syslogic.cloudkms;

import org.jetbrains.annotations.NotNull;

/**
 * Public API for Gradle build scripts.
 * @author Martin Zeitler
 */
@SuppressWarnings("unused")
public class CloudKmsExtensionImpl implements CloudKmsExtension {
    private String ciphertextFile = null;
    private String plaintextFile = null;
    private String kmsLocation = null;
    private String kmsKeyring = null;
    private String kmsKey = null;

    /**
     * Define the path to the encoded keystore file.
     * <br><br>
     * <code>cloudKms {ciphertextFile = ""}</code>
     * @param value the absolute path to the encoded keystore file.
     */
    public void setCiphertextFile(@NotNull String value) {
        this.ciphertextFile = value;
    }

    /**
     * Define the path to the plain-text keystore file.
     * <br><br>
     * <code>cloudKms {plaintextFile = ""}</code>
     * @param value the absolute path to the encoded keystore file.
     */
    public void setPlaintextFile(@NotNull String value) {
        this.plaintextFile = value;
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

    /** @return the path to the cipher-text file. */
    @Override
    public String getCiphertextFile() {return this.ciphertextFile;}

    /** @return the path to the plain-text file. */
    @Override
    public String getPlaintextFile() {
        return this.plaintextFile;
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
