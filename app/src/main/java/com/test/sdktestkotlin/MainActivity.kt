package com.test.sdktestkotlin

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.skt.tmap.TMapView
import android.widget.FrameLayout
import android.widget.LinearLayout
import com.skt.tmap.TMapData
import com.skt.tmap.poi.TMapPOIItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class MainActivity : AppCompatActivity() {
    private lateinit var tMapView: TMapView
    private lateinit var start: EditText
    private lateinit var end: EditText
    private lateinit var search: Button
    private lateinit var ll: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        start = findViewById(R.id.start)
        end = findViewById(R.id.end)
        search = findViewById(R.id.search)
        ll = findViewById(R.id.ll)

        search.setOnClickListener {
            var a = TMapPOIItem()
            var b = TMapPOIItem()
            runBlocking {
                launch(Dispatchers.IO) {
                    a = TMapData().findAllPOI(start.text.toString(), 1)[0]
                }
                launch(Dispatchers.IO) {
                    b = TMapData().findAllPOI(end.text.toString(), 1)[0]
                }
            }
            routeApi(a, b)
        }

        // TMapView 초기화 및 AppKey 인증 대기
        drawMap()
    }

    private fun routeApi(start: TMapPOIItem, end: TMapPOIItem) {
        val transitManager = TransitManager(this@MainActivity)

        val routeRequest = RouteRequest(
            startX = start.frontLon,
            startY = start.frontLat,
            endX = end.frontLon,
            endY = end.frontLat,
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
        ll.bringToFront()
    }
}


