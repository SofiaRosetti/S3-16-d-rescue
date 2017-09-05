package it.unibo.drescue.database;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;
import java.util.List;

public class JsonFileUtils {

    private final Gson gson = new Gson();

    /**
     * Get a generic list of objects from a json file
     * <p>
     * Note: the json file must be in the format of JsonArray:
     * ex. : [{jsonObj1},{jsonObj2},{jsonObj3},...]
     * where the JsonObjects keys must be the same as parameters in the object's model of the specified type
     * </p>
     *
     * @param path indicates the path to the json file
     * @param type indicates the type of the class of the object to get
     * @param <T>  the type of elements maintained by this list
     * @return a List of Objects with all objects contained in the json file
     */
    public <T> List<T> getListFromJsonFile(final String path, final Class<T[]> type) {
        T[] array = null;
        try {
            final BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
            array = this.gson.fromJson(bufferedReader, type);
        } catch (final FileNotFoundException e) {
            e.printStackTrace();
        }
        return Arrays.asList(array);
    }
}
