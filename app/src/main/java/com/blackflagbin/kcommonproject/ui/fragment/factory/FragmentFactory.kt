package com.blackflagbin.kcommonproject.ui.fragment.factory

import android.support.v4.app.Fragment

/**
 * Created by blackflagbin on 2018/3/20.
 */
object FragmentFactory {
    private val mFragmentMap: HashMap<Int, Fragment> by lazy { hashMapOf<Int, Fragment>() }
    
    fun getFragment(position: Int): Fragment {
        if (mFragmentMap[position] == null) {
            mFragmentMap[position] = Fragment()
        }
        return mFragmentMap[position]!!
    }
}