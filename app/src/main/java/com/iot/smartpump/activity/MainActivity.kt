package com.iot.smartpump.activity

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog

import com.google.android.material.bottomnavigation.BottomNavigationView
import com.iot.smartpump.R
import com.iot.smartpump.fragment.DashboardFragment
import com.iot.smartpump.fragment.HomeFragment
import com.iot.smartpump.fragment.UpdateProfileFragment
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.iot.smartpump.MainApplication

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        val navView = findViewById<BottomNavigationView>(R.id.nav_view)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        //        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
        //                R.id.nav_home, R.id.navigation_dashboard, R.id.navigation_notifications)
        //                .build();
        val navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        //        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController)
        navView.setOnNavigationItemSelectedListener(this)
        changeFragment(HomeFragment())

    }

    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        var fragment: Fragment? = null
        when (menuItem.itemId) {
            R.id.nav_home -> fragment = HomeFragment()
//            R.id.navigation_dashboard -> fragment = DashboardFragment()
            R.id.nav_profile -> fragment = UpdateProfileFragment()
//            R.id.nav_logout -> showLogOutDialog()
        }
        if (fragment != null) {
            changeFragment(fragment!!)
        }
        return true
    }

    fun showLogOutDialog() {

        val message = "Are you sure to want logout from Application?"
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Confirmation!!")
        builder.setMessage(message)
        builder.setCancelable(false)
        builder.setNegativeButton("Cancel") { dialog, which -> dialog.cancel() }
        builder.setPositiveButton("Logout") { dialog, which ->
            dialog.dismiss()
            MainApplication.instance!!.getPrefs().clearLocalData()
            startActivity(Intent(this@MainActivity, LoginActivity::class.java))
          finish()
        }

        val alertDialog = builder.create()
        alertDialog.setOnShowListener {
            alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(resources.getColor(R.color.bg))
            alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(resources.getColor(R.color.bg))
        }
        alertDialog.show()
    }

    fun changeFragment(fragment: Fragment) {
        val TAG = fragment.javaClass.simpleName
        val fm = supportFragmentManager
        val ts = fm.beginTransaction()
        ts.replace(R.id.nav_host_fragment, fragment, TAG)
        ts.commitAllowingStateLoss()
        //        .getMenu().getItem(0).setChecked(true);

    }

}
