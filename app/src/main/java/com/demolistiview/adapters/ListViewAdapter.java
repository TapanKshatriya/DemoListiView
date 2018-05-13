package com.demolistiview.adapters;

import android.app.Activity;
import android.os.Build;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.demolistiview.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Tapan Kshatriya on 5/12/2018.
 */

public class ListViewAdapter extends BaseAdapter {
    Activity activity;
    LayoutInflater inflater;
    private List<String> imageList;
    private Picasso mPicasso;


    public ListViewAdapter(Activity activity, List<String> imageList) {
        this.activity = activity;
        inflater = LayoutInflater.from(activity);
        this.imageList = imageList;
        mPicasso = Picasso.get();
        mPicasso.setIndicatorsEnabled(true);
    }

    @Override
    public int getCount() {
        return imageList.size();
    }

    @Override
    public String getItem(int position) {
        return imageList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.row_image_list, null);
            holder.textViewPosition = view.findViewById(R.id.textViewPosition);
            holder.textViewImageLink = view.findViewById(R.id.textViewImageLink);
            holder.circleImageView = view.findViewById(R.id.circleImageView);
            holder.squareImageView = view.findViewById(R.id.squareImageView);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.textViewPosition.setText("Position : " + (position + 1));
        holder.textViewImageLink.setClickable(true);
        holder.textViewImageLink.setMovementMethod(LinkMovementMethod.getInstance());
        String text = "<a href='" + imageList.get(position) + "'> " + imageList.get(position) + " </a>";
        if (Build.VERSION.SDK_INT >= 24) {
            holder.textViewImageLink.setText(Html.fromHtml(text, Html.FROM_HTML_MODE_LEGACY));
        } else {
            holder.textViewImageLink.setText(Html.fromHtml(text));
        }

        if (position % 2 == 0) {
            holder.circleImageView.setVisibility(View.VISIBLE);
            holder.squareImageView.setVisibility(View.GONE);
            mPicasso.load(imageList.get(position)).placeholder(R.drawable.ic_launcher).error(R.drawable.ic_launcher).into(holder.circleImageView);
        } else {
            holder.circleImageView.setVisibility(View.GONE);
            holder.squareImageView.setVisibility(View.VISIBLE);
            mPicasso.load(imageList.get(position)).placeholder(R.drawable.ic_launcher).error(R.drawable.ic_launcher).into(holder.squareImageView);
        }

        return view;
    }

    public class ViewHolder {
        TextView textViewPosition, textViewImageLink;
        CircleImageView circleImageView;
        ImageView squareImageView;
    }
}
