package com.mirena.appbibliotecas.ui.SplashScreen

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.ViewModelProvider
import com.mirena.appbibliotecas.R
import com.mirena.appbibliotecas.SessionManager
import com.mirena.appbibliotecas.retrofit.RetrofitRepository
import com.mirena.appbibliotecas.ui.MainActivity.ScrollingActivity

class SplashScreen : AppCompatActivity() {
    private lateinit var sessionManager: SessionManager
    private lateinit var retrofitrepository: RetrofitRepository
    private lateinit var splashScreenViewModel: SplashScreenViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        sessionManager = SessionManager(this)
        retrofitrepository = RetrofitRepository(this)
        splashScreenViewModel = ViewModelProvider(this)[SplashScreenViewModel::class.java]

        Handler(Looper.getMainLooper()).post {
            if(isNetworkAvailable(applicationContext)){

                //splashScreenViewModel.getGeneros()
                //splashScreenViewModel.getLibrosRandom()
                val intent = Intent(this, ScrollingActivity::class.java)
                startActivity(intent)
                this.finish()

            }

        }
    }


    /**
     * función para comprobar que hay internet
     */
    private fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val nw      = connectivityManager.activeNetwork ?: return false
            val actNw = connectivityManager.getNetworkCapabilities(nw) ?: return false
            return when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                //for other device how are able to connect with Ethernet
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                //for check internet over Bluetooth
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) -> true
                else -> false
            }
        } else {
            val nwInfo = connectivityManager.activeNetworkInfo ?: return false
            return nwInfo.isConnected
        }
    }
}