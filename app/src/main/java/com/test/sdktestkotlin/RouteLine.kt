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
                val tMapPoints = ArrayList<TMapPoint>()

                tMapPoints.add(TMapPoint(leg.start.lat, leg.start.lon))

                if (leg.mode == "WALK") {
                    if(leg.steps != null) {
                        for (step in leg.steps) {
                            val linestring = step.linestring.split(" ").map { it.split(",") } // "111,222 333,444" -> [(111,222), (333,444)]
                                .associate { (key, value) -> key to value }
                            linestring.forEach{ it ->
                                tMapPoints.add(TMapPoint(it.value.toDouble(), it.key.toDouble()))
                            }
                        }
                    }
                    tMapPoints.add(TMapPoint(leg.end.lat, leg.end.lon))
                    t = TMapPolyLine("line"+(i++), tMapPoints)
                    t.lineColor = Color.BLUE
                } else if (leg.mode == "SUBWAY") {
                    val stationList = leg.passStopList!!.stationList!!
                    for (station in stationList) {
                        tMapPoints.add(TMapPoint(station.lat.toDouble(), station.lon.toDouble()))
                    }
                    tMapPoints.add(TMapPoint(leg.end.lat, leg.end.lon))
                    t = TMapPolyLine("line"+(i++), tMapPoints)
                    t.lineColor = Color.RED
                }
                tmp.add(t)
            }
        }
        return tmp
    }
}