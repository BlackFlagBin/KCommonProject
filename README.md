## KCommon-使用Kotlin编写，基于MVP的极速开发框架
我们在开发Android应用程序的时候其实会有很多通用的代码，比方说很常见的页面的几种基本状态的切换：正常、加载失败、加载中、空页面。又或者是下拉刷新和如果数据需要分页而带来的上拉加载更多数据等等操作。当然，这其中最繁琐的还是关于MVP相关模板代码的编写，熟悉Android中MVP架构的小伙伴们应该都知道，严格按照MVP架构的话，我们每一个Activity或者Fragment都需要多写一个接口和两个实现类：MVPContract、MVPModel和MVPPresenter。而这些Contract、Model和Presenter又不近相似，所以在我之前的开发中，如果一个新的APP有30个页面，那么加上这些MVP架构所需的代码，我需要多添加90个文件，即使是复制粘贴这些代码当时也耗费了我将近2个多小时的时间(当然不仅仅是复制，还包括文件名，方法名称的修改等等所需的细节)。当然，这也是促使我开源出KCommon这个使用Kotlin编写的，基于MVP架构的极速开发框架的主要原因。

#### KCommon可以解决的开发中的痛点
* 页面状态的切换，包括正常、加载失败、加载中、空页面和自定义的页面
* 对下拉刷新和上拉加载更多的逻辑做了完善的处理，极大的减少了开发者的工作量
* 对网络请求框架做了封装，便于方便的请求网络和加载缓存
* 对网络请求返回的错误进行了封装，方便根据错误码进行错误处理
* 提供了自动生成MVP相关文件的模板代码，实现了真正一键创建MVP的所有代码

#### 集成方法
* api 'com.blackflagbin:kcommonlibrary:1.0.0'
* 在根目录的gradle文件中添加：
```
allprojects {
    repositories {
        //添加这一行依赖
        maven { url "https://jitpack.io" }
    }
}

```
* 在自定义的Application类中的onCreate方法中初始化：
```
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
* 将[KCommonTemplate模板文件](https://github.com/BlackFlagBin/KCommonTemplate)放到指定位置

#### 详细功能使用说明
如果只是针对不同的模块进行介绍的话，可能不是那么容易理解，这里我结合一个Kotlin编写的[Demo](https://github.com/BlackFlagBin/KCommonProject)，来一步一步详细演示如何使用这个极速开发框架。
#### 明确需求
首先我们这个APP的需求很明确，要有统一的网络错误处理、页面的不同状态切换、下拉刷新和上拉加载更多、处理网络请求时的Loading效果、在无网络时加载缓存数据，和使用MVP架构来编写代码。在这里我使用Kotlin编写整个APP的代码，对Kotlin不熟悉的同学也不用害怕，Java和Kotlin的写法基本是一致的，并且我的MVP模板文件也提供了Kotlin和Java两个版本的选项。

#### 添加依赖，复制模板代码
这两步在集成方法中已经介绍过了。


#### 配置MultiDexEnable
由于KCommon为了方便开发依赖了很多开发中常用的第三方库，完整的依赖如下所示：
```
dependencies {
    api fileTree(include: ['*.jar'], dir: 'libs')
    api 'com.android.support:appcompat-v7:27.1.1'
    api 'com.android.support:recyclerview-v7:27.1.1'
    api 'org.jetbrains.anko:anko:0.10.3'
    api 'androidx.core:core-ktx:0.3'
    api 'com.android.support:multidex:1.0.3'
    api 'com.squareup.okhttp3:okhttp:3.10.0'
    api 'com.squareup.okhttp3:logging-interceptor:3.9.1'
    api 'com.squareup.retrofit2:retrofit:2.4.0'
    api 'com.squareup.retrofit2:converter-gson:2.4.0'
    api 'com.jakewharton.retrofit:retrofit2-rxjava2-adapter:1.0.0'
    api 'io.reactivex.rxjava2:rxandroid:2.0.1'
    api 'com.github.VictorAlbertos.RxCache:runtime:1.8.3-2.x'
    api 'io.reactivex.rxjava2:rxjava:2.1.7'
    api 'com.github.VictorAlbertos.Jolyglot:gson:0.0.4'
    api 'com.jakewharton.rxbinding2:rxbinding:2.0.0'
    api 'com.tbruyelle.rxpermissions2:rxpermissions:0.9.4@aar'
    api 'org.greenrobot:eventbus:3.0.0'
    api 'com.github.CymChad:BaseRecyclerViewAdapterHelper:2.9.35'
    api 'com.github.Kennyc1012:MultiStateView:1.3.0'
    api 'com.github.ybq:Android-SpinKit:1.1.0'
    api 'com.blankj:utilcode:1.17.1'
    api 'com.github.bumptech.glide:glide:3.8.0'
    api 'com.github.anzaizai:EasySwipeMenuLayout:1.1.2'
    api 'com.trello.rxlifecycle2:rxlifecycle:2.2.1'
    api 'com.trello.rxlifecycle2:rxlifecycle-components:2.2.1'
    api 'com.trello.rxlifecycle2:rxlifecycle-kotlin:2.2.1'
    api 'com.trello.rxlifecycle2:rxlifecycle-android-lifecycle-kotlin:2.2.1'
    api 'org.jetbrains.kotlin:kotlin-stdlib:1.2.51'
    api 'com.android.support:cardview-v7:27.1.1'
    api 'com.hx.multi-image-selector:multi-image-selector:1.2.1'
    api 'com.android.support:design:27.1.1'
}
```
所以方法数基本上是要超过65535的，因此需要配置MultiDex：
```
android {
    compileSdkVersion 27
    buildToolsVersion '27.0.3'
    defaultConfig {
        minSdkVersion 19
        targetSdkVersion 27
        versionCode 1
        versionName "1.0"
        //在这里配置multiDex
        multiDexEnabled true
    }
}
```

#### 创建项目的目录结构
由于开发中需要配合KCommonTemplate一键生成相关MVP代码使用，所以对整个项目的目录结构有着要求(如果项目目录不正确的话，一键生成的模板代码文件的位置会错位)。

首先在项目的主包名下创建4个平级的package：**app** 、**common** 、**mvp** 、**ui** 。

根据名称大家也很好理解，**app** 包中存放我们自定义的Application，**common** 包中存放一些通用的基础代码，比如常量、数据类、网络访问接口类等等，**mvp** 包中存放我们MVP架构所需的组件类，**ui** 包中存放我们的Activity、Fragment和Adapter等等与界面相关的类。

* **app** 包中创建我们自定义的Application，Application中的内容之后会详细说明
* **common** 包中创建 **http** 包，里面创建两个接口， **ApiService**  和 **CacheService** 这两个接口的名字是固定的，也是因为模板文件中写死了这两个接口的名字。使用过Retrofit的同学应该都清楚，前者是存放网络请求的接口，而后者是结合RxCache使用的存放缓存方法的接口。如果对RxCache不熟悉的同学或者不需要缓存的同学可以把 **CacheService** 中的内容清空，保持一个空接口即可，但是 **CacheService** 这个接口文件必须存在。
* **mvp** 包中分别创建 **contract** 、 **model**  、 **presenter**  三个包，对MVP架构熟悉的同学应该非常清楚这些，而这些包中的相关代码会在我们创建Activity或Fragment的时候一键生成。
* **ui** 包中创建 **activity** 和 **fragment** ，很好理解了，存放我们开发中的Activity和fragment。

上面这些目录结构都是在一个新的项目开发前必须创建好的。有的同学可能看到我的[Demo](https://github.com/BlackFlagBin/KCommonProject)中在一些目录中也添加了别的一些包，比如在 **common** 包下创建了 **constant** 、 **util** 、 **entity** 等等包。其实除了之前提到的必须的目录结构，你在[Demo](https://github.com/BlackFlagBin/KCommonProject)中看到的别的包都是可选的，这个随你，只不过这些都是我个人的开发习惯。我习惯在 **common** 包下存放我项目中的常量、工具类、和数据实体类，同理，我也喜欢在 **ui** 包下存放adapter和自定义view。当然，这些都是经验之谈，我推荐你跟我采用相同的结构。我们最后来看一张图片有个更明确的概念。
![目录结构](https://github.com/BlackFlagBin/MarkDownPicture/blob/master/kcommon/KCommonProjectIndex.png)


#### 完成我们自定义的Application类
```
class App : Application() {

    companion object {
        fun startLoginActivity(context: Context, loginClazz: Class<*>) {
            CommonLibrary.instance.headerMap = hashMapOf(
                    "token" to SPUtils.getInstance("KCommonDemo").getString("token", "123"))
            context.startActivity(
                    Intent(
                            context,
                            loginClazz).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK))
        }
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)

    }

    override fun onCreate() {
        super.onCreate()
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this)
        BlockCanary.install(this, AppBlockCanaryContext()).start()

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
    }
}
```
上面是Demo中自定义的Application，首先要重写这个方法，处理Multidex：
```
    //Multidex
    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
```
然后再onCreate方法中初始化我们的 **CommonLibrary** ：
```
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
这里传入的第一个参数是Application本身，第二个参数是BaseUrl，之后是我们之前提到的APIService和CacheService，之后传入了一个spName，这个表示SharedPrefrence的文件名称，之后是errorHandleMap，这存放了根据不同的网络错误码对应的回调，最后传入一个isDebug表示debug环境下会开启网络日志输入，release环境下会关闭网络日志输出。下面是详细说明：
```
    /**
     * 初始化
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
当然这些参数中前4个参数都是必须的，因为很明显嘛，它们都没有默认值。其余的参数如果有需要的话是可以按需配置的。

如果是跟着Demo一起看的话，有的小伙伴可能会对APP这段代码中的：
```
companion object {
        fun startLoginActivity(context: Context, loginClazz: Class<*>) {
            CommonLibrary.instance.headerMap = hashMapOf(
                    "token" to SPUtils.getInstance("KCommonDemo").getString("token", "123"))
            context.startActivity(
                    Intent(
                            context,
                            loginClazz).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK))
        }
    }
```
感到疑惑，这里其实是一个伴生对象，可以理解为Java中的静态方法，主要是方便当token过期的时候跳转到登录页面并将之前网络请求中的token这个请求头清空。当然，在我们正在开发的这个Demo中是没有用到的，这里只是提供一种token过期，跳转登录页的思路。

#### 完成网络数据类的编写
在 **common** 包下创建 **entity** 数据类包，之后再 **entity** 包下创建 **net** 网络数据类包，在 **net** 中创建 **DataEntity** 这个数据文件。

这个数据文件如下所示：
```
//最外层数据类
data class HttpResultEntity<T>(
        private var code: Int = 0,
        private var message: String = "",
        private var error: Boolean = false,
        private var results: T) : IHttpResultEntity<T> {
    override val isSuccess: Boolean
        get() = !error
    override val errorCode: Int
        get() = code
    override val errorMessage: String
        get() = message
    override val result: T
        get() = results
}


data class DataItem(
		@SerializedName("desc") var desc: String = "",
		@SerializedName("ganhuo_id") var ganhuoId: String = "",
		@SerializedName("publishedAt") var publishedAt: String = "",
		@SerializedName("readability") var readability: String = "",
		@SerializedName("type") var type: String = "",
		@SerializedName("url") var url: String = "",
		@SerializedName("who") var who: String = ""
)

```

我个人习惯将所有的数据类都写到一个文件中，因为数据类都是很简单的，全部写在一个文件中看起来比较清晰，也便于管理。

**DataEntity** 中有两个数据类，第一个是我们网络返回数据的最外层数据类，可以看到，它实现了一个 **KCommon** 库中定义的一个接口 **IHttpResultEntity** ，我们来看一下这个接口：
```
interface IHttpResultEntity<T> {
    //网络请求结果是否成功
    val isSuccess: Boolean

    //错误码
    val errorCode: Int

    //错误信息
    val errorMessage: String

    //返回的有效数据
    val result: T
}
```
这个接口的意思其实很明显了，注释写的很清楚。那么有些还没进入公司的小伙伴们可能会有问题了：为什么必须要添加一个实现了 **IHttpResultEntity** 接口的数据类呢？

我们先来看一下公司开发中实际返回的数据结构：
```
{"data":null,"code":200,"message":null,"success":true}
```
可以看到返回的数据包含4个部分，正好对应着接口中的4个部分。大多数的公司都会返回类似的数据结构，当然有些会字段名称不一样，也有一些会缺一些字段，这时候我们应该灵活应变。

第二个 **DataItem** 是 **GankApi** 返回的数据结构，比方说下面就是一条数据：
```
{
          "desc": "\u8fd8\u5728\u7528ListView\uff1f", 
          "ganhuo_id": "57334c9d67765903fb61c418", 
          "publishedAt": "2016-05-12T12:04:43.857000", 
          "readability": "", 
          "type": "Android", 
          "url": "http://www.jianshu.com/p/a92955be0a3e", 
          "who": "\u9648\u5b87\u660e"
        }
```

#### 一键生成主页面的MVP相关代码

我们平常写MVP架构的代码，虽然整体页面逻辑看起来非常清晰：Model只管理数据的获取、Presenter管理数据和页面的交互逻辑、View只处理ui相关的事件。但这个清晰是有代价的，文章开篇已经提到过了：我们要多写3个文件 -> **Contract** 、 **Model** 、 **Presenter** 。对，，没错，一个Activity或者Fragment就要多写三个文件，在我短短的开发生涯中，除此之外我还遇到过更过分的，说到这里，可能有的小伙伴要跟我想到一块去了：没错，就是 **Dagger2** ，这个东西首先理解起来有些费劲，其次就是一个Activity或者Fragment也是要多配置2个文件： **Component** 和 **Module** 。相信使用过 **Dagger2** 的朋友都懂我在说什么，那么问题来了，我只想写一个页面，但却要多写5个文件，这简直不可忍受（ **Dagger2** 我已经在新开发的项目中移除了，而且以后也不打算再使用，原因嘛很简单：使用很繁琐，而且基本上没什么好的效果，本质上就是把new对象的代码放在了别处。如果没有用过 **Dagger2** 的同学，请你听我一句劝：珍惜生命，远离 **Dagger2** )。

那回到我们的主题，我们现在要创建一个主页面。这个主页面要有以下几点功能：
1. 使用MVP架构
2. 包含页面的加载、成功、失败和空页面的逻辑
3. 这个页面并不具有上拉加载更多的功能

要创建这样一个页面我们有这么几个步骤：
*  用鼠标选中项目的根包名，比方说Demo中的包名为 **kcommonproject** ，记住，一定要是根包名(当然也不是说不能选中其他包，只不过选中其他包的话生成的相关代码文件的位置会不太正确)，如下图所示：
![](https://github.com/BlackFlagBin/MarkDownPicture/blob/master/kcommon/root_project.png?raw=true)
*  Mac上是Command+N，Windows上是Ctrl+N，弹出新建文件的弹框，因为我们要创建的是Activity，所以找到Activity的选项，进入之后可以看到我们的模板文件的选项因为我们使用Kotlin开发，而且也没不需要上拉加载更多的功能，所以我们选择 **Kotlin MVP Activity** 这个选项，点击生成相关代码，如下图所示：
![](https://github.com/BlackFlagBin/MarkDownPicture/raw/master/ASTemplatePicActivity.png)

接下来就是见证奇迹的时刻，你会发现你的mvp包中生成了相关MVP的代码，并且在Activity中默认会有一些配置代码，并且XML文件中也生成了便于切换页面Loading、成功、失败、空布局的代码，简而言之，一键生成了你所需的一切。

接下来我们来看一下这些生成的文件。
* **mvp** 包下的 **contract** 包中生成了一个 **MainContract** 的接口：
```
interface MainContract {
    interface IMainModel

    interface IMainPresenter : IBasePresenter

    interface IMainView : IBaseView<Any?>
}
```
这是一个主页面的 **Contract** 接口，包含了我们 **mvp** 的三个接口，由于我们在 **MainActivity** 中并不做关于网络请求相关的操作，所以我们并没有修改这个接口中的任何代码。
* **mvp** 包下的 **model** 包中生成了一个 **MainModel** 的实现类：
```
class MainModel : BaseModel<ApiService, CacheService>(), MainContract.IMainModel
```
可以看到我们的 **MainModel** 继承了 **BaseModel** 并且实现了我们 **MainContract** 中的 **MainContract.IMainModel** 。由于并不涉及网络数据的获取，所以并没有任何内容。
* **mvp** 包下的 **presenter** 包中生成了一个 **MainPresenter** 的实现类：
```
class MainPresenter(iMainView: MainContract.IMainView) :
        BasePresenter<MainContract.IMainModel, MainContract.IMainView>(iMainView),
        MainContract.IMainPresenter {
    override val model: MainContract.IMainModel
        get() = MainModel()

    override fun initData(dataMap: Map<String, String>?) {
    }

}
```
可以看到生成的 **MainPresenter** 继承了 **BasePresenter** 并且实现了我们 **MainContract** 中的 **MainContract.IMainPresenter** 。同时还持有了一个 **MainModel** 的引用对象 **model** ,这个主要是方便我们在presenter中调用model中的方法获取网络数据。

还有一个 **initData(dataMap: Map<String, String>?)** 方法，这个方法从名称也可以理解，页面加载数据的方法，需要传入一个 **Map** 类型的参数。为什么要传一个 **Map** 对象呢？主要原因还是在实际开发中我们请求网络接口所需的参数个数是不确定的，可能不需要传参数，比方说退出登录的接口其实是不需要传参的；当然也可能传个数不同的参数，比方说你有一个根据条件查询数据的接口，然而这些条件并不是必须的，可以有，也可以不传，这个时候针对参数个数不同的问题，我们需要一个数据结构来满足我们的需求，而 **Map** 这个数据结构正好满足我们的需求。

同样的情况，由于在 **MainActivity** 中我们并不处理网络，所以代码是不需要修改的。
* **ui** 包下的 **activity** 包中生成了 **MainActivity** ：
```
class MainActivity : BaseActivity<ApiService, CacheService, MainPresenter, Any?>(),
        MainContract.IMainView {
    private val AVATAR_URL = "https://avatars2.githubusercontent.com/u/17843145?s=400&u=d417a5a50d47426c0f0b6b9ff64d626a36bf0955&v=4"
    private val ABOUT_ME_URL = "https://github.com/BlackFlagBin"
    private val READ_ME_URL = "https://github.com/BlackFlagBin/KCommonProject/blob/master/README.md"
    private val MORE_PROJECT_URL = "https://github.com/BlackFlagBin?tab=repositories"
    private val mTypeArray: Array<String> by lazy {
        arrayOf("all", "Android", "iOS", "休息视频", "福利", "拓展资源", "前端", "瞎推荐", "App")
    }


    override val swipeRefreshView: SwipeRefreshLayout?
        get() = null

    override val multiStateView: MultiStateView?
        get() = null

    override val layoutResId: Int
        get() = R.layout.activity_main

    override val presenter: MainPresenter
        get() = MainPresenter(this)

    override fun initView() {
        super.initView()
        setupSlidingView()
        setupViewPager()
        rl_right.onClick {
            startActivity(
                    WebActivity::class.java, bundleOf("url" to ABOUT_ME_URL, "title" to "关于作者"))
        }
        ll_read_me.onClick {
            startActivity(
                    WebActivity::class.java, bundleOf("url" to READ_ME_URL, "title" to "ReadMe"))
        }
        ll_more_project.onClick {
            startActivity(
                    WebActivity::class.java, bundleOf("url" to MORE_PROJECT_URL, "title" to "更多项目"))
        }
        ll_clear_cache.onClick { clearCache() }


    }

    override fun initData() {
    }

    override fun showContentView(data: Any?) {
    }

    private fun setupSlidingView() {
        val slidingRootNav = SlidingRootNavBuilder(this).withToolbarMenuToggle(
                tb_main).withMenuOpened(
                false).withContentClickableWhenMenuOpened(false).withMenuLayout(
                R.layout.menu_main_drawer).inject()
        ll_menu_root.onClick { slidingRootNav.closeMenu(true) }
        Glide.with(this).load(
                AVATAR_URL).placeholder(
                R.mipmap.avatar).error(R.mipmap.avatar).dontAnimate().transform(
                GlideCircleTransform(
                        this)).into(iv_user_avatar)
    }

    private fun setupViewPager() {
        vp_content.adapter = MainPagerAdapter(supportFragmentManager)
        tl_type.setupWithViewPager(vp_content)
        vp_content.offscreenPageLimit = mTypeArray.size - 1
    }

    private fun clearCache() {
        val cache = CacheUtils.getInstance(cacheDir)
        val cacheSize = Formatter.formatFileSize(
                this, cache.cacheSize)
        cache.clear()
        toast("清除缓存$cacheSize")


    }

}
```
我们需要关注的是带 **override** 部分的成员和方法：
```
    override val swipeRefreshView: SwipeRefreshLayout?
        get() = null
```
如果需要下拉刷新，需要在布局XML文件中加入 **SwipeRefreshView** 并将这个View赋值给它。我们的 **MainActivity** 不需要下拉刷新，所以默认是 **null** 。
```
    override val multiStateView: MultiStateView?
        get() = null
```
这是负责页面Loading、成功、失败、空布局切换的一个自定义View， ~~需要在布局文件中加入~~ 并赋值给它。额，实际上因为模板生成的布局文件中会默认带有 **MultiStateView** ，所以其实不需要我们主动在布局文件中加入。因为 **MainActivity** 并没有网络数据的加载，不需要切换页面状态，所以赋值为 **null** 。
```
    override val layoutResId: Int
        get() = R.layout.activity_main

    override val presenter: MainPresenter
        get() = MainPresenter(this)
```
这两个放在一起，前者是布局文件的 **id** ，后者是我们当前页面的 **presenter** ，都是模板自动生成的，没什么可多说的。
```
override fun initView() {
        super.initView()
        setupSlidingView()
        setupViewPager()
        rl_right.onClick {
            startActivity(
                    WebActivity::class.java, bundleOf("url" to ABOUT_ME_URL, "title" to "关于作者"))
        }
        ll_read_me.onClick {
            startActivity(
                    WebActivity::class.java, bundleOf("url" to READ_ME_URL, "title" to "ReadMe"))
        }
        ll_more_project.onClick {
            startActivity(
                    WebActivity::class.java, bundleOf("url" to MORE_PROJECT_URL, "title" to "更多项目"))
        }
        ll_clear_cache.onClick { clearCache() }
    }
```
从名字可以看出来，初始化界面布局，所有关于页面 **不需要网络数据** 的UI的初始化代码推荐放在这里。
```
    override fun initData() {
    }
```
很明显了，在 **initData** 中我们推荐的是加载网络数据，通常会调用 `mPresenter.initData(mDataMap)` 来实现我们网络数据的加载。但 **MainActivity** 不需要加载网络数据，所以我们保持空置。
```
    override fun showContentView(data: Any?) {
    }
```
这个方法的调用时机是在我们请求网络接口返回正确的数据之后，所以在这个方法中我们可以获取所需的网络数据，并修改UI。这里同样因为 **MainActivity** 不需要加载网络数据，所以我们保持空置。

* **layout** 下的布局文件 ：
```
<?xml version="1.0" encoding="utf-8"?>
<android.support.design.widget.CoordinatorLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:orientation="vertical">

        <com.kennyc.view.MultiStateView
            android:id="@+id/multi_state_view"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            app:msv_emptyView="@layout/layout_empty"
            app:msv_errorView="@layout/layout_error"
            app:msv_loadingView="@layout/layout_loading"
            app:msv_viewState="content">


            <LinearLayout
                android:layout_width="match_parent"
                android:layout_height="match_parent"
                android:background="@color/white"
                android:orientation="vertical">


                <LinearLayout
                    android:layout_width="match_parent"
                    android:layout_height="wrap_content"
                    android:elevation="3dp"
                    android:orientation="vertical">

                    <android.support.v7.widget.Toolbar
                        android:id="@+id/tb_main"
                        android:layout_width="match_parent"
                        android:layout_height="48dp"
                        android:background="@android:color/transparent"
                        android:elevation="10dp">

                        <RelativeLayout
                            android:id="@+id/rl_right"
                            android:layout_width="50dp"
                            android:layout_height="match_parent"
                            android:layout_gravity="right"
                            android:gravity="center">

                            <ImageView
                                android:layout_width="20dp"
                                android:layout_height="20dp"
                                android:src="@mipmap/about_me"/>
                        </RelativeLayout>

                    </android.support.v7.widget.Toolbar>
                </LinearLayout>

                <android.support.design.widget.CoordinatorLayout
                    android:layout_width="match_parent"
                    android:layout_height="match_parent">

                    <android.support.design.widget.AppBarLayout
                        android:id="@+id/appbar_layout"
                        android:layout_width="match_parent"
                        android:layout_height="wrap_content">

                        <android.support.design.widget.CollapsingToolbarLayout
                            android:layout_width="match_parent"
                            android:layout_height="wrap_content"
                            android:minHeight="0dp"
                            app:layout_scrollFlags="scroll|enterAlways|snap">

                            <android.support.design.widget.TabLayout
                                android:id="@+id/tl_type"
                                android:layout_width="match_parent"
                                android:layout_height="48dp"
                                android:layout_gravity="center_horizontal"
                                android:background="@color/white"
                                android:elevation="1dp"
                                app:layout_collapseMode="parallax"
                                app:layout_collapseParallaxMultiplier="0.1"
                                app:tabGravity="center"
                                app:tabIndicatorHeight="0dp"
                                app:tabMode="scrollable"
                                app:tabSelectedTextColor="@color/colorPrimary"
                                app:tabTextColor="@color/gray_text">
                            </android.support.design.widget.TabLayout>

                        </android.support.design.widget.CollapsingToolbarLayout>


                    </android.support.design.widget.AppBarLayout>


                    <android.support.v4.view.ViewPager
                        android:id="@+id/vp_content"
                        android:layout_width="match_parent"
                        android:layout_height="match_parent"
                        app:layout_behavior="@string/appbar_scrolling_view_behavior">

                    </android.support.v4.view.ViewPager>


                </android.support.design.widget.CoordinatorLayout>


            </LinearLayout>


        </com.kennyc.view.MultiStateView>
    </LinearLayout>

</android.support.design.widget.CoordinatorLayout>
```
值得注意的是 **MultiStateView** 中的这几个属性：
```
            app:msv_emptyView="@layout/layout_empty"
            app:msv_errorView="@layout/layout_error"
            app:msv_loadingView="@layout/layout_loading"
            app:msv_viewState="content"
```
需要我们传入我们自己编写的空布局、错误布局、加载中布局和当前页面的状态，当然你可以直接使用我写的默认布局。页面的状态有4种 **content** 、**loading** 、**error** 和 **empty** ，你需要当前页面的初始状态是什么，就改变 **msv_viewState** 这个属性值即可。一般来说如果需要加载网络数据，初始状态应该是 **loading** ，如果不需要加载网络数据，就像我们的 **MainActivity** 一样的话，初始状态应该是 **content** 。

#### 一键生成 **MainFragment** 的MVP相关代码

由于这个App采用的是ViewPager+Fragment的UI结构，所以我们还需要创建一个 **MainPageFragment** 。和创建 **MainActivity** 的时候几乎是一模一样的，唯一的区别在于以选择一键生成的选项不是 **Kotlin MVP Activity** 而是 **Kotlin RefreshAndLoadMore MVP Fragment** ，如下图所示：
![](https://github.com/BlackFlagBin/MarkDownPicture/blob/master/ASTemplatePicFragmen.png?raw=true) 

因为 **MainPageFragment** 需要下拉刷新和上拉加载更多，所以我们创建的是 **Kotlin RefreshAndLoadMore MVP Fragment** 而不是 **Kotlin MVP Fragment** 。这里需要注意一点的是有的同学会创建成 **MainFragment** ，结构发现fragment的MVP代码覆盖了 **MainActivity** 的MVP代码，所以在创建Activity或者Fragment的时候应该避免二者前面的名字重复，否则后者的MVP代码会覆盖前者。

和一键生成 **MainActivity** 时候一样，这时候项目目录中也会出现 **MainPageContract** 、 **MainPageModel** 、 **MainPagePresenter** 和我们的 **MainPageFragment** 。跟之前的流程一样，我们来看一下这些生成的代码：
* **MainPageContract**：
```
interface MainPageContract {
    interface IMainPageModel {
        fun getData(type: String, pageNo: Int, limit: Int): Observable<Optional<List<DataItem>>>
    }

    interface IMainPagePresenter : IBaseRefreshAndLoadMorePresenter

    interface IMainPageView : IBaseRefreshAndLoadMoreView<List<DataItem>>
}
```
跟之前的 **MainContract** 类似，区别在于我们的页面是带 **RefreshAndLoadMore** 的，所以可以看到 **presenter** 接口继承了 **IBaseRefreshAndLoadMorePresenter** ，而 **view** 接口继承了 **IBaseRefreshAndLoadMoreView** 。值得注意的是 ** IBaseRefreshAndLoadMoreView &lt;List&lt;DataItem&gt;&gt; ** 带有一个泛型 ** List&lt;DataItem&gt; ** ，这个类型与我们的 **override fun showContentView(data: List&lt;DataItem&gt; )** 中参数的类型对应。

同样要注意的点是在 **IMainPageModel** 这个接口中我们定义了一个 **getData(type: String, pageNo: Int, limit: Int)** 的方法用于获取分页数据。要强调的是网络接口实际返回的是 **List&lt;DataItem&gt; ** 类型的数据，但这个 **getData** 的返回值我们需要在外面包上一层 **Optional**。这个 **Optional** 是 **KCommon** 库中定义的一个类，其实很简单：
```
/**
 * Created by blackflagbin on 2018/3/29.
 * 解决rxjava2不能处理null的问题，我们把所有返回的有效数据包一层Optional，通过isEmpty判断是否为空
 */
data class Optional<T>(var data: T)  {

    fun isEmpty(): Boolean {
        return data == null
    }
}
```
这其实就是在网络返回的原始数据上包了一层，那么有的人会不理解了，为什么要包这一层，这不是多此一举么？

使用过 **RxJava** 的同学应该很清楚，在我们实际开发中，网络接口间的顺序调用是一个很常见的事情。比方说我在首页想展示一个用户当前小区下的通知公告，那其实肯定要有两个接口，获取用户当前小区的接口、根据小区id获取通知公告的接口。这两个接口之间存在着先后的逻辑关系，必须先拿到小区id，才能获取通知公告。

通常来说，用过 **RxJava** 的同学会使用 **flatMap** 这个操作符，即使是用户没有小区，接口返回的小区数据为 **null** ，这在 **RxJava1** 的时候是不会存在任何问题的。然而，在新的 **RxJava2** 中，一旦接口返回的是 **null** ，而你又使用了 **flatMap** ，那么很抱歉，程序会报错，原因就是在 **RxJava2** 中不支持 **null** 值事件的传递。

其实我们可以让后台不给我们返回 **null** 来避免这个问题，但往往在实际开发中后台为了图省事也是不会处理这种事情的，而且最常见的理由就是为什么IOS可以返回 **null**，Android就不行？

所以为了避免跟后端的同学过多的撕逼，我们只能在返回的真实数据上包上一层 **Optional** ，来处理 **RxJava2** 中无法传递 **null** 事件的问题。这也是我目前可以想到的最好的处理方案，如果大家有更好的处理办法，不妨通过留言告诉我，我们共同学习，共同进步。


* **MainPageModel**：
```
class MainPageModel : BaseModel<ApiService, CacheService>(), MainPageContract.IMainPageModel {
    override fun getData(type: String, pageNo: Int, limit: Int): Observable<Optional<List<DataItem>>> {
        return if (NetworkUtils.isConnected()) {
            mCacheService.getMainDataList(
                    mApiService.getMainDataList(
                            type, limit, pageNo).compose(DefaultTransformer()),
                    DynamicKeyGroup(type, pageNo),
                    EvictDynamicKeyGroup(true)).subscribeOn(Schedulers.io()).observeOn(
                    AndroidSchedulers.mainThread())
        } else {
            mCacheService.getMainDataList(
                    mApiService.getMainDataList(
                            type, limit, pageNo).compose(DefaultTransformer()),
                    DynamicKeyGroup(type, pageNo),
                    EvictDynamicKeyGroup(false)).subscribeOn(Schedulers.io()).observeOn(
                    AndroidSchedulers.mainThread())
        }
    }
}
```
在我们的 **MainPageModel** 中，实现了 **getData** 这个在 **IMainPageModel** 中定义的接口方法。因为页面的逻辑是网络正常时获取网络数据，无网络时加载缓存数据，所以方法中会有关于网络状态的判断。我们需要注意的是两个变量： **mApiService** 和 **mCacheService** 。这两个变量都是 **BaseModel** 中的成员变量，我们在 **BaseModel** 的继承类中都可以直接拿来使用。关于网络请求和缓存我使用的是 **Retrofit** 和 **RxCache** ，如果有不太了解的同学可以自行查看相关的文档，我这里就不再赘述了。这里会有小伙伴问：如果我不需要缓存怎么办？如果不需要缓存就更好办了，这个方法直接返回
```
mApiService.getMainDataList(
                            type, limit, pageNo).compose(DefaultTransformer())
```
就可以了。

有的同学可能还会有疑问，为什么请求接口后面要加上 `compose(DefaultTransformer())` 这么一句，可不可以省略？其实这一句是 **KCommon** 中网络处理的关键部分，这句代码对我们网络请求返回的结果做了相应的错误处理和 **Optional** 的包装，所以这一句是必不可少的。对它的实现原理感兴趣的同学可以直接通过 **Android Studio** 查看源码，原理并不繁琐。
* **MainPagePresenter**
```
class MainPagePresenter(iMainPageView: MainPageContract.IMainPageView) :
        BasePresenter<MainPageContract.IMainPageModel, MainPageContract.IMainPageView>(iMainPageView),
        MainPageContract.IMainPagePresenter {
    override val model: MainPageContract.IMainPageModel
        get() = MainPageModel()

    override fun initData(dataMap: Map<String, String>?) {
        initData(dataMap, CommonLibrary.instance.startPage)
    }

    override fun initData(dataMap: Map<String, String>?, pageNo: Int) {
        if (!NetworkUtils.isConnected()) {
            mView.showTip("网络已断开，当前数据为缓存数据")
        }
        if (pageNo == CommonLibrary.instance.startPage) {
            //如果请求的是分页的首页，必须先调用这个方法
            mView.beforeInitData()
            mModel.getData(
                    dataMap!!["type"].toString(),
                    pageNo,
                    CommonLibrary.instance.pageSize).bindToLifecycle(mLifecycleProvider).subscribeWith(
                    NoProgressObserver(mView, object : ObserverCallBack<Optional<List<DataItem>>> {
                        override fun onNext(t: Optional<List<DataItem>>) {
                            mView.showSuccessView(t.data)
                            mView.dismissLoading()
                        }

                        override fun onError(e: Throwable) {
                            mView.showErrorView("")
                            mView.dismissLoading()
                        }
                    }))
        } else {
            mModel.getData(
                    dataMap!!["type"].toString(),
                    pageNo,
                    CommonLibrary.instance.pageSize).bindToLifecycle(mLifecycleProvider).subscribeWith(
                    NoProgressObserver(
                            mView, mIsLoadMore = true))
        }
    }
}
```
可以看到和 **MainPresenter** 的结构大同小异，区别在于多了 `override fun initData(dataMap: Map<String, String>?, pageNo: Int)` 这个方法，很明显这是加载分页用的。要注意我这个方法中代码的写法，需要注意的有这么几个点：

`mView.beforeInitData()`
在请求分页的首页时，必须先调用这行代码进行数据的整理。之后再去使用 **mModel** 中的方法请求网络。

`.bindToLifecycle(mLifecycleProvider)`
这句的目的是将网络请求和当前页面(Activity或Fragment)的生命周期绑定，当页面结束的时候会终止网络请求，防止内存泄漏，使用的是 **RxLifeCycle** ,有兴趣的同学可以自行研究。我的建议是所有的 **presenter** 中的网络请求调用中都要加上这一句，防止内存泄漏。

`NoProgressObserver`
由于整个网络框架使用的是 **Rxjava+Retrofit+OKHttp** ，所以最终是需要一个 **Observer** 来最终处理我们网络请求返回的数据。在 **KCommon** 中内置了两种 **Observer** ：**NoProgressObserver** 和 **ProgressObserver** 。从名字也可以明白，分别是没有加载动画的和有加载动画的 **Observer** 。那么二者的使用时机分别是什么呢？简而言之就是当你请求一个网络时页面需要有Loading动画的显示时使用 **NoProgressObserver** ，请求网络时不需要Loading动画时使用 **ProgressObserver** 。

`mIsLoadMore = true`
这是 **NoProgressObserver** 和 **ProgressObserver** 中都存在的一个默认参数，默认为 **false** ，意思是 **当前网络请求是否是加载更多的请求** 。当我们加载非首页的时候将之置为 **true** 。

`mView.showSuccessView(t.data)`
当网络请求成功时必须调用。作用是将当前页面从Loading状态切换到成功的状态。

`mView.showErrorView("网络连接异常")`
当网络请求失败时必须调用。作用是将当前页面从Loading状态切换到失败的状态。传入的参数我们可以自定义错误的原因，这个随便写。

`mView.dismissLoading()`
这个主要是带有下拉刷新的页面中当获取到网络数据(无论成功或者失败)后，必须调用。

* **MainPageFragment**
```
@SuppressLint("ValidFragment")
class MainPageFragment() :
        BaseRefreshAndLoadMoreFragment<ApiService, CacheService, MainPageContract.IMainPagePresenter, List<DataItem>>(),
        MainPageContract.IMainPageView {
    private val mTypeArray: Array<String> by lazy {
        arrayOf("all", "Android", "iOS", "休息视频", "福利", "拓展资源", "前端", "瞎推荐", "App")
    }

    private lateinit var mType: String

    override val adapter: BaseQuickAdapter<*, *>?
        get() = MainPageAdapter(arrayListOf())

    override val recyclerView: RecyclerView?
        get() = rv_list

    override val layoutManager: RecyclerView.LayoutManager?
        get() = FixedLinearLayoutManager(activity)

    override val swipeRefreshView: SwipeRefreshLayout?
        get() = swipe_refresh

    override val multiStateView: MultiStateView?
        get() = multi_state_view

    override val layoutResId: Int
        get() = R.layout.fragment_main_page

    override val presenter: MainPageContract.IMainPagePresenter
        get() = MainPagePresenter(this)

    constructor(position: Int) : this() {
        mType = mTypeArray[position]
    }

    override fun initData() {
        mDataMap["type"] = mType
        mPresenter.initData(mDataMap)
    }

    override fun showContentView(data: List<DataItem>) {
        mAdapter?.onItemClickListener = BaseQuickAdapter.OnItemClickListener { adapter, view, position ->
            startActivity(
                    WebActivity::class.java,
                    bundleOf(
                            "url" to (mAdapter?.data!![position] as DataItem).url,
                            "title" to (mAdapter?.data!![position] as DataItem).desc))
        }
    }
}
```
可以看到，和 **MainActivity** 相似处很多，我这里着重说明一下不同的地方：

```
override val adapter: BaseQuickAdapter<*, *>?
        get() = MainPageAdapter(arrayListOf())
```
因为要有上拉加载更多的列表，所以很明显需要一个 **Adapter** 。 **KCommon** 中依赖了 **BaseRecyclerViewAdapterHelper** 这个第三方库，我平常用起来挺方便的，而且功能很强大，在这里也推荐大家使用。这个三方库的具体使用方法我就不赘述了，大家可以自行去 **GitHub** 上查看它的文档。

```
    override val recyclerView: RecyclerView?
        get() = rv_list
```
不用多说了吧，需要一个 **RecyclerView** 对象。

```
    override val layoutManager: RecyclerView.LayoutManager?
        get() = FixedLinearLayoutManager(activity)
```
大家肯定都知道要使用 **LayoutManager** ，但这里必须使用我在 **KCommon** 中定义的几个带 **Fixed** 打头的 **LayoutManager** ,这样会避免一些诡异的异常。

```
    override fun initData() {
        mDataMap["type"] = mType
        mPresenter.initData(mDataMap)
    }
```
在 **initData** 中我们首先将类型参数存放进了 **mDataMap** 中，然后调用了 **mPresenter.initData(mDataMap)** 进行了网络请求。

没错，只需要配置这么几个参数，我们的一个带有下拉刷新和上拉加载更多的页面就完成了。相信做过类似功能页面的同学应该很清楚要实现同样的功能如果全部自己写的话会很繁琐，但使用了 **KCommon** ，一切都会变得非常容易。

#### 额外的提醒
到此为止，通过一个简单Demo的讲解， **KCommon** 基础的用法已经全部介绍完毕了，除了我上面说的之外，在 **KCommon** 中还集成依赖了一些我个人在实际项目开发中经常用到的，而且非常好用的第三方库，这里大家有兴趣的话可以尝试了解一下。

最后要说的是我会长期维护和改进 **KCommon** ，如果大家在使用的过程中存在疑惑，可以在 **GitHub** 上提出 **issue** ，我会一一解答。感谢大家花时间看这么一篇文章，如果我的努力解决了大家实际开发中的问题，提高了大家的效率，希望可以顺手给个 **star** ，谢谢。

[GitHub地址](https://github.com/BlackFlagBin/KCommonProject)

















 