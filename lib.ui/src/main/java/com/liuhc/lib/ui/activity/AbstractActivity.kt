package com.liuhc.lib.ui.activity

import android.os.Bundle
import android.os.Parcelable
import android.support.annotation.LayoutRes
import android.support.v7.app.AppCompatActivity
import com.liuhc.lib.ui.fragment.AbstractFragment
import com.liuhc.lib.ui.util.SwitchFragmentUtil
import java.io.Serializable

/**
 * 所有Activity的父类
 * Created by liuhc on 2017/12/12.
 */
abstract class AbstractActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getContextViewResId())
        savedInstanceState?.let {
            recovery(it)
        } ?: doInitView()
    }

    @LayoutRes
    protected abstract fun getContextViewResId(): Int

    /**
     * 初始化UI控件
     */
    protected abstract fun doInitView()

    companion object {
        private val KEY_SERIALIZABLE_COUNT = "KEY_SERIALIZABLE_COUNT"
        private val KEY_PARCELABLE_COUNT = "KEY_PARCELABLE_COUNT"
    }

    /**
     * activity被回收时保存信息
     *
     * 示例:
     * String a="hello",b="world";
     * Serializable[] onSaveSerializableData(){
     * 		return new Serializable[]{a,b};
     * }
     *
     */
    override fun onSaveInstanceState(outState: Bundle) {
        onSaveData(outState)
        super.onSaveInstanceState(outState)
    }

    private fun onSaveData(outState: Bundle) {
        val serializableData = onSaveSerializableData()
        serializableData?.let {
            outState.putInt(KEY_SERIALIZABLE_COUNT, it.size)
            for (i in it.indices) {
                outState.putSerializable(i.toString(), it[i])
            }
        }
        val parcelableData = onSaveParcelableData()
        parcelableData?.let {
            outState.putInt(KEY_PARCELABLE_COUNT, it.size)
            for (i in it.indices) {
                outState.putParcelable(i.toString(), it[i])
            }
        }
    }

    /**
     * activity被恢复时恢复信息
     */
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        onRestoreData(savedInstanceState)
        super.onRestoreInstanceState(savedInstanceState)
    }

    /**
     * 恢复数据
     *
     * @param savedInstanceState
     */
    private fun onRestoreData(savedInstanceState: Bundle) {
        val serializableData = onSaveSerializableData()
        serializableData?.let {
            val count = savedInstanceState.getInt(KEY_SERIALIZABLE_COUNT)
            for (i in 0 until count) {
                it[i] = savedInstanceState.getSerializable(i.toString())
            }
        }
        val parcelableData = onSaveParcelableData()
        parcelableData?.let {
            val count = savedInstanceState.getInt(KEY_PARCELABLE_COUNT)
            for (i in 0 until count) {
                it[i] = savedInstanceState.getParcelable(i.toString())
            }
        }
        onRestored()
    }

    /**
     * 如果当前Fragment是AbstractFragment并且它要自己处理后退事件,该Activity就不处理后退了
     */
    override fun onBackPressed() {
        val currentFragment = SwitchFragmentUtil.getCurrentFragment(supportFragmentManager)
        if (currentFragment is AbstractFragment) {
            if (currentFragment.handleBackPressed()) {
                return
            }
        }
        super.onBackPressed()
    }

//    override fun onBackPressed() {
//        if (fragmentManager.backStackEntryCount == 1 || SwitchFragmentUtil.getCurrentFragment(fragmentManager) == null) {
//            finish()
//        } else {
//            super.onBackPressed()
//        }
//    }

    /**
     * activity被恢复的时候会调用此方法
     */
    open protected fun recovery(savedInstanceState: Bundle) = Unit

    /**
     * 数据已经被恢复,可以在这里操作被恢复的数据
     */
    open protected fun onRestored() = Unit

    open protected fun onSaveSerializableData(): Array<Serializable>? {
        return null
    }

    open protected fun onSaveParcelableData(): Array<Parcelable>? {
        return null
    }

}