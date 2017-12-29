package com.daydaynote.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.daydaynote.R;

import java.util.ArrayList;
import java.util.List;

public class BlankFragment3 extends Fragment {
    private View view;
    private ListView mListviewTestf3;
    private List<String> mlist;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_blank_fragment3, container, false);
         mlist=new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            mlist.add(i+"JHGJHGJ");
        }
        initView(view);
        return view;
    }

    private void initView(View view) {
        mListviewTestf3 = (ListView) view.findViewById(R.id.listview_testf3);
        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,mlist);
        mListviewTestf3.setAdapter(arrayAdapter);
    }
}
