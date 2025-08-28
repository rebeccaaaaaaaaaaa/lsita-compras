package com.example.supercompras

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.supercompras.ui.theme.Coral
import com.example.supercompras.ui.theme.Marinho
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun ListaDeComprasScreen(
    modifier: Modifier = Modifier,
    viewModel: ListaDeComprasViewModel = viewModel()
) {
    val items = viewModel.items

    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize()
    ) {
        ImagemTopo()
        AdicionarItem(
            aoSalvarItem = { viewModel.addItem(it) }
        )
        Spacer(modifier = Modifier.height(24.dp))

        if (items.isNotEmpty()) {
            Titulo("Lista de compras")
            ListaDeItems(
                lista = items.filter { !it.foiComprado },
                aoMudarStatus = { viewModel.toggleComprado(it) },
                aoRemoveritem = { viewModel.removeItem(it) },
                aoEditarItem = { item, novoTexto ->
                    viewModel.editarItem(item, novoTexto)
                }
            )
        }

        if (items.any { it.foiComprado }) {
            Spacer(modifier = Modifier.height(16.dp))
            Titulo("Comprados")
            ListaDeItems(
                lista = items.filter { it.foiComprado },
                aoMudarStatus = { viewModel.toggleComprado(it) },
                aoRemoveritem = { viewModel.removeItem(it) },
                aoEditarItem = { item, novoTexto ->
                    viewModel.editarItem(item, novoTexto)
                }
            )
        }
    }
}

@Composable
fun ListaDeItems(
    lista: List<ItemCompra>,
    aoMudarStatus: (ItemCompra) -> Unit,
    aoRemoveritem: (ItemCompra) -> Unit,
    aoEditarItem: (ItemCompra, String) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        lista.forEach { item ->
            ItemDaLista(
                item = item,
                aoMudarStatus = aoMudarStatus,
                aoRemoveritem = aoRemoveritem,
                aoEditarItem = aoEditarItem
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun ItemDaLista(
    item: ItemCompra,
    aoMudarStatus: (ItemCompra) -> Unit,
    aoRemoveritem: (ItemCompra) -> Unit,
    aoEditarItem: (ItemCompra, String) -> Unit
) {
    var emEdicao by remember { mutableStateOf(false) }
    var textoEditado by rememberSaveable { mutableStateOf(item.texto) }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
    ) {
        Checkbox(
            checked = item.foiComprado,
            onCheckedChange = { aoMudarStatus(item) },
            modifier = Modifier.padding(end = 8.dp)
        )

        if (emEdicao) {
            OutlinedTextField(
                value = textoEditado,
                onValueChange = { textoEditado = it },
                modifier = Modifier.weight(1f),
                singleLine = true
            )
            Button(onClick = {
                aoEditarItem(item, textoEditado)
                emEdicao = false
            }) {
                Text("OK")
            }
        } else {
            Text(
                text = item.texto,
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Start,
                modifier = Modifier.weight(1f)
            )
            IconButton(onClick = { aoRemoveritem(item) }) {
                Icone(Icons.Default.Delete)
            }
            IconButton(onClick = { emEdicao = true }) {
                Icone(Icons.Default.Edit)
            }
        }
    }
}

@Composable
fun Titulo(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.headlineSmall,
        modifier = Modifier.padding(8.dp)
    )
}

@Composable
fun ImagemTopo() {
    Image(
        painter = painterResource(R.drawable.mercado),
        contentDescription = null,
        modifier = Modifier.size(120.dp)
    )
}

@Composable
fun Icone(icone: ImageVector) {
    Icon(
        icone,
        contentDescription = null,
        tint = Marinho,
        modifier = Modifier.size(20.dp)
    )
}

@Composable
fun AdicionarItem(aoSalvarItem: (String) -> Unit) {
    var texto by rememberSaveable { mutableStateOf("") }

    Row(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            value = texto,
            onValueChange = { texto = it },
            placeholder = { Text("Digite o item", color = Color.Gray) },
            modifier = Modifier.weight(1f),
            shape = RoundedCornerShape(24.dp),
            singleLine = true
        )
        Spacer(modifier = Modifier.width(8.dp))
        Button(
            onClick = {
                if (texto.isNotBlank()) {
                    aoSalvarItem(texto)
                    texto = ""
                }
            },
            shape = RoundedCornerShape(24.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Coral,
                contentColor = Color.White
            )
        ) {
            Text("Salvar")
        }
    }
}
