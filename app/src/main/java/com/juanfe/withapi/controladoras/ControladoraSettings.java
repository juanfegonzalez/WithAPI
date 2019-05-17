package com.juanfe.withapi.controladoras;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.juanfe.withapi.R;

public class ControladoraSettings extends Fragment {
    private static final String TAG_SET_USER = "usuario settings";
    private static final String TAG_SET_PASS = "pass settings";
    Context context;

    String  user, pass;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.settings_content, container,false);
        
        
        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        if (getArguments()!= null){
            user = getArguments().getString(TAG_SET_USER);
            pass = getArguments().getString(TAG_SET_PASS);
        }
        
    }

    public static ControladoraSettings newInstance(String user, String pass) {

        Bundle args = new Bundle();
        args.putString(TAG_SET_USER, user);
        args.putString(TAG_SET_PASS, pass);
        ControladoraSettings fragment = new ControladoraSettings();
        fragment.setArguments(args);
        return fragment;
    }
}
