package com.juanfe.withapi.controladoras;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ControladoraBonos extends Fragment {
    private static final String TAG_B_USER = "usuario bonos";
    private static final String TAG_B_PASS = "pass bonos";
    Context context;
    String user,pass;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;

        if (getArguments() != null) {
            user = getArguments().getString(TAG_B_USER);
            pass = getArguments().getString(TAG_B_PASS);

        }
    }


    public static ControladoraBonos newInstance(String user, String pass) {

        Bundle args = new Bundle();
        args.putString(TAG_B_USER, user);
        args.putString(TAG_B_PASS, pass);
        ControladoraBonos fragment = new ControladoraBonos();
        fragment.setArguments(args);
        return fragment;
    }
}
