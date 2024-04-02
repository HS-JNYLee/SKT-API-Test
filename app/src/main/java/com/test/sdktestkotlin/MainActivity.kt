package com.test.sdktestkotlin

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.skt.tmap.TMapView
import android.graphics.Color
import android.widget.FrameLayout
import com.skt.tmap.TMapData
import com.skt.tmap.TMapPoint
import com.skt.tmap.overlay.TMapPolyLine
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.RequestBody
import okhttp3.Response
import java.io.IOException

class MainActivity : AppCompatActivity() {
    private lateinit var tMapView: TMapView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // TMapView 초기화 및 AppKey 인증 대기
        drawMap()
        routeApi()
    }

    private fun routeApi() {
        val transitManager = TransitManager(this@MainActivity)

        val routeRequest = RouteRequest(
            startX = "126.926493082645",
            startY = "37.6134436427887",
            endX = "127.126936754911",
            endY = "37.5004198786564",
            lang = 0,
            format = "json",
            count = 10
        )

        transitManager.getRoutes(routeRequest)
    }

    private fun drawMap() {
        // TMapView 초기화
        tMapView = TMapView(this)
        tMapView.setSKTMapApiKey("skiGDsaPMI4lDE0AFUVIIa9cOhymri9g8t7qGxMZ")

        val tMapContainer = findViewById<FrameLayout>(R.id.frameLayout)
        tMapContainer.addView(tMapView)
    }
}


