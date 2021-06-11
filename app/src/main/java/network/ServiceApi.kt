package network

import Category.data.CategoryData
import Category.data.CategoryResponse
import Category.data.SaveCategoryData
import Category.data.SaveCategoryResponse
import Closet.data.*
import Color.data.ColorData
import Color.data.ColorResponse
import Color.data.ToneData
import Color.data.ToneResponse
import Cutout.data.InfoData
import Cutout.data.InfoResponse
import ImageSelect.PostItemData
import ImageSelect.PostItemDataResponse
import Login_Main.data.*
import LookBook.LookBookData.LookBookData
import LookBook.LookBookData.LookBookResponse
import LookBook.LookBookResultData.LookBookResultData
import LookBook.LookBookResultData.LookBookResultResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*
import styleList.data.RatingData
import styleList.data.RatingResponse
import styleList.data.Stylelist

//로그인, 회원가입 관련
interface ServiceApi {
    //로그인 routes->login.js
    @POST("/user/login")
    fun userLogin(@Body data: LoginData?): Call<LoginResponse?>?

    //회원가입 routes->login.js
    @POST("/user/join")
    fun userJoin(@Body data: JoinData?): Call<JoinResponse?>?

    //회원가입 때 ID중복체크 routes->login.js
    @POST("/user/dup")
    fun userCheckDup(@Body data: DupCheckData?): Call<DupCheckResponse?>?

    //이거 사용 안함!!!!!-사진 업로드 하기 위한 정보(ID, 파일 이름) 업로드 routes->upload.js
    @POST("/upload/info")
    fun postImageInfo(@Body data: InfoData?): Call<InfoResponse?>?

    //사진 업로드(어떤 옷인지 파악하여 옷장에 저장) routes->upload.js
    @Multipart
    @POST("/upload/pic")
    fun postImage(@Part image: MultipartBody.Part?, @Part("upload") name: RequestBody?): Call<ResponseBody?>?

    //openCV 배경제거 위해 사진 업로드(어떤 옷인지 파악하여 옷장에 저장) routes->upload2bg.js
    @Multipart
    @POST("/upload2bg/pic2")
    fun postImage2bg(@Part image: MultipartBody.Part?, @Part("upload2bg") name: RequestBody?): Call<ResponseBody?>?


    //카테고리 종류 갖고오기 routes->category.js
    @POST("/category/model")
    fun findCategory(@Body data: CategoryData?): Call<CategoryResponse?>?

    //카테고리 db에 저장하기 routes->closet.js
    @POST("/category/save")
    fun saveCategory(@Body data: SaveCategoryData?): Call<SaveCategoryResponse?>?

    //Closet에서 사진 url들 갖고올 때 사용
    @POST("/closet/image")
    fun getClosetImages(@Body data: ImageData?): Call<ImageResponse?>?

    //Closet FullImageActivity에서 삭제할 때 사용
    @POST("/closet/delete")
    fun deleteImage(@Body data: DeleteData?): Call<DeleteResponse?>?

    //Closet FullImageActivity에서 수정할 때 사용
    @POST("/closet/modify")
    fun modifyImage(@Body data: ModifyData?): Call<ModifyResponse?>?

    //Call<List<ImageResponse>> getClosetImages(@Body ImageData data);
    @POST("/color/color")
    fun userCheckColor(@Body data: ColorData?): Call<ColorResponse?>?

    /* @POST("/color1/color1")
    Call<ColorResponse> userCheckColor1(@Body ColorData data);

    @POST("/color1/color2")
    Call<ColorResponse> userCheckColor2(@Body ColorData data);
*/
    @POST("/color1/color1")
    fun userCheckColor123(@Body data: ColorData?): Call<ColorResponse?>?

    @POST("/tone1/tone1")
    fun userCheckTone123(@Body data: ToneData?): Call<ToneResponse?>?

    @POST("/tone2/tone2")
    fun userCheckTone2(@Body data: ToneData?): Call<ToneResponse?>?

    @POST("/tone3/tone3")
    fun userCheckTone3(@Body data: ToneData?): Call<ToneResponse?>?


    @GET("/style")
    suspend fun getStylelistData(@Query("userId") userId:String): Response<List<Stylelist>>





    @GET("/rating")
    fun userStyleRating(@Query("imageID") imageID: Int, @Query("userId") userId:String): Call<RatingResponse?>?

    @POST("/rating/update")
    fun updateStyleRating(@Body data: RatingData?): Call<RatingResponse?>?

    @GET("/style/select")
    fun getPostItemData(@Query("userId") userId:String,@Query("Purpose") Purpose:Int): Call<PostItemDataResponse>


    @POST("/style/update")
    fun setStylePurpose(@Body data: PostItemData): Call<PostItemDataResponse?>?



    //LookBook
    @POST("/lookbook")
    fun getCoordiList(@Body data: LookBookData?): Call<LookBookResponse?>?

   // @POST("/lookbook")
   // fun getCoordiList(@Body data: LookBookData?): Call<LookBookResponse2?>?

    //@POST("/lookbook")
     //fun getCoordiList(@Body data: LookBookData?): Call<JSONObject?>?

    @POST("/lookbook/result")
    fun getUrlsList(@Body data: LookBookResultData?): Call<LookBookResultResponse?>?

    //스타일별로 필터링해서 보여주기
    @GET("/rating/byname")
    fun getStyleName(@Query("style") style:String) : Response<List<Stylelist>>
    // fun getStyleImages(@Body data: ImageData?): Call<ImageResponse?>?

}