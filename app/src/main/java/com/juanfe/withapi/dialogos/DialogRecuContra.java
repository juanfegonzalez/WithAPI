package com.juanfe.withapi.dialogos;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.juanfe.withapi.R;

public class DialogRecuContra extends DialogFragment {
    Context context;
    EditText edit;
    OnDialogoRecuListener onDialogoRecuListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context =context;
        onDialogoRecuListener = (OnDialogoRecuListener) context;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder b = new AlertDialog.Builder(context);
        View v = LayoutInflater.from(context).inflate(R.layout.dialog_recu,null,false);
        edit  = v.findViewById(R.id.emailrec);
        b.setView(v);
        b.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                onDialogoRecuListener.onDialogoRecuClick(edit.getText().toString());
            }
        });
        b.setTitle(R.string.recucontra);
        return b.show();
    }

    public interface OnDialogoRecuListener{
        void onDialogoRecuClick(String emailouser);
    }

}
