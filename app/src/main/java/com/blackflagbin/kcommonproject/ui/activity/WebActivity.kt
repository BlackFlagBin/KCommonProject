package com.blackflagbin.kcommonproject.ui.activity

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.view.KeyEvent
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import com.blackflagbin.kcommon.base.BaseActivity
import com.blackflagbin.kcommonproject.R
import com.blackflagbin.kcommonproject.common.http.ApiService
import com.blackflagbin.kcommonproject.common.http.CacheService
import com.blackflagbin.kcommonproject.mvp.contract.WebContract
import com.blackflagbin.kcommonproject.mvp.presenter.WebPresenter
import com.kennyc.view.MultiStateView
import kotlinx.android.synthetic.main.activity_web.*
import kotlinx.android.synthetic.main.layout_toolbar.*
import org.jetbrains.anko.sdk25.coroutines.onClick

class WebActivity : BaseActivity<ApiService, CacheService, WebPresenter, Any?>(),
        WebContract.IWebView {
    private lateinit var mUrl: String
    private lateinit var mTitle: String

    override val swipeRefreshView: SwipeRefreshLayout?
        get() = null

    override val multiStateView: MultiStateView?
        get() = null

    override val layoutResId: Int
        get() = R.layout.activity_web

    override val presenter: WebPresenter
        get() = WebPresenter(this)

    override fun onExtraBundleReceived(bundle: Bundle) {
        super.onExtraBundleReceived(bundle)
        mUrl = bundle.getString("url")
        mTitle = bundle.getString("title")

    }

    override fun initView() {
        super.initView()
        rl_left.onClick {
            if (webView.canGoBack()) {
                webView.goBack()
            } else {
                finish()
            }
        }
        tv_middle.text = mTitle

        webView.settings.javaScriptEnabled = true
        webView.settings.domStorageEnabled = true
        webView.loadUrl(mUrl)
        webView.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
                if (newProgress == 100) {
                    if (progress_bar != null) {
                        progress_bar.visibility = View.GONE
                    }
                } else {
                    if (progress_bar != null) {
                        progress_bar.visibility = View.VISIBLE
                        progress_bar.progress = newProgress //设置加载进度
                    }
                }
            }

        }

        webView.webViewClient = object : WebViewClient() {

            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                mUrl = url
                view.loadUrl(mUrl)
                return true
            }

        }
    }

    override fun initData() {
    }

    override fun showContentView(data: Any?) {
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (webView.canGoBack()) {
                webView.goBack()
                return true
            }
        }
        return super.onKeyDown(keyCode, event)
    }

}
