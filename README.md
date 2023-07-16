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
        classpath 'io.syslogic:google-cloud-kms-gradle-plugin:1.0.0'
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

`CloudKmsExtension` can be configured with the following properties:

 - `ciphertextFile`: 
 - `plaintextFile`: 
 - `kmsLocation`: 
 - `kmsKeystore`:
 - `kmsKey`:

````groovy

/** Google Cloud KMS */
cloudKms {
    ciphertextFile = 'credentials/debug.keystore.enc'
    plaintextFile = '~/.android/debug.keystore'
    kmsLocation = 'global'
    kmsKeystore = 'android-gradle'
    kmsKey = 'default'
}
````

These properties are all optional, while:

 - providing the plain-text file at the default location: `~/.android/debug.keystore`.
 - providing the cipher file at the default location: `credentials/debug.keystore.enc`.

### Known Issues
 - It currently only supports one single filename.
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
