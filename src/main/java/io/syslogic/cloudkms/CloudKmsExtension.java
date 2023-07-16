package io.syslogic.cloudkms;

/**
 * Public API for Gradle build scripts.
 *
 * @author Martin Zeitler
 */
public interface CloudKmsExtension {

    /**
     * Define the path to the cipher-text file.
     * <code>cloudKms {ciphertextFile = "credentials/*.keystore.enc"}</code>
     * @return path to the encoded keystore file.
     */
    String getCiphertextFile();

    /**
     * Define the path to the plain-text file.
     * <code>cloudKms {plaintextFile = "~/.android/*.keystore"}</code>
     * @return path to the plain-text keystore file.
     */
    String getPlaintextFile();

    /**
     * Define the key-ring location for Cloud KMS.
     * <code>cloudKms {kmsLocation = "global"}</code>
     * @return the key-ring location for Cloud KMS.
     */
    String getKmsLocation();

    /**
     * Define the key-ring name for Cloud KMS.
     * <code>cloudKms {kmsKeyring = "android-gradle"}</code>
     * @return the key-ring name for Cloud KMS.
     */
    String getKmsKeyring();

    /**
     * Define the key name for Cloud KMS.
     * <code>cloudKms {kmsKey = "default"}</code>
     * @return the key name for Cloud KMS.
     */
    String getKmsKey();
}
