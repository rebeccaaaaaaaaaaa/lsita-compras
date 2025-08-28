package com.example.supercompras

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

class ListaDeComprasViewModel : ViewModel() {

    // Estado observável (equivalente a um useState([]) no React)
    private val _items = mutableStateListOf<ItemCompra>()
    val items: List<ItemCompra> get() = _items

    fun addItem(texto: String) {
        if (texto.isNotBlank()) {
            _items.add(ItemCompra(texto))
        }
    }

    fun removeItem(item: ItemCompra) {
        _items.remove(item)
    }

    fun toggleComprado(item: ItemCompra) {
        val index = _items.indexOf(item)
        if (index != -1) {
            _items[index] = _items[index].copy(
                foiComprado = !_items[index].foiComprado
            )
        }
    }

    fun editarItem(item: ItemCompra, novoTexto: String) {
        val index = _items.indexOf(item)
        if (index != -1 && novoTexto.isNotBlank()) {
            _items[index] = _items[index].copy(texto = novoTexto)
        }
    }
}
