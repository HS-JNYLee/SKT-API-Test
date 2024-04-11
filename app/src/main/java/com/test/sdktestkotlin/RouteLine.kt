package com.test.sdktestkotlin

import android.graphics.Color
import com.skt.tmap.TMapPoint
import com.skt.tmap.overlay.TMapPolyLine

class RouteLine(rr: RouteResponse) {
    private var routeResponse: RouteResponse = rr
    fun drawRoute(): ArrayList<TMapPolyLine> {
        val tmp = ArrayList<TMapPolyLine>()
        val itineraries = routeResponse.metaData?.plan?.itineraries?.filter { it.pathType == 1 } // 지하철+도보
        if (itineraries != null) {
            val legs = itineraries[0].legs!!
            var i = 1
            for (leg in legs) {
                var t = TMapPolyLine()
                var color = Color.RED
                val tMapPoints = ArrayList<TMapPoint>()
                tMapPoints.add(TMapPoint(leg.start.lat, leg.start.lon))
                if (leg.mode == "WALK" && leg.steps != null) { // 경로가 도보일 때
                    leg.steps.forEach { step ->
                        step.linestring.split(" ") // "111,222 333,444" -> ["111,222", "333,444"]
                            .map { it.split(",") } // ["111,222", "333,444"] -> ["111", "222"], ["333", "444"]
                            .associate { (key, value) -> key to value } // ["111", "222"], ["333", "444"] -> [("111", "222"), ("333", "444")]
                            .forEach {
                                tMapPoints.add(
                                    TMapPoint(
                                        it.value.toDouble(), // longitude
                                        it.key.toDouble() // latitude
                                    )
                                )
                            }
                    }
                    color = Color.RED
                } else if (leg.mode == "SUBWAY") { // 경로가 지하철일 때
                    leg.passStopList!!.stationList!!.forEach {
                        tMapPoints.add(
                            TMapPoint(
                                it.lat.toDouble(), // longitude
                                it.lon.toDouble() // latitude
                            )
                        )
                    }
                    color = Color.BLUE
                }
                tMapPoints.add(TMapPoint(leg.end.lat, leg.end.lon))
                t = TMapPolyLine("line" + (i++), tMapPoints)
                t.lineColor = color
                tmp.add(t)
            }
        }
        return tmp
    }
}