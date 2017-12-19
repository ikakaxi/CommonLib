package com.liuhc.lib.ui.util

import android.support.annotation.IdRes
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction

/**
 * 切换Fragment的工具类
 * Created by liuhc on 2017/12/15.
 */
object SwitchFragmentUtil {

    /**
     * 得到当前正在显示的fragment
     *
     * @param fragmentManager
     * @return
     */
     fun getCurrentFragment(fragmentManager: FragmentManager): Fragment? {
        val fragments = fragmentManager.fragments ?: return null// 这个数据需要判断空值
        var curFragment: Fragment?
        for (i in fragments.indices.reversed()) {
            curFragment = fragments[i]
            if (curFragment != null && curFragment.isVisible) {
                return curFragment
            }
        }
        return null
    }

    /**
     * 用replace方式显示Fragment
     *
     * @param fragmentResId  显示的位置
     * @param targetFragment 要显示的Fragment
     * @param addToBackStack 是否添加到回退栈
     */
    fun replaceFragment(fragmentManager: FragmentManager, @IdRes fragmentResId: Int, targetFragment: Fragment, addToBackStack: Boolean = true) {
        switchFragment(fragmentManager, fragmentResId, targetFragment, addToBackStack, true)
    }

    /**
     * 用show,hide方式显示Fragment
     *
     * @param fragmentResId  显示的位置
     * @param targetFragment 要显示的Fragment
     * @param addToBackStack 是否添加到回退栈
     */
    fun showFragment(fragmentManager: FragmentManager, @IdRes fragmentResId: Int, targetFragment: Fragment, addToBackStack: Boolean = false) {
        switchFragment(fragmentManager, fragmentResId, targetFragment, addToBackStack, false)
    }

    private fun switchFragment(fragmentManager: FragmentManager, @IdRes fragmentResId: Int, targetFragment: Fragment, addToBackStack: Boolean, isReplace: Boolean) {
        val transaction = fragmentManager.beginTransaction()
        switchFragment(transaction, getCurrentFragment(fragmentManager), targetFragment, fragmentResId, addToBackStack, isReplace)
    }

    /**
     * show,hide方式切换fragment的显示
     *
     * @param transaction
     * @param currentFragment 当前Fragment
     * @param to              目标Fragment
     * @param addToBackStack  是否加入回退栈
     * @param isReplace       是否是替换
     *                          true:替换
     *                          false:show/hide
     */
    @Synchronized
    private fun switchFragment(transaction: FragmentTransaction, currentFragment: Fragment?, to: Fragment,
                               fragmentResId: Int, addToBackStack: Boolean, isReplace: Boolean) {
        if (currentFragment != null && currentFragment == to) {// 同一个页面,不做跳转
            return
        }
        if (isReplace) {
            transaction.replace(fragmentResId, to, to.javaClass.name)
        } else {
            transaction.hide(currentFragment)
            if (to.isAdded) {
                transaction.show(to)
            } else {
                transaction.add(fragmentResId, to, to.javaClass.name)
            }
        }
        if (addToBackStack) {
            //addToBackStack(tag)方法可接受可选的字符串参数,来为事务指定独一无二的名称。
            //除非你打算使用 FragmentManager.BackStackEntry API执行高级Fragment操作,否则无需使用此名称。
            transaction.addToBackStack(to.javaClass.name)
        }
        transaction.commitAllowingStateLoss()
    }

    /**
     * 弹出fragment
     * @param: containSelf 是否自己也要弹出 true:自己也要弹出栈 false:自己不弹出栈
     */
    fun popTopFragmentByClassSimpleName(fragmentManager: FragmentManager, fragmentClassSimpleName: String, containSelf: Boolean = false) {
        fragmentManager.popBackStackImmediate(fragmentClassSimpleName, if (containSelf) FragmentManager.POP_BACK_STACK_INCLUSIVE else 0)
    }
}