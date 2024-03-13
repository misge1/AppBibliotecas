package com.mirena.appbibliotecas.notifications

import android.util.Log
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.mirena.appbibliotecas.SessionManager
import com.mirena.appbibliotecas.objects.DispositivosUsuarios
import com.mirena.appbibliotecas.retrofit.RetrofitInstance

class FirebaseService: FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Log.d("messageFrom", "From: ${remoteMessage.from}")

        // Check if message contains a data payload.
        if (remoteMessage.data.isNotEmpty()) {
            Log.d("messagePay", "Message data payload: ${remoteMessage.data}")

        }

        // Check if message contains a notification payload.
        remoteMessage.notification?.let {
            Log.d("messageNotif", "Message Notification Body: ${it.body}")
        }

    }

    override fun onNewToken(token: String) {
        Log.d("TAG token", "Refreshed token: $token")

        val sessionManager = SessionManager(this)
       if(sessionManager.fetchAuthToken()!=0){
           var dispositivosUsuario = DispositivosUsuarios(token, sessionManager.fetchAuthToken())
           RetrofitInstance.api.saveDispositivoToken(dispositivosUsuario)
       }

    }

}