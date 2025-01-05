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

        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}