package com.example.banco_lufaga.activities

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.banco_lufaga.R
import com.example.banco_lufaga.bd.MiBancoOperacional
import com.example.banco_lufaga.databinding.ActivityTransferBinding
import com.example.banco_lufaga.pojo.Cliente
import com.example.banco_lufaga.pojo.Cuenta

class TransferActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTransferBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTransferBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mbo: MiBancoOperacional? = MiBancoOperacional.getInstance(this)
        val cliente: Cliente = intent.getSerializableExtra("Cliente") as Cliente

        val spCuentas:Spinner = binding.spCuentas
        val spRdb:Spinner = binding.spRdb

        val cuentas: ArrayList<Cuenta> = mbo?.getCuentas(cliente) as ArrayList<Cuenta>

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

            var toastPersonalizado = Toast(applicationContext)
            var inflater = layoutInflater
            var layout = inflater.inflate(R.layout.toast_layout, findViewById(R.id.toastLayout))
            val txtMsg = layout.findViewById<TextView>(R.id.txtToast)
            txtMsg.text = "Cuenta origen: $cuentaSel \n$tipoCuenta $cuenta \n" +
                    "Importe: $importe$tipoImporte \n$justificante"
            toastPersonalizado.duration = Toast.LENGTH_LONG
            toastPersonalizado.view = layout
            toastPersonalizado.show()

        }

        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}