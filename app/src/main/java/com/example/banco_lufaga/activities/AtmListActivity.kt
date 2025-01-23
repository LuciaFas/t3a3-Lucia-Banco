package com.example.banco_lufaga.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.banco_lufaga.R
import com.example.banco_lufaga.adapters.AtmAdapter
import com.example.banco_lufaga.adapters.OnClickListener
import com.example.banco_lufaga.bd.CajeroApplication
import com.example.banco_lufaga.databinding.ActivityAtmListBinding
import com.example.banco_lufaga.pojo.CajeroEntity
import com.example.banco_lufaga.pojo.Cliente

class AtmListActivity : AppCompatActivity(), OnClickListener {
    private lateinit var binding: ActivityAtmListBinding
    private lateinit var atmAdapter: AtmAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager
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

        cliente = intent.getSerializableExtra("Cliente") as Cliente

        setContentView(binding.root)
        linearLayoutManager = LinearLayoutManager(this)

        var cajeros: List<CajeroEntity>

        Thread{
            cajeros = CajeroApplication.database.cajeroDao().getAllCajeros()
            atmAdapter = AtmAdapter(cajeros, this)

            binding.recyclerCajeros.apply {
                layoutManager = linearLayoutManager
                adapter = atmAdapter
            }
        }.start()
    }

    override fun onClick(obj: Any) {
        val cajero: CajeroEntity = obj as CajeroEntity

        val intent = Intent(this, AtmFormActivity::class.java)
        intent.putExtra("Cajero", cajero)
        intent.putExtra("Cliente", cliente)
        startActivity(intent)
    }
}