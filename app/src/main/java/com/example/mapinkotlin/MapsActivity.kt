package com.example.mapinkotlin

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.mapinkotlin.databinding.ActivityMapsBinding
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        // Add a marker in Sydney and move the camera
        val sydney = LatLng(31.519628, 34.456677)  // تعريف النقطة تبعت
        getLastLocation()
        //mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Gaza"))
//        val markerOptions = MarkerOptions()
//        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)) // التحكم باللون تبع الماركر
//        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_launcher_background)) // التحكم باللون تبع الماركر
        // ال snippet هذا هو الوصف تبع المكان
        mMap.mapType = GoogleMap.MAP_TYPE_SATELLITE
//        mMap.uiSettings.isZoomControlsEnabled = true // رح يحط ازرار التكبير والتصغير على الخريطة
//        mMap.uiSettings.isCompassEnabled = true // خيار البوصلة
//        mMap.uiSettings.isMyLocationButtonEnabled = true // بجيبلك موقعك الحالي
//        mMap.uiSettings.isRotateGesturesEnabled = true // عملية تدوير للخريطة
//        mMap.uiSettings.isTiltGesturesEnabled = true
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
        //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 15f)) // تعمل زوم قوي
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, 15f))// زوم بس بشكل انيميشن

//        mMap.addPolyline(
//            PolylineOptions()
//                .add(LatLng(31.519628, 34.456677))
//                .add(LatLng(32.519628, 34.756677))
//                .color(Color.BLUE)
//                .visible(true)
//        )

//        mMap.addPolygon(
//            PolygonOptions()
//                .clickable(true)
//                .fillColor(Color.BLUE)
//                .strokeColor(Color.GRAY)
//                .strokeWidth(4f)
//                .add(
//                    LatLng(-27.457, 153.040),
//                    LatLng(-33.852, 151.211),
//                    LatLng(-37.813, 144.962),
//                    LatLng(-34.928, 138.599)
//                )
//        )

//        mMap.addCircle(
//            CircleOptions()
//                .center(sydney)
//                .radius(500.0)
//                .clickable(true)
//                .fillColor(Color.BLUE)
//                .strokeColor(Color.BLACK)
//        )
    }

    fun getLastLocation() {
        val locationClient = LocationServices.getFusedLocationProviderClient(this)
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        locationClient.lastLocation
            .addOnSuccessListener { location ->
                if (location != null) {
                    val lat = location.latitude
                    val long = location.longitude
                    val latLng = LatLng(lat, long)
                    val markerOptions = MarkerOptions()
                    markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))
                    mMap.addMarker(
                        markerOptions.position(latLng).title("Gaza").snippet("Details Gaza")
                    )
                    Log.d("yazan", latLng.toString())
                }
            }
            .addOnFailureListener {
                Log.e("yazan", it.message.toString())
            }
    }
}