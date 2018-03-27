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

  ## 详细说明

  * KCommon快速开发框架是基于rxjava、rxcache、okhttp、retrofit、rxlifecycle等第三方库，采用mvp架构，使用kotlin语言开发的一个第三方库，配合一键生成mvp相关代码文件的Android Studio模板文件，可以使使用者快速开发，专注于代码逻辑，而非mvp相关的繁琐配置。同时还提供了对下拉刷新以及上拉加载更多的列表的模板（BaseRefreshAndLoadMoreActivity||BaseRefreshAndLoadMoreFragment）,省去了Android开发人员对相同类型代码的重复编写。
  * 使用此开发框架需要的一些前置知识
    * 熟悉android中的mvp架构
    * 熟悉rxjava、rxcache、rxlifecycle等响应式编程框架
    * 熟悉okhttp、retrofit
    * 会复制快速开发模板到指定位置并使用
  * KCommon提供了两种便于开发的模板
    * 以Base开头的BaseActivity和BaseFragment。这种适合通用的开发。具体的使用请参考相关的demo。
    * 以BaseRefreshAndLoadMore开头的BaseRefreshAndLoadMoreActivity和BaseRefreshAndLoadMoreFragment。这种适合列表展示的页面开发，具备下拉刷新和上拉加载更多数据的功能。具体的使用请参考相关的demo。
    * [kotlin版本的demo](https://github.com/BlackFlagBin/KCommonProject) [java版本的demo](https://github.com/BlackFlagBin/KCommonDemoWithJava)