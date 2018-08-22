package com.example.hjhjh.sum_01_smokingnomad_daum;

import android.Manifest;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.ViewGroup;
import android.widget.Toast;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import net.daum.mf.map.api.CameraUpdateFactory;
import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapPointBounds;
import net.daum.mf.map.api.MapView;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements MapView.CurrentLocationEventListener, MapView.POIItemEventListener {

    private static final String LOG_TAG = "LocationDemoActivity";
    private final int MENU_LOCATION = Menu.FIRST;
    private final int MENU_REVERSE_GEO = Menu.FIRST + 1;

    private MapPOIItem mDefaultMarker;
    private MapPOIItem mCustomMarker;
    private MapPOIItem mCustomBmMarker;

    private static final MapPoint CUSTOM_MARKER_POINT = MapPoint.mapPointWithGeoCoord(37.526820, 126.863945);
    private static final MapPoint CUSTOM_MARKER_POINT2 = MapPoint.mapPointWithGeoCoord(37.525587, 126.859382);
    private static final MapPoint DEFAULT_MARKER_POINT = MapPoint.mapPointWithGeoCoord(37.531687, 126.863408);


    private MapView mapView;

    protected void onTop(){
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mapView = new MapView(this);

    //    mapView.setMapViewEventListener(this);
        mapView.setPOIItemEventListener(this);
        // 구현한 CalloutBalloonAdapter 등록
        createDefaultMarker(mapView);
        showAll();


        // 권한 허가/거부 시 결과 리턴해주는 리스너
        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                Toast.makeText(MainActivity.this, "권한 허가", Toast.LENGTH_SHORT).show();

                ViewGroup mapViewContainer = (ViewGroup) findViewById(R.id.map_view);
                mapViewContainer.addView(mapView);
                mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading);

            }

            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                Toast.makeText(MainActivity.this, "권한 거부\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
            }
        };

        // 권한체크 시작
        TedPermission.with(this)
                .setPermissionListener(permissionlistener)
                .setRationaleMessage("현재 위치 파악을 위해서는 위치 접근 권한이 필요합니다")
                .setDeniedMessage("[설정] > [권한] 에서 권한을 허용할 수 있습니다.")
                .setPermissions(Manifest.permission.ACCESS_FINE_LOCATION)
                .check();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOff);
        mapView.setShowCurrentLocationMarker(false);
    }

    // 현위치 좌표값 통보
    @Override
    public void onCurrentLocationUpdate(MapView mapView, MapPoint currentLocation, float accuracyInMeters) {
        MapPoint.GeoCoordinate mapPointGeo = currentLocation.getMapPointGeoCoord();
        Log.i(LOG_TAG, String.format("MapView onCurrentLocationUpdate (%f,%f) accuracy (%f)", mapPointGeo.latitude, mapPointGeo.longitude, accuracyInMeters));
    }


    private void createDefaultMarker(MapView mapView) {

        onCurrentLocationUpdate(mapView,mapView.getMapCenterPoint(),1);

        mDefaultMarker = new MapPOIItem();
        String name = "Default Marker";
        mDefaultMarker.setItemName(name);
        mDefaultMarker.setTag(0);
        mDefaultMarker.setMapPoint(DEFAULT_MARKER_POINT);
        mDefaultMarker.setMarkerType(MapPOIItem.MarkerType.BluePin);
        mDefaultMarker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin);

        mapView.addPOIItem(mDefaultMarker);
        mapView.selectPOIItem(mDefaultMarker, true);
        mapView.setMapCenterPoint(DEFAULT_MARKER_POINT, false);
    }


    private void showAll() {
        int padding = 20;
        float minZoomLevel = 7;
        float maxZoomLevel = 10;
        MapPointBounds bounds = new MapPointBounds(CUSTOM_MARKER_POINT, DEFAULT_MARKER_POINT);
        mapView.moveCamera(CameraUpdateFactory.newMapPointBounds(bounds, padding, minZoomLevel, maxZoomLevel));
    }

    @Override
    public void onCurrentLocationDeviceHeadingUpdate(MapView mapView, float v) {

    }

    @Override
    public void onCurrentLocationUpdateFailed(MapView mapView) {

    }

    @Override
    public void onCurrentLocationUpdateCancelled(MapView mapView) {

    }


    @Override
    public void onPOIItemSelected(MapView mapView, MapPOIItem mapPOIItem) {

    }

    @Override
    public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem) {

    }

    @Override
    public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem, MapPOIItem.CalloutBalloonButtonType calloutBalloonButtonType) {

    }

    @Override
    public void onDraggablePOIItemMoved(MapView mapView, MapPOIItem mapPOIItem, MapPoint mapPoint) {

    }


}
