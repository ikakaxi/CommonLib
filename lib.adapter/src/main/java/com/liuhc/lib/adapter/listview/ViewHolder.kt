package com.liuhc.lib.adapter.listview

import android.util.SparseArray
import android.view.View
import android.widget.TextView

/**
 * 封装ViewHolder,使用更简单方便
 * Created by liuhc on 2017/12/19.
 */
class ViewHolder(private val convertView: View) {
    private val views = SparseArray<View>()

    /**
     * 根据id获取view
     */
    fun <T : View> getView(viewId: Int): T {
        var view: View? = views.get(viewId)
        if (null == view) {
            view = convertView.findViewById(viewId)
            views.put(viewId, view)
        }
        @Suppress("UNCHECKED_CAST")
        return view as T
    }

    fun setText(viewId: Int, text: CharSequence): ViewHolder {
        getView<TextView>(viewId).text = text
        return this
    }
}