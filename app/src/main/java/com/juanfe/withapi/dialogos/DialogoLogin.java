package com.juanfe.withapi.dialogos;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.appcompat.app.AlertDialog;

import com.juanfe.withapi.R;

public class DialogoLogin extends DialogFragment {

    Context context;
    Boolean ok;
    final static String TAG_MSG = "mensaje";


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder b = new AlertDialog.Builder(context);

        if (ok){
            b.setMessage(R.string.loginSi);
        }else {
            b.setMessage(R.string.loginNo);
        }
        return b.show();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context=context;
        if (getArguments()!=null){
            ok = getArguments().getBoolean(TAG_MSG);
        }
    }

    public static DialogoLogin newInstance(Boolean ok) {

        Bundle b = new Bundle();
        b.putBoolean(TAG_MSG,ok);
        DialogoLogin dia = new DialogoLogin();
        dia.setArguments(b);
        return dia;
    }
}
