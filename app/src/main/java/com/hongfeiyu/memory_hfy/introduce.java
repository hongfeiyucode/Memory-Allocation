package com.hongfeiyu.memory_hfy;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by 红绯鱼 on 2015/11/2 0002.
 */
public class introduce extends Fragment{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View introduce = inflater.inflate(R.layout.introduction_layout,container,false);
        return introduce;
    }
}
