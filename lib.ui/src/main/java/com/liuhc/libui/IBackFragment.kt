package com.liuhc.libui

/**
 * 放在AbstractBackFragment里面的Fragment需要实现该接口
 * Created by liuhc on 2017/12/19.
 */
interface IBackFragment {

    fun getActivityTitle(): CharSequence
}