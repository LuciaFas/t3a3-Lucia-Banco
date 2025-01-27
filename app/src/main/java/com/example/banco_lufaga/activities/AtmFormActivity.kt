package com.example.banco_lufaga.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.banco_lufaga.R
import com.example.banco_lufaga.bd.CajeroApplication
import com.example.banco_lufaga.databinding.ActivityAtmFormBinding
import com.example.banco_lufaga.pojo.CajeroEntity
import com.example.banco_lufaga.pojo.Cliente

class AtmFormActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAtmFormBinding
    private lateinit var cajero: CajeroEntity
    private lateinit var cliente: Cliente
    private var mostrarMenu = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityAtmFormBinding.inflate(layoutInflater)

        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        cliente = intent.getSerializableExtra("Cliente") as Cliente

        val cajeroRecibido = intent.getSerializableExtra("Cajero")

        if (cajeroRecibido!=null) {
            cajero = cajeroRecibido as CajeroEntity
            val toolBar = findViewById<Toolbar>(R.id.appbarCajeros)
            toolBar.setTitle("Visualizar ATM")
            setSupportActionBar(toolBar)

            binding.editDireccion.setText(cajero.direccion)
            binding.editLongitud.setText(cajero.longitud.toString())
            binding.editLatitud.setText(cajero.latitud.toString())

            binding.editDireccion.isEnabled = false
            binding.editLongitud.isEnabled = false
            binding.editLatitud.isEnabled = false

            mostrarMenu = true

            binding.btnGuardar.setOnClickListener {
                var direccion = binding.editDireccion.text.toString()
                var latitud = binding.editLatitud.text.toString()
                var longitud = binding.editLongitud.text.toString()

                if (direccion.isNotEmpty() && latitud.isNotEmpty() && longitud.isNotEmpty()) {
                    Thread{
                        cajero.latitud = latitud.toDouble()
                        cajero.longitud = longitud.toDouble()
                        cajero.direccion = direccion

                        CajeroApplication.database.cajeroDao().updateCajero(cajero)
                    }.start()

                    val intent = Intent(this, AtmListActivity::class.java)
                    intent.putExtra("Cliente", cliente)
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "Los campos no pueden estar vacios", Toast.LENGTH_SHORT).show()
                }
            }

        } else {
            val toolBar = findViewById<Toolbar>(R.id.appbarCajeros)
            toolBar.setTitle("Crear ATM")
            setSupportActionBar(toolBar)

            binding.btnGuardar.setOnClickListener {
                var direccion = binding.editDireccion.text.toString()
                var latitud = binding.editLatitud.text.toString()
                var longitud = binding.editLongitud.text.toString()


                if (direccion.isNotEmpty() && latitud.isNotEmpty() && longitud.isNotEmpty()) {
                    Thread{
                        val nuevoCajero = CajeroEntity(
                            direccion = binding.editDireccion.text.toString(),
                            latitud = binding.editLatitud.text.toString().toDouble(),
                            longitud = binding.editLongitud.text.toString().toDouble(),
                            zoom = ""
                        )
                        CajeroApplication.database.cajeroDao().addCajero(nuevoCajero)
                    }.start()

                    val intent = Intent(this, AtmManagementActivity::class.java)
                    intent.putExtra("Cliente", cliente)
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "Los campos no pueden estar vacios", Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.btnCanc.setOnClickListener {
            finish()
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        if (mostrarMenu) {
            menuInflater.inflate(R.menu.menu_atm, menu)
            return true
        }
        return false
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.eliminar ->{
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Delete ATM")
                builder.setMessage("¿Are you sure you want to delete this ATM?")
                builder.setCancelable(false)

                builder.setPositiveButton("Ok") { dialog, id ->
                    Thread{
                        CajeroApplication.database.cajeroDao().deleteCajero(cajero)
                    }.start()

                    val intent = Intent(this, AtmListActivity::class.java)
                    intent.putExtra("Cliente", cliente)
                    startActivity(intent)
                }

                builder.setNegativeButton("Cancel") { dialog, id ->
                    dialog.dismiss()
                }

                builder.create().show()
                true
            }
            R.id.actualizar ->{
                binding.editDireccion.isEnabled = true
                binding.editLongitud.isEnabled = true
                binding.editLatitud.isEnabled = true

                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

}