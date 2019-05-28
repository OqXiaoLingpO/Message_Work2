package com.example.message_work;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.ColorSpace;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

import org.litepal.LitePal;
import org.litepal.crud.LitePalSupport;
import org.litepal.exceptions.DataSupportException;
import org.litepal.tablemanager.Connector;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static List<Activity> activityList = new LinkedList();
    private List<Message> messageList;
    private MyAdapter madapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MainActivity.activityList.add(this);
        //Connector.getDatabase();
        //getList;
        messageList = getMessageList();

        final RecyclerView messageRv = findViewById(R.id.rv_main);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        madapter = new MyAdapter(this, messageList);
        messageRv.setLayoutManager(layoutManager);
        messageRv.setAdapter(madapter);
        madapter.setClickListener(new MyAdapter.onClickListener() {
            @Override
            public void onClick(int pos) {
                Intent intent = new Intent(MainActivity.this, editActivity.class);

                intent.putExtra("message", messageList.get(pos));//???????
                startActivity(intent);
            }

            @Override
            public void onLongClick(int pos) {
                //notifyItemRemoved(position);
                //notifyItemRangeChanged(position, data.size() - position);
                deleteData(pos);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        messageList = LitePal.findAll(Message.class);

        switch (item.getItemId()) {
            case R.id.menu_creat: {
                Intent intent = new Intent(MainActivity.this,editActivity.class);
                startActivity(intent);
            }
            break;
            case R.id.menu_exit:{
                exit();
            }
        }
        return true;
    }

    private List<Message> getMessageList(){
        //Connector.getDatabase();
        return LitePal.findAll(Message.class);
    }

    private void deleteData(final int pos){
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("delete");
        dialog.setMessage("请");
        dialog.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                LitePal.deleteAll(Message.class,"title = ?",messageList.get(pos).getTitle());
                messageList.remove(pos);
                madapter.notifyItemRemoved(pos);//不加这句，删除时会报错，但能成功删除，加上后，无错
                madapter.notifyItemChanged(pos);
            }
        });
       dialog.show();
    }

    public void exit()
    {

        for(Activity act:activityList)
        {
            act.finish();
        }

        System.exit(0);

    }

    @Override
    protected void onPause() {
        super.onPause();
        messageList = LitePal.findAll(Message.class);
    }
}
