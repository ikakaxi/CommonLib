package com.liuhc.lib.adapter.listview;

import android.content.Context;
import android.content.res.Resources;
import android.database.DataSetObserver;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 超级简单适配器，具有ListView的优化功能，设置CheckBox互斥选中功能，设置View的监听功能，设置View的Tag功能
 *
 * @param <T>
 * @author liuhaichao
 */
public class SuperSimpleAdapter<T> extends BaseAdapter {
	/**
	 * 业务数据
	 */
	private List<T> mSrcData = new ArrayList<T>();
	/**
	 * 资源ID
	 */
	private int mResource;
	/**
	 * SpinnerAdapter的下拉列表项
	 */
	private int mDropDownResource;
	private LayoutInflater mInflater;

	private Context mContext;
	private Resources mRes;

	/**
	 * @param context
	 * @param resource 资源ID
	 */
	public SuperSimpleAdapter(Context context, @LayoutRes int resource) {
		mResource = mDropDownResource = resource;
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mContext = context;
		mRes = context.getResources();
	}

	@Override
	public int getCount() {
		return mSrcData.size();
	}

	@Override
	public T getItem(int position) {
		return mSrcData.get(position);
	}

	public int getIndexByData(T data) {
		for (int i = 0, size = mSrcData.size(); i < size; i++) {
			if (mSrcData.get(i).equals(data)) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * @see Adapter#getItemId(int)
	 */
	@Override
	public long getItemId(int position) {
		return position;
	}

	public Context getContext() {
		return mContext;
	}

	/**
	 * @see Adapter#getView(int, View, ViewGroup)
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		return createViewFromResource(position, convertView, parent, mResource);
	}

	@SuppressWarnings("unchecked")
	private View createViewFromResource(int position, View convertView, ViewGroup parent, int resource) {
		T data = null;
		if (position < getCount()) {
			// 如果存在业务数据,获取该position的业务数据
			data = getItem(position);
		}
		ViewHolder viewHolder;
		if (convertView == null) {
			convertView = mInflater.inflate(resource, null);
			viewHolder = new ViewHolder(convertView);
			convertView.setTag(viewHolder);
			// 做一些convertView第一次实例化后的操作
			doViewConvertViewIsNull(convertView, viewHolder, data, position);
		}
		viewHolder = (ViewHolder) convertView.getTag();
		doViewConvertViewNotNull(convertView, viewHolder, data, position);
		return convertView;
	}

	/**
	 * 该方法用于convertView不是null的时候设置View属性
	 *
	 * @param viewHolder
	 * @param data
	 * @param position
	 */
	protected void doViewConvertViewNotNull(View convertView, ViewHolder viewHolder, T data, int position) {

	}

	/**
	 * 该方法用于convertView为null的时候设置View属性
	 *
	 * @param viewHolder
	 * @param data
	 * @param position
	 */
	protected void doViewConvertViewIsNull(View convertView, ViewHolder viewHolder, T data, int position) {

	}

	/**
	 * <p>
	 * Sets the layout resource to create the drop down views.
	 * </p>
	 *
	 * @param resource the layout resource defining the drop down views
	 * @see #getDropDownView(int, View, ViewGroup)
	 */
	public void setDropDownViewResource(int resource) {
		this.mDropDownResource = resource;
	}

	/**
	 * 返回下拉列表项<br>
	 * 继承关系:public abstract class BaseAdapter implements ListAdapter,
	 * SpinnerAdapter<br>
	 * getDropDownView这个方法是SpinnerAdapter的
	 */
	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
//		return createDropDownViewFromResource(position, convertView, parent, mDropDownResource);//实现了以后替换下面的方法
		return getView(position, convertView, parent);
	}

	/**
	 * TODO 这是spinner用到的方法,有空实现一下
	 *
	 * @param position
	 * @param convertView
	 * @param parent
	 * @param dropDownResource
	 * @return
	 */
	private View createDropDownViewFromResource(int position, View convertView, ViewGroup parent, int dropDownResource) {
		return null;
	}

	protected void setViewToggleText(ToggleButton v, String text) {
		v.setText(text);
		v.setTextOn(text);
		v.setTextOff(text);
	}

	/**
	 * 解决4.0以上机型the observer is null的异常
	 */
	@Override
	public void unregisterDataSetObserver(DataSetObserver observer) {
		if (observer != null) {
			super.unregisterDataSetObserver(observer);
		}
	}

	/**
	 * 增加业务数据
	 *
	 * @param srcData
	 */
	public void appendData(Collection<T> srcData) {
		if (srcData != null) {
			this.mSrcData.addAll(srcData);
		}
	}

	/**
	 * 设置业务数据
	 *
	 * @param srcData
	 */
	public void setData(Collection<T> srcData) {
		if (srcData == null) {
			this.mSrcData.clear();
		} else {
			this.mSrcData = new ArrayList<T>(srcData);
		}
	}

	/**
	 * 添加业务数据
	 *
	 * @param data
	 */
	public void addData(int position, T data) {
		this.mSrcData.add(position, data);
	}

	/**
	 * 添加业务数据
	 *
	 * @param data
	 */
	public void addData(T data) {
		this.mSrcData.add(data);
	}

	/**
	 * 删除业务数据
	 *
	 * @param location
	 */
	public void removeData(int location) {
		this.mSrcData.remove(location);
	}

	/**
	 * 删除业务数据
	 *
	 * @param data
	 */
	public void removeData(T data) {
		this.mSrcData.remove(data);
	}

	public void clearData() {
		if (mSrcData != null) {
			mSrcData.clear();
		}
	}

	@SuppressWarnings("unchecked")
	public List<T> getDataClone() {
		return new ArrayList(mSrcData);
	}

	@Override
	public void notifyDataSetChanged() {
		super.notifyDataSetChanged();
	}

	/**
	 * 局部更新数据，调用一次getView()方法；Google推荐的做法
	 *
	 * @param listView 要更新的listview
	 * @param position 要更新的位置
	 */
	public void notifyDataSetChanged(ListView listView, int position) {
		//第一个可见的位置
		int firstVisiblePosition = listView.getFirstVisiblePosition();
		//最后一个可见的位置
		int lastVisiblePosition = listView.getLastVisiblePosition();

		//在看见范围内才更新，不可见的滑动后自动会调用getView方法更新
		if (position >= firstVisiblePosition && position <= lastVisiblePosition) {
			//获取指定位置view对象
			View view = listView.getChildAt(position - firstVisiblePosition);
			getView(position, view, listView);
		}
	}

}
