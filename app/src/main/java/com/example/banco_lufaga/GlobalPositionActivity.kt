package com.example.banco_lufaga

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.banco_lufaga.bd.MiBancoOperacional
import com.example.banco_lufaga.databinding.ActivityGlobalPositionBinding
import com.example.banco_lufaga.pojo.Cliente
import com.example.banco_lufaga.pojo.Cuenta

class GlobalPositionActivity : AppCompatActivity() {
    private lateinit var cuentaAdapter: CuentaAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager

    private lateinit var binding: ActivityGlobalPositionBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val mbo: MiBancoOperacional? = MiBancoOperacional.getInstance(this)
        val cliente: Cliente = intent.getSerializableExtra("Cliente") as Cliente

        binding = ActivityGlobalPositionBinding.inflate(layoutInflater)
        cuentaAdapter = CuentaAdapter(mbo?.getCuentas(cliente) as ArrayList<Cuenta>?)
        linearLayoutManager = LinearLayoutManager(this)

        binding.recyclerCuentas.apply {
            layoutManager = linearLayoutManager
            adapter = cuentaAdapter
        }

        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}