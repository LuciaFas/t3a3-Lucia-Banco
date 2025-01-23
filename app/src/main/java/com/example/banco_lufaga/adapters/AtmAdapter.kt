package com.example.banco_lufaga.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.banco_lufaga.R
import com.example.banco_lufaga.databinding.ItemListAtmBinding
import com.example.banco_lufaga.pojo.CajeroEntity

class AtmAdapter(private val cajeros: List<CajeroEntity>, private val listener: OnClickListener):
    RecyclerView.Adapter<AtmAdapter.ViewHolder>() {

    private lateinit var context: Context

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val binding = ItemListAtmBinding.bind(view)
        fun setListener(cajero:CajeroEntity){
            binding.root.setOnClickListener {
                listener.onClick(cajero)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val view = LayoutInflater.from(context).inflate(R.layout.item_list_atm, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return cajeros!!.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cajero = cajeros?.get(position) as CajeroEntity

        with(holder) {
            setListener(cajero)
            binding.txtNumCajero.text = "ATM " + cajero.id
            binding.txtDescripcion.text = cajero.direccion
        }
    }
}