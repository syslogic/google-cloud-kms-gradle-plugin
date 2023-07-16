package io.syslogic.cloudkms;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.List;

import io.syslogic.cloudkms.task.CloudKmsDecryptTask;
import io.syslogic.cloudkms.task.CloudKmsEncryptTask;

/**
 * Google Cloud KMS Plugin
 *
 * @author Martin Zeitler
 */
@SuppressWarnings("unused")
class CloudKmsPlugin implements Plugin<Project> {

    @Nullable CloudKmsExtension extension;
    private @Nullable String plaintextFile = null;
    private @Nullable String ciphertextFile = null;
    private @NotNull String kmsLocation = "global";
    private @NotNull String kmsKeyring = "android-gradle";
    private @NotNull String kmsKey = "default";

    // private @Nullable List<String> plaintextFiles = null;
    // private @Nullable List<String> ciphertextFiles = null;

    @Override
    public void apply(@NotNull Project project) {

        /* Create project extension `cloudKms`. */
        this.extension = project.getExtensions().create("cloudKms", CloudKmsExtensionImpl.class);

        /* Project after evaluate. */
        project.afterEvaluate(it -> {

            if (this.extension.getCiphertextFile() != null) {
                this.ciphertextFile = this.extension.getCiphertextFile();
            } else {
                /* Apply the default path for the encrypted file. */
                this.ciphertextFile = project.getRootProject().getProjectDir().getAbsolutePath() +
                        File.separator + "credentials" + File.separator + "debug.keystore.enc";
            }

            if (this.extension.getPlaintextFile() != null) {
                this.plaintextFile = this.extension.getPlaintextFile();
            } else {
                /* Apply the default path for the plain-text file. */
                this.plaintextFile = System.getProperty("user.home") + File.separator +
                        ".android" + File.separator + "debug.keystore";
            }

            if (this.extension.getKmsLocation() != null) {
                this.kmsLocation = this.extension.getKmsLocation();
            }

            if (this.extension.getKmsKeyring() != null) {
                this.kmsKeyring = this.extension.getKmsKeyring();
            }

            if (extension.getKmsKey() != null) {
                this.kmsKey = this.extension.getKmsKey();
            }

            /* Register Tasks: CloudKmsEncode */
            registerCloudKmsEncryptTask(project);

            /* Register Tasks: CloudKmsDecrypt */
            registerCloudKmsDecryptTask(project);
        });
    }

    void registerCloudKmsEncryptTask(@NotNull Project project) {
        if (project.getTasks().findByName("cloudKmsEncrypt") == null) {
            project.getTasks().register("cloudKmsEncrypt", CloudKmsEncryptTask.class, task -> {
                task.setGroup("cloudkms");
                task.getCiphertextFile().set(this.ciphertextFile);
                task.getPlaintextFile().set(this.plaintextFile);
                task.getKmsLocation().set(this.kmsLocation);
                task.getKmsKeyring().set(this.kmsKeyring);
                task.getKmsKey().set(this.kmsKey);
            });
        }
    }

    void registerCloudKmsDecryptTask(@NotNull Project project) {
        if (project.getTasks().findByName("cloudKmsDecrypt") == null) {
            project.getTasks().register("cloudKmsDecrypt", CloudKmsDecryptTask.class, task -> {
                task.setGroup("cloudkms");
                task.getCiphertextFile().set(this.ciphertextFile);
                task.getPlaintextFile().set(this.plaintextFile);
                task.getKmsLocation().set(this.kmsLocation);
                task.getKmsKeyring().set(this.kmsKeyring);
                task.getKmsKey().set(this.kmsKey);
            });
        }
    }
}
