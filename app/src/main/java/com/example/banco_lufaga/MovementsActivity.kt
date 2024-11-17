package com.example.banco_lufaga

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.banco_lufaga.bd.MiBancoOperacional
import com.example.banco_lufaga.databinding.ActivityMovementsBinding
import com.example.banco_lufaga.pojo.Cliente
import com.example.banco_lufaga.pojo.Cuenta
import android.widget.AdapterView
import android.view.View
import com.example.banco_lufaga.pojo.Movimiento

class MovementsActivity : AppCompatActivity() {
    private lateinit var movimientoAdapter: MovimientoAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager

    private lateinit var binding: ActivityMovementsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val mbo: MiBancoOperacional? = MiBancoOperacional.getInstance(this)
        val cliente: Cliente = intent.getSerializableExtra("Cliente") as Cliente

        binding = ActivityMovementsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        linearLayoutManager = LinearLayoutManager(this)

        val cuentas: ArrayList<Cuenta> = mbo?.getCuentas(cliente) as ArrayList<Cuenta>
        val adapterSpinner = ArrayAdapter(this, android.R.layout.simple_spinner_item, cuentas)
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spCuentasMov.adapter = adapterSpinner

        binding.spCuentasMov.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedItem = parent?.getItemAtPosition(position) as Cuenta

                binding.recyclerMovimientos.apply {
                    layoutManager = linearLayoutManager
                    movimientoAdapter = MovimientoAdapter(mbo.getMovimientos(selectedItem) as ArrayList<Movimiento>)
                    adapter = movimientoAdapter
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}