package com.yellowsoft.yellow.ui.adapters;

import android.content.Context;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.yellowsoft.yellow.R;
import com.yellowsoft.yellow.model.pojo.Post;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by subhankar on 10/11/2016.
 */
public class PostsListAdapter extends RecyclerView.Adapter<PostsListAdapter.ViewHolder> {

    private Context context;
    private List<Post> posts;

    public PostsListAdapter(Context context) {
        this.posts = new ArrayList<>();
        this.context = context;
    }

    public void addPosts(List<Post> newPosts) {
        posts.addAll(newPosts);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int position) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        viewHolder.postTitle.setText(posts.get(position).getPostBody());
        Picasso.with(context).load(posts.get(position).getImageUrl()).resize(320
                , 0).into(viewHolder.postBody);

    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.tvBody)
        TextView postTitle;
        @InjectView(R.id.ivFeedCenter)
        ImageView postBody;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }
    }
}
