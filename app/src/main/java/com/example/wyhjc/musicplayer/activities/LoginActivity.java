package com.example.wyhjc.musicplayer.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.wyhjc.musicplayer.R;
import com.example.wyhjc.musicplayer.manager.UserManager;
import com.example.wyhjc.musicplayer.model.User;
import com.example.wyhjc.musicplayer.util.MD5Util;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private Button mRegisterBtn;
    private Button mLoginBtn;
    private EditText mPhoneEdit;
    private EditText mPasswordEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mRegisterBtn = (Button)findViewById(R.id.register_btn);
        mLoginBtn = (Button)findViewById(R.id.login_btn);
        mRegisterBtn.setOnClickListener(this);
        mLoginBtn.setOnClickListener(this);
        mPhoneEdit = (EditText)findViewById(R.id.login_phone_edit);
        mPasswordEdit = (EditText)findViewById(R.id.login_password_edit);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.register_btn:
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                break;
            case R.id.login_btn:
                String phone = MD5Util.encodeToHex(mPhoneEdit.getText().toString());
                String password = MD5Util.encodeToHex(mPasswordEdit.getText().toString());
                BmobQuery<User> query = new BmobQuery<User>();
                query.addWhereEqualTo("phone", phone);
                query.addWhereEqualTo("password", password);
                query.findObjects(new FindListener<User>() {
                    @Override
                    public void done(List<User> list, BmobException e) {
                        if(e==null){
                            UserManager userManager = UserManager.getInstance();
                            userManager.setUser(list.get(0));
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        }
                        else
                            Toast.makeText(LoginActivity.this, "Wrong user name or password", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
        }
    }
}
