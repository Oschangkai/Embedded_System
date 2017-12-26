package com.yzu.itac.triptaotwo;

/**
 * Created by man1ax on 26/12/2017.
 */

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class siteTab extends Fragment {

    ListView lsv;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.site_tab, container, false);
        lsv = (ListView)findViewById(R.id.lsv);
        return rootView;
    }
}
