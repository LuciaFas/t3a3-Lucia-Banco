package com.example.banco_lufaga

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.banco_lufaga.databinding.ItemCuentaBinding
import com.example.banco_lufaga.pojo.Cuenta

class CuentaAdapter(private val cuentas: ArrayList<Cuenta>?):
    RecyclerView.Adapter<CuentaAdapter.ViewHolder>() {

    private lateinit var context: Context

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val binding = ItemCuentaBinding.bind(view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val view = LayoutInflater.from(context).inflate(R.layout.item_cuenta, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return cuentas!!.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cuenta = cuentas?.get(position)
        var color:Int

        with(holder) {
            binding.txtCuenta.text = cuenta?.getBanco() + "-" + cuenta?.getSucursal() + "-" + cuenta?.getDc() + "-" + cuenta?.getNumeroCuenta()
            binding.txtCantidad.text = cuenta?.getSaldoActual().toString()

            if (cuenta?.getSaldoActual()!! >= 0) {
                color = ContextCompat.getColor(context, R.color.green)
            } else {
                color = ContextCompat.getColor(context, R.color.red)
            }
            binding.txtCantidad.setTextColor(color)
        }
    }
}