package com.example.banco_lufaga.fragments

import com.example.banco_lufaga.pojo.Cuenta

interface AccountsListener {
    fun onCuentaSeleccionada(c: Cuenta)
}