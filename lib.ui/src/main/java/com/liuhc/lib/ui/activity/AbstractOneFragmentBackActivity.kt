package com.liuhc.lib.ui.activity

import android.support.v4.app.Fragment
import com.liuhc.lib.ui.IBackFragment
import com.liuhc.lib.ui.extension.showFragment
import com.liuhc.lib.ui.R

/**
 * 内置一个Fragment的Activity
 * Created by liuhc on 2017/12/19.
 */
abstract class AbstractOneFragmentBackActivity<out T> : AbstractBackActivity() where T : IBackFragment, T : Fragment {

    private val mBackFragment: T by lazy {
        getBackFragment()
    }

    override fun getToolbarTitle(): CharSequence {
        return mBackFragment.getActivityTitle()
    }

    override fun getContextViewResId(): Int = R.layout.activity_fragment

    override fun doInitView() {
        super.doInitView()
        showFragment(R.id.fragment, mBackFragment, false)
    }

    protected abstract fun getBackFragment(): T

}