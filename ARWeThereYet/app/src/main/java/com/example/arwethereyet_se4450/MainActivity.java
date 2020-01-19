package com.example.arwethereyet_se4450;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.mapbox.android.core.location.LocationEngine;
import com.mapbox.android.core.location.LocationEngineCallback;
import com.mapbox.android.core.location.LocationEngineProvider;
import com.mapbox.android.core.location.LocationEngineRequest;
import com.mapbox.android.core.location.LocationEngineResult;
import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.location.LocationComponent;
import com.mapbox.mapboxsdk.location.LocationComponentActivationOptions;
import com.mapbox.mapboxsdk.location.modes.CameraMode;
import com.mapbox.mapboxsdk.location.modes.RenderMode;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Map;

//specific imports for pin query
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PointF;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.gson.JsonElement;
import com.mapbox.geojson.Feature;
import com.mapbox.geojson.FeatureCollection;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.style.layers.SymbolLayer;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;
import com.mapbox.mapboxsdk.annotations.BubbleLayout;
import com.mapbox.mapboxsdk.annotations.Marker;

import java.util.HashMap;

import static com.mapbox.mapboxsdk.style.layers.Property.ICON_ANCHOR_BOTTOM;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconAllowOverlap;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconAnchor;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconIgnorePlacement;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconImage;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconOffset;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, PermissionsListener, MapboxMap.OnMapClickListener{

    private static final long DEFAULT_INTERVAL_IN_MILLISECONDS = 1000L;
    private static final long DEFAULT_MAX_WAIT_TIME = DEFAULT_INTERVAL_IN_MILLISECONDS * 5;
    private MapView mapView;
    private MapboxMap mapboxMap;
    private PermissionsManager permissionsManager;
    private LocationEngine locationEngine;
    private LocationChangeListeningActivityLocationCallback callback = new LocationChangeListeningActivityLocationCallback(this);

    //specific var for pin query
    private static final String GEOJSON_SOURCE_ID = "ck58iqryj01px2nk0t6mca66g";
    private static final String MARKER_IMAGE_ID = "MARKER_IMAGE_ID";
    private static final String CALLOUT_IMAGE_ID = "CALLOUT_IMAGE_ID";
    private static final String MARKER_LAYER_ID = "MARKER_LAYER_ID";
    private static final String CALLOUT_LAYER_ID = "CALLOUT_LAYER_ID";
    private GeoJsonSource source;

    //pin query youtube
//    private Location ogLoc;
    private Point origin,dest;
    private Marker destM;
    private String TAG = "TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mapbox.getInstance(this, getString(R.string.access_token));
        setContentView(R.layout.activity_main);
        mapView = (MapView) findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
    }

    @Override
    public void onMapReady(@NonNull final MapboxMap mapboxMap) {
        this.mapboxMap = mapboxMap;
        //this.mapboxMap.addOnMapClickListener(this);
        mapboxMap.setStyle(Style.MAPBOX_STREETS, new Style.OnStyleLoaded() {
            @Override
            public void onStyleLoaded(@NonNull Style style) {

                // Map is set up and the style has loaded. Now you can add data or make other map adjustments
                 mapboxMap.setStyle(new Style.Builder().fromUri("mapbox://styles/aribasilone/ck5fo78du23jc1jo66i0hmm71"));

                //functs for pin query
                // setUpData();
                    //setupSource(style);
                    //setUpClickLocationIconImage(style);
//                    setUpClickLocationMarkerLayer(style);
//                    setUpInfoWindowLayer(style);
                mapboxMap.addOnMapClickListener(MainActivity.this);
                Toast.makeText(MainActivity.this,
                        getString(R.string.click_on_map_instruction), Toast.LENGTH_LONG).show();
            }
        });

    }

    //for pin query
    /**
     * Sets up all of the sources and layers needed for this example
     *public void setUpData() {
     *         if (mapboxMap != null) {
     *             mapboxMap.getStyle(style -> {
     *                 setupSource(style);
     *                 setUpClickLocationIconImage(style);
     *                 setUpClickLocationMarkerLayer(style);
     *                 setUpInfoWindowLayer(style);
     *             });
     *         }
     *     }
     */


    /**
     * Adds the GeoJSON source to the map
     */
//    private void setupSource(@NonNull Style loadedStyle) {
//        source = new GeoJsonSource(GEOJSON_SOURCE_ID);
//        loadedStyle.addSource(source);
//    }

    /**
     * Adds the marker image to the map for use as a SymbolLayer icon
     */
//    private void setUpClickLocationIconImage(@NonNull Style loadedStyle) {
////        loadedStyle.addImage(MARKER_IMAGE_ID, BitmapFactory.decodeResource(
////                this.getResources(), R.drawable.red_marker));
//    }

    /**
     * Needed to show the Feature properties info window.
     */
//    private void refreshSource(Feature featureAtClickPoint) {
//        if (source != null) {
//            source.setGeoJson(featureAtClickPoint);
//        }
//    }

//    /**
//     * Adds a SymbolLayer to the map to show the click location marker icon.
//     */
//    private void setUpClickLocationMarkerLayer(@NonNull Style loadedStyle) {
//        loadedStyle.addLayer(new SymbolLayer(MARKER_LAYER_ID, GEOJSON_SOURCE_ID)
//                .withProperties(
//                        iconImage(MARKER_IMAGE_ID),
//                        iconAllowOverlap(true),
//                        iconIgnorePlacement(true),
//                        iconOffset(new Float[] {0f, -8f})
//                ));
//    }
//
//    /**
//     * Adds a SymbolLayer to the map to show the Feature properties info window.
//     */
//    private void setUpInfoWindowLayer(@NonNull Style loadedStyle) {
//        loadedStyle.addLayer(new SymbolLayer(CALLOUT_LAYER_ID, GEOJSON_SOURCE_ID)
//                .withProperties(
//// show image with id title based on the value of the name feature property
//                        iconImage(CALLOUT_IMAGE_ID),
//
//// set anchor of icon to bottom-left
//                        iconAnchor(ICON_ANCHOR_BOTTOM),
//
//// prevent the feature property window icon from being visible even
//// if it collides with other previously drawn symbols
//                        iconAllowOverlap(false),
//
//// prevent other symbols from being visible even if they collide with the feature property window icon
//                        iconIgnorePlacement(false),
//
//// offset the info window to be above the marker
//                        iconOffset(new Float[] {-2f, -28f})
//                ));
//    }
//
//    /**
//     * This method handles click events for SymbolLayer symbols.
//     *
//     * @param screenPoint the point on screen clicked
//     */
    private boolean handleClickIcon(PointF screenPoint) {
        List<Feature> features = mapboxMap.queryRenderedFeatures(screenPoint);
        Log.i(TAG,"here");
        if (!features.isEmpty()) {
            Feature feature = features.get(0);
            Log.i(TAG,"here2");

            StringBuilder stringBuilder = new StringBuilder();

            if (feature.properties() != null) {
                for (Map.Entry<String, JsonElement> entry : feature.properties().entrySet()) {
                    // Log all the properties
                    Log.i(TAG, String.format("%s = %s", entry.getKey(), entry.getValue()));
                    stringBuilder.append(String.format("%s - %s", entry.getKey(), entry.getValue()));
                    stringBuilder.append(System.getProperty("line.separator"));
                }
                //new GenerateViewIconTask(MainActivity.this).execute(FeatureCollection.fromFeature(feature));
            }
        } else {
            Toast.makeText(this, getString(R.string.query_feature_no_properties_found), Toast.LENGTH_SHORT).show();
        }
        return true;
    }


    @Override
    public boolean onMapClick(@NonNull LatLng point) {
        if(destM != null){
            mapboxMap.removeMarker(destM);
        }
        destM = mapboxMap.addMarker(new MarkerOptions().position(point));
        dest = Point.fromLngLat(point.getLongitude(), point.getLatitude());
//        origin = Point.fromLngLat(ogLoc.getLongitude(), ogLoc.getLatitude());
        //supposedly black icon/marker is an emulator issue, alt has to deal with deprecation of Marker

        //return true;
        return handleClickIcon(mapboxMap.getProjection().toScreenLocation(point));
    }


//    /**
//     * Invoked when the bitmap has been generated from a view.
//     */
//    public void setImageGenResults(HashMap<String, Bitmap> imageMap) {
////        if (mapboxMap != null) {
////            mapboxMap.getStyle(style -> {
////                style.addImages(imageMap);
////            });
////        }
//    }
//
//    private static class GenerateViewIconTask extends AsyncTask<FeatureCollection, Void, HashMap<String, Bitmap>> {
//
//        private final WeakReference<MainActivity> activityRef;
//        private Feature featureAtMapClickPoint;
//
//        GenerateViewIconTask(MainActivity activity) {
//            this.activityRef = new WeakReference<>(activity);
//        }
//
//        @SuppressWarnings("WrongThread")
//        @Override
//        protected HashMap<String, Bitmap> doInBackground(FeatureCollection... params) {
//            MainActivity activity = activityRef.get();
//            HashMap<String, Bitmap> imagesMap = new HashMap<>();
//            if (activity != null) {
//                LayoutInflater inflater = LayoutInflater.from(activity);
//
//                if (params[0].features() != null) {
//                    featureAtMapClickPoint = params[0].features().get(0);
//
//                    StringBuilder stringBuilder = new StringBuilder();
//
//                    BubbleLayout bubbleLayout = (BubbleLayout) inflater.inflate(
//                            R.layout.activity_main_win_sym, null);
//
//                    TextView titleTextView = bubbleLayout.findViewById(R.id.info_window_title);
//                    titleTextView.setText(activity.getString(R.string.query_feature_marker_title));
//
//                    if (featureAtMapClickPoint.properties() != null) {
//                        for (Map.Entry<String, JsonElement> entry : featureAtMapClickPoint.properties().entrySet()) {
//                            stringBuilder.append(String.format("%s - %s", entry.getKey(), entry.getValue()));
//                            stringBuilder.append(System.getProperty("line.separator"));
//                        }
//
//                        TextView propertiesListTextView = bubbleLayout.findViewById(R.id.info_window_feature_properties_list);
//                        propertiesListTextView.setText(stringBuilder.toString());
//
//                        int measureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
//                        bubbleLayout.measure(measureSpec, measureSpec);
//
//                        float measuredWidth = bubbleLayout.getMeasuredWidth();
//
//                        bubbleLayout.setArrowPosition(measuredWidth / 2 - 5);
//
//                        //Bitmap bitmap = MainActivity.SymbolGenerator.generate(bubbleLayout);
//                        //imagesMap.put(CALLOUT_IMAGE_ID, bitmap);
//                    }
//                }
//            }
//
//            return imagesMap;
//        }
//
//        @Override
//        protected void onPostExecute(HashMap<String, Bitmap> bitmapHashMap) {
//            super.onPostExecute(bitmapHashMap);
//            MainActivity activity = activityRef.get();
//            if (activity != null && bitmapHashMap != null) {
//                //activity.setImageGenResults(bitmapHashMap);
//                //activity.refreshSource(featureAtMapClickPoint);
//            }
//        }
//
//    }
//
//    /**
//     * Utility class to generate Bitmaps for Symbol.
//     */
//    private static class SymbolGenerator {
//
//        /**
//         * Generate a Bitmap from an Android SDK View.
//         *
//         * @param view the View to be drawn to a Bitmap
//         * @return the generated bitmap
//         */
//        static Bitmap generate(@NonNull View view) {
//            int measureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
//            view.measure(measureSpec, measureSpec);
//
//            int measuredWidth = view.getMeasuredWidth();
//            int measuredHeight = view.getMeasuredHeight();
//
//            view.layout(0, 0, measuredWidth, measuredHeight);
//            Bitmap bitmap = Bitmap.createBitmap(measuredWidth, measuredHeight, Bitmap.Config.ARGB_8888);
//            bitmap.eraseColor(Color.TRANSPARENT);
//            Canvas canvas = new Canvas(bitmap);
//            view.draw(canvas);
//            return bitmap;
//        }
//    }

    //end of pin query sect

    private static class LocationChangeListeningActivityLocationCallback
            implements LocationEngineCallback<LocationEngineResult> {

        private final WeakReference<MainActivity> activityWeakReference;

        LocationChangeListeningActivityLocationCallback(MainActivity activity) {
            this.activityWeakReference = new WeakReference<>(activity);
        }

        /**
         * The LocationEngineCallback interface's method which fires when the device's location has changed.
         *
         * @param result the LocationEngineResult object which has the last known location within it.
         */
        @Override
        public void onSuccess(LocationEngineResult result) {
            MainActivity activity = activityWeakReference.get();

            if (activity != null) {
                Location location = result.getLastLocation();

                if (location == null) {
                    return;
                }

//                // Create a Toast which displays the new location's coordinates
//                Toast.makeText(activity, String.format(activity.getString(R.string.new_location),
//                        String.valueOf(result.getLastLocation().getLatitude()),
//                        String.valueOf(result.getLastLocation().getLongitude())),
//                        Toast.LENGTH_SHORT).show();

                // Pass the new location to the Maps SDK's LocationComponent
                if (activity.mapboxMap != null && result.getLastLocation() != null) {
                    activity.mapboxMap.getLocationComponent().forceLocationUpdate(result.getLastLocation());
                }
            }
        }

        /**
         * The LocationEngineCallback interface's method which fires when the device's location can't be captured
         *
         * @param exception the exception message
         */
        @Override
        public void onFailure(@NonNull Exception exception) {
            Log.d("LocationChangeActivity", exception.getLocalizedMessage());
            MainActivity activity = activityWeakReference.get();
            if (activity != null) {
                Toast.makeText(activity, exception.getLocalizedMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        }
    }


    @Override
    protected void onStart(){
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onResume(){
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onExplanationNeeded(List<String> permissionsToExplain) {
        Toast.makeText(this, "good",
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void onPermissionResult(boolean granted) {
        if (granted) {
            if (mapboxMap.getStyle() != null) {
                enableLocationComponent(mapboxMap.getStyle());
            }
        } else {
            Toast.makeText(this, "bad", Toast.LENGTH_LONG).show();
            finish();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        permissionsManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @SuppressLint("MissingPermission")
    private void initLocationEngine() {
        locationEngine = LocationEngineProvider.getBestLocationEngine(this);

        LocationEngineRequest request = new LocationEngineRequest.Builder(DEFAULT_INTERVAL_IN_MILLISECONDS)
                .setPriority(LocationEngineRequest.PRIORITY_HIGH_ACCURACY)
                .setMaxWaitTime(DEFAULT_MAX_WAIT_TIME).build();

        locationEngine.requestLocationUpdates(request, callback, getMainLooper());
        locationEngine.getLastLocation(callback);
    }

    /**
     * Initialize the Maps SDK's LocationComponent
     */
    @SuppressWarnings( {"MissingPermission"})
    private void enableLocationComponent(@NonNull Style loadedMapStyle) {
        // Check if permissions are enabled and if not request
        if (PermissionsManager.areLocationPermissionsGranted(this)) {

            // Get an instance of the component
            LocationComponent locationComponent = mapboxMap.getLocationComponent();

            // Set the LocationComponent activation options
            LocationComponentActivationOptions locationComponentActivationOptions =
                    LocationComponentActivationOptions.builder(this, loadedMapStyle)
                            .useDefaultLocationEngine(false)
                            .build();

            // Activate with the LocationComponentActivationOptions object
            locationComponent.activateLocationComponent(locationComponentActivationOptions);

            // Enable to make component visible
            locationComponent.setLocationComponentEnabled(true);

            // Set the component's camera mode
            locationComponent.setCameraMode(CameraMode.TRACKING);

            // Set the component's render mode
            locationComponent.setRenderMode(RenderMode.COMPASS);

            initLocationEngine();
        } else {
            permissionsManager = new PermissionsManager(this);
            permissionsManager.requestLocationPermissions(this);
        }
    }

}
