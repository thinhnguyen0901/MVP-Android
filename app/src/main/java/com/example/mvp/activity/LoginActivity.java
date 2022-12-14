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
import com.example.mvp.interfaces.ForgetPasswordInterface;
import com.example.mvp.interfaces.LoginInterface;
import com.example.mvp.model.User;
import com.example.mvp.presenter.ForgetPasswordPresenter;
import com.example.mvp.presenter.LoginPresenter;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;


public class LoginActivity extends AppCompatActivity implements LoginInterface.View, ForgetPasswordInterface.View {

    private LoginPresenter loginPresenter;
    private ForgetPasswordPresenter forgetPasswordPresenter;
    private EditText edtEmail;
    private EditText edtPassword;
    private Dialog dialog;
    private EditText edtEmailDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        initPresenter();
    }

    private void initDialogForgetPassword() {
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.item_dialog);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false); //Optional
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation; //Setting the animations to dialog
    }

    private void initView() {
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
    }

    private void initPresenter() {
        loginPresenter = new LoginPresenter(this);
        loginPresenter.setView(this);
        forgetPasswordPresenter = new ForgetPasswordPresenter();
        forgetPasswordPresenter.setView(this);
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
    public void loginPasswordError() {
        edtPassword.setError("M???t kh???u kh??ng h???p l???");
    }

    @Override
    public void loginEmailError() {
        edtEmail.setError("Email kh??ng h???p l???");

    }

    @Override
    public void loginNotEmailExit() {
        edtEmail.setError("Email kh??ng t???n t???i!");
    }

    @Override
    public void loginError() {
        loginEmailError();
        loginPasswordError();
    }

    @Override
    public void loginPasswordWrong() {
        edtPassword.setError("M???t kh???u kh??ng ????ng");
    }

    @Override
    public void emailError() {
        edtEmailDialog.setError("Email kh??ng h???p l???");
    }

    @Override
    public void sendSuccess() {
        Toast.makeText(this, "G???i th??nh c??ng ?????n " + edtEmailDialog.getText().toString(), Toast.LENGTH_LONG).show();
    }

    public void openDialogForgetPassword(View view) {
        initDialogForgetPassword();
        Button btn_send = dialog.findViewById(R.id.btn_send);
        Button btn_cancel = dialog.findViewById(R.id.btn_cancel);

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtEmailDialog = dialog.findViewById(R.id.edtEmailDialog);
                String srtSendEmail = edtEmailDialog.getText().toString().trim();
                User user = new User(srtSendEmail);
                if (forgetPasswordPresenter.handleForgetPassword(user))
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
