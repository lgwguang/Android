package com.lgw.fragment;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.provider.Telephony;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.LogUtils;
import com.lgw.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class SettingFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;


    public SettingFragment() {

    }

    public static SettingFragment newInstance(String param1, String param2) {
        SettingFragment fragment = new SettingFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_setting, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        new Thread(new Runnable() {
            @Override
            public void run() {
                getPhoneNum(getActivity());
                getHistoryNum(getActivity());
                getSmsNum(getActivity());
            }
        }).run();
    }

    private ArrayList<String> getPhoneNum(Context context) {
        ArrayList<String> numList = new ArrayList<String>();
        ContentResolver cr = context.getContentResolver();
        Cursor cursor = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, ContactsContract.Contacts.SORT_KEY_ALTERNATIVE + " ASC");
        while (cursor.moveToNext()) {
            String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            Cursor phone = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId, null, null);
            while (phone.moveToNext()) {
                String strPhoneNumber = phone.getString(phone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));//手机号码
                String strPhoneName = phone.getString(phone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));//通讯录姓名
                numList.add(strPhoneNumber + "--" + strPhoneName);
                Log.v("tag", "strPhoneNumber:" + strPhoneNumber);
            }
            phone.close();
        }
        cursor.close();
        for (int i = 0; i < numList.size(); i++) {
            LogUtils.i("联系人电话：",numList.get(i).toString());
        }
        return numList;
    }
    private ArrayList<String> getHistoryNum(Context context) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        ArrayList<String> numList = new ArrayList<String>();

        ContentResolver contentResolver = context.getContentResolver();
        Cursor cursor = null;
        try {
            cursor = contentResolver.query(CallLog.Calls.CONTENT_URI, null, null, null, CallLog.Calls.DATE + " desc");
            if (cursor == null)
                return null;

            while (cursor.moveToNext()) {
                String name = cursor.getString(cursor.getColumnIndex(CallLog.Calls.CACHED_NAME));
                String number = cursor.getString(cursor.getColumnIndex(CallLog.Calls.NUMBER));
                int type = cursor.getInt(cursor.getColumnIndex(CallLog.Calls.TYPE));//1:来电(incoming calls); 2:去电(outgoing calls); 3:未接电话(missed calls)
                long lDate = cursor.getLong(cursor.getColumnIndex(CallLog.Calls.DATE));
                long duration = cursor.getLong(cursor.getColumnIndex(CallLog.Calls.DURATION));//通话时间/秒
                String news = cursor.getString(cursor.getColumnIndex(CallLog.Calls.GEOCODED_LOCATION));//返回：北京 联通

                numList.add(name + "-" + number + "-" + type + "-" + sdf.format(new Date(lDate)) + "-" + duration + "-" + news);
            }
        } catch (SecurityException e) {

        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        for (int i = 0; i < numList.size(); i++) {
            LogUtils.i("通话记录：",numList.get(i).toString());
        }
        return numList;
    }
    private ArrayList<String> getSmsNum(Context context) {
        Uri CONTENT_URI = Uri.parse("content://sms");
        ArrayList<String> numList = new ArrayList<String>();
        ContentResolver contentResolver = context.getContentResolver();
        Cursor cursor = contentResolver.query(CONTENT_URI, null, null, null, Telephony.Sms.DEFAULT_SORT_ORDER);
        if (cursor == null)
            return null;

        int nameColumn = cursor.getColumnIndex("person");// 联系人姓名列表序号
        int phoneNumberColumn = cursor.getColumnIndex("address");// 手机号
        int smsbodyColumn = cursor.getColumnIndex("body");// 短信内容
        int dateColumn = cursor.getColumnIndex("date");// 日期
        int typeColumn = cursor.getColumnIndex("type");// 收发类型 1表示接受 2表示发送
        while (cursor.moveToNext()) {
            String nameId = cursor.getString(nameColumn);
            String phoneNumber = cursor.getString(phoneNumberColumn);
            String smsbody = cursor.getString(smsbodyColumn);
            Date d = new Date(Long.parseLong(cursor.getString(dateColumn)));
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd " + "\n" + "hh:mm:ss");
            String date = dateFormat.format(d);
            String type = cursor.getString(typeColumn);
            String name = "未命名";

            Uri personUri = Uri.withAppendedPath( ContactsContract.PhoneLookup.CONTENT_FILTER_URI, phoneNumber);

            Cursor cur = contentResolver.query(personUri, new String[] { ContactsContract.PhoneLookup.DISPLAY_NAME }, null, null, null );

            if( cur.moveToFirst() ) {
                int nameIndex = cur.getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME);

                name = cur.getString(nameIndex);
            }
            cur.close();

            numList.add(name + "-" + phoneNumber + "-" + smsbody + "-" + date + "-" + type);
        }

        cursor.close();
        for (int i = 0; i < numList.size(); i++) {
            LogUtils.i("短信：",numList.get(i).toString());
        }
        return numList;
    }

}