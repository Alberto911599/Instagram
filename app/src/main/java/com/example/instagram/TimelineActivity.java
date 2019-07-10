package com.example.instagram;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class TimelineActivity extends AppCompatActivity {

    private static final int MAX_SIZE = 20;
    private String TIMELINE_TAG = "Timeline";
    private ArrayList<Post> arrPosts;
    private RecyclerView rvTimeline;
    private PostAdapter postAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);


        arrPosts = new ArrayList<>();
        queryPosts();
        rvTimeline = findViewById(R.id.rvTimeline);
        postAdapter = new PostAdapter(this, arrPosts);
        rvTimeline.setAdapter(postAdapter);
        rvTimeline.setLayoutManager(new LinearLayoutManager(this));

        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);



        ImageView myImage =  findViewById(R.id.ivToolbarLogo);
        Glide.with(this)
                .load("https://1.bp.blogspot.com/-5cSNkUlM6Ns/WEfffCkKw7I/AAAAAAAABMo/odiYMhl33M8nav4HjLnSQB1DeIjQgcdJQCLcB/s1600/instagram-1594387_960_720.png")
                .into(myImage);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.timeline_menu, menu);
        return true;
    }

    private void queryPosts() {
        ParseQuery<Post> query = new ParseQuery<Post>(Post.class);
        query.include(Post.KEY_USER);
        query.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> posts, ParseException e) {
                if(e != null){
                    Log.e(TIMELINE_TAG, "Error with query");
                    e.printStackTrace();
                    return;
                }
                for(int i = 0; i < MAX_SIZE && i < posts.size(); i++){
                    arrPosts.add(posts.get(i));
                    postAdapter.notifyItemInserted(arrPosts.size()-1);
//                    Log.d(TIMELINE_TAG, "Post: " + post.getDescription() + ", username: " + post.getUser().getUsername());
                }
            }
        });
    }
}
