### 简介
* KCommon快速开发框架是基于rxjava、rxcache、okhttp、retrofit、rxlifecycle等第三方库，采用mvp架构，使用kotlin语言开发的一个第三方库，配合一键生成mvp相关代码文件的Android Studio模板文件，可以使使用者快速开发，专注于代码逻辑，而非mvp相关的繁琐配置。并且集成了Loading、成功、失败和空页面的切换，同时还提供了对下拉刷新以及上拉加载更多的列表的模板（BaseRefreshAndLoadMoreActivity||BaseRefreshAndLoadMoreFragment）,省去了Android开发人员对相同类型代码的重复编写。[Github地址](https://github.com/BlackFlagBin/KCommonProject)
* 使用此开发框架需要的一些前置知识
    * 熟悉android中的mvp架构
    * 熟悉rxjava、rxcache、rxlifecycle等响应式编程框架
    * 熟悉okhttp、retrofit
    * 会复制快速开发模板到指定位置并使用
* KCommon提供了两种便于开发的模板
    * 以Base开头的BaseActivity和BaseFragment。这种适合通用的开发。具体的使用请参考相关的demo。
    * 以BaseRefreshAndLoadMore开头的BaseRefreshAndLoadMoreActivity和BaseRefreshAndLoadMoreFragment。这种适合列表展示的页面开发，具备下拉刷新和上拉加载更多数据的功能。具体的使用请参考相关的demo。
    * [kotlin版本的demo](https://github.com/BlackFlagBin/KCommonProject) [java版本的demo](https://github.com/BlackFlagBin/KCommonDemoWithJava)
### 图片展示
* 总的演示
![演示图片](https://github.com/BlackFlagBin/KCommonProject/blob/master/screenshot.gif?raw=true)
* 下拉刷新和上拉加载更多
![图片1](https://github.com/BlackFlagBin/KCommonProject/blob/master/screenshots/RefreshAndLoadMore.gif?raw=true)
* 空页面
![图片2](https://github.com/BlackFlagBin/KCommonProject/blob/master/screenshots/EmptyPage.gif?raw=true)
* 在断网情况下加载缓存
![图片3](https://github.com/BlackFlagBin/KCommonProject/blob/master/screenshots/LoadCacheData.gif?raw=true)
* 网络请求错误并重新加载
![图片4](https://github.com/BlackFlagBin/KCommonProject/blob/master/screenshots/ErrorPageAndReload.gif?raw=true)
### 基于Kotlin的使用说明（Java的使用具体参考java版本的demo）
* 导入依赖
    * 在根目录中的build.gradle文件中加入
    ```
    maven { url "https://jitpack.io" }
    ```
    * 在应用下的build.gradle文件中加入
    ```
    api 'com.blackflagbin:kcommonlibrary:0.0.4-test'
* 在自定义的Application类中的onCreate方法中初始化CommonLibrary
```kotlin
//初始化KCommon
CommonLibrary.instance.initLibrary(this,
                BuildConfig.APP_URL,
                ApiService::class.java,
                CacheService::class.java)
```
* 建议配合一键生成相关MVP类的Android Studio模板进行开发，可极大提高开发效率。模板位置 [Android Studio 快速开发模板](https://github.com/BlackFlagBin/KCommonTemplate)

### 详细说明
* CommonLibrary.instance.initLibrary()
```
    /**
     * 初始化
     *
     * @param context Application
     * @param baseUrl retrofit所需的baseUrl
     * @param apiClass retrofit使用的ApisService::Class.java
     * @param cacheClass rxcache使用的CacheService::Class.java
     * @param spName Sharedpreference文件名称
     * @param isDebug 是debug环境还是release环境。debug环境有网络请求的日志，release反之
     * @param startPage 分页列表的起始页，有可能是0，或者是2，这个看后台
     * @param pageSize 分页大小
     * @param headerMap 网络请求头的map集合，便于在网络请求添加统一的请求头，比如token之类
     * @param errorHandleMap 错误处理的map集合，便于针对相关网络请求返回的错误码来做相应的处理，比如错误码401，token失效需要重新登录
     * @param onPageCreateListener 对应页面activity或fragment相关生命周期的回调，便于在页面相关时机做一些统一处理，比如加入友盟统计需要在所有页面的相关生命周期加入一些处理
     * @param onPageDestroyListener 对应页面activity或fragment相关生命周期的回调，便于在页面相关时机做一些统一处理，比如加入友盟统计需要在所有页面的相关生命周期加入一些处理
     * @param onPageResumeListener 对应页面activity或fragment相关生命周期的回调，便于在页面相关时机做一些统一处理，比如加入友盟统计需要在所有页面的相关生命周期加入一些处理
     * @param onPagePauseListener 对应页面activity或fragment相关生命周期的回调，便于在页面相关时机做一些统一处理，比如加入友盟统计需要在所有页面的相关生命周期加入一些处理
     *
     */
    fun initLibrary(
            context: Application,
            baseUrl: String,
            apiClass: Class<*>,
            cacheClass: Class<*>,
            spName: String = "kcommon",
            isDebug: Boolean = true,
            startPage: Int = 1,
            pageSize: Int = 20,
            headerMap: Map<String, String>? = null,
            errorHandleMap: Map<Int, (exception: IApiException) -> Unit>? = null,
            onPageCreateListener: OnPageCreateListener? = null,
            onPageDestroyListener: OnPageDestroyListener? = null,
            onPageResumeListener: OnPageResumeListener? = null,
            onPagePauseListener: OnPagePauseListener? = null)
```



