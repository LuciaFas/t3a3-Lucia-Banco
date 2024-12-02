package com.example.banco_lufaga.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.banco_lufaga.R
import com.example.banco_lufaga.databinding.ItemListAccountsBinding
import com.example.banco_lufaga.pojo.Cuenta

class AccountsAdapter(private val cuentas: ArrayList<Cuenta>?, private val listener: OnClickListener):
    RecyclerView.Adapter<AccountsAdapter.ViewHolder>() {

    private lateinit var context: Context

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val binding = ItemListAccountsBinding.bind(view)
        fun setListener(cuenta:Cuenta){
            binding.root.setOnClickListener {
                listener.onClick(cuenta)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val view = LayoutInflater.from(context).inflate(R.layout.item_list_accounts, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return cuentas!!.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cuenta = cuentas?.get(position) as Cuenta
        var color:Int

        with(holder) {
            setListener(cuenta)
            binding.txtCuenta.text = cuenta.getBanco() + "-" + cuenta.getSucursal() + "-" + cuenta.getDc() + "-" + cuenta.getNumeroCuenta()
            binding.txtCantidad.text = cuenta.getSaldoActual().toString()

            if (cuenta.getSaldoActual()!! >= 0) {
                color = ContextCompat.getColor(context, R.color.green)
            } else {
                color = ContextCompat.getColor(context, R.color.red)
            }
            binding.txtCantidad.setTextColor(color)
        }
    }
}