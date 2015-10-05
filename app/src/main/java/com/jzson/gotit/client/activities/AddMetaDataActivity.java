package com.jzson.gotit.client.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.jzson.gotit.client.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class AddMetaDataActivity extends Activity {

    public static final int REQUEST_ADD = 1;

    @InjectView(R.id.videoName)
    protected EditText videoName;

    @InjectView(R.id.videoUrl)
    protected EditText videoUrl;

    @InjectView(R.id.videoDuration)
    protected EditText videoDuration;

    @InjectView(R.id.addButton)
    protected Button addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_meta_data);

        ButterKnife.inject(this);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent result = new Intent();
                result.putExtra("name", videoName.getText().toString());
                result.putExtra("url", videoUrl.getText().toString());
                result.putExtra("duration", Long.parseLong(videoDuration.getText().toString()));
                setResult(Activity.RESULT_OK, result);
                finish();
            }
        });
    }
}
