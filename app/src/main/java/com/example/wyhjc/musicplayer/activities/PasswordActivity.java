package com.example.wyhjc.musicplayer.activities;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.wyhjc.musicplayer.R;
import com.example.wyhjc.musicplayer.model.User;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class PasswordActivity extends AppCompatActivity {
    private EditText mPasswordEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);
        String phone = getIntent().getExtras().get("phone").toString();
        initView(phone);
    }

    private void initView(final String phone){
        Toolbar toolbar = (Toolbar)findViewById(R.id.password_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }

        mPasswordEdit = (EditText)findViewById(R.id.register_password_edit);

        Button passwordRegisterBtn = (Button)findViewById(R.id.password_register_btn);
        passwordRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User user = new User();
                user.setPhone(phone);
                user.setPassword(mPasswordEdit.getText().toString());
                user.save(new SaveListener<String>() {
                    @Override
                    public void done(String s, BmobException e) {
                        if(e==null){
                            Toast.makeText(PasswordActivity.this, "Register succeed", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(PasswordActivity.this, LoginActivity.class));
                        }else{
                            Toast.makeText(PasswordActivity.this, "Register failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle item selection
        switch (item.getItemId()) {
            case android.R.id.home: //Menu icon
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
