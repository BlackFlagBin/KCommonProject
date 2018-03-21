package com.blackflagbin.kcommonproject.ui.adapter.pageradapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.blackflagbin.kcommonproject.ui.fragment.factory.FragmentFactory

/**
 * Created by blackflagbin on 2017/11/30.
 */
class MainPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    private val mTypeArray: Array<String> by lazy {
        arrayOf("all", "Android", "iOS", "休息视频", "福利", "拓展资源", "前端", "瞎推荐", "App")
    }

    override fun getItem(position: Int): Fragment {
        return FragmentFactory.getFragment(position)
    }

    override fun getCount(): Int {
        return mTypeArray.size
    }

    override fun getPageTitle(position: Int): CharSequence {
        return mTypeArray[position]
    }
}