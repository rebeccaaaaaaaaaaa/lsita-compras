package com.example.supercompras

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import com.example.supercompras.ui.theme.SuperComprasTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SuperComprasTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ListaDeCompras(Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun ListaDeCompras(modifier: Modifier = Modifier) {
    val listaDeItems = remember { mutableStateListOf<ItemCompra>() }

    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        ImagemTopo()
        AdicionarItem(aoSalvarItem = { textoNovo ->
            if (textoNovo.isNotBlank()) {
                listaDeItems.add(ItemCompra(texto = textoNovo))
            }
        })
        Spacer(modifier = Modifier.height(24.dp))

        if (listaDeItems.isNotEmpty()) {
            Titulo(text = "Lista de compras")
            ListaDeItems(
                lista = listaDeItems.filter { !it.foiComprado },
                aoMudarStatus = { itemSelecionado ->
                    val index = listaDeItems.indexOf(itemSelecionado)
                    if (index != -1) {
                        listaDeItems[index] =
                            listaDeItems[index].copy(foiComprado = !listaDeItems[index].foiComprado)
                    }
                },
                aoRemoveritem = { itemRemovido ->
                    listaDeItems.remove(itemRemovido)
                },
                aoEditarItem = { itemEditado, novoTexto ->
                    val index = listaDeItems.indexOf(itemEditado)
                    if (index != -1) {
                        listaDeItems[index] = listaDeItems[index].copy(texto = novoTexto)
                    }
                }
            )
        }

        if (listaDeItems.any { it.foiComprado }) {
            Spacer(modifier = Modifier.height(16.dp))
            Titulo(text = "Comprados")
            ListaDeItems(
                lista = listaDeItems.filter { it.foiComprado },
                aoMudarStatus = { itemSelecionado ->
                    val index = listaDeItems.indexOf(itemSelecionado)
                    if (index != -1) {
                        listaDeItems[index] =
                            listaDeItems[index].copy(foiComprado = !listaDeItems[index].foiComprado)
                    }
                },
                aoRemoveritem = { itemRemovido ->
                    listaDeItems.remove(itemRemovido)
                },
                aoEditarItem = { itemEditado, novoTexto ->
                    val index = listaDeItems.indexOf(itemEditado)
                    if (index != -1) {
                        listaDeItems[index] = listaDeItems[index].copy(texto = novoTexto)
                    }
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
    aoEditarItem: (ItemCompra, String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
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
fun Titulo(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text,
        style = MaterialTheme.typography.headlineSmall,
        modifier = modifier.padding(8.dp)
    )
}

@Composable
fun ItemDaLista(
    aoMudarStatus: (item: ItemCompra) -> Unit,
    aoRemoveritem: (item: ItemCompra) -> Unit,
    aoEditarItem: (item: ItemCompra, novoTexto: String) -> Unit,
    item: ItemCompra,
    modifier: Modifier = Modifier
) {
    var emEdicao by remember { mutableStateOf(false) }
    var textoEditado by rememberSaveable { mutableStateOf(item.texto) }

    Column(modifier = modifier.padding(horizontal = 16.dp)) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier.fillMaxWidth()
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
                    if (textoEditado.isNotBlank()) {
                        aoEditarItem(item, textoEditado)
                        emEdicao = false
                    }
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
                    Icone(icone = Icons.Default.Delete)
                }
                IconButton(onClick = { emEdicao = true }) {
                    Icone(icone = Icons.Default.Edit)
                }
            }
        }
    }
}

@Composable
fun ImagemTopo(modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(R.drawable.mercado),
        contentDescription = null,
        modifier = modifier.size(120.dp)
    )
}

@Composable
fun Icone(icone: ImageVector, modifier: Modifier = Modifier) {
    Icon(
        icone,
        contentDescription = null,
        tint = Marinho,
        modifier = modifier.size(20.dp)
    )
}

@Composable
fun AdicionarItem(
    aoSalvarItem: (texto: String) -> Unit,
    modifier: Modifier = Modifier
) {
    var texto by rememberSaveable { mutableStateOf("") }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            value = texto,
            onValueChange = { texto = it },
            placeholder = {
                Text(
                    text = "Digite o item",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
            },
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

data class ItemCompra(
    val texto: String,
    var foiComprado: Boolean = false
)
