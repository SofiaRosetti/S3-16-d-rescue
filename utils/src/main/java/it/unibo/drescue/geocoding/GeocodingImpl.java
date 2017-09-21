package it.unibo.drescue.geocoding;

import com.google.gson.*;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;

public class GeocodingImpl implements Geocoding {

    private static final String KEY = "AIzaSyBL8F9ebXIIXBF5IqjkG5Go2aFuJPpc-zQ";
    private static final String AREA = "ADMINISTRATIVE_AREA_LEVEL_2";
    private static final String COUNTRY = "COUNTRY";
    private static final String ITALY_SHORT_NAME = "IT";
    private static final String TYPES = "types";
    private static final String SHORT_NAME = "shortName";
    private String response;
    private String district = "";

    public GeocodingImpl() {
    }

    @Override
    public String getDistrict(final double latitude, final double longitude) throws GeocodingException {

        boolean inItaly = false;

        try {
            this.reverseGeocode(latitude, longitude);
        } catch (final Exception e) {
            throw new GeocodingException();
        }

        final JsonParser parser = new JsonParser();
        final JsonElement jsonTree = parser.parse(this.response);
        final JsonArray array = jsonTree.getAsJsonArray();
        for (int i = 0; i < array.size(); i++) {
            final JsonObject object = (JsonObject) array.get(i);
            final Object type = object.get(TYPES);
            final String typeString = type.toString();
            if (typeString.contains(AREA)) {
                this.district = object.get(SHORT_NAME).toString().substring(1, 3);
            }
            if (typeString.contains(COUNTRY) && object.get(SHORT_NAME).toString().substring(1, 3).equals(ITALY_SHORT_NAME)) {
                inItaly = true;
            }
        }

        if (!inItaly) {
            throw new GeocodingException();
        }

        return this.district;
    }

    @Override
    public JsonObject getLatLng(final String address) throws GeocodingException {

        try {
            this.geocode(address);
        } catch (final Exception e) {
            throw new GeocodingException();
        }

        final JsonObject json = new JsonParser().parse(this.response).getAsJsonObject();
        return json;

    }

    private void reverseGeocode(final double latitude, final double longitude) throws Exception {
        final GeoApiContext context = new GeoApiContext.Builder()
                .apiKey(KEY)
                .build();
        final LatLng latlng = new LatLng(latitude, longitude);
        final GeocodingResult[] results;
        try {
            results = GeocodingApi.reverseGeocode(context, latlng).await();
            final Gson gson = new GsonBuilder().setPrettyPrinting().create();
            this.response = gson.toJson(results[0].addressComponents);
        } catch (final Exception e) {
            throw e;
        }
    }

    private void geocode(final String address) throws Exception {
        final GeoApiContext context = new GeoApiContext.Builder()
                .apiKey(KEY)
                .build();
        final GeocodingResult[] results;
        try {
            results = GeocodingApi.geocode(context, address).await();
            final Gson gson = new GsonBuilder().setPrettyPrinting().create();
            this.response = gson.toJson(results[0].geometry.location);
        } catch (final Exception e) {
            throw e;
        }

    }
}
