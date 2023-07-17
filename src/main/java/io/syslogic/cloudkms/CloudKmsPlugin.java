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
    private @Nullable String kmsKeyPath = null;

    @Override
    public void apply(@NotNull Project project) {

        /* Create project extension `cloudKms`. */
        this.extension =
                project.getExtensions().create("cloudKms", CloudKmsExtensionImpl.class);

        /* Project after evaluate. */
        project.afterEvaluate(it -> {
            if (this.extension.getCiphertextFiles() != null) {
                this.ciphertextFiles = this.extension.getCiphertextFiles();
            }
            if (this.extension.getPlaintextFiles() != null) {
                this.plaintextFiles = this.extension.getPlaintextFiles();
            }
            if (this.extension.getKmsKeyPath() != null) {
                this.kmsKeyPath = this.extension.getKmsKeyPath();
            }
            registerCloudKmsEncryptTask(project);
            registerCloudKmsDecryptTask(project);
        });
    }

    void registerCloudKmsEncryptTask(@NotNull Project project) {
        if (project.getTasks().findByName("cloudKmsEncrypt") == null) {
            project.getTasks().register("cloudKmsEncrypt", CloudKmsEncryptTask.class, task -> {
                task.setGroup("cloudkms");
                task.getCiphertextFiles().set(this.ciphertextFiles);
                task.getPlaintextFiles().set(this.plaintextFiles);
                task.getKmsKeyPath().set(this.kmsKeyPath);
            });
        }
    }

    void registerCloudKmsDecryptTask(@NotNull Project project) {
        if (project.getTasks().findByName("cloudKmsDecrypt") == null) {
            project.getTasks().register("cloudKmsDecrypt", CloudKmsDecryptTask.class, task -> {
                task.setGroup("cloudkms");
                task.getCiphertextFiles().set(this.ciphertextFiles);
                task.getPlaintextFiles().set(this.plaintextFiles);
                task.getKmsKeyPath().set(this.kmsKeyPath);
            });
        }
    }
}
