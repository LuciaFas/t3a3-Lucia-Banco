package com.example.banco_lufaga.activities

import android.content.DialogInterface
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
import android.widget.TextView
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.banco_lufaga.R
import com.example.banco_lufaga.adapters.MovementsAdapter
import com.example.banco_lufaga.adapters.OnClickListener
import com.example.banco_lufaga.pojo.Movimiento
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.text.SimpleDateFormat

class MovementsActivity : AppCompatActivity() {
    private lateinit var movimientoAdapter: MovementsAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var itemDecoration: DividerItemDecoration


    private lateinit var binding: ActivityMovementsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val mbo: MiBancoOperacional? = MiBancoOperacional.getInstance(this)
        val cliente: Cliente = intent.getSerializableExtra("Cliente") as Cliente

        binding = ActivityMovementsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        linearLayoutManager = LinearLayoutManager(this)
        itemDecoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)

        val cuentas: ArrayList<Cuenta> = mbo?.getCuentas(cliente) as ArrayList<Cuenta>
        val adapterSpinner = ArrayAdapter(this, android.R.layout.simple_spinner_item, cuentas)
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spCuentasMov.adapter = adapterSpinner

        binding.spCuentasMov.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedItem = parent?.getItemAtPosition(position) as Cuenta

                binding.recyclerMovimientos.apply {
                    layoutManager = linearLayoutManager
                    movimientoAdapter = MovementsAdapter(
                        mbo.getMovimientos(selectedItem) as ArrayList<Movimiento>,
                        object : OnClickListener {
                            override fun onClick(obj: Any) {
                                val movimiento = obj as Movimiento
                                val dialogView = layoutInflater.inflate(R.layout.dialog_movement, null)

                                val txtFecha = dialogView.findViewById<TextView>(R.id.txtFecha)
                                val txtId = dialogView.findViewById<TextView>(R.id.txtId)
                                val txtTipo = dialogView.findViewById<TextView>(R.id.txtTipo)
                                val txtDesc = dialogView.findViewById<TextView>(R.id.txtDescripcion)
                                val txtImporte = dialogView.findViewById<TextView>(R.id.txtImporte)
                                val formateador = SimpleDateFormat("dd/MM/yyyy")
                                val fecha = formateador.format(movimiento.getFechaOperacion())

                                txtFecha.text = "Fecha Operacion: ${fecha}"
                                txtId.text = "ID: ${movimiento.getId()}"
                                txtDesc.text = "Descripción: ${movimiento.getDescripcion()}"
                                txtTipo.text = "Tipo: ${movimiento.getTipo()}"
                                txtImporte.text = "Importe: ${movimiento.getImporte()}"

                                context?.let {
                                    MaterialAlertDialogBuilder(it)
                                        .setTitle("Detalle del movimiento")
                                        .setView(dialogView)
                                        .setPositiveButton("Aceptar", DialogInterface.OnClickListener { dialog, i ->
                                            dialog.cancel()
                                        })
                                        .setCancelable(false)
                                        .show()
                                }
                            }
                        }
                    )
                    adapter = movimientoAdapter
                    addItemDecoration(itemDecoration)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}