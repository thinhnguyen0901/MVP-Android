package com.example.mvp.presenter;

import android.app.Activity;
import android.content.ContentValues;
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
    private Activity activity;
    private RegisterInterface.View view;

    public RegisterPresenter(Activity activity) {
        this.activity = activity;
        if (Database.getConnect(activity)) {
            listUserEmail = getListUserEmail();
        }
    }

    public void setView(RegisterInterface.View view) {
        this.view = view;
    }


    @Override
    public void handleRegister(User user, String rePassword) {
        boolean exitEmail = false;
        if (user.isValidEmail() && user.isValidPassword()) {
            for (int i = 0; i < listUserEmail.size(); i++) {
                if (user.getEmail().equals(listUserEmail.get(i))) {
                    view.registerEmailExit();
                    exitEmail = true;
                    break;
                }
            }
            if (!exitEmail) {
                if (user.getPassword().equals(rePassword)) {
                    view.registerSuccess();
                    if (Database.getConnect(activity))
                        insertDBKetQuaThi(user);
                } else
                    view.registerRePasswordError();
            }
        } else {
            if (!user.isValidEmail() && !user.isValidPassword())
                view.registerError();
            else if (!user.isValidPassword())
                view.registerPasswordError();
            else if (!user.isValidEmail())
                view.registerEmailError();
        }
    }

    private ArrayList<String> getListUserEmail() {
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

    private void insertDBKetQuaThi(User user) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("user_email", user.getEmail());
        contentValues.put("user_password", user.getPassword());
        try {
            SQLiteDatabase db = Database.initDatabase(activity);
            db.insert("user", null, contentValues);
            db.close();
        } catch (SQLException e) {
            Toast.makeText(activity, "Lỗi kết nối tới CSDL" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
