package com.example.banco_lufaga.activities

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.banco_lufaga.R
import com.example.banco_lufaga.adapters.AtmAdapter
import com.example.banco_lufaga.bd.CajeroApplication
import com.example.banco_lufaga.databinding.ActivityAtmListBinding
import com.example.banco_lufaga.pojo.CajeroEntity
import com.example.banco_lufaga.pojo.Cliente

class AtmListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAtmListBinding
    private lateinit var atmAdapter: AtmAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager
    //private lateinit var listener: AccountsListener
    private lateinit var cliente: Cliente

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityAtmListBinding.inflate(layoutInflater)

        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setContentView(binding.root)
        linearLayoutManager = LinearLayoutManager(this)

        var cajeros: List<CajeroEntity>

        Thread{
            cajeros = CajeroApplication.database.cajeroDao().getAllCajeros()
            atmAdapter = AtmAdapter(cajeros, /*this*/)

            binding.recyclerCajeros.apply {
                layoutManager = linearLayoutManager
                adapter = atmAdapter
            }
        }.start()
    }
}