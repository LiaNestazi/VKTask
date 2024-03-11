package com.example.vktask

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.example.vktask.ui.theme.VKTaskTheme
import com.example.vktask.view.InternalErrorDialog
import com.example.vktask.view.InternetDialog
import com.example.vktask.view.MainPage
import com.example.vktask.viewmodel.MainViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val context = applicationContext
        val mainViewModel by viewModels<MainViewModel>()
        setContent {
            VKTaskTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    when (isOnline(context)){
                        true -> {
                            when (mainViewModel.errorMessage.value){
                                "" -> {
                                    mainViewModel.getProductsList()
                                    mainViewModel.getAllCategories()
                                    MainPage(mainViewModel = mainViewModel)
                                }
                                else -> {
                                    InternalErrorDialog(
                                        onReloadButtonClick = {
                                            finish()
                                            startActivity(intent) },
                                        errorMsg = mainViewModel.errorMessage.value
                                    )
                                }
                            }
                        }
                        else -> {
                            InternetDialog {
                                finish()
                                startActivity(intent)
                            }
                        }
                    }
                }
            }
        }
    }

    fun isOnline(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                return true
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                return true
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                return true
            }
        }
        return false
    }

}