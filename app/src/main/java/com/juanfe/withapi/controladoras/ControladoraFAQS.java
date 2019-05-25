package com.juanfe.withapi.controladoras;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.juanfe.withapi.R;

public class ControladoraFAQS extends Fragment {
    Context context;
    ImageButton onback;
    TextView description;
    OnFAQSListener ofq;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.faqs_content,container,false);
        description = v.findViewById(R.id.textofaqs);
        onback = v.findViewById(R.id.onback);
        onback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ofq.onFQASClick();
            }
        });


        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        ofq = (OnFAQSListener) context;

    }

    public interface OnFAQSListener{
        void onFQASClick();
    }


}
