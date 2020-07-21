package com.cartrack.challenge.views.map

import com.cartrack.challenge.R
import com.cartrack.challenge.base.BaseActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.view_toolbar.*

class UserMapActivity : BaseActivity(), OnMapReadyCallback {

    override fun getLayoutRes(): Int {
        return R.layout.activity_user_map
    }

    override fun bindView() {
        setToolbar()
        setMap()
    }

    override fun bindObservers() {
    }

    override fun onMapReady(p0: GoogleMap?) {
        p0?.let {
            val lat = intent.getDoubleExtra(EXTRA_LAT, 0.0)
            val lng = intent.getDoubleExtra(EXTRA_LNG, 0.0)
            val location = LatLng(lat, lng)

            val settings = it.uiSettings
            settings.isZoomControlsEnabled = true

            it.addMarker(MarkerOptions().position(location))
            it.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 11f))

        }
    }

    private fun setToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        ivBack.setOnClickListener { finish() }
    }

    private fun setMap() {
        val mapFragment = supportFragmentManager.findFragmentById(R.id.fragmentMap)
        if (mapFragment is SupportMapFragment) {
            mapFragment.getMapAsync(this)
        }
    }

    companion object {
        const val EXTRA_LAT = "LAT"
        const val EXTRA_LNG = "LNG"
    }
}