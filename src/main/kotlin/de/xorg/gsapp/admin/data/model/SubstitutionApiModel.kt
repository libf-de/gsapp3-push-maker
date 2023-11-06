package de.xorg.gsapp.admin.data.model

data class SubstitutionApiModel(
    val klass: String,
    val lessonNr: String,
    val origSubject: String,
    val substTeacher: String,
    val substRoom: String,
    val substSubject: String,
    val notes: String,
    val isNew: Boolean
)
