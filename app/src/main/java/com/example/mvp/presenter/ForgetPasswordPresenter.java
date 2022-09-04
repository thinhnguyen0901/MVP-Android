package com.example.mvp.presenter;

import android.app.Activity;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.example.mvp.data.Database;
import com.example.mvp.interfaces.ForgetPasswordInterface;
import com.example.mvp.model.User;

import java.util.ArrayList;

public class ForgetPasswordPresenter implements ForgetPasswordInterface.Presenter {

    private ForgetPasswordInterface.View view;

    public void setView(ForgetPasswordInterface.View view) {
        this.view = view;
    }

    @Override
    public boolean handleForgetPassword(User user) {
        if (user.isValidEmail()) {
            view.sendSuccess();
            return true;
        } else {
            view.emailError();
            return false;
        }
    }
}
