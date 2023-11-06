package de.xorg.gsapp.admin.data.repository

import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.Message
import de.xorg.gsapp.admin.data.model.SubstitutionApiModelSet
import de.xorg.gsapp.admin.data.sources.FirebaseSource
import de.xorg.gsapp.admin.data.sources.GsWebsiteDataSource
import org.kodein.di.DI
import org.kodein.di.instance


class AppRepository(di: DI) {
    private val websiteDataSource: GsWebsiteDataSource by di.instance()
    private val firebaseSource: FirebaseSource by di.instance()
    suspend fun getSubstitutions(): Result<SubstitutionApiModelSet> {
        return websiteDataSource.loadSubstitutionPlan()
    }

    suspend fun sendNotificationToFirebase(date: String,
                                           classes: String,
                                           teachers: String, ): String {
        return firebaseSource.sendNotification(date, classes, teachers)
    }
}