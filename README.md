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
        google()
        mavenCentral()
        maven { url 'https://jitpack.io' }
    }
    dependencies {
        classpath 'com.android.tools.build:gradle:8.0.2'
        classpath 'io.syslogic:google-cloud-kms-gradle-plugin:1.0.4'
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

|                    Property |      Default       |
|----------------------------:|:------------------:|
|  `String[] ciphertextFiles` |        `[]`        |
|   `String[] plaintextFiles` |        `[]`        |
|        `String kmsLocation` |     `'global'`     |
|        `String kmsKeystore` | `'android-gradle'` |
|             `String kmsKey` |    `'default'`     |

### Example

````groovy
/** Google Cloud KMS */
cloudKms {
    ciphertextFiles = [
            getRootDir().absolutePath + File.separator + 'credentials/debug.keystore.enc',
            getRootDir().absolutePath + File.separator + 'credentials/release.keystore.enc',
            getRootDir().absolutePath + File.separator + 'credentials/keystore.properties.enc',
            getRootDir().absolutePath + File.separator + 'credentials/google-service-account.json.enc',
            getRootDir().absolutePath + File.separator + 'credentials/google-services.json.enc'
    ]
    plaintextFiles = [
            System.getProperty("user.home") + File.separator + ".android" + File.separator + "debug.keystore",
            System.getProperty("user.home") + File.separator + ".android" + File.separator + "release.keystore",
            getRootDir().absolutePath + File.separator + 'keystore.properties',
            getRootDir().absolutePath + File.separator + 'credentials/google-service-account.json',
            getProjectDir().absolutePath + File.separator + 'google-services.json'
    ]
    kmsLocation = 'global'
    kmsKeystore = 'android-gradle'
    kmsKey = 'android-gradle'
}
````
### Known Issues
 - In case the key-ring cannot be found:
````
ERROR: (gcloud.kms.encrypt) NOT_FOUND: CryptoKey projects/PROJECT_ID/locations/global/keyRings/android-gradle/cryptoKeys/default not found.``
````

It may help to switch the account ID or the project ID.
````
gcloud auth login
gcloud config set project PROJECT_ID
````

### Support
- [Stack Overflow](https://stackoverflow.com/questions/tagged/google-cloud-kms)
- [Issue Tracker](https://github.com/syslogic/google-cloud-kms-gradle-plugin/issues)

### Status

[![Gradle CI](https://github.com/syslogic/google-cloud-kms-gradle-plugin/actions/workflows/gradle.yml/badge.svg)](https://github.com/syslogic/google-cloud-kms-gradle-plugin/actions/workflows/gradle.yml)

[![Release](https://jitpack.io/v/syslogic/google-cloud-kms-gradle-plugin.svg)](https://jitpack.io/#io.syslogic/google-cloud-kms-gradle-plugin)

[![MIT License](https://img.shields.io/github/license/syslogic/google-cloud-kms-gradle-plugin)](https://github.com/syslogic/agconnect-publishing-gradle-plugin/blob/master/LICENSE)
