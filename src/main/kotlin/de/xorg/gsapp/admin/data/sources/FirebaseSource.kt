package de.xorg.gsapp.admin.data.sources

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.Message
import org.kodein.di.DI
import java.io.FileInputStream




class FirebaseSource {
    init {
        val serviceAccount = FileInputStream("auth.json")

        val options: FirebaseOptions = FirebaseOptions.builder()
            .setCredentials(GoogleCredentials.fromStream(serviceAccount))
            .build()

        FirebaseApp.initializeApp(options)
    }

    fun sendNotification(date: String,
                         classes: String,
                         teachers: String): String {
        val topic = "substitutions"

        val message: Message = Message.builder()
            .putData("classes", classes)
            .putData("teachers", teachers)
            .putData("date", date)
            .setTopic(topic)
            .build()

        val response = FirebaseMessaging.getInstance().send(message)

        println("Successfully sent message: $response")

        return response
    }
}