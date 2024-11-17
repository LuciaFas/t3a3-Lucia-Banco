package com.example.banco_lufaga

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.banco_lufaga.databinding.ItemMovimientoBinding
import com.example.banco_lufaga.pojo.Movimiento
import java.text.SimpleDateFormat

class MovimientoAdapter (private val movimientos: ArrayList<Movimiento>?):
    RecyclerView.Adapter<MovimientoAdapter.ViewHolder>() {

        private lateinit var context: Context

        inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){
            val binding = ItemMovimientoBinding.bind(view)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            context = parent.context
            val view = LayoutInflater.from(context).inflate(R.layout.item_movimiento, parent, false)
            return ViewHolder(view)
        }

        override fun getItemCount(): Int {
            return movimientos!!.size
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val movimiento = movimientos?.get(position)
            val formateador = SimpleDateFormat("dd-MM-yyyy")

            with(holder) {
                binding.txtMov.text = movimiento?.getDescripcion()
                binding.txtInfoMov.text = formateador.format(movimiento?.getFechaOperacion()) + " Importe: " + movimiento?.getImporte().toString()
                binding.txtInfoMov.setTextColor(ContextCompat.getColor(context, R.color.red))
            }
        }
}