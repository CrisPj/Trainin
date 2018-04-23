package com.pythonteam.training;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AboutOf extends Fragment
{
    @BindView(R.id.txtwebTec) TextView txtwebTec;
    @BindView(R.id.txtemailDev) TextView txtemailDev;
    @BindView(R.id.txtphoneDev) TextView txtphoneDev;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    public AboutOf(){
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_about_of, container, false);
        ButterKnife.bind(this,view);

        Linkify.addLinks(txtwebTec, Linkify.WEB_URLS);
        Linkify.addLinks(txtemailDev, Linkify.EMAIL_ADDRESSES);
        Linkify.addLinks(txtphoneDev, Linkify.PHONE_NUMBERS);
        return view;

    }
}
