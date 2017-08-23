package it.unibo.drescue.utils;

import com.google.gson.*;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;

import java.io.IOException;

public class Geocoding {

    private static final String KEY = "AIzaSyAhBFJafCe2FWr0ZejtEcQqvuB3TtyLZrE";
    private final double latitude;
    private final double longitude;
    private String response;
    private String district = "";

    public Geocoding(final double latitude, final double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.sendRequest();
    }

    private void sendRequest() {
        final GeoApiContext context = new GeoApiContext.Builder()
                .apiKey(this.KEY)
                .build();
        final LatLng latlng = new LatLng(this.latitude, this.longitude);
        final GeocodingResult[] results;
        try {
            results = GeocodingApi.reverseGeocode(context, latlng).await();
            final Gson gson = new GsonBuilder().setPrettyPrinting().create();
            this.response = gson.toJson(results[0].addressComponents);
        } catch (final ApiException e) {
            e.printStackTrace();
        } catch (final InterruptedException e) {
            e.printStackTrace();
        } catch (final IOException e) {
            e.printStackTrace();
        }

    }

    public String getDistrict() {
        final JsonParser parser = new JsonParser();
        final JsonElement jsonTree = parser.parse(this.response);
        final JsonArray array = jsonTree.getAsJsonArray();
        for (int i = 0; i < array.size(); i++) {
            final JsonObject object = (JsonObject) array.get(i);
            final Object type = object.get("types");
            final String typeString = type.toString();
            if (typeString.contains("ADMINISTRATIVE_AREA_LEVEL_2")) {
                this.district = object.get("shortName").toString().substring(1, 3);
            }
        }
        return this.district;
    }
}
