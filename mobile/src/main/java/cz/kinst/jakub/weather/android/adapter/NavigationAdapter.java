package cz.kinst.jakub.weather.android.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cz.kinst.jakub.weather.android.R;

/**
 * Adapter used in navigation drawer
 * <p>
 * Created by jakubkinst on 04/04/15.
 */
public class NavigationAdapter extends ArrayAdapter<NavigationAdapter.NavigationItem> {
	private static final int LAYOUT = R.layout.item_navigation;


	public NavigationAdapter(Context context, List<NavigationItem> objects) {
		super(context, LAYOUT, objects);
	}


	@Override
	public View getView(final int position, View view, ViewGroup parent) {
		ViewHolder row;
		if (view == null) {
			view = LayoutInflater.from(getContext()).inflate(LAYOUT, parent, false);
			row = new ViewHolder(view);
			view.setTag(row);
		} else {
			row = (ViewHolder) view.getTag();
		}

		row.title.setText(getItem(position).title);
		row.icon.setImageResource(getItem(position).iconResource);
		return view;
	}


	public static class ViewHolder {
		@InjectView(R.id.navigation_title)
		public TextView title;
		@InjectView(R.id.navigation_icon)
		public ImageView icon;


		public ViewHolder(View v) {
			ButterKnife.inject(this, v);
		}
	}

	public static class NavigationItem {
		public String title;
		public int iconResource;


		public NavigationItem(String title, int iconResource) {
			this.title = title;
			this.iconResource = iconResource;
		}
	}
}
