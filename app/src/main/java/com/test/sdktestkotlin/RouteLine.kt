package com.test.sdktestkotlin

import android.graphics.Color
import com.beust.klaxon.token.VALUE_TYPE.value
import com.skt.tmap.TMapPoint
import com.skt.tmap.overlay.TMapPolyLine

class RouteLine(rr: RouteResponse) {
    private var routeResponse: RouteResponse = rr
    public fun drawRoute(): Array<TMapPolyLine> {
        var tmp = emptyArray<TMapPolyLine>()
        val itineraries = routeResponse.metaData?.plan?.itineraries?.filter { it -> it.pathType == 1 } // 지하철+도보
        if (itineraries != null) {
            val legs = itineraries[0].legs!!
            for (leg in legs) {
                var t: TMapPolyLine = TMapPolyLine()
                t.addLinePoint(TMapPoint(leg.start.lat, leg.start.lon))
                if (leg.mode == "WALK") {
                    t.lineColor = Color.BLUE
                    for (step in leg.steps!!) {
                        val linestring = step.linestring.split(" ").map { it -> it.split(",") } // "111,222 333,444" -> [(111,222), (333,444)]
                            .associate { (key, value) -> key to value }
                        linestring.forEach{ it -> t.addLinePoint(TMapPoint(it.key.toDouble(), it.value.toDouble()))}
                    }
                } else if (leg.mode == "SUBWAY") {
                    t.lineColor = Color.RED
                    val stationList = leg.passStopList!!.stationList!!
                    for (station in stationList) {
                        t.addLinePoint(TMapPoint(station.lat.toDouble(), station.lon.toDouble()))
                    }
                }
                t.addLinePoint(TMapPoint(leg.end.lat, leg.end.lon))
                tmp.plus(t)
            }
        }
        return tmp
    }
}