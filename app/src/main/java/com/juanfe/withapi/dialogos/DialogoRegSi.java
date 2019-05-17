package com.juanfe.withapi.dialogos;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.juanfe.withapi.R;

public class DialogoRegSi extends DialogFragment {
    final static String TAG_MSG = "mensaje de registro";
    Boolean msg;
    Context context;
    OnDialogoRegListener odrl;
    int message;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder b = new AlertDialog.Builder(context);
        putMessage();
        b.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //todo buscar fragment login y borrar fragment registro
                odrl.onOkclick();
            }
        });
        b.setMessage(message);

        return b.show();
    }

    private int putMessage() {
        if (msg){
            message = R.string.okreg;


        }else {
            message=R.string.falloReg;

        }

        return message;
    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        odrl = (OnDialogoRegListener) context;

        if (getArguments()!=null){
            msg = getArguments().getBoolean(TAG_MSG);
        }
    }

    public static DialogoRegSi newInstance(Boolean msg) {

        Bundle b = new Bundle();
        b.putBoolean(TAG_MSG,msg);
        DialogoRegSi dialogo = new DialogoRegSi();
        dialogo.setArguments(b);
        return dialogo;
    }

    public interface OnDialogoRegListener{
        void onOkclick();

    }



}
