package com.example.mvp.activity;

import android.app.Dialog;
import android.content.Intent;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mvp.R;
import com.example.mvp.data.Database;
import com.example.mvp.interfaces.LoginInterface;
import com.example.mvp.model.User;
import com.example.mvp.presenter.LoginPresenter;

import java.util.ArrayList;


public class LoginActivity extends AppCompatActivity implements LoginInterface.View {

    private LoginPresenter loginPresenter;
    private EditText edtEmail;
    private EditText edtPassword;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        initPresenter();
    }

    private void initView() {
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
    }

    private void initPresenter() {
        loginPresenter = new LoginPresenter(this);
        loginPresenter.setView(this);
    }


    public void openProfile(View view) {
        String srtEmail = edtEmail.getText().toString().trim();
        String srtPassword = edtPassword.getText().toString().trim();
        User user = new User(srtEmail, srtPassword);
        loginPresenter.handleSignIn(user);
    }

    @Override
    public void loginSuccess() {
        startActivity(new Intent(this, ProfileActivity.class));
        finish();
    }

    @Override
    public void loginError() {
        edtEmail.setError("Email không hợp lệ");
        edtPassword.setError("Mật khẩu không hợp lệ");
    }

    @Override
    public void loginEmailError() {
        edtEmail.setError("Email không tồn tại!");

    }

    @Override
    public void loginPasswordError() {
        edtPassword.setError("Mật khẩu không đúng!");

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

    public void openRegister(View view) {
        startActivity(new Intent(this, RegisterActivity.class));
    }
}
