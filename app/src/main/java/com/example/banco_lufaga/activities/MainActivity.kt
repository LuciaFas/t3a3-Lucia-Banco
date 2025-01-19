package com.example.banco_lufaga.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.banco_lufaga.R
import com.example.banco_lufaga.databinding.ActivityMainBinding
import com.example.banco_lufaga.pojo.Cliente
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var cliente: Cliente

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        drawerLayout = findViewById<DrawerLayout>(R.id.main)


        val toolBar = findViewById<Toolbar>(R.id.appbar)
        setSupportActionBar(toolBar)


        val navigationView: NavigationView = findViewById(R.id.nav_menu)
        navigationView.setNavigationItemSelectedListener(this)
        navigationView.setCheckedItem(R.id.nav_home)

        val toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolBar,
            R.string.open_nav,
            R.string.close_nav
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()


        cliente = intent.getSerializableExtra("Cliente") as Cliente

        binding.txtview.text = binding.txtview.text.toString() + "\n " + cliente.getNombre()



        binding.btnVolver.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        binding.btnCambContr.setOnClickListener {
            val intent = Intent(this, ChangePasswordActivity::class.java)
            intent.putExtra("Cliente", cliente)
            startActivity(intent)
        }

        binding.btnTransferencias.setOnClickListener {
            val intent = Intent(this, TransferActivity::class.java)
            intent.putExtra("Cliente", cliente)
            startActivity(intent)
        }

        binding.btnPosicion.setOnClickListener {
            val intent = Intent(this, GlobalPositionActivity::class.java)
            intent.putExtra("Cliente", cliente)
            startActivity(intent)
        }

        binding.btnMovimientos.setOnClickListener {
            val intent = Intent(this, MovementsActivity::class.java)
            intent.putExtra("Cliente", cliente)
            startActivity(intent)
        }

        binding.btnCajero.setOnClickListener {
            val intent = Intent(this, AtmManagementActivity::class.java)
            intent.putExtra("Cliente", cliente)
            startActivity(intent)
        }

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {

            R.id.nav_home -> {
                Log.d("MainActivity", "Item seleccionado:")
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("Cliente", cliente)
                startActivity(intent)
            }

            R.id.nav_contrasenya -> {
                val intent = Intent(this, ChangePasswordActivity::class.java)
                intent.putExtra("Cliente", cliente)
                startActivity(intent)
            }

            R.id.nav_posglobal -> {
                val intent = Intent(this, GlobalPositionActivity::class.java)
                intent.putExtra("Cliente", cliente)
                startActivity(intent)
            }

            R.id.nav_movimientos -> {
                val intent = Intent(this, MovementsActivity::class.java)
                intent.putExtra("Cliente", cliente)
                startActivity(intent)
            }

            R.id.nav_transferencias -> {
                val intent = Intent(this, TransferActivity::class.java)
                intent.putExtra("Cliente", cliente)
                startActivity(intent)
            }

            R.id.nav_config -> {
                val intent = Intent(this, SettingsActivity::class.java)
                startActivity(intent)
            }

            R.id.nav_salir -> {
                finish()
            }

        }

        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }


}