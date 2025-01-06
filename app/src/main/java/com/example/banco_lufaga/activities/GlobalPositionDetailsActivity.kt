package com.example.banco_lufaga.activities

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.banco_lufaga.R
import com.example.banco_lufaga.databinding.ActivityGlobalPositionDetailsBinding
import com.example.banco_lufaga.fragments.AccountsMovementFragment
import com.example.banco_lufaga.pojo.Cuenta

class GlobalPositionDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGlobalPositionDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGlobalPositionDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val cuenta = intent.getSerializableExtra("Cuenta") as Cuenta
        val frgMovimiento: AccountsMovementFragment =
            AccountsMovementFragment.newInstance(cuenta)
        supportFragmentManager.beginTransaction()
            .replace(R.id.contenedorMovimientos, frgMovimiento)
            .commit()


        binding.bottomNavigation.setOnNavigationItemSelectedListener {
            it.isChecked = true

            val fragment: AccountsMovementFragment
            when (it.itemId) {
                R.id.nav_all -> {
                    fragment = AccountsMovementFragment.newInstance(cuenta)
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.contenedorMovimientos, fragment)
                        .commit()
                }

                R.id.nav_zero -> {
                    fragment = AccountsMovementFragment().apply {
                        arguments = Bundle().apply {
                            putString("TipoCuenta", "zero")
                            putSerializable("Cuenta", cuenta)
                        }
                    }

                    supportFragmentManager.beginTransaction()
                        .replace(R.id.contenedorMovimientos, fragment)
                        .commit()
                }

                R.id.nav_one -> {
                    fragment = AccountsMovementFragment().apply {
                        arguments = Bundle().apply {
                            putString("TipoCuenta", "one")
                            putSerializable("Cuenta", cuenta)
                        }
                    }

                    supportFragmentManager.beginTransaction()
                        .replace(R.id.contenedorMovimientos, fragment)
                        .commit()
                }

                R.id.nav_two -> {
                    fragment = AccountsMovementFragment().apply {
                        arguments = Bundle().apply {
                            putString("TipoCuenta", "two")
                            putSerializable("Cuenta", cuenta)
                        }
                    }

                    supportFragmentManager.beginTransaction()
                        .replace(R.id.contenedorMovimientos, fragment)
                        .commit()
                }
            }
            false
        }


        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}