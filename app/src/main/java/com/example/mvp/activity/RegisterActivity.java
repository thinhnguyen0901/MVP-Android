package com.example.mvp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mvp.R;
import com.example.mvp.interfaces.RegisterInterface;
import com.example.mvp.model.User;
import com.example.mvp.presenter.RegisterPresenter;

public class RegisterActivity extends AppCompatActivity implements RegisterInterface.View {
    private RegisterInterface.View viewRRegisterInterface;
    private RegisterPresenter registerPresenter;
    private EditText edtEmail;
    private EditText edtPassword;
    private EditText edtRePassword;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
        initPresenter();
    }

    private void initPresenter() {
        registerPresenter = new RegisterPresenter(this);
        registerPresenter.setView(this);
    }

    private void initView() {
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        edtRePassword = findViewById(R.id.edtRePassword);

    }

    public void openLogin(View view) {
        startActivity(new Intent(this, LoginActivity.class));
    }

    public void openMain(View view) {
        String email = edtEmail.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();
        String rePassword = edtRePassword.getText().toString().trim();
        User user = new User(email, password);
        registerPresenter.handleRegister(user, rePassword);
    }

    @Override
    public void registerSuccess() {
        startActivity(new Intent(this, MainActivity.class));
    }

    @Override
    public void registerError() {
        Toast.makeText(this, R.string.announce_error_login, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void registerEmailExit() {
        edtEmail.setError("Email đã tồn tại");
    }

    @Override
    public void registerRePasswordError() {
        edtRePassword.setError("Mật khẩu không khớp");
    }
}
