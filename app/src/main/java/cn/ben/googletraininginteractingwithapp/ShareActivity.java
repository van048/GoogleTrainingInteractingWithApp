package cn.ben.googletraininginteractingwithapp;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import cn.ben.googletraininginteractingwithapp.databinding.ActivityShareBinding;

public class ShareActivity extends AppCompatActivity {

    public static final int RESULT_COLOR_RED = 2;
    @SuppressWarnings("unused")
    public View.OnClickListener returnResultOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
//            returnResult();
            returnResultInteger();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityShareBinding dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_share);
        dataBinding.setActivity(this);

        // Get the intent that started this activity
        Intent intent = getIntent();
        switch (intent.getAction()) {
            case Intent.ACTION_SENDTO:
                Toast.makeText(this, "ACTION_SENDTO", Toast.LENGTH_SHORT).show();
                break;
            case Intent.ACTION_SEND:
                String type = intent.getType();
                if (type != null) {
                    // Figure out what to do based on the intent type
                    if (type.contains("image/")) {
                        // Handle intents with image data ...
                        Toast.makeText(this, "ACTION_SEND image data", Toast.LENGTH_SHORT).show();
                    } else if (intent.getType().equals("text/plain")) {
                        // Handle intents with text ...
                        Toast.makeText(this, "ACTION_SEND text", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "ACTION_SEND type is null!", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    @SuppressWarnings("unused")
    private void returnResult() {
        // Create intent to deliver some kind of result data
        Intent result = new Intent("com.example.RESULT_ACTION", Uri.parse("content://result_uri"));
        setResult(Activity.RESULT_OK, result);
        finish();
    }

    private void returnResultInteger() {
        // deliver an integer
        setResult(RESULT_COLOR_RED);
        finish();
    }
}
