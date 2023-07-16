package io.syslogic.cloudkms;

import java.util.List;

/**
 * Public API for Gradle build scripts.
 *
 * @author Martin Zeitler
 */
public interface CloudKmsExtension {

    /**
     * Define the path to the cipher-text file.
     * <code>cloudKms {ciphertextFiles = ["credentials/*.keystore.enc"]}</code>
     * @return path to the encoded keystore file.
     */
    List<String> getCiphertextFiles();

    /**
     * Define the path to the plain-text file.
     * <code>cloudKms {plaintextFiles = ["~/.android/*.keystore"]}</code>
     * @return path to the plain-text keystore file.
     */
    List<String> getPlaintextFiles();

    /**
     * Define the key-path for Cloud KMS.
     * <code>cloudKms {kmsKeyPath = "projects/PROJECT_ID/locations/global/keyRings/android-gradle/cryptoKeys/default"}</code>
     * @return the fully qualified key-path for Cloud KMS.
     */
    String getKmsKeyPath();

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
