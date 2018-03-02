package com.tracy.ui;

/**
 * Created by angoo on 2018/1/31.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;



import com.firebase.client.ChildEventListener;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Tab2 extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab2, container, false);
        ListView list = (ListView) rootView.findViewById(R.id.ListView);
        final ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(getActivity(),
                        android.R.layout.simple_list_item_1);
        list.setAdapter(adapter);
        Firebase.setAndroidContext(getActivity());
        //將Firebase實體化後，把在Firebase官網上的資料庫網址貼上
        Firebase firebase = new Firebase("https://fir-1-2b746.firebaseio.com/User");
        //建立監聽事件，當資料庫當中的欄位被增加/刪除/移動等等就會進行觸發
        //當資料庫有欄位異動，就會同步刷新listview
        firebase.addChildEventListener(new ChildEventListener() {


            @Override
            public void onChildAdded(com.firebase.client.DataSnapshot dataSnapshot, String s) {
               // adapter.add((String) dataSnapshot.child(userUID).getKey());
            }

            @Override
            public void onChildChanged(com.firebase.client.DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(com.firebase.client.DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(com.firebase.client.DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        return rootView;
    }
}
