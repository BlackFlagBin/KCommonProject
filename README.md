# KCommonProject
Kotlin实现的快速开发框架

* 导入依赖
    * 在根目录中的build.gradle文件中加入

    ```kotlin
    maven { url "https://jitpack.io" }
    ```
    * 在应用下的build.gradle文件中加入
    ```kotlin
    api 'com.blackflagbin:kcommonlibrary:0.0.4-test'
    ```
* 在自定义的Application类中的onCreate方法中初始化CommonLibrary
```kotlin
CommonLibrary.instance.initLibrary(this,
                BuildConfig.APP_URL,
                ApiService::class.java,
                CacheService::class.java,
                spName = "KCommonDemo",
                errorHandleMap = hashMapOf<Int, (exception: IApiException) -> Unit>(401 to { exception ->

                }, 402 to { exception ->

                }, 403 to { exception ->

                }),
                isDebug = BuildConfig.DEBUG)
```
* 建议配合一键生成相关MVP类的Android Studio模板进行开发，可极大提高开发效率。模板位置[Android Studio 快速开发模板](https://github.com/BlackFlagBin/KCommonTemplate)