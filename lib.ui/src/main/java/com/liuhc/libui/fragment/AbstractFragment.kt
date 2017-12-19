package com.liuhc.libui.fragment

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * 所有Fragment的父类
 * Created by liuhc on 2017/12/18.
 */
abstract class AbstractFragment : Fragment() {

    private var mIsShow = false
    private lateinit var mRoot: View

    //是否需要处理后退事件
    private var mHandledPress = false
    protected var mContext: Context? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.mContext = context
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        mRoot = inflater.inflate(getContentView(), container, false)
        return mRoot
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView(mRoot)
    }

    //解决no activity的android系统的bug
    override fun onDetach() {
        super.onDetach()
        try {
            val childFragmentManager = Fragment::class.java.getDeclaredField("mChildFragmentManager")
            childFragmentManager.isAccessible = true
            childFragmentManager.set(this, null)
        } catch (e: NoSuchFieldException) {
            throw RuntimeException(e)
        } catch (e: IllegalAccessException) {
            throw RuntimeException(e)
        }
        mContext = null
    }

    override fun onResume() {
        super.onResume()
        if (!mIsShow) {
            mIsShow = true
            onShow()
        }
    }

    override fun onStop() {
        super.onStop()
        mIsShow = false
        onHide()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        if (arguments != null) {
            outState.putAll(arguments)
        }
        super.onSaveInstanceState(outState)
    }

    //当Fragment配合ViewPager使用时，使用setUserVisibleHint()判断Fragment是显示还是隐藏
    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) {
            mIsShow = true
            onShow()
        } else {
            mIsShow = false
            onHide()
        }
    }

    //当Fragment配合FragmentTransition使用时，
    //使用onHiddenChanged()来判断Fragment是显示还是隐藏，
    //但是第一次显示要在onResume()里判断。
    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (hidden) {
            mIsShow = false
            onHide()
        } else {
            if (!mIsShow) {
                mIsShow = true
                onShow()
            }
        }
    }

    /**
     * true:该Fragment需要处理后退事件
     * false:该Fragment不需要处理后退事件
     */
    internal fun handleBackPressed(): Boolean {
        return mHandledPress
    }

    /**
     * 获得要显示的view的资源id
     *
     * @return
     */
    protected abstract fun getContentView(): Int

    /**
     * 初始化控件
     *
     * @param root
     */
    protected abstract fun initView(root: View)

    /**
     * 页面显示
     */
    protected abstract fun onShow()

    /**
     * 页面隐藏
     */
    protected abstract fun onHide()
}