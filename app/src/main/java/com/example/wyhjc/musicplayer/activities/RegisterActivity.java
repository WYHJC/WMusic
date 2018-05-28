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

import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;

public class RegisterActivity extends AppCompatActivity {

    private EditText mPhoneEdit;
    private EditText mSmsEdit;
    private String phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initView();
    }

    private void initView(){
        Toolbar toolbar = (Toolbar)findViewById(R.id.register_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }

        mPhoneEdit = (EditText)findViewById(R.id.register_phone_edit);
        mSmsEdit = (EditText)findViewById(R.id.register_sms_edit);

        Button sendBtn = (Button)findViewById(R.id.send_sms_btn);
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                phone = mPhoneEdit.getText().toString();
                if(phone.equals("")){
                    Toast.makeText(RegisterActivity.this, "Phone can not be empty", Toast.LENGTH_SHORT).show();
                }else{
                    BmobSMS.requestSMSCode(phone,"WMusic SMS", new QueryListener<Integer>() {
                        @Override
                        public void done(Integer smsId,BmobException ex) {
                            if(ex==null){//验证码发送成功
                                //Log.i("smile", "短信id："+smsId);//用于后续的查询本次短信发送状态
                                Toast.makeText(RegisterActivity.this, "Verification code has been sent", Toast.LENGTH_SHORT).show();
                            }else{
                                ex.printStackTrace();
                                Toast.makeText(RegisterActivity.this, phone, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        Button nextStepBtn = (Button)findViewById(R.id.next_step_btn);
        nextStepBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String smsCode = mSmsEdit.getText().toString();
                BmobSMS.verifySmsCode(phone, smsCode, new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if(e==null){
                            Intent intent = new Intent(RegisterActivity.this, PasswordActivity.class);
                            intent.putExtra("phone", phone);
                            startActivity(intent);
                        }else{
                            Toast.makeText(RegisterActivity.this, "Incorrect verification code" ,Toast.LENGTH_SHORT).show();
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
