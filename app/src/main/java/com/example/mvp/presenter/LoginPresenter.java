package com.example.mvp.presenter;

import android.app.Activity;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.example.mvp.data.Database;
import com.example.mvp.interfaces.LoginInterface;
import com.example.mvp.model.User;

import java.util.ArrayList;

public class LoginPresenter implements LoginInterface.Presenter {

    private LoginInterface.View view;
    private ArrayList<User> listUser;

    public LoginPresenter(Activity activity) {
        if (Database.getConnect(activity)) {
            listUser = getListUser(activity);
        }
    }

    public void setView(LoginInterface.View view) {
        this.view = view;
    }

    @Override
    public void handleSignIn(User user) {
        if (user.isValidEmail() && user.isValidPassword()) {
            for (int i = 0; i < listUser.size(); i++) {
                if (user.getEmail().equals(listUser.get(i).getEmail())) {
                    if (user.getPassword().equals(listUser.get(i).getPassword())) {
                        view.loginSuccess();
                    } else view.loginPasswordWrong();
                    break;
                } else
                    view.loginNotEmailExit();
            }
        } else {
            if (!user.isValidEmail() && !user.isValidPassword())
                view.loginError();
            else if (!user.isValidPassword())
                view.loginPasswordError();
            else if (!user.isValidEmail())
                view.loginEmailError();
        }
    }

    private ArrayList<User> getListUser(Activity activity) {
        final String sql = "select * from user";
        ArrayList<User> listUser = new ArrayList<>();
        try {
            SQLiteDatabase database = Database.initDatabase(activity);
            Cursor cursor = database.rawQuery(sql, null);
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToPosition(i);
                User user = new User();
                user.setEmail(cursor.getString(1));
                user.setPassword(cursor.getString(2));
                listUser.add(user);
            }
            cursor.close();
            database.close();
        } catch (SQLException e) {
            Toast.makeText(activity, e.getMessage(), Toast.LENGTH_LONG).show();
        }
        return listUser;
    }
}
