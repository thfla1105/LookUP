package Cutout

import Closet.activity.Closet_MainActivity
import Cookie.SaveSharedPreference
import ImageSelect.ImageSelectActivity
import Login_Main.activity.MainActivity
import LookBook.activity.LookBookActivity
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.AsyncTask
import android.os.AsyncTask.execute
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.util.Pair
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.room.CoroutinesRoom.Companion.execute
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import network.RetrofitClient
import network.ServiceApi
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import styleList.RatingActivity
import java.R
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*

//import android.support.annotation.Nullable;
//import android.support.design.widget.FloatingActionButton;
//import android.support.v7.app.AppCompatActivity;
//import android.support.v7.widget.Toolbar;
//import com.github.gabrielbb.cutout.CutOut;
//import petrov.kristiyan.colorpicker_sample.R;
class CropActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private var imageView: ImageView? = null
    var drawerLayout: DrawerLayout? = null
    var navigationView: NavigationView? = null
    var toolbar: Toolbar? = null
    private val tempFile: File? = null
    var photoUri : Uri? = null
    private var service: ServiceApi? = null
    var dialog: ProgressBar? =null
    var bitmap: Bitmap? = null
    val outer=CropActivity()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.cutout_activity_crop)
        //var toolbar = findViewById<Toolbar>(R.id.toolbar)
        //setSupportActionBar(toolbar)
        service = RetrofitClient.getClient().create(ServiceApi::class.java)

        //serverDialog = new ProgressDialog(CutOutActivity.this);
        //  serverDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); //progress bar가 동그란 형태
        // serverDialog.setMessage("사진을 저장중입니다.");

        //메뉴 시작
        drawerLayout = findViewById(R.id.drawer_layout)
        navigationView = findViewById(R.id.nav_view)
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        //로그인 로그아웃 부분
        val menu = navigationView?.getMenu()
        menu?.findItem(R.id.nav_logout)?.isVisible = false
        navigationView?.bringToFront()
        val toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawerLayout?.addDrawerListener(toggle)
        toggle.syncState()
        navigationView?.setNavigationItemSelectedListener(this)
        navigationView?.setCheckedItem(R.id.nav_add)
        //메뉴 끝

        imageView = findViewById(R.id.imageView)
        val imageIconUri = getUriFromDrawable(R.drawable.buttonbg)
        imageView?.setImageURI(imageIconUri)
        imageView?.setTag(imageIconUri)


        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setImageResource(R.drawable.add_icon)
        /*
        fab.setOnClickListener(view -> {
            final Uri testImageUri = getUriFromDrawable(R.drawable.test_image);

            CutOut.activity()
                    .src(testImageUri)
                    .bordered()
                    .noCrop()
                    .start(this);
        });
*/
        //내가 추가함
        fab.setOnClickListener { view: View? ->
           // val intent = Intent(Intent.ACTION_GET_CONTENT)
          //  intent.addCategory(Intent.CATEGORY_OPENABLE)
           // intent.type = "image/*"

           // startActivityForResult(Intent.createChooser(intent, "Select Picture"), CropActivity.REQUEST_GET_SINGLE_FILE)
            val intent = Intent(Intent.ACTION_PICK)
            //intent.addCategory(Intent.CATEGORY_OPENABLE)
            intent.type = "image/*"
            intent.putExtra("crop", true) //기존 코드에 이 줄 추가!
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), CropActivity.PICK_IMAGE_FROM_ALBUM)
        }
        //여기까지

        val uploadBtn=findViewById<Button>(R.id.uploadBtn)
        uploadBtn.setOnClickListener { view: View? ->
            startUpload2();
        }
    }

    fun uri2path(context: Context, contentUri: Uri?): String? {
        val proj = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = context.contentResolver.query(contentUri!!, proj, null, null, null)
        cursor?.moveToNext()
        val path = cursor?.getString(cursor.getColumnIndex(MediaStore.MediaColumns.DATA))
        val uri = Uri.fromFile(File(path))
        cursor?.close()
        return path
    }

    fun getFullPathFromUri(ctx: Context, fileUri: Uri?): String? {
        var fullPath: String? = null
        val column = "_data"
        var cursor: Cursor? = ctx.contentResolver.query(fileUri!!, null, null, null, null)
        Log.e("cursor", cursor.toString())
        if (cursor != null) {
            cursor.moveToFirst()
            var document_id: String = cursor.getString(0)
            if (document_id == null) {
                for (i in 0 until cursor.getColumnCount()) {
                    if (column.equals(cursor.getColumnName(i), ignoreCase = true)) {
                        fullPath = cursor.getString(i)
                        break
                    }
                }
            } else {
                document_id = document_id.substring(document_id.lastIndexOf(":") + 1)
                cursor.close()
                val projection = arrayOf(column)
                try {
                    cursor = ctx.contentResolver.query(
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                            projection, MediaStore.Images.Media._ID + " = ? ", arrayOf(document_id), null)
                    if (cursor != null) {
                        cursor.moveToFirst()
                        fullPath = cursor.getString(cursor.getColumnIndexOrThrow(column))
                    }
                } finally {
                    if (cursor != null) cursor.close()
                }
            }
        }
        return fullPath
    }


    fun getPathFromUri2(uri: Uri?): String? {
        val cursor = contentResolver.query(uri!!, null, null, null, null)
        cursor!!.moveToNext()
        val path = cursor.getString(cursor.getColumnIndex("_data"))
        cursor.close()
        return path
    }

    private fun getTempFile(context: Context, url: String): File? =
            Uri.parse(url)?.lastPathSegment?.let { filename ->
                File.createTempFile(makeName(), ".jpg", context.cacheDir)
            }

    /*
    class GetImageFromUrl : AsyncTask<String?, Bitmap?, Bitmap?>() {
        var bitmap: Bitmap? = null
        protected override fun doInBackground(vararg url: String?): Bitmap? {
            val inputStream: InputStream
            try {
                Log.e("URL", url[0])
                inputStream = URL(url[0]).openStream()
                bitmap = BitmapFactory.decodeStream(inputStream)
            } catch (e: IOException) {
                e.printStackTrace()
            }
            return bitmap!!
        }

        override fun onPostExecute(result: Bitmap?) {
            super.onPostExecute(result)
            //imageView.setImageBitmap(bitmap);
        }
    }

    class SaveDrawingTask2 : AsyncTask<Bitmap?, Pair<File?, Exception?>?, Pair<File?, Exception?>?>() {
        protected override fun doInBackground(vararg bitmaps: Bitmap?): Pair<File?, Exception?>? {
            //try 전까지 내가 추가
            val path = File(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DCIM), "LookUP")
            if (!path.exists()) path.mkdir()
            Log.d("SaveDrawingTaskPath", path.toString())
            var name=makeName()
            try {
                val file = File(path.toString() + "/" + makeName() + ".png")
                FileOutputStream(file).use { out ->
                    bitmaps[0]?.compress(Bitmap.CompressFormat.PNG, 100, out)
                    return Pair(file, null)
                }
            } catch (e: IOException) { //IOException e 임
                e.printStackTrace()
                return Pair(null, e)
            }
        }

        protected override fun onPostExecute(result: Pair<File?, Exception?>?) {
            super.onPostExecute(result)
        }
    }
     */

    private fun startUpload2() {
        Log.d("startUpload2", "startUpload2 함수 시작은 되는구만")
        val imgName = makeImgName(applicationContext)
        ////File file = getNewestFile();
        //val result = CropImage.getActivityResult(data) //0603 추가함
        //Log.e("cropImage", data.toString())
        val result=photoUri
        val outputDir: File = applicationContext.getCacheDir() // context being the Activity pointer
        //val outputFile = File.createTempFile(makeName(), "extension", outputDir)
        val outputFile=getTempFile(applicationContext, photoUri.toString())
        val cacheFile=File(applicationContext.cacheDir, outputFile?.name)
        /*
        var pairResult: Pair<File?, kotlin.Exception?>? = null
        try {
            bitmap = GetImageFromUrl().execute(result.toString()).get()
            Log.e("Bitmaps", bitmap.toString())
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }

        try {
            Thread.sleep(1000 * 1.toLong()) //2초 대기
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }

        try {
            pairResult = SaveDrawingTask2().execute(bitmap).get()
            if (pairResult?.first == null) {
                Toast.makeText(this@CropActivity, "오류가 발생하였습니다.", Toast.LENGTH_SHORT).show()
                val intent = Intent(applicationContext, MainActivity::class.java)
                finish()
            }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
         */
        //var result2=getFullPathFromUri(applicationContext, photoUri)
        //var result3=getPathFromUri2(photoUri)
        Log.e("result1", result.toString())
        //Log.e("File", outputFile.toString())
        //Log.e("File Name", outputFile?.name)
        //Log.e("cache File", cacheFile.toString());
        //Log.e("cache File Name", cacheFile?.name)
        //Log.e("result2", result2)
        //String mimeType = Files.probeContentType(String.valueOf(file));
        val requestBody = RequestBody.create(MediaType.parse("image/*"), outputFile)
        val fileToUpload = MultipartBody.Part.createFormData("upload2bg", imgName, requestBody)
        service?.postImage2bg(fileToUpload, requestBody)?.enqueue(object : Callback<ResponseBody?> {
            override fun onResponse(call: Call<ResponseBody?>, response: Response<ResponseBody?>) { //ResponseBody result = response.body();
                //Toast.makeText(JoinActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();
                if (response.code() == 200) {
                    //if (dialog != null) {
                    //    dialog.dismiss()
                   // }

                    /////Intent intent = new Intent(getApplicationContext(), CutOut_MainActivity.class);
                    /////intent.putExtra("imgFile",String.valueOf(result));
                    /////intent.putExtra("imgName",imgName);

                    /////startActivity(intent);
                }
            }

            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
               // if (dialog != null) {
                  //  dialog.dismiss()
               // }
                Toast.makeText(applicationContext, "req fail", Toast.LENGTH_SHORT).show()
                t.printStackTrace()
                Log.e("파일 업로드 에러 발생", t.message)
            }
        })
    }

    private fun cropImage(uri: Uri?) {
        CropImage.activity(uri).setGuidelines(CropImageView.Guidelines.ON)
                .setCropShape(CropImageView.CropShape.RECTANGLE) //사각형 모양으로 자른다
                .start(this@CropActivity)
    }

    //내가 추가함 start
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            PICK_IMAGE_FROM_ALBUM -> {
                data?.data?.let { uri ->
                    cropImage(uri) //이미지를 선택하면 여기가 실행됨
                }
                photoUri = data?.data
                imageView?.setImageURI(photoUri)
            }
            CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE -> {
                //그후, 이곳으로 들어와 RESULT_OK 상태라면 이미지 Uri를 결과 Uri로 저장!
                val result = CropImage.getActivityResult(data)
                if(resultCode == Activity.RESULT_OK){
                    result.uri?.let {
                        imageView?.setImageBitmap(result.bitmap)
                        imageView?.setImageURI(result.uri)
                        photoUri = result.uri

                    }
                }else if(resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE){
                    val error = result.error
                    Toast.makeText(this@CropActivity, error.message, Toast.LENGTH_SHORT).show()
                }
            }
            else ->{finish()}
        }
        /*
        try {
            if (resultCode == RESULT_OK) {
                if (requestCode == REQUEST_GET_SINGLE_FILE) {
                    Uri selectedImageUri = data.getData();
                    // Get the path from the Uri
                    final String path = getPathFromURI(selectedImageUri);
                    if (path != null) {
                        File f = new File(path);
                        selectedImageUri = Uri.fromFile(f);
                    }
                    // Set the image in ImageView
                    //ImageView((ImageView) findViewById(R.id.imageView)).setImageURI(selectedImageUri);

                    //imageView.setImageURI(selectedImageUri);  //작동됨! 임시 주석


                    CutOut.activity()
                            .src(selectedImageUri)
                            .bordered()
                            .noCrop()
                            .start(this);
                }
            }
        } catch (Exception e) {
            Log.e("FileSelectorActivity", "File select error", e);
        }
         */
    }

    fun getPathFromURI(contentUri: Uri?): String? {
        var res: String? = null
        val proj = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = contentResolver.query(contentUri!!, proj, null, null, null)
        if (cursor!!.moveToFirst()) {
            val column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            res = cursor.getString(column_index)
        }
        cursor.close()
        return res
    }

    //여기까지
    /*    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == CutOut.CUTOUT_ACTIVITY_REQUEST_CODE) {

            switch (resultCode) {
                case Activity.RESULT_OK:
                    Uri imageUri = CutOut.getUri(data);
                    // Save the image using the returned Uri here
                    imageView.setImageURI(imageUri);
                    imageView.setTag(imageUri);
                    break;
                case CutOut.CUTOUT_ACTIVITY_RESULT_ERROR_CODE:
                    Exception ex = CutOut.getError(data);
                    break;
                default:
                    System.out.print("User cancelled the CutOut screen");
            }
        }
    }*/
    fun getUriFromDrawable(drawableId: Int): Uri {
        return Uri.parse("android.resource://" + packageName + "/drawable/" + applicationContext.resources.getResourceEntryName(drawableId))
    }

    override fun onBackPressed() {  //Back 눌렸을 때 어플 꺼지는 거 방지,,
        if (drawerLayout!!.isDrawerOpen(GravityCompat.START)) {
            drawerLayout!!.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(menuitem: MenuItem): Boolean {
        when (menuitem.itemId) {
            R.id.nav_home -> {
                val intent1 = Intent(this@CropActivity, MainActivity::class.java)
                startActivity(intent1)
            }
            R.id.nav_closet -> {
                val intent = Intent(this@CropActivity, Closet_MainActivity::class.java)
                startActivity(intent)
            }
            R.id.nav_add -> {
            }
            R.id.nav_stylerate -> {
                val intent2 = Intent(this@CropActivity, RatingActivity::class.java)
                startActivity(intent2)
            }
            R.id.nav_situation -> {
                val intent3 = Intent(this@CropActivity, ImageSelectActivity::class.java)
                startActivity(intent3)
            }
            R.id.nav_lookbook -> {
                val intent4 = Intent(this@CropActivity, LookBookActivity::class.java)
                startActivity(intent4)
            }
        }
        drawerLayout!!.closeDrawer(GravityCompat.START) //메뉴 선택되면 drawer 닫히도록
        return true
    }
    companion object {
        private const val REQUEST_GET_SINGLE_FILE = 0
        private const val PICK_IMAGE_FROM_ALBUM = 300
        private const val TAG = "lookup"
        fun makeImgName(context: Context?): String {
            val id = SaveSharedPreference.getString(context, "ID")
            val now = System.currentTimeMillis()
            val date = Date(now)
            val mFormat = SimpleDateFormat("yyyyMMdd_HHmmss")
            val time = mFormat.format(date)
            return id + "_" + time + ".png"
        }

        fun makeName(): String? {
            val now = System.currentTimeMillis()
            val date = Date(now)
            val mFormat = SimpleDateFormat("yyyyMMddHHmm")
            val time = mFormat.format(date)
            return "LookUP$time"
        }
    }
}