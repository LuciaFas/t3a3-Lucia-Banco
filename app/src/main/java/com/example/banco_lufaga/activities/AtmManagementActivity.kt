package com.example.banco_lufaga.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.banco_lufaga.R
import com.example.banco_lufaga.bd.MiBancoOperacional
import com.example.banco_lufaga.databinding.ActivityAtmManagementBinding
import com.example.banco_lufaga.pojo.Cliente

class AtmManagementActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAtmManagementBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAtmManagementBinding.inflate(layoutInflater)

        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val mbo: MiBancoOperacional? = MiBancoOperacional.getInstance(this)
        val cliente: Cliente = intent.getSerializableExtra("Cliente") as Cliente

        binding.btnListaCajeros.setOnClickListener {
            val intent = Intent(this, AtmListActivity::class.java)
            intent.putExtra("Cliente", cliente)
            startActivity(intent)
        }

        binding.btnAnadirCajero.setOnClickListener {
            val intent = Intent(this, AtmFormActivity::class.java)
            intent.putExtra("Cliente", cliente)
            startActivity(intent)
        }
    }
}