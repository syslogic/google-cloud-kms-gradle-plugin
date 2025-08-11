### Google Cloud KMS Plugin

![Social Media Preview](https://raw.githubusercontent.com/syslogic/google-cloud-kms-gradle-plugin/master/screenshots/repository.png)

 ---
### Features

 - It encrypts and decrypts multiple files with Cloud KMS.
 - It only overwrites destination files, when they're empty.

### Development

The plugin source code can be swiftly installed into any Gradle project with `git clone`:

````bash
git clone https://github.com/syslogic/google-cloud-kms-gradle-plugin.git ./buildSrc
````

### Package Installation

The plugin depends on the [Google Cloud CLI](https://cloud.google.com/sdk/docs/install) `gcloud` command.

A) The plugin can either be set up in the `buildscript` block of the root project's `build.gradle`:
````groovy
buildscript {
    repositories {
        maven { url = uri("https://jitpack.io") }
    }
    dependencies {
        classpath "io.syslogic:google-cloud-kms-gradle-plugin:1.0.8"
    }
}
````

B) Or the repository has to be defined in the root project's `settings.gradle`:
````groovy
pluginManagement {
    repositories {
        gradlePluginPortal()
        maven { url = uri("https://jitpack.io") }
    }
}
````

Then it can be loaded in the `plugins` block of the root project's `build.gradle`:
````groovy
plugins {
    id("io.syslogic.cloudkms") version("1.0.8") apply false
}
````

C) Finally, it has to be applied in the module's `build.gradle`:
````groovy
plugins {
    id("io.syslogic.cloudkms")
}
````

### Configuration

The `CloudKmsExtension` can be configured with the following properties:

|                   Property |      Default       |
|---------------------------:|:------------------:|
|        `String kmsKeyPath` |       `null`       |
|  `String[] plaintextFiles` |        `[]`        |
| `String[] ciphertextFiles` |        `[]`        |


### Usage Example

Properties `plaintextFiles` and `ciphertextFiles` must match; they are being used for both directions.

````groovy
/** Google Cloud KMS */
cloudKms {

    // The leading underscore is required due to the CloudBuild environment.
    kmsKeyPath = System.getenv('_CLOUD_KMS_KEY_PATH')

    // local files to be ignored by version control:
    plaintextFiles = [
            /* 0 */ System.getProperty("user.home") + File.separator + ".android" + File.separator + "debug.keystore",
            /* 1 */ System.getProperty("user.home") + File.separator + ".android" + File.separator + "release.keystore",
            /* 2 */ getRootDir().absolutePath + File.separator + 'keystore.properties',
            /* 3 */ getRootDir().absolutePath + File.separator + 'credentials/google-service-account.json',
            /* 4 */ getProjectDir().absolutePath + File.separator + 'google-services.json'
    ]
    
    // encrypted files can be checked in to version control:
    ciphertextFiles = [
            /* 0 */ getRootDir().absolutePath + File.separator + 'credentials/debug.keystore.enc',
            /* 1 */ getRootDir().absolutePath + File.separator + 'credentials/release.keystore.enc',
            /* 2 */ getRootDir().absolutePath + File.separator + 'credentials/keystore.properties.enc',
            /* 3 */ getRootDir().absolutePath + File.separator + 'credentials/google-service-account.json.enc',
            /* 4 */ getRootDir().absolutePath + File.separator + 'credentials/google-services.json.enc'
    ]
}
````
### Gradle Tasks

 - `:cloudKmsEncrypt` is meant to run locally, in order to encrypt relevant files.<br/>
When having done so, one can check in these files to version control.
 - `:cloudKmsDecrypt` is meant to run remotely, in order to decrypt relevant files.<br/>
The encrypted files will come from version control.

With task `:cloudKmsEncrypt`
 - `plaintextFiles` is the local source,
 - `ciphertextFiles` is the local destination.

With task `:cloudKmsDecrypt`
 - `ciphertextFiles` is the remote source,
 - `plaintextFiles` is the remote destination.

### Known Issues
 - In case the key cannot be found:
````
ERROR: (gcloud.kms.encrypt) NOT_FOUND: CryptoKey projects/PROJECT_ID/locations/LOCATION/keyRings/KEY_RING/cryptoKeys/KEY not found.``
````

It may help to switch the account ID and/or the project ID.
````
gcloud auth login
gcloud projects list
gcloud config set project PROJECT_ID
````
One can also list all the available keys of a project.
````
gcloud kms keyrings list --location=global
gcloud kms keys list --keyring=projects/PROJECT_ID/locations/LOCATION/keyRings/KEY_RING
````

### Support
 - [Cloud Key Management](https://cloud.google.com/kms/doc)
 - [CLI Documentation](https://cloud.google.com/sdk/gcloud/reference/kms)
 - [Stack Overflow](https://stackoverflow.com/questions/tagged/google-cloud-kms)
 - [Issue Tracker](https://github.com/syslogic/google-cloud-kms-gradle-plugin/issues)

### Status

[![Gradle CI](https://github.com/syslogic/google-cloud-kms-gradle-plugin/actions/workflows/gradle.yml/badge.svg)](https://github.com/syslogic/google-cloud-kms-gradle-plugin/actions/workflows/gradle.yml)

[![Release](https://jitpack.io/v/syslogic/google-cloud-kms-gradle-plugin.svg)](https://jitpack.io/#io.syslogic/google-cloud-kms-gradle-plugin)

[![MIT License](https://img.shields.io/github/license/syslogic/google-cloud-kms-gradle-plugin)](https://github.com/syslogic/agconnect-publishing-gradle-plugin/blob/master/LICENSE)
