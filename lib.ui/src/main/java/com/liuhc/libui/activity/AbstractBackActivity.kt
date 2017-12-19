package com.liuhc.libui.activity

import android.support.annotation.CallSuper
import android.support.v7.widget.Toolbar
import com.liuhc.libui.R

/**
 * 有后退标题栏的Activity,子类的xml中需要有id为toolbar的ToolBar
 * Created by liuhc on 2017/12/18.
 */
abstract class AbstractBackActivity : AbstractActivity() {

    lateinit var mToolbar: Toolbar

    /**
     * 初始化UI控件
     */
    @CallSuper
    override fun doInitView() {
        initToolbar()
    }

    private fun initToolbar() {
        mToolbar = findViewById(R.id.toolbar)
        mToolbar.title = getToolbarTitle()
        setSupportActionBar(mToolbar)
        if (!isFirstPage()) {
            //setNavigationIcon需要放在setSupportActionBar之后才会生效
            mToolbar.setNavigationIcon(R.drawable.back_icon)
        }
    }

    open protected fun getToolbarTitle(): CharSequence = title

    /**
     * 是否是首页,首页不应该显示后退图标
     */
    open protected fun isFirstPage() = false
}