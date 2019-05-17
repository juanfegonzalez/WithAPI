package com.juanfe.withapi.dialogos;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

public class DialogoConfirPass extends DialogFragment {
    private static final String TAG_SET_DIA_USER = "usuario dialogo";
    private static final String TAG_SET_DIA_PASS = "pass dialogo";
    Context context;
    String user,pass;
    //TODO hacer validacion de contraseña y dialogo de confirmacion o rechazo de peticion
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder b = new AlertDialog.Builder(context);
        return b.show();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        if (getArguments()!=null){
            user = getArguments().getString(TAG_SET_DIA_USER);
            pass = getArguments().getString(TAG_SET_DIA_PASS);

        }
    }

    public static DialogoConfirPass newInstance(String user, String pass) {

        Bundle args = new Bundle();
        args.putString(TAG_SET_DIA_USER,user);
        args.putString(TAG_SET_DIA_PASS,pass);

        DialogoConfirPass fragment = new DialogoConfirPass();
        fragment.setArguments(args);
        return fragment;
    }
}
