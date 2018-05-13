package com.demolistiview.adapters;

import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.demolistiview.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Tapan Kshatriya on 5/12/2018.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private List<String> imageList;
    private Picasso mPicasso;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewPosition, textViewImageLink;
        public CircleImageView circleImageView;
        public ImageView squareImageView;

        public MyViewHolder(View view) {
            super(view);
            textViewPosition = view.findViewById(R.id.textViewPosition);
            textViewImageLink = view.findViewById(R.id.textViewImageLink);
            circleImageView = view.findViewById(R.id.circleImageView);
            squareImageView = view.findViewById(R.id.squareImageView);
        }
    }


    public RecyclerViewAdapter(List<String> imageList) {
        this.imageList = imageList;
        mPicasso = Picasso.get();
        mPicasso.setIndicatorsEnabled(true);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_image_list, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
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

    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }
}