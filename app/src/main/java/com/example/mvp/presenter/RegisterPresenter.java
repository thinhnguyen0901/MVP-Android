package com.example.mvp.presenter;

import android.app.Activity;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.example.mvp.data.Database;
import com.example.mvp.interfaces.RegisterInterface;
import com.example.mvp.model.User;

import java.util.ArrayList;

public class RegisterPresenter implements RegisterInterface.Presenter {

    private ArrayList<String> listUserEmail;
    private RegisterInterface.View viewRegisterInterface;

    public RegisterPresenter(Activity activity) {
        if (Database.getConnect(activity)) {
            listUserEmail = getListUserEmail(activity);
        }
    }

    public void setView(RegisterInterface.View view) {
        viewRegisterInterface = view;
    }


    @Override
    public void handleRegister(User user, String rePassword) {
        boolean exitEmail = true;
        if (user.isValidEmail() && user.isValidPassword()) {
            for (int i = 0; i < listUserEmail.size(); i++) {
                if (user.getEmail().equals(listUserEmail.get(i))) {
                    viewRegisterInterface.registerEmailExit();
                    exitEmail = false;
                    break;
                }
            }
            if (exitEmail) {
                if (!user.getPassword().equals(rePassword))
                    viewRegisterInterface.registerRePasswordError();
                else
                    viewRegisterInterface.registerSuccess();
            }
        } else
            viewRegisterInterface.registerError();
    }

    private ArrayList<String> getListUserEmail(Activity activity) {
        ArrayList<String> listUserEmail = new ArrayList<>();
        final String sql = "select user_email from user";
        try {
            SQLiteDatabase database = Database.initDatabase(activity);
            Cursor cursor = database.rawQuery(sql, null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                listUserEmail.add(cursor.getString(0));
                cursor.moveToNext();
            }
            cursor.close();
            database.close();
        } catch (SQLException e) {
            Toast.makeText(activity, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return listUserEmail;
    }
}
