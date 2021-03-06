package com.example.weatherable.activity

import android.os.Build
import android.os.Bundle
import android.view.Window
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.weatherable.application.appComponent
import com.example.weatherable.ui.screens.gismeteo.DetailGis
import com.example.weatherable.ui.viewmodel.DetailGisViewModel
import dagger.Lazy
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

class DetailGisActivity : AppCompatActivity() {
    @Inject
    lateinit var viewModelFactory: Lazy<ViewModelProvider.Factory>

    @RequiresApi(Build.VERSION_CODES.M)
    @OptIn(ExperimentalCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        appComponent.inject(this)
        val viewModel = ViewModelProvider(
            this,
            viewModelFactory.get()
        )[DetailGisViewModel::class.java]
        setContent {
            DetailGis(viewModel)
        }
    }

}