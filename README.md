### Google Cloud KMS Plugin

![Social Media Preview](https://raw.githubusercontent.com/syslogic/google-cloud-kms-gradle-plugin/master/screenshots/repository.png)

 ---
### Features

 - It encrypts and decrypts files with Cloud KMS.

### Development

The plugin source code can be swiftly installed into any Android Gradle project with `git clone`:

````bash
git clone https://github.com/syslogic/google-cloud-kms-gradle-plugin.git ./buildSrc
````

### Package Installation

The plugin depends on the common Google Cloud CLI `gcloud` command.

The plugin can be set up in the `buildscript` block of the root project's `build.gradle`:
````groovy
buildscript {
    repositories {
        maven { url 'https://jitpack.io' }
    }
    dependencies {
        classpath 'io.syslogic:google-cloud-kms-gradle-plugin:1.0.5'
    }
}
````

Then they can be applied in the module's `build.gradle`:
````groovy
plugins {
    id 'com.android.application'
    id 'io.syslogic.cloudkms'
}
````

### Configuration

The `CloudKmsExtension` can be configured with the following properties:

|                   Property |      Default       |
|---------------------------:|:------------------:|
| `String[] ciphertextFiles` |        `[]`        |
|  `String[] plaintextFiles` |        `[]`        |
|        `String kmsKeyPath` |       `null`       |
|       `String kmsLocation` |     `'global'`     |
|       `String kmsKeystore` | `'android-gradle'` |
|            `String kmsKey` |    `'default'`     |

### Example

Properties `ciphertextFiles` and `plaintextFiles` must match; they are being used for both directions.

````groovy
/** Google Cloud KMS */
cloudKms {

    // Property kmsKeyPath is being preferred.
    kmsKeyPath = 'projects/PROJECT_ID/locations/global/keyRings/android-gradle/cryptoKeys/default'
    
    ciphertextFiles = [
            /* 0 */ getRootDir().absolutePath + File.separator + 'credentials/debug.keystore.enc',
            /* 1 */ getRootDir().absolutePath + File.separator + 'credentials/release.keystore.enc',
            /* 2 */ getRootDir().absolutePath + File.separator + 'credentials/keystore.properties.enc',
            /* 3 */ getRootDir().absolutePath + File.separator + 'credentials/google-service-account.json.enc',
            /* 4 */ getRootDir().absolutePath + File.separator + 'credentials/google-services.json.enc'
    ]
    plaintextFiles = [
            /* 0 */ System.getProperty("user.home") + File.separator + ".android" + File.separator + "debug.keystore",
            /* 1 */ System.getProperty("user.home") + File.separator + ".android" + File.separator + "release.keystore",
            /* 2 */ getRootDir().absolutePath + File.separator + 'keystore.properties',
            /* 3 */ getRootDir().absolutePath + File.separator + 'credentials/google-service-account.json',
            /* 4 */ getProjectDir().absolutePath + File.separator + 'google-services.json'
    ]
}
````
### Known Issues
 - In case the key-ring cannot be found:
````
ERROR: (gcloud.kms.encrypt) NOT_FOUND: CryptoKey projects/PROJECT_ID/locations/global/keyRings/android-gradle/cryptoKeys/default not found.``
````

It may help to switch the account ID and/or the project ID.
````
gcloud auth login
gcloud config set project PROJECT_ID
gcloud kms keyrings list --location=global
gcloud kms keys list --keyring=projects/PROJECT_ID/locations/global/keyRings/android-gradle
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
