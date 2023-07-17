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

    private String kmsKeyPath = null;
    private List<String> plaintextFiles;
    private List<String> ciphertextFiles;

    /**
     * Define the Cloud KMS key path.
     * <br><br>
     * <code>cloudKms {kmsKeyPath = "global"}</code>
     * @param value the path of the key.
     */
    public void setKmsKeyPath(@NotNull String value) {
        this.kmsKeyPath = value;
    }

    /**
     * Define an array of plain-text files.
     * <br><br>
     * <code>cloudKms {plaintextFiles = [""]}</code>
     * @param value the absolute path to the encoded keystore file.
     */
    public void setPlaintextFiles(@NotNull List<String> value) {
        this.plaintextFiles = value;
    }

    /**
     * Define an array of cipher-text files.
     * <br><br>
     * <code>cloudKms {ciphertextFiles = [""]}</code>
     * @param value the absolute path to the encoded keystore file.
     */
    public void setCiphertextFiles(@NotNull List<String> value) {
        this.ciphertextFiles = value;
    }

    /** @return Cloud KMS key path. */
    @Override
    public String getKmsKeyPath() {
        return this.kmsKeyPath;
    }

    /** @return an array of plain-text filenames. */
    @Override
    public List<String> getPlaintextFiles() {
        return this.plaintextFiles;
    }

    /** @return an array of cipher-text filenames. */
    @Override
    public List<String> getCiphertextFiles() {
        return this.ciphertextFiles;
    }
}
