package com.iot.smartpump.activity

import android.os.Bundle
import android.os.Parcelable
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.iot.smartpump.R
import com.iot.smartpump.fragment.DeviceDetailsFragment
import com.iot.smartpump.model.DeviceData

class DeviceDetailsActivityNew : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {
    private var deviceData: DeviceData? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_devices)
        val navView = findViewById<BottomNavigationView>(R.id.nav_view)
        deviceData = intent.getParcelableExtra<DeviceData>("data")
        navView.setOnNavigationItemSelectedListener(this)
        val fragment = DeviceDetailsFragment()
        val bundle = Bundle()
        bundle.putParcelable("data", deviceData)
        fragment.arguments = bundle
        changeFragment(fragment)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        var fragment: Fragment? = null
        when (menuItem.itemId) {
            R.id.nav_home -> fragment = DeviceDetailsFragment()


        }//            case R.id.navigation_dashboard:
        //                fragment= new DashboardFragment();
        //                break;
        //            case R.id.navigation_notifications:
        //                fragment= new NotificationsFragment();
        //                break;
        if (fragment != null) {
            val bundle = Bundle()
            bundle.putParcelable("data", deviceData)
            fragment.arguments = bundle
            changeFragment(fragment)
        }
        return false
    }




    fun changeFragment(fragment: Fragment) {
        val TAG = fragment.javaClass.simpleName
        val fm = supportFragmentManager
        val ts = fm.beginTransaction()
        ts.replace(R.id.nav_device_fragment, fragment, TAG)
        ts.commitAllowingStateLoss()
        //        .getMenu().getItem(0).setChecked(true);

    }
}
