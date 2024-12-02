package com.example.banco_lufaga.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.banco_lufaga.R
import com.example.banco_lufaga.databinding.ActivityMainBinding
import com.example.banco_lufaga.pojo.Cliente

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val cliente:Cliente = intent.getSerializableExtra("Cliente") as Cliente

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.txtview.text = binding.txtview.text.toString() + "\n " + cliente.getNif()

        binding.btnVolver.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        binding.btnCambContr.setOnClickListener {
            val intent = Intent(this, ChangePasswordActivity::class.java)
            intent.putExtra("Cliente", cliente)
            startActivity(intent)
        }

        binding.btnTransferencias.setOnClickListener {
            val intent = Intent(this, TransferActivity::class.java)
            intent.putExtra("Cliente", cliente)
            startActivity(intent)
        }

        binding.btnPosicion.setOnClickListener {
            val intent = Intent(this, GlobalPositionActivity::class.java)
            intent.putExtra("Cliente", cliente)
            startActivity(intent)
        }

        binding.btnMovimientos.setOnClickListener {
            val intent = Intent(this, MovementsActivity::class.java)
            intent.putExtra("Cliente", cliente)
            startActivity(intent)
        }

        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}