package com.example.banco_lufaga.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.banco_lufaga.adapters.AccountsAdapter
import com.example.banco_lufaga.adapters.OnClickListener
import com.example.banco_lufaga.bd.MiBancoOperacional
import com.example.banco_lufaga.databinding.FragmentAccountsBinding
import com.example.banco_lufaga.pojo.Cliente
import com.example.banco_lufaga.pojo.Cuenta

class AccountsFragment : Fragment(), OnClickListener {
    private lateinit var binding: FragmentAccountsBinding
    private lateinit var cuentaAdapter: AccountsAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var listener: AccountsListener
    private lateinit var cliente: Cliente

    companion object {
        @JvmStatic
        fun newInstance(c: Cliente) =
            AccountsFragment().apply {
                arguments = Bundle().apply {
                    putSerializable("Cliente", c)
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            cliente = it.getSerializable("Cliente") as Cliente
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val mbo: MiBancoOperacional? = MiBancoOperacional.getInstance(context)

        cuentaAdapter = AccountsAdapter(mbo?.getCuentas(cliente) as ArrayList<Cuenta>?, this)
        binding = FragmentAccountsBinding.inflate(inflater, container, false)
        linearLayoutManager = LinearLayoutManager(context)

        binding.recyclerCuentas.apply {
            layoutManager = linearLayoutManager
            adapter = cuentaAdapter
        }
        return binding.root
    }

    fun setCuentasListener(listener: AccountsListener) {
        this.listener = listener
    }

    override fun onClick(obj: Any) {
        val cuenta: Cuenta = obj as Cuenta

        if (listener != null) {
            listener.onCuentaSeleccionada(cuenta)
        }
    }
}