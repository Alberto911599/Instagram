package com.example.instagram;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

//@RuntimePermissions
public class TimelineActivity extends AppCompatActivity {

    private static final int MAX_SIZE = 20;
    private String TIMELINE_TAG = "Timeline";
    private ArrayList<Post> arrPosts;
    private RecyclerView rvTimeline;
    private PostAdapter postAdapter;
    private SwipeRefreshLayout swipeContainer;
    private ImageButton btnCamera;

    private BottomNavigationView bottomNavigationView;


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
        btnCamera = findViewById(R.id.btnTakePic);
        bottomNavigationView = findViewById(R.id.bottom_navigation_timeline);

        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);


        ImageView myImage =  findViewById(R.id.ivToolbarLogo);
        Glide.with(this)
                .load("https://1.bp.blogspot.com/-5cSNkUlM6Ns/WEfffCkKw7I/AAAAAAAABMo/odiYMhl33M8nav4HjLnSQB1DeIjQgcdJQCLcB/s1600/instagram-1594387_960_720.png")
                .into(myImage);


        // Lookup the swipe container view
        swipeContainer =  findViewById(R.id.swipeContainer);
        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
                queryPosts();
            }
        });
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TimelineActivity.this, HomeActivity.class);
                intent.putExtra("camera/gallery", true);
                startActivity(intent);
            }
        });

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.homeItem:
                        rvTimeline.scrollToPosition(0);
                        return true;
                    case R.id.addItem:
                        Intent intent = new Intent(TimelineActivity.this, HomeActivity.class);
                        intent.putExtra("camera/gallery", false);
                        startActivity(intent);
                        return true;
                    default:
                        Toast.makeText(TimelineActivity.this, "Click!!", Toast.LENGTH_LONG).show();
                        return true;
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.timeline_menu, menu);
        return true;
    }

    private void queryPosts() {
        ParseQuery<Post> query = new ParseQuery<Post>(Post.class);
        query.orderByDescending("Born");
        query.include(Post.KEY_USER);
        query.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> posts, ParseException e) {
                if(e != null){
                    Log.e(TIMELINE_TAG, "Error with query");
                    e.printStackTrace();
                    return;
                }
                postAdapter.clear();;
                for(int i = 0; i < MAX_SIZE && i < posts.size(); i++){
                    arrPosts.add(posts.get(i));
                    postAdapter.notifyItemInserted(arrPosts.size()-1);
                }
                swipeContainer.setRefreshing(false);
                rvTimeline.scrollToPosition(0);
            }
        });
    }
}
