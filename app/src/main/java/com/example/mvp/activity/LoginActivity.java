package com.example.mvp.activity;

import android.app.Dialog;
import android.content.Intent;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mvp.R;
import com.example.mvp.data.Database;
import com.example.mvp.model.User;
import com.example.mvp.presenter.LoginPresenter;

import java.util.ArrayList;


public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        LoginPresenter loginPresenter =new LoginPresenter(this);
    }

    public void openDialogForgetPassword(View view) {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.item_dialog);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false); //Optional
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation; //Setting the animations to dialog

        Button btn_send = dialog.findViewById(R.id.btn_send);
        Button btn_cancel = dialog.findViewById(R.id.btn_cancel);

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoginActivity.this, "Gửi thành công", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();

    }

    public void openProfile(View view) {
        startActivity(new Intent(this, ProfileActivity.class));
    }

    public void openRegister(View view) {
        startActivity(new Intent(this, RegisterActivity.class));
    }
}
