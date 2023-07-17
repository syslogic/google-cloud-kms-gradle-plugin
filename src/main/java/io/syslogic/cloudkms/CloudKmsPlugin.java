package io.syslogic.cloudkms;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import io.syslogic.cloudkms.task.BaseTask;
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

    void registerTask(@NotNull Project project, String taskName, Class<? extends BaseTask> cls) {
        if (project.getTasks().findByName(taskName) == null) {
            project.getTasks().register(taskName, cls, task -> {
                task.setGroup("cloudkms");
                task.getKmsKeyPath().set(this.kmsKeyPath);
                task.getPlaintextFiles().set(this.plaintextFiles);
                task.getCiphertextFiles().set(this.ciphertextFiles);
            });
        }
    }

    @Override
    public void apply(@NotNull Project project) {

        /* Create project extension `cloudKms`. */
        this.extension =
                project.getExtensions().create("cloudKms", CloudKmsExtensionImpl.class);

        /* Project after evaluate. */
        project.afterEvaluate(it -> {
            if (this.extension.getKmsKeyPath() != null) {
                this.kmsKeyPath = this.extension.getKmsKeyPath();
            }
            if (this.extension.getPlaintextFiles() != null) {
                this.plaintextFiles = this.extension.getPlaintextFiles();
            }
            if (this.extension.getCiphertextFiles() != null) {
                this.ciphertextFiles = this.extension.getCiphertextFiles();
            }
            registerTask(project, "cloudKmsEncrypt", CloudKmsEncryptTask.class);
            registerTask(project, "cloudKmsDecrypt", CloudKmsDecryptTask.class);
        });
    }
}
