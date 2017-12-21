package com.liuhc.lib.adapter.listview;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 万能ExpandableListAdapter适配器
 * Created by liuhc on 2017/10/18.
 */

public abstract class AbstractExpandableListAdapter<GROUP, CHILD> extends BaseExpandableListAdapter {

	protected abstract void getChildView(ViewHolder holder, int groupPositon, int childPositon, boolean isLastChild, CHILD data);

	protected abstract void getGroupView(ViewHolder holder, int groupPositon, boolean isExpanded, GROUP data);

	@LayoutRes
	private int childResource;
	@LayoutRes
	private int groupResource;
	private Map<GROUP, List<CHILD>> dataMap = new LinkedHashMap<GROUP, List<CHILD>>();
	private Context context;

	public void clearData() {
		this.dataMap.clear();
	}

	public void add(GROUP group, CHILD child) {
		if (this.dataMap.get(group) == null) {
			this.dataMap.put(group, new ArrayList<CHILD>());
		}
		this.dataMap.get(group).add(child);
	}

	public void add(GROUP group, Collection<CHILD> child) {
		if (this.dataMap.get(group) == null) {
			this.dataMap.put(group, new ArrayList<CHILD>());
		}
		this.dataMap.get(group).addAll(child);
	}

	public AbstractExpandableListAdapter(Context context, @LayoutRes int childResource, @LayoutRes int groupResource) {
		this.context = context;
		this.childResource = childResource;
		this.groupResource = groupResource;
	}

	@Override
	public CHILD getChild(int groupPosition, int childPosition) {
		return this.dataMap.get(getGroup(groupPosition)).get(childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;
		if (null == convertView) {
			convertView = LayoutInflater.from(context).inflate(childResource, parent, false);
			viewHolder = new ViewHolder(convertView);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		getChildView(viewHolder, groupPosition, childPosition, isLastChild, getChild(groupPosition, childPosition));
		return convertView;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return getGroupCount() > 0 ? this.dataMap.get(getGroup(groupPosition)).size() : 0;
	}

	@Override
	public GROUP getGroup(int groupPosition) {
		Set<GROUP> groups = dataMap.keySet();
		Iterator<GROUP> iterator = groups.iterator();
		int i = 0;
		GROUP group;
		while (iterator.hasNext()) {
			group = iterator.next();
			if (i == groupPosition) {
				return group;
			}
			i++;
		}
		return null;
	}

	public int getChildCount(GROUP group) {
		return dataMap.get(group).size();
	}

	@Override
	public int getGroupCount() {
		return dataMap.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;
		if (null == convertView) {
			convertView = LayoutInflater.from(context).inflate(groupResource, parent, false);
			viewHolder = new ViewHolder(convertView);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		getGroupView(viewHolder, groupPosition, isExpanded, getGroup(groupPosition));
		return convertView;
	}

	@Override
	public boolean hasStableIds() {
		return true;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

}
