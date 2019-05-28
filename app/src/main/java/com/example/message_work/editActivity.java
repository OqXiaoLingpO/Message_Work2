package com.example.message_work;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;

public class editActivity extends AppCompatActivity {
    private EditText mMessageTitleEt;
    private EditText mMessageContenEt;
    private static int flag;
    private Message message;
    private Button mBtnback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        MainActivity.activityList.add(this);

        mMessageTitleEt = findViewById(R.id.lo_title);
        mMessageContenEt = findViewById(R.id.lo_content);
        final Intent intent = getIntent();

        flag = 0;//需要一个初始化的值

        if (intent.hasExtra("message")){
            flag = 1;
            message = (Message) intent.getSerializableExtra("message");
            mMessageTitleEt.setText(message.getTitle());
            mMessageContenEt.setText(message.getContent());
        }

        mBtnback = findViewById(R.id.btn_back);
        mBtnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertOrUpdate();
                Intent intent1 = new Intent(editActivity.this, MainActivity.class);
                startActivity(intent1);
            }
        });

    }

    private void insertOrUpdate(){
        String messageTitle = mMessageTitleEt.getText().toString();
        String messageContent = mMessageContenEt.getText().toString();
        if(flag == 0){
            if(!TextUtils.isEmpty(messageTitle)||!TextUtils.isEmpty(messageContent)){
                Message message = new Message();
                message.setTitle(messageTitle);
                message.setContent(messageContent);
                //message.assignBaseObjId();
                message.save();
            }
        }else if (flag == 1){
            if(TextUtils.isEmpty(messageTitle)&&TextUtils.isEmpty(messageContent)){

            }else {//改错，用原本就有的message保存
                message.setTitle(messageTitle);
                message.setContent(messageContent);
                message.saveOrUpdate();
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        insertOrUpdate();
    }
}
