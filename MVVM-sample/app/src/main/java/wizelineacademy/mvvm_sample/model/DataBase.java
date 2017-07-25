package wizelineacademy.mvvm_sample.model;

import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nicole Terc on 7/20/17.
 */

public class DataBase {
    private static final String POJO_MESSAGE = "pojoMessage";

    SharedPreferences preferences;

    public DataBase(SharedPreferences preferences) {
        this.preferences = preferences;
    }

    public List<Pojo> getSavedPojos() {
        List<Pojo> list = new ArrayList<>();
        // retrieve pojo list from local storage
        return list;
    }

    public Pojo getSavedPojo() {
        //Load from database
        String message = preferences.getString(POJO_MESSAGE, "");

        Pojo pojo = new Pojo();
        pojo.setMessage(message);

        return pojo;
    }

    public void savePojo(Pojo pojo) {
        // Save Pojo in database
        String message = pojo.getMessage();
        preferences.edit().putString(POJO_MESSAGE, message).apply();
    }

    public void updatePojo(Pojo pojo) {
        // Update Pojo in database
        savePojo(pojo);
    }
}
