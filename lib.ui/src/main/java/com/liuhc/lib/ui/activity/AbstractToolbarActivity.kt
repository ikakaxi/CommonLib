package com.liuhc.lib.ui.activity

import android.content.Context
import android.support.annotation.CallSuper
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.ViewGroup
import com.liuhc.lib.ui.R
import kotlinx.android.synthetic.main.activity_has_toolbar.*


/**
 * 有后退标题栏的Activity,子类的xml中需要有id为toolbar的ToolBar
 * Created by liuhc on 2017/12/18.
 */
abstract class AbstractToolbarActivity : AbstractActivity() {

    lateinit private var mToolbar: Toolbar

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
            mToolbar.setNavigationOnClickListener {
                onBackPressed()
            }
        }
        if (getContextViewResId() > 0) {
            setContentLayout(getContextViewResId())
        }
    }

    override final fun getContextViewResId(): Int = R.layout.activity_has_toolbar

    fun getContentId() = R.id.content

    fun getToolbar() = mToolbar

    /**
     * 设置toolbar下面区域的内容,如果自己要显示Fragment,就返回-1或者0,
     * 然后用getContentId()获取需要放置Fragment的位置
     *
     * @param layoutId
     */
    private fun setContentLayout(layoutId: Int) {
        val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val contentView = inflater.inflate(layoutId, null)
        val params = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT)
        content.addView(contentView, params)
    }

    open protected fun getToolbarTitle(): CharSequence = title

    /**
     * 是否是首页,首页不应该显示后退图标
     */
    open protected fun isFirstPage() = false
}