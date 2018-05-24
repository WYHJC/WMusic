package com.example.wyhjc.musicplayer.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.wyhjc.musicplayer.R;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private Button mRegisterBtn;
    private Button mLoginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mRegisterBtn = (Button)findViewById(R.id.register_btn);
        mLoginBtn = (Button)findViewById(R.id.login_btn);
        mRegisterBtn.setOnClickListener(this);
        mLoginBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.register_btn:
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                break;
            case R.id.login_btn:
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                break;
        }
    }
}
