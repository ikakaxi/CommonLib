package com.liuhc.lib.ui.extension

import android.support.annotation.IdRes
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import com.liuhc.lib.ui.util.SwitchFragmentUtil

/**
 * Activity的扩展类
 * Created by liuhc on 2017/12/15.
 */
fun FragmentActivity.showFragment(@IdRes fragmentResId: Int, targetFragment: Fragment, addToBackStack: Boolean = true) {
    SwitchFragmentUtil.showFragment(this.supportFragmentManager, fragmentResId, targetFragment, addToBackStack)
}

fun FragmentActivity.replaceFragment(@IdRes fragmentResId: Int, targetFragment: Fragment, addToBackStack: Boolean = true) {
    SwitchFragmentUtil.replaceFragment(this.supportFragmentManager, fragmentResId, targetFragment, addToBackStack)
}

inline fun <reified T : Fragment> FragmentActivity.findFragmentByClassSimpleName(): Fragment {
    return this.supportFragmentManager.findFragmentByTag(T::class.simpleName)
}

inline fun <reified T : Fragment> FragmentActivity.popTopFragmentByClassSimpleName(containSelf: Boolean = false) {
    return SwitchFragmentUtil.popTopFragmentByClassSimpleName(this.supportFragmentManager, T::class.simpleName!!, containSelf)
}