package com.blackflagbin.kcommonproject.ui.activity

import android.support.v4.widget.SwipeRefreshLayout
import com.blackflagbin.kcommon.base.BaseActivity
import com.blackflagbin.kcommon.widget.GlideCircleTransform
import com.blackflagbin.kcommonproject.R
import com.blackflagbin.kcommonproject.common.http.ApiService
import com.blackflagbin.kcommonproject.common.http.CacheService
import com.blackflagbin.kcommonproject.mvp.contract.MainContract
import com.blackflagbin.kcommonproject.mvp.presenter.MainPresenter
import com.blackflagbin.kcommonproject.ui.adapter.pageradapter.MainPagerAdapter
import com.bumptech.glide.Glide
import com.kennyc.view.MultiStateView
import com.yarolegovich.slidingrootnav.SlidingRootNavBuilder
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.menu_main_drawer.*
import org.jetbrains.anko.sdk25.coroutines.onClick

class MainActivity : BaseActivity<ApiService, CacheService, MainPresenter, Any?>(),
        MainContract.IMainView {
    private val AVATAR_URL = "https://avatars2.githubusercontent.com/u/17843145?s=400&u=d417a5a50d47426c0f0b6b9ff64d626a36bf0955&v=4"

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
    }

    override fun initData() {

    }

    override fun showContentView(data: Any?) {
    }

    private fun setupViewPager() {
        vp_content.offscreenPageLimit = 10
        vp_content.adapter = MainPagerAdapter(supportFragmentManager)
        tl_type.setupWithViewPager(vp_content)
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

}
