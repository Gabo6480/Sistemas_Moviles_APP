package com.example.chordgalore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.custom_toolbar.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Le decimos que utilice el toolbar que creamos
        setSupportActionBar(toolbar)

        nav_view.setNavigationItemSelectedListener(this)

        //Esto es para declarar la creación del botón para abrir el menú de manera automática
        toggle = ActionBarDrawerToggle(this, drawer_layout, toolbar, R.string.open_drawer, R.string.close_drawer)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        //Abrimos la pantalla de inicio como default en caso de que no haya ningun estado guardado
        if(savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, HomeFragment()).commit()
            nav_view.setCheckedItem(R.id.nav_home)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){

            R.id.nav_home -> supportFragmentManager.beginTransaction().replace(R.id.fragment_container,HomeFragment()).commit()

            R.id.nav_profile -> supportFragmentManager.beginTransaction().replace(R.id.fragment_container,ProfileFragment()).commit()

            R.id.nav_favorite -> supportFragmentManager.beginTransaction().replace(R.id.fragment_container,FavoriteFragment()).commit()

            R.id.nav_download -> supportFragmentManager.beginTransaction().replace(R.id.fragment_container,DownloadFragment()).commit()

            R.id.nav_config -> supportFragmentManager.beginTransaction().replace(R.id.fragment_container,ConfigurationFragment()).commit()

        }

        drawer_layout.closeDrawer(GravityCompat.START)

        //Toast.makeText(applicationContext, "Perfil Cliqueado", Toast.LENGTH_SHORT).show()
        return true
    }

    //Esta funcion es llamada cuando le picamos al botón "<" del telefono
    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)){
            drawer_layout.closeDrawer(GravityCompat.START)
        }
        else
            super.onBackPressed()
    }
}
