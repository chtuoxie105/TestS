package com.example.testwifi.camera;

import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;

import com.example.testwifi.R;
import com.example.testwifi.recycle.MyRecyAdapter;

import java.util.ArrayList;

public class PhoneActivity extends AppCompatActivity {
    RecyclerView recyclerView = null;
    private PhoneAdapter recyAdapter;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            ArrayList<PhonePeopleBean> list = (ArrayList<PhonePeopleBean>) msg.obj;
            recyAdapter.setData(list);
            recyAdapter.notifyDataSetChanged();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);
        recyclerView = findViewById(R.id.test_phone_recycleview);
        LinearLayoutManager layoutmanager = new LinearLayoutManager(this);
        layoutmanager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutmanager);
        recyclerView.addItemDecoration(new MyItemDecoration());
        recyclerView.setAdapter(recyAdapter = new PhoneAdapter());
        getData();
    }

    private void getData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                getContacts();
            }
        }).start();
    }

    private void getContacts() {
        ArrayList<PhonePeopleBean> list = new ArrayList<>();
        //联系人的Uri，也就是content://com.android.contacts/contacts
        Uri uri = ContactsContract.Contacts.CONTENT_URI;
        //指定获取_id和display_name两列数据，display_name即为姓名
        String[] projection = new String[]{
                ContactsContract.Contacts._ID,
                ContactsContract.Contacts.DISPLAY_NAME
        };
        //根据Uri查询相应的ContentProvider，cursor为获取到的数据集
        Cursor cursor = this.getContentResolver().query(uri, null, null, null, null);
        if (cursor != null && cursor.getCount() > 0) {
            int contactIdIndex = 0;
            int nameIndex = 0;
            if (cursor.getCount() > 0) {
                //获取联系人的ID
                contactIdIndex = cursor.getColumnIndex(ContactsContract.Contacts._ID);
                //获取联系人的姓名
                nameIndex = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
            }
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    PhonePeopleBean bean = new PhonePeopleBean();
                    String id = cursor.getString(contactIdIndex);
                    //获取姓名
                    String name = cursor.getString(nameIndex);
                    //指定获取NUMBER这一列数据
                    String[] phoneProjection = new String[]{
                            ContactsContract.CommonDataKinds.Phone.NUMBER
                    };
                    Log.e("11", "name;;;" + name);
                    if (TextUtils.isEmpty(name)) {
                        continue;
                    }
                    bean.setName(name);
                    //根据联系人的ID获取此人的电话号码
                    Cursor phonesCusor = this.getContentResolver().query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + id,
                            null,
                            null);
                    if (phonesCusor == null) return;
                    //因为每个联系人可能有多个电话号码，所以需要遍历
                    String num = "";
                    int phoneIndex = 0;
                    if (phonesCusor.getCount() > 0) {
                        phoneIndex = phonesCusor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                    }
                    if (phonesCusor != null && phonesCusor.moveToFirst()) {
                        do {
                            num = phonesCusor.getString(phoneIndex).replaceAll(" ", "");
                            Log.e("11", "number;;;" + num);
                            bean.setPhone(num);
                        } while (TextUtils.isEmpty(num));
                    }
                    if (TextUtils.isEmpty(name) || TextUtils.isEmpty(num)) {
                        break;
                    }
                    list.add(bean);
                } while (cursor.moveToNext());
            }
        }
        if (handler != null) {
            Message msg = handler.obtainMessage();
            msg.obj = list;
            handler.sendMessage(msg);
        }
    }

    @Override
    protected void onDestroy() {
        handler.removeCallbacksAndMessages(null);
        handler = null;
        super.onDestroy();
    }
}
