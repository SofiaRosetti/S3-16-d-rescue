package it.unibo.drescue.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import it.unibo.drescue.model.DistrictImpl;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class JsonFileUtils {

    public final static String JSON_DISTRICTS_PATH = "server/res/districts.json";
    private final Gson gson = new Gson();

    public JsonFileUtils() {
    }

    public ArrayList<DistrictImpl> getDistrictsFromFile() {
        ArrayList<DistrictImpl> districts = null;
        try {
            final BufferedReader bufferedReader = new BufferedReader(new FileReader(JSON_DISTRICTS_PATH));
            final Type type = new TypeToken<ArrayList<DistrictImpl>>() {
            }.getType();
            districts = this.gson.fromJson(bufferedReader, type);
        } catch (final FileNotFoundException e) {
            e.printStackTrace();
        }
        return districts;
    }
}
