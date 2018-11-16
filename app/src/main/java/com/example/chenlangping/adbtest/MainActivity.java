package com.example.chenlangping.adbtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final String COMMAND = "pm list packages -f -3";
    private static final String ERROR = "error";
    private Button button;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
        button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String content = runADBCommand();
                if (content != ERROR) {
                    textView.setText(content);
                    Toast.makeText(MainActivity.this, "刷新成功", Toast.LENGTH_SHORT).show();
                } else {
                    textView.setText(ERROR);
                }
            }
        });

    }


    private String runADBCommand() {
        BufferedReader reader = null;
        try {
            Process process = Runtime.getRuntime().exec(COMMAND);
            reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuffer output = new StringBuffer();
            int read;
            char[] buffer = new char[1024];
            while ((read = reader.read(buffer)) > 0) {
                output.append(buffer, 0, read);
            }
            reader.close();
            Log.d(TAG, "runADBCommand: success");
            Log.d(TAG, "runADBCommand: \n" + output.toString());
            return output.toString();
        } catch (IOException e) {
            // TODO 异常处理
            e.printStackTrace();
            return ERROR;
        }
    }

}
