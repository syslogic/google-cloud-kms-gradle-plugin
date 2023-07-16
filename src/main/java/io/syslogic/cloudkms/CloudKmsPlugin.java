package io.syslogic.cloudkms;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
    private @Nullable List<String> plaintextFiles = List.of(new String[]{});
    private @Nullable List<String> ciphertextFiles = List.of(new String[]{});
    private @NotNull String kmsLocation = "global";
    private @NotNull String kmsKeyring = "android-gradle";
    private @NotNull String kmsKey = "default";

    @Override
    public void apply(@NotNull Project project) {

        /* Create project extension `cloudKms`. */
        this.extension = project.getExtensions().create("cloudKms", CloudKmsExtensionImpl.class);

        /* Project after evaluate. */
        project.afterEvaluate(it -> {

            if (this.extension.getCiphertextFiles() != null) {
                this.ciphertextFiles = this.extension.getCiphertextFiles();
            }

            if (this.extension.getPlaintextFiles() != null) {
                this.plaintextFiles = this.extension.getPlaintextFiles();
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
                task.getCiphertextFiles().set(this.ciphertextFiles);
                task.getPlaintextFiles().set(this.plaintextFiles);
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
                task.getCiphertextFiles().set(this.ciphertextFiles);
                task.getPlaintextFiles().set(this.plaintextFiles);
                task.getKmsLocation().set(this.kmsLocation);
                task.getKmsKeyring().set(this.kmsKeyring);
                task.getKmsKey().set(this.kmsKey);
            });
        }
    }
}
