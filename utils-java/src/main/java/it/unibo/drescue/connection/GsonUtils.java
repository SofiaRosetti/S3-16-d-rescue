package it.unibo.drescue.connection;

import com.google.gson.Gson;

public class GsonUtils {

    /**
     * Deserializes the specified Json into an object of the specified class.
     *
     * @param message the string from which the object is to be deserialized
     * @param clazz   the class of T
     * @param <T>     type of the desired object
     * @return an object of type T from the string
     */
    public static <T> T fromGson(final String message, final Class<T> clazz) {
        final Gson gson = new Gson();
        return gson.fromJson(message, clazz);
    }

    /**
     * Serializes the specified object into its equivalent Json representation.
     *
     * @param object the object to serialize
     * @return Json representation of object
     */
    public static String toGson(final Object object) {
        final Gson gson = new Gson();
        return gson.toJson(object);
    }

}
