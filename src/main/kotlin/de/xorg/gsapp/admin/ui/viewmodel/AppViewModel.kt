package de.xorg.gsapp.admin.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import de.xorg.gsapp.admin.data.model.SubstitutionApiModelSet
import de.xorg.gsapp.admin.data.repository.AppRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope
import org.kodein.di.DI
import org.kodein.di.instance

val MULTI_CLASS_REGEX = Regex("([0-9]{1,2}.[0-9])(\\/[0-9])+") //8.1/2/3
val ABITUR_KURS_REGEX = Regex("([0-9]{2})[a-zA-Z][a-zA-Z0-9]+") //25spj2

class AppViewModel(di: DI): ViewModel() {
    private val appRepo: AppRepository by di.instance()

    private val _subs: MutableStateFlow<SubstitutionApiModelSet>
                = MutableStateFlow(SubstitutionApiModelSet())

    var errorLog by mutableStateOf("")


    var dateStr by mutableStateOf("")
    var classesStr by mutableStateOf("")
    var teachersStr by mutableStateOf("")

    fun loadPlan() {
        viewModelScope.launch {
            val start = System.currentTimeMillis()
            val subsResult = appRepo.getSubstitutions()

            if(subsResult.isFailure) {
                errorLog = "${errorLog}Failed to fetch plan: ${subsResult.exceptionOrNull()?.message ?: ""}\n"
                return@launch
            }

            errorLog = "${errorLog}Successfully fetched plan after ${System.currentTimeMillis()-start}ms\n"

            val entries = subsResult.getOrNull()!!.substitutionApiModels


            dateStr = subsResult.getOrNull()!!.date
            classesStr = entries.map { it.klass }
                .filter { !it.contains("#") }
                .flatMap {
                    if(it.matches(MULTI_CLASS_REGEX)) {
                        val rootClass = MULTI_CLASS_REGEX.find(it)!!.groupValues[1]
                        val rootStem = rootClass.split(".")[0]
                        val subClasses = it.replace("${rootClass}/", "")
                            .split("/").map { "${rootStem}.${it}" }
                        listOf(rootClass) + subClasses
                    } else if(it.matches(ABITUR_KURS_REGEX)) {
                        val stammkurs = ABITUR_KURS_REGEX.find(it)!!.groupValues[1]
                        listOf("A${stammkurs}", it)
                    } else {
                        listOf(it)
                    } }
                .distinct()
                .joinToString("　;　")
            teachersStr = entries.map { it.substTeacher }
                                 .distinct()
                                 .filter { it.length > 2 && !it.contains("#") }
                                 .joinToString("　;　")
        }
    }

    fun sendNotification() {
        viewModelScope.launch {
            try {
                val r = appRepo.sendNotificationToFirebase(date = dateStr.trim(),
                    classes = classesStr.replace("　","").trim(),
                    teachers = teachersStr.replace("　", "").trim())
                errorLog = "${errorLog}Push sent: ${r}\n"
            } catch(ex: Exception) {
                errorLog = "${errorLog}Failed to send push: ${ex.message}\n"
            }

        }
    }


}