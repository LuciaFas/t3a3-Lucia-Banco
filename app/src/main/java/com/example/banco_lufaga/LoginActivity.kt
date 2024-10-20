package com.example.banco_lufaga

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.banco_lufaga.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.botonEntrar.setOnClickListener {
            val textoUsuario = binding.txtUsuario.text.toString()
            val textoCont = binding.txtContrasenya.text.toString()

            if (textoUsuario.isEmpty()) {
                binding.txtUsuario.error = "El usuario no puede estar vacio"
            }
            if (textoCont.isEmpty()) {
                binding.txtContrasenya.error = "La contraseña no puede estar vacia"
            }

            if (textoUsuario.isNotEmpty() && textoCont.isNotEmpty()) {
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("Usuario", binding.txtUsuario.text.toString())
                startActivity(intent)
            }
        }

        binding.botonSalir.setOnClickListener {
            finish()
        }

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}