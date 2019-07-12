package com.example.instagram;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseException;

import java.util.List;


public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {
    final String KEY_POST = Post.class.getSimpleName();
    private List<Post> posts;
    private LayoutInflater inflater;
    private Context context;

    public PostAdapter(Context context, List<Post> postList){
        inflater = LayoutInflater.from(context);
        this.posts = postList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        context = parent.getContext();
        View postView = inflater.inflate(R.layout.item_sample, parent, false);
        return new ViewHolder(postView, this);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post post = posts.get(position);
        holder.tvUsername.setText(post.getUser().getString("username"));
        holder.tvDescription.setText(post.getDescription());
        holder.tvLikes.setText(post.getLikes() + " likes");
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

    // Clean all elements of the recycler
    public void clear() {
        posts.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(List<Post> list) {
        posts.addAll(list);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final TextView tvUsername;
        public final TextView tvDescription;
        public final TextView tvLikes;
        public final ImageView ivPicture;
        final PostAdapter pAdapter;
        AppCompatImageButton btnDetails;
        AppCompatImageButton btnLike;

        public ViewHolder(View itemView, final PostAdapter adapter) {
            super(itemView);
            tvUsername = itemView.findViewById(R.id.user_item);
            tvDescription = itemView.findViewById(R.id.description_item);
            tvLikes = itemView.findViewById(R.id.tvLikes);
            ivPicture = itemView.findViewById(R.id.ivItem);
            btnDetails = itemView.findViewById(R.id.btnDetails);
            btnLike = itemView.findViewById(R.id.btnLike);
            this.pAdapter = adapter;
            btnDetails.setOnClickListener(this);
            btnLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    Post post = posts.get(position);
                    post.setLikes(post.getLikes()+1);
                    adapter.onBindViewHolder(ViewHolder.this, position);
                }
            });
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if(position != RecyclerView.NO_POSITION){
                Post post = posts.get(position);
                Intent intent = new Intent(context, PostDetails.class);
                intent.putExtra(KEY_POST, post);
                // show the activity
                context.startActivity(intent);
            }
            else{
                Toast.makeText(context, "Invalid position", Toast.LENGTH_LONG);
            }
        }
    }
}
