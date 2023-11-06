package de.xorg.gsapp.admin.data.model

data class SubstitutionApiModelSet(
    val date: String,
    val notes: String,
    val substitutionApiModels: List<SubstitutionApiModel>
) {
    constructor() : this("", "", emptyList())
}