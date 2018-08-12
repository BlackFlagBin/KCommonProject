package com.blackflagbin.kcommonproject.ui.activity

import android.support.v4.widget.SwipeRefreshLayout
import android.text.format.Formatter
import com.blackflagbin.kcommon.base.BaseActivity
import com.blackflagbin.kcommon.widget.GlideCircleTransform
import com.blackflagbin.kcommonproject.R
import com.blackflagbin.kcommonproject.common.http.ApiService
import com.blackflagbin.kcommonproject.common.http.CacheService
import com.blackflagbin.kcommonproject.mvp.contract.MainContract
import com.blackflagbin.kcommonproject.mvp.presenter.MainPresenter
import com.blackflagbin.kcommonproject.ui.adapter.pageradapter.MainPagerAdapter
import com.blackflagbin.kcommonproject.ui.fragment.factory.FragmentFactory
import com.blankj.utilcode.util.CacheUtils
import com.bumptech.glide.Glide
import com.kennyc.view.MultiStateView
import com.yarolegovich.slidingrootnav.SlidingRootNavBuilder
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.menu_main_drawer.*
import org.greenrobot.eventbus.EventBus
import org.jetbrains.anko.bundleOf
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.toast
import retrofit2.Retrofit

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

    override fun onDestroy() {
        super.onDestroy()
        //清除缓存的fragment，避免内存泄漏
        FragmentFactory.clear()
    }

}
