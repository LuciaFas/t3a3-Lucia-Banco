package com.example.banco_lufaga.activities

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.content.Intent
import android.view.View
import com.example.banco_lufaga.R
import com.example.banco_lufaga.databinding.ActivityGlobalPositionBinding
import com.example.banco_lufaga.fragments.AccountsFragment
import com.example.banco_lufaga.fragments.AccountsListener
import com.example.banco_lufaga.fragments.AccountsMovementFragment
import com.example.banco_lufaga.pojo.Cliente
import com.example.banco_lufaga.pojo.Cuenta

class GlobalPositionActivity : AppCompatActivity(), AccountsListener {
    private lateinit var binding: ActivityGlobalPositionBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGlobalPositionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val cliente = intent.getSerializableExtra("Cliente") as Cliente
        val frgCuenta: AccountsFragment =
            AccountsFragment.newInstance(cliente)
        supportFragmentManager.beginTransaction()
            .replace(R.id.contenedorCuentas, frgCuenta)
            .commit()
        frgCuenta.setCuentasListener(this)

        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onCuentaSeleccionada(cuenta: Cuenta) {
        if (cuenta != null) {
            var hayMovimiento = findViewById<View>(R.id.contenedorMovimientos) != null
            if(hayMovimiento){
                val movimientoFragment: AccountsMovementFragment =
                    AccountsMovementFragment.newInstance(cuenta)
                supportFragmentManager.beginTransaction()
                    .replace(R.id.contenedorMovimientos, movimientoFragment)
                    .commit()

            } else {
                val i = Intent(this, GlobalPositionDetailsActivity::class.java)
                i.putExtra("Cuenta", cuenta)
                startActivity(i)
            }
        }

    }

}