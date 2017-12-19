package com.liuhc.libui.extension

import android.support.annotation.IdRes
import android.support.v4.app.Fragment
import com.liuhc.libui.util.SwitchFragmentUtil

/**
 * Fragment的扩展类
 * Created by liuhc on 2017/12/15.
 */
fun Fragment.showFragment(@IdRes fragmentResId: Int, targetFragment: Fragment, addToBackStack: Boolean = true) {
    SwitchFragmentUtil.showFragment(this.childFragmentManager, fragmentResId, targetFragment, addToBackStack)
}

fun Fragment.replaceFragment(@IdRes fragmentResId: Int, targetFragment: Fragment, addToBackStack: Boolean = true) {
    SwitchFragmentUtil.replaceFragment(this.childFragmentManager, fragmentResId, targetFragment, addToBackStack)
}

inline fun <reified T : Fragment> Fragment.findFragmentByClassSimpleName(): Fragment {
    return this.childFragmentManager.findFragmentByTag(T::class.simpleName)
}

inline fun <reified T : Fragment> Fragment.popTopFragmentByClassSimpleName(containSelf: Boolean = false) {
    return SwitchFragmentUtil.popTopFragmentByClassSimpleName(this.childFragmentManager, T::class.simpleName!!, containSelf)
}