package com.example.instagram;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.ParseException;

import org.ocpsoft.prettytime.PrettyTime;

public class PostDetails extends AppCompatActivity {

    private Post post;
    private TextView tvUser;
    private TextView tvTimestamp;
    private TextView tvDescription;
    private ImageView ivDetail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_details);

        Intent intent = getIntent();
        PrettyTime pt = new PrettyTime();
        post = intent.getParcelableExtra(Post.class.getSimpleName());
        tvUser = findViewById(R.id.user_detail);
        tvTimestamp = findViewById(R.id.timestamp_detail);
        tvDescription = findViewById(R.id.description_detail);
        ivDetail = findViewById(R.id.ivDetail);
        tvUser.setText(post.getUser().getString("username"));
        tvTimestamp.setText(pt.format(post.getTimestamp()));
        post.getTimestamp();
        tvDescription.setText(post.getDescription());
        try {
            ivDetail.setImageBitmap(BitmapFactory.decodeFile(post.getImage().getFile().getAbsolutePath()));
        } catch (ParseException e) {
            e.printStackTrace();
            Log.d("Timeline", "Could not load image");
        }
    }
}
