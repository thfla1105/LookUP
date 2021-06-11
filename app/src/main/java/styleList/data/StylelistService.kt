package styleList.data
import retrofit2.Response
import retrofit2.http.GET

interface StylelistService {
    @GET("/feed/monster_data.json")
    //@GET("/mj_nodejs/SR_StyleList/json_data/monster_json.json")
    suspend fun getStylelistData(): Response<List<Stylelist>>



}