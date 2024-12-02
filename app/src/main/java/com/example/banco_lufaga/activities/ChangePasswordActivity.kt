package com.example.banco_lufaga.activities

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.banco_lufaga.R
import com.example.banco_lufaga.bd.MiBancoOperacional
import com.example.banco_lufaga.databinding.ActivityChangePasswordBinding
import com.example.banco_lufaga.pojo.Cliente

class ChangePasswordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChangePasswordBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangePasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mbo: MiBancoOperacional? = MiBancoOperacional.getInstance(this)

        val cliente:Cliente = intent.getSerializableExtra("Cliente") as Cliente

        binding.botonAceptar.setOnClickListener {
            val txtContrActual = binding.txtContrActual.text.toString()
            val txtContraNueva = binding.txtContraNueva.text.toString()
            val txtContraNuevaRep = binding.txtContraNuevaRep.text.toString()

            if (txtContraNueva != txtContraNuevaRep) {
                binding.txtContraNuevaRep.error = "La contraseña no coincide"
                return@setOnClickListener
            }
            if (txtContrActual != cliente.getClaveSeguridad()) {
                Toast.makeText(this@ChangePasswordActivity, "La contraseña es incorrecta", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            cliente.setClaveSeguridad(txtContraNueva)
            val p = mbo?.changePassword(cliente)
            if (p == 0) {
                Toast.makeText(this@ChangePasswordActivity, "Ha habido un problema", Toast.LENGTH_SHORT).show()
            }
            finish()
        }

        binding.botonCancelar.setOnClickListener {
            finish()
        }

        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}