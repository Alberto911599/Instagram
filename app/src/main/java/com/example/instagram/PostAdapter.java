package com.example.instagram;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.ParseException;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {
    private List<Post> posts;
    private LayoutInflater inflater;

    public PostAdapter(Context context, List<Post> postList){
        inflater = LayoutInflater.from(context);
        this.posts = postList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View postView = inflater.inflate(R.layout.item_sample, parent, false);
        return new ViewHolder(postView, this);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post post = posts.get(position);
        holder.tvUsername.setText(post.getUser().getString("username"));
        holder.tvDescription.setText(post.getDescription());
        try {
            holder.ivPicture.setImageBitmap(BitmapFactory.decodeFile(post.getImage().getFile().getAbsolutePath()));
        } catch (ParseException e) {
            e.printStackTrace();
            Log.d("Timeline", "Could not load image");
        }
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public final TextView tvUsername;
        public final TextView tvDescription;
        public final ImageView ivPicture;
        final PostAdapter pAdapter;

        public ViewHolder(View itemView, PostAdapter adapter){
            super(itemView);
            tvUsername = itemView.findViewById(R.id.user_item);
            tvDescription = itemView.findViewById(R.id.description_item);
            ivPicture = itemView.findViewById(R.id.ivItem);
            this.pAdapter = adapter;
        }
    }
}
