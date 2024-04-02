import com.test.sdktestkotlin.RouteRequest
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface TransitService {
    @Headers(
        "accept: application/json",
        "content-type: application/json",
    )
    @POST("transit/routes")
    fun getRoutes(@Header("appKey") appKey: String, @Body body: RouteRequest): Call<ResponseBody>
}
