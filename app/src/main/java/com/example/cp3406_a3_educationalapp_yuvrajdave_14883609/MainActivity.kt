package com.example.cp3406_a3_educationalapp_yuvrajdave_14883609

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.cp3406_a3_educationalapp_yuvrajdave_14883609.navigation.ClimateQuestApp
import com.example.cp3406_a3_educationalapp_yuvrajdave_14883609.ui.theme.CP3406_A3EducationalApp_YuvrajDave_14883609Theme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            CP3406_A3EducationalApp_YuvrajDave_14883609Theme {
                ClimateQuestApp()
            }
        }
    }
}