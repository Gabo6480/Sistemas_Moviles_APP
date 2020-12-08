package com.example.chordgalore

import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import com.example.chordgalore.data.DataDbHelper
import com.example.chordgalore.data.LoginRepository
import com.example.chordgalore.ui.login.LoginActivity
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.custom_toolbar.*
import kotlinx.android.synthetic.main.drawer_header.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var toggle: ActionBarDrawerToggle
    var DBSqlite = DataDbHelper(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        LoginRepository.instance()?.context = applicationContext

        if(LoginRepository.instance()?.user == null) {
            LoginRepository.instance()?.autoLogin()
            if (LoginRepository.instance()?.isLoggedIn!!) {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        }

        //Le decimos que utilice el toolbar que creamos
        setSupportActionBar(toolbar)

        nav_view.setNavigationItemSelectedListener(this)

        //Esto es para declarar la creación del botón para abrir el menú de manera automática
        toggle = ActionBarDrawerToggle(
            this,
            drawer_layout,
            toolbar,
            R.string.open_drawer,
            R.string.close_drawer
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        //Abrimos la pantalla de inicio como default en caso de que no haya ningun estado guardado
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, ListFragment(0)).commit()
            nav_view.setCheckedItem(R.id.nav_home)
        }

        floating_button.setOnClickListener {
            startActivity(Intent(this, NewSongActivity::class.java))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)

        nav_userName.text = LoginRepository.instance()?.user?.displayName
        nav_userPic.setImageBitmap(LoginRepository.instance()?.user?.profileBitmap)

        val searchItem = menu?.findItem(R.id.toolbar_search)

        val searchView: SearchView = searchItem?.actionView as SearchView
        searchView.queryHint = "Busqueda..."
        searchView.isIconified = false
        searchView.setOnCloseListener {
            searchView.setQuery("", false)
            true
        }

        //Aquí manejamos lo que pasa cuando cambia el texto de la busqueda o le damos a summit
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                //No se usa
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                val fragment =
                    supportFragmentManager.findFragmentById(R.id.fragment_container) as ListFragment
                fragment.setFilter(newText)

                return false
            }
        })

        //Aquí manejamos lo que va a pasar cuando se abra/cerre el menú de busqueda
        searchItem.setOnActionExpandListener(object: MenuItem.OnActionExpandListener{
            override fun onMenuItemActionCollapse(p0: MenuItem?): Boolean {
                val fragment =
                    supportFragmentManager.findFragmentById(R.id.fragment_container) as ListFragment
                fragment.setFilter("")
                searchView.setQuery("", false)
                return true
            }

            override fun onMenuItemActionExpand(p0: MenuItem?): Boolean {
                return true
            }
        })

        return super.onCreateOptionsMenu(menu)
    }

    //Aquí van los callback de los botones de el Drawer Menu
    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        val fragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container) as ListFragment

        var i = 0

        when (item.itemId) {

            R.id.nav_home -> supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, ListFragment(0)).addToBackStack("Home").commit()

            R.id.nav_favorite -> supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, ListFragment(1)).addToBackStack("Favorite")
                .commit()

            R.id.nav_draft -> supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, ListFragment(2)).addToBackStack("Draft")
                .commit()

            R.id.nav_profile -> startActivity(Intent(this, ProfileActivity::class.java))


            R.id.nav_logout -> {
                LoginRepository.instance()?.logout()
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }

        }

        drawer_layout.closeDrawer(GravityCompat.START)

        return true
    }

    //Esta funcion es llamada cuando le picamos al botón "<" del telefono
    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else
            super.onBackPressed()
    }
}
