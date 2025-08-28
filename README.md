# 🛒 SuperCompras  

Um aplicativo Android desenvolvido em **Kotlin** utilizando **Jetpack Compose** para a interface moderna e **MVVM (Model-View-ViewModel)** para separar lógica de negócio da camada de UI.  

O objetivo é gerenciar uma lista de compras de forma simples e organizada.  

---

## 📌 Tecnologias  

- **Kotlin** – Linguagem principal  
- **Jetpack Compose** – UI declarativa  
- **Material 3** – Componentes visuais  
- **AndroidX Lifecycle ViewModel** – Gerenciamento de estado  
- **MVVM** – Arquitetura para separar lógica e UI  

---

## 📂 Estrutura do Projeto  

```bash
app/
 ├── java/com/example/supercompras/
 │    ├── MainActivity.kt              # Ponto de entrada
 │    ├── ui/
 │    │    └── ListaDeComprasScreen.kt # Tela com Jetpack Compose
 │    ├── viewmodel/
 │    │    └── ListaDeComprasViewModel.kt # Lógica da lista (MVVM)
 │    └── model/
 │         └── Produto.kt              # Exemplo de entidade de domínio
 └── res/
      └── values/...

```

## ⚡ Configuração do Gradle

O projeto já está configurado para Jetpack Compose.

No build.gradle.kts (Module: app) temos:

```bash
dependencies {
    // Core
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)

    // Jetpack Compose
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    // ViewModel integrado ao Compose (MVVM)
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.6")

    // Testes
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}

```
No ListaDeComprasScreen.kt use:

import androidx.lifecycle.viewmodel.compose.viewModel

## 🏗️ Como rodar

Clone o repositório:

git clone https://github.com/seuusuario/supercompras.git
cd supercompras


Abra no Android Studio (versão Flamingo ou superior recomendada).

Construa o projeto:

./gradlew build


Rode no emulador ou dispositivo físico.

## 🧩 Exemplo de MVVM

```bash
ViewModel (ListaDeComprasViewModel.kt)
class ListaDeComprasViewModel : ViewModel() {
    private val _itens = mutableStateListOf<String>()
    val itens: List<String> = _itens

    fun adicionarItem(item: String) {
        if (item.isNotBlank()) _itens.add(item)
    }

    fun removerItem(item: String) {
        _itens.remove(item)
    }
}
```

```bash
View (ListaDeComprasScreen.kt)
@Composable
fun ListaDeComprasScreen(viewModel: ListaDeComprasViewModel = viewModel()) {
    val itens = viewModel.itens

    Column {
        Text("Minha Lista de Compras")
        LazyColumn {
            items(itens) { item ->
                Text(item)
            }
        }
        Button(onClick = { viewModel.adicionarItem("Novo Item") }) {
            Text("Adicionar")
        }
    }
}
```

## 🤝 Contribuindo

Fork este repositório

Crie uma branch: git checkout -b minha-feature

Commit suas alterações: git commit -m "feat: minha nova feature"

Envie para o repositório: git push origin minha-feature

Abra um Pull Request 🚀
