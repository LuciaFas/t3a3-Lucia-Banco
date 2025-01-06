package com.example.banco_lufaga.fragments

import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.banco_lufaga.R
import com.example.banco_lufaga.adapters.MovementsAdapter
import com.example.banco_lufaga.adapters.OnClickListener
import com.example.banco_lufaga.bd.MiBancoOperacional
import com.example.banco_lufaga.databinding.FragmentAccountsMovementBinding
import com.example.banco_lufaga.pojo.Cuenta
import com.example.banco_lufaga.pojo.Movimiento
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.text.SimpleDateFormat

class AccountsMovementFragment : Fragment(), OnClickListener {
    private lateinit var binding: FragmentAccountsMovementBinding
    private lateinit var movementsAdapter: MovementsAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var itemDecoration: DividerItemDecoration
    private lateinit var cuenta: Cuenta

    companion object {
        @JvmStatic
        fun newInstance(c: Cuenta) =
            AccountsMovementFragment().apply {
                arguments = Bundle().apply {
                    putSerializable("Cuenta", c)
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            cuenta = it.getSerializable("Cuenta") as Cuenta
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAccountsMovementBinding.inflate(inflater, container, false)
        linearLayoutManager = LinearLayoutManager(context)
        itemDecoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)

        val mbo: MiBancoOperacional? = MiBancoOperacional.getInstance(context)

        var cuenta = arguments?.getSerializable("Cuenta")
        var tipoCuenta = arguments?.getSerializable("TipoCuenta")

        if (cuenta != null) {
            cuenta = cuenta as Cuenta

            when (tipoCuenta) {
                "zero" -> movementsAdapter = MovementsAdapter(mbo?.getMovimientosTipo(cuenta, 0) as ArrayList<Movimiento>, this)
                "one" -> movementsAdapter = MovementsAdapter(mbo?.getMovimientosTipo(cuenta, 1) as ArrayList<Movimiento>, this)
                "two" -> movementsAdapter = MovementsAdapter(mbo?.getMovimientosTipo(cuenta, 2) as ArrayList<Movimiento>, this)
                else -> movementsAdapter = MovementsAdapter(mbo?.getMovimientos(cuenta) as ArrayList<Movimiento>, this)
            }

            binding.recyclerMovimientos.apply {
                layoutManager = linearLayoutManager
                adapter = movementsAdapter
                addItemDecoration(itemDecoration)
            }
        }
        return binding.root
    }

    override fun onClick(obj: Any) {
        val movimiento: Movimiento = obj as Movimiento
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