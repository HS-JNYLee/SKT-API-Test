package com.test.sdktestkotlin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.skt.tmap.TMapView
import android.widget.FrameLayout

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
            startX = "126.98510375",
            startY = "37.56648210",
            endX = "127.01035102",
            endY = "37.58178633",
            lang = 0,
            format = "json",
            count = 10
        )
        transitManager.getRoutes(routeRequest).observe(this@MainActivity) { routeResponse ->
            val lines = RouteLine(routeResponse).drawRoute()

            for (tLine in lines) {
                tMapView.addTMapPolyLine(tLine)
            }
        }
    }

    private fun drawMap() {
        // TMapView 초기화
        tMapView = TMapView(this)
        tMapView.setSKTMapApiKey("skiGDsaPMI4lDE0AFUVIIa9cOhymri9g8t7qGxMZ")

        val tMapContainer = findViewById<FrameLayout>(R.id.frameLayout)
        tMapContainer.addView(tMapView)
    }
}


