package com.example.banco_lufaga

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.banco_lufaga.databinding.ActivityMainBinding
import com.example.banco_lufaga.databinding.ActivityTransferBinding

class TransferActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTransferBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTransferBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val spCuentas:Spinner = binding.spCuentas
        val spRdb:Spinner = binding.spRdb

        val cuentas = resources.getStringArray(R.array.cuentas)

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, cuentas)

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spCuentas.adapter = adapter
        spRdb.adapter = adapter

        val spDivisa:Spinner = binding.spDivisa

        val divisas = resources.getStringArray(R.array.divisa)

        val adapter2 = ArrayAdapter(this, android.R.layout.simple_spinner_item, divisas)

        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spDivisa.adapter = adapter2

        binding.radiogroup.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.rdbtn_propia -> {
                    binding.spRdb.visibility = View.VISIBLE
                    binding.editTextRdb.visibility = View.INVISIBLE
                }
                R.id.rdbtn_ajena -> {
                    binding.editTextRdb.visibility = View.VISIBLE
                    binding.spRdb.visibility = View.INVISIBLE
                }
            }
        }

        binding.btnCancelar.setOnClickListener {
            finish()
        }

        binding.btnEnviar.setOnClickListener {
            var cuentaSel = binding.spCuentas.selectedItem.toString()
            var tipoCuenta:String
            var cuenta:String
            var tipoImporte = binding.spDivisa.selectedItem.toString()
            var importe = binding.editTextCant.text.toString()
            var justificante:String

            if (binding.spRdb.visibility == View.VISIBLE) {
                tipoCuenta = "Cuenta Propia:"
                cuenta = binding.spRdb.selectedItem.toString()
            } else {
                tipoCuenta = "Cuenta Ajena:"
                cuenta = binding.editTextRdb.text.toString()
            }

            if (binding.cbJustificante.isChecked) {
                justificante = "Enviar justificante"
            } else {
                justificante = "No enviar justificante"
            }

            Toast.makeText(this@TransferActivity, "Cuenta origen: $cuentaSel \n$tipoCuenta $cuenta \n" +
                    "Importe: $importe$tipoImporte \n$justificante", Toast.LENGTH_LONG).show()
        }

        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}