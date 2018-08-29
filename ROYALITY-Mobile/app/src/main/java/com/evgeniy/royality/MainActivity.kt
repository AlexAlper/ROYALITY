package com.evgeniy.royality

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import javax.inject.Inject


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    @Inject
    lateinit var sharedPrefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        App.component.inject(this)

        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
                this,
                drawer_layout,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
        nav_view.menu.getItem(1).isChecked = true
        onNavigationItemSelected(nav_view.menu.getItem(1))

        nav_view.getHeaderView(0).findViewById<TextView>(R.id.name)
                .text =  sharedPrefs.getString(NAME, getString(R.string.user_name))
        nav_view.getHeaderView(0).findViewById<TextView>(R.id.city)
                .text =  sharedPrefs.getString(CITY, getString(R.string.city))
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.profile -> {setFragment(DevelopmentFragment(), R.string.profile)}
            R.id.bonuses -> {setFragment(BonusFragment(), R.string.bonuses)}
            R.id.shares -> {setFragment(DevelopmentFragment(), R.string.shares)}
            R.id.loyalty_programs -> {setFragment(DevelopmentFragment(), R.string.loyalty_programs)}
            R.id.polls -> {setFragment(DevelopmentFragment(), R.string.polls)}
            R.id.settings -> {setFragment(DevelopmentFragment(), R.string.settings)}
            R.id.logout -> {
                sharedPrefs.edit()
                        .putBoolean(LOGIN, false)
                        .apply()
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        }
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun setFragment(fragment: Fragment, resId: Int) {
        val args = Bundle()
        args.putString(TITLE, getString(resId))

        fragment.arguments = args

        supportFragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .commit()
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}