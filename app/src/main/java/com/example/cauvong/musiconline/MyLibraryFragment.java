package com.example.cauvong.musiconline;

import android.app.TabActivity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TabHost;

/**
 * Created by tusss on 1/1/2018.
 */

public class MyLibraryFragment extends Fragment {
    public void MyLibraryFragment(){
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_library_tabhost, container, false);
        return view;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        addControl();
    }

    public void addControl(){
        TabHost tabHost = (TabHost) getView().findViewById(R.id.tabML);
        tabHost.setup();
        TabHost.TabSpec tab1 = tabHost.newTabSpec("t1");
        tab1.setIndicator("Genres");
        tab1.setContent(R.id.tabG);
        tabHost.addTab(tab1);

        TabHost.TabSpec tab2 = tabHost.newTabSpec("t2");
        tab2.setIndicator("Artists");
        tab2.setContent(R.id.tabA);
        tabHost.addTab(tab2);

        TabHost.TabSpec tab3 = tabHost.newTabSpec("t3");
        tab3.setIndicator("Albums");
        tab3.setContent(R.id.tabAlbums);
        tabHost.addTab(tab3);

        TabHost.TabSpec tab4 = tabHost.newTabSpec("t4");
        tab4.setIndicator("Songs");
        tab4.setContent(R.id.tabSongs);
        tabHost.addTab(tab4);
    }
}