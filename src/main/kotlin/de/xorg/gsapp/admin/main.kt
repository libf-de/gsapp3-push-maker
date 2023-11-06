package de.xorg.gsapp.admin

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.example.compose.AppTheme
import de.xorg.gsapp.admin.data.repository.AppRepository
import de.xorg.gsapp.admin.data.sources.FirebaseSource
import de.xorg.gsapp.admin.data.sources.GsWebsiteDataSource
import de.xorg.gsapp.admin.ui.viewmodel.AppViewModel
import org.kodein.di.bind
import org.kodein.di.compose.localDI
import org.kodein.di.compose.withDI
import org.kodein.di.instance
import org.kodein.di.singleton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun App() = withDI({
    bind<GsWebsiteDataSource>() with singleton { GsWebsiteDataSource(di) }
    bind<AppRepository>() with singleton { AppRepository(di) }
    bind<FirebaseSource>() with singleton { FirebaseSource(di) }
    bind<AppViewModel>() with singleton { AppViewModel(di) }
}) {
    val viewModel: AppViewModel by localDI().instance()

    println(System.getProperty("user.dir"))

    var text by remember { mutableStateOf("Hello, World!") }

    AppTheme {
        Scaffold(
            /*topBar = {
                TopAppBar(
                    title = { Text("GSApp Push Maker") },
                    modifier = Modifier
                )
            }*/
        ) { padVal ->
            Column(
                modifier = Modifier.padding(padVal).padding(18.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Card(modifier = Modifier) {
                    Row(
                        modifier = Modifier
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "1. Plan laden",
                             style = MaterialTheme.typography.titleLarge,
                             modifier = Modifier.padding(top = 0.dp))
                        Spacer(Modifier.weight(1f))
                        Button(modifier = Modifier,
                            onClick = { viewModel.loadPlan() }) {
                            Text("Plan laden")
                        }
                    }
                }

                Card(modifier = Modifier) {
                    Column(
                        modifier = Modifier
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                            .fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(text = "2. Daten prüfen",
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier)
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(text = "Datum", style = MaterialTheme.typography.bodyLarge,
                                 modifier = Modifier.width(72.dp).alignByBaseline())
                            OutlinedTextField(value = viewModel.dateStr,
                                      onValueChange = { viewModel.dateStr = it },
                                      modifier = Modifier.fillMaxWidth().alignByBaseline()
                            )
                        }
                        Row() {
                            Text(text = "Klassen", style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.width(72.dp).alignByBaseline())
                            OutlinedTextField(value = viewModel.classesStr,
                                onValueChange = {
                                    viewModel.classesStr = it.replace(" ", "　")
                                        /*.replace(Regex("(?:[a-zA-Z0-9])　?;(?:[a-zA-Z0-9])|(?:[a-zA-Z0-9]);　(?:[a-zA-Z0-9])"), "　;　")*/
                                                },
                                modifier = Modifier.fillMaxWidth().height(96.dp).alignByBaseline()
                            )
                        }
                        Row() {
                            Text(text = "Lehrer", style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.width(72.dp).alignByBaseline())
                            OutlinedTextField(value = viewModel.teachersStr,
                                onValueChange = { viewModel.teachersStr = it.replace(" ", "　") },
                                modifier = Modifier.fillMaxWidth().height(96.dp).alignByBaseline()
                            )
                        }
                    }
                }

                Card(modifier = Modifier) {
                    Row(
                        modifier = Modifier
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(text = "3. Push senden",
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier.padding(top = 0.dp))
                        Spacer(Modifier.weight(1f))
                        Button(modifier = Modifier,
                            enabled = viewModel.dateStr.isNotBlank(),
                            onClick = { viewModel.sendNotification() }) {
                            Text("Senden", lineHeight = 10.sp)
                        }
                    }
                }

                Card(modifier = Modifier.fillMaxHeight()) {
                    Column(
                        modifier = Modifier
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(text = "Protokoll",
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier.padding(top = 0.dp))
                        OutlinedTextField(value = viewModel.errorLog,
                            onValueChange = { },
                            modifier = Modifier.fillMaxSize())
                    }
                }

            }
        }
    }
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication,
           title = "GSApp Push Maker",
           icon = rememberVectorPainter(Icons.Default.Notifications)) {
        App()
    }
}
