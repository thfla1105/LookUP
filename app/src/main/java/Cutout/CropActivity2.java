package Cutout;
/*
import Closet.activity.Closet_MainActivity;
import Cookie.SaveSharedPreference;
import ImageSelect.ImageSelectActivity;
import Login_Main.activity.MainActivity;
import LookBook.activity.LookBookActivity;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore.Images.Media;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.drawerlayout.widget.DrawerLayout.DrawerListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.navigation.NavigationView.OnNavigationItemSelectedListener;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImage.ActivityResult;
import com.theartofdev.edmodo.cropper.CropImageView.CropShape;
import com.theartofdev.edmodo.cropper.CropImageView.Guidelines;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import network.RetrofitClient;
import network.ServiceApi;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.MultipartBody.Part;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import styleList.RatingActivity;

@Metadata(
        mv = {1, 1, 16},
        bv = {1, 0, 3},
        k = 1,
        d1 = {"\u0000\u008c\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u0000 Q2\u00020\u00012\u00020\u0002:\u0001QB\u0005¢\u0006\u0002\u0010\u0003J\u0012\u00101\u001a\u0002022\b\u00103\u001a\u0004\u0018\u00010\"H\u0002J\u001a\u00104\u001a\u0004\u0018\u0001052\u0006\u00106\u001a\u0002072\b\u00108\u001a\u0004\u0018\u00010\"J\u0012\u00109\u001a\u0004\u0018\u0001052\b\u0010:\u001a\u0004\u0018\u00010\"J\u0012\u0010;\u001a\u0004\u0018\u0001052\b\u00103\u001a\u0004\u0018\u00010\"J\u001a\u0010<\u001a\u0004\u0018\u00010*2\u0006\u0010=\u001a\u0002072\u0006\u0010>\u001a\u000205H\u0002J\u000e\u0010?\u001a\u00020\"2\u0006\u0010@\u001a\u00020AJ\"\u0010B\u001a\u0002022\u0006\u0010C\u001a\u00020A2\u0006\u0010D\u001a\u00020A2\b\u0010E\u001a\u0004\u0018\u00010FH\u0014J\b\u0010G\u001a\u000202H\u0016J\u0012\u0010H\u001a\u0002022\b\u0010I\u001a\u0004\u0018\u00010JH\u0014J\u0010\u0010K\u001a\u00020L2\u0006\u0010M\u001a\u00020NH\u0016J\b\u0010O\u001a\u000202H\u0002J\u001a\u0010P\u001a\u0004\u0018\u0001052\u0006\u0010=\u001a\u0002072\b\u0010:\u001a\u0004\u0018\u00010\"R\u001c\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR\u001c\u0010\n\u001a\u0004\u0018\u00010\u000bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000fR\u001c\u0010\u0010\u001a\u0004\u0018\u00010\u0011X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015R\u0010\u0010\u0016\u001a\u0004\u0018\u00010\u0017X\u0082\u000e¢\u0006\u0002\n\u0000R\u001c\u0010\u0018\u001a\u0004\u0018\u00010\u0019X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001a\u0010\u001b\"\u0004\b\u001c\u0010\u001dR\u0011\u0010\u001e\u001a\u00020\u0000¢\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010 R\u001c\u0010!\u001a\u0004\u0018\u00010\"X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b#\u0010$\"\u0004\b%\u0010&R\u0010\u0010'\u001a\u0004\u0018\u00010(X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010)\u001a\u0004\u0018\u00010*X\u0082\u0004¢\u0006\u0002\n\u0000R\u001c\u0010+\u001a\u0004\u0018\u00010,X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b-\u0010.\"\u0004\b/\u00100¨\u0006R"},
        d2 = {"LCutout/CropActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "Lcom/google/android/material/navigation/NavigationView$OnNavigationItemSelectedListener;", "()V", "bitmap", "Landroid/graphics/Bitmap;", "getBitmap", "()Landroid/graphics/Bitmap;", "setBitmap", "(Landroid/graphics/Bitmap;)V", "dialog", "Landroid/widget/ProgressBar;", "getDialog", "()Landroid/widget/ProgressBar;", "setDialog", "(Landroid/widget/ProgressBar;)V", "drawerLayout", "Landroidx/drawerlayout/widget/DrawerLayout;", "getDrawerLayout", "()Landroidx/drawerlayout/widget/DrawerLayout;", "setDrawerLayout", "(Landroidx/drawerlayout/widget/DrawerLayout;)V", "imageView", "Landroid/widget/ImageView;", "navigationView", "Lcom/google/android/material/navigation/NavigationView;", "getNavigationView", "()Lcom/google/android/material/navigation/NavigationView;", "setNavigationView", "(Lcom/google/android/material/navigation/NavigationView;)V", "outer", "getOuter", "()LCutout/CropActivity;", "photoUri", "Landroid/net/Uri;", "getPhotoUri", "()Landroid/net/Uri;", "setPhotoUri", "(Landroid/net/Uri;)V", "service", "Lnetwork/ServiceApi;", "tempFile", "Ljava/io/File;", "toolbar", "Landroidx/appcompat/widget/Toolbar;", "getToolbar", "()Landroidx/appcompat/widget/Toolbar;", "setToolbar", "(Landroidx/appcompat/widget/Toolbar;)V", "cropImage", "", "uri", "getFullPathFromUri", "", "ctx", "Landroid/content/Context;", "fileUri", "getPathFromURI", "contentUri", "getPathFromUri2", "getTempFile", "context", "url", "getUriFromDrawable", "drawableId", "", "onActivityResult", "requestCode", "resultCode", "data", "Landroid/content/Intent;", "onBackPressed", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onNavigationItemSelected", "", "menuitem", "Landroid/view/MenuItem;", "startUpload2", "uri2path", "Companion", "Lookup_Application.app"}
)
public final class CropActivity2 extends AppCompatActivity implements OnNavigationItemSelectedListener {
    private ImageView imageView;
    @Nullable
    private DrawerLayout drawerLayout;
    @Nullable
    private NavigationView navigationView;
    @Nullable
    private Toolbar toolbar;
    private final File tempFile;
    @Nullable
    private Uri photoUri;
    private ServiceApi service;
    @Nullable
    private ProgressBar dialog;
    @Nullable
    private Bitmap bitmap;
    @NotNull
    private final CropActivity outer = new CropActivity();
    private static final int REQUEST_GET_SINGLE_FILE = 0;
    private static final int PICK_IMAGE_FROM_ALBUM = 300;
    private static final String TAG = "lookup";
    public static final CropActivity.Companion Companion = new CropActivity.Companion((DefaultConstructorMarker)null);
    private HashMap _$_findViewCache;

    @Nullable
    public final DrawerLayout getDrawerLayout() {
        return this.drawerLayout;
    }

    public final void setDrawerLayout(@Nullable DrawerLayout var1) {
        this.drawerLayout = var1;
    }

    @Nullable
    public final NavigationView getNavigationView() {
        return this.navigationView;
    }

    public final void setNavigationView(@Nullable NavigationView var1) {
        this.navigationView = var1;
    }

    @Nullable
    public final Toolbar getToolbar() {
        return this.toolbar;
    }

    public final void setToolbar(@Nullable Toolbar var1) {
        this.toolbar = var1;
    }

    @Nullable
    public final Uri getPhotoUri() {
        return this.photoUri;
    }

    public final void setPhotoUri(@Nullable Uri var1) {
        this.photoUri = var1;
    }

    @Nullable
    public final ProgressBar getDialog() {
        return this.dialog;
    }

    public final void setDialog(@Nullable ProgressBar var1) {
        this.dialog = var1;
    }

    @Nullable
    public final Bitmap getBitmap() {
        return this.bitmap;
    }

    public final void setBitmap(@Nullable Bitmap var1) {
        this.bitmap = var1;
    }

    @NotNull
    public final CropActivity getOuter() {
        return this.outer;
    }

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(1300033);
        this.service = (ServiceApi)RetrofitClient.getClient().create(ServiceApi.class);
        this.drawerLayout = (DrawerLayout)this.findViewById(1000545);
        this.navigationView = (NavigationView)this.findViewById(1000098);
        this.toolbar = (Toolbar)this.findViewById(1000522);
        this.setSupportActionBar(this.toolbar);
        NavigationView var10000 = this.navigationView;
        Menu menu = var10000 != null ? var10000.getMenu() : null;
        if (menu != null) {
            MenuItem var7 = menu.findItem(1000516);
            if (var7 != null) {
                var7.setVisible(false);
            }
        }

        var10000 = this.navigationView;
        if (var10000 != null) {
            var10000.bringToFront();
        }

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle((Activity)this, this.drawerLayout, this.toolbar, 1900076, 1900093);
        DrawerLayout var8 = this.drawerLayout;
        if (var8 != null) {
            var8.addDrawerListener((DrawerListener)toggle);
        }

        toggle.syncState();
        var10000 = this.navigationView;
        if (var10000 != null) {
            var10000.setNavigationItemSelectedListener((OnNavigationItemSelectedListener)this);
        }

        var10000 = this.navigationView;
        if (var10000 != null) {
            var10000.setCheckedItem(1000107);
        }

        this.imageView = (ImageView)this.findViewById(1000189);
        Uri imageIconUri = this.getUriFromDrawable(700169);
        ImageView var9 = this.imageView;
        if (var9 != null) {
            var9.setImageURI(imageIconUri);
        }

        var9 = this.imageView;
        if (var9 != null) {
            var9.setTag(imageIconUri);
        }

        FloatingActionButton fab = (FloatingActionButton)this.findViewById(1000470);
        fab.setImageResource(700171);
        fab.setOnClickListener((OnClickListener)(new OnClickListener() {
            public final void onClick(@Nullable View view) {
                Intent intent = new Intent("android.intent.action.PICK");
                intent.setType("image/*");
                intent.putExtra("crop", true);
                intent.setAction("android.intent.action.GET_CONTENT");
                CropActivity.this.startActivityForResult(Intent.createChooser(intent, (CharSequence)"Select Picture"), 300);
            }
        }));
        Button uploadBtn = (Button)this.findViewById(1000213);
        uploadBtn.setOnClickListener((OnClickListener)(new OnClickListener() {
            public final void onClick(@Nullable View view) {
                CropActivity.this.startUpload2();
            }
        }));
    }

    @Nullable
    public final String uri2path(@NotNull Context context, @Nullable Uri contentUri) {
        Intrinsics.checkParameterIsNotNull(context, "context");
        String[] proj = new String[]{"_data"};
        ContentResolver var10000 = context.getContentResolver();
        if (contentUri == null) {
            Intrinsics.throwNpe();
        }

        Cursor cursor = var10000.query(contentUri, proj, (String)null, (String[])null, (String)null);
        if (cursor != null) {
            cursor.moveToNext();
        }

        String path = cursor != null ? cursor.getString(cursor.getColumnIndex("_data")) : null;
        Uri uri = Uri.fromFile(new File(path));
        if (cursor != null) {
            cursor.close();
        }

        return path;
    }

    @Nullable
    public final String getFullPathFromUri(@NotNull Context ctx, @Nullable Uri fileUri) {
        Intrinsics.checkParameterIsNotNull(ctx, "ctx");
        String fullPath = (String)null;
        String column = "_data";
        ContentResolver var10000 = ctx.getContentResolver();
        if (fileUri == null) {
            Intrinsics.throwNpe();
        }

        Cursor cursor = var10000.query(fileUri, (String[])null, (String)null, (String[])null, (String)null);
        Log.e("cursor", String.valueOf(cursor));
        if (cursor != null) {
            cursor.moveToFirst();
            String var12 = cursor.getString(0);
            Intrinsics.checkExpressionValueIsNotNull(var12, "cursor.getString(0)");
            String document_id = var12;
            int var8;
            if (document_id == null) {
                int i = 0;

                for(var8 = cursor.getColumnCount(); i < var8; ++i) {
                    if (StringsKt.equals(column, cursor.getColumnName(i), true)) {
                        fullPath = cursor.getString(i);
                        break;
                    }
                }
            } else {
                var8 = StringsKt.lastIndexOf$default((CharSequence)document_id, ":", 0, false, 6, (Object)null) + 1;
                var12 = document_id.substring(var8);
                Intrinsics.checkExpressionValueIsNotNull(var12, "(this as java.lang.String).substring(startIndex)");
                document_id = var12;
                cursor.close();
                String[] projection = new String[]{column};

                try {
                    cursor = ctx.getContentResolver().query(Media.EXTERNAL_CONTENT_URI, projection, "_id = ? ", new String[]{document_id}, (String)null);
                    if (cursor != null) {
                        cursor.moveToFirst();
                        fullPath = cursor.getString(cursor.getColumnIndexOrThrow(column));
                    }
                } finally {
                    if (cursor != null) {
                        cursor.close();
                    }

                }
            }
        }

        return fullPath;
    }

    @Nullable
    public final String getPathFromUri2(@Nullable Uri uri) {
        ContentResolver var10000 = this.getContentResolver();
        if (uri == null) {
            Intrinsics.throwNpe();
        }

        Cursor cursor = var10000.query(uri, (String[])null, (String)null, (String[])null, (String)null);
        if (cursor == null) {
            Intrinsics.throwNpe();
        }

        cursor.moveToNext();
        String path = cursor.getString(cursor.getColumnIndex("_data"));
        cursor.close();
        return path;
    }

    private final File getTempFile(Context context, String url) {
        Uri var10000 = Uri.parse(url);
        File var8;
        if (var10000 != null && var10000.getLastPathSegment() != null) {
            boolean var4 = false;
            boolean var5 = false;
            int var7 = false;
            var8 = File.createTempFile(Companion.makeName(), ".jpg", context.getCacheDir());
        } else {
            var8 = null;
        }

        return var8;
    }

    private final void startUpload2() {
        Log.d("startUpload2", "startUpload2 함수 시작은 되는구만");
        String imgName = Companion.makeImgName(this.getApplicationContext());
        Uri result = this.photoUri;
        Intrinsics.checkExpressionValueIsNotNull(this.getApplicationContext().getCacheDir(), "applicationContext.getCacheDir()");
        Context var10001 = this.getApplicationContext();
        Intrinsics.checkExpressionValueIsNotNull(var10001, "applicationContext");
        File outputFile = this.getTempFile(var10001, String.valueOf(this.photoUri));
        Context var10002 = this.getApplicationContext();
        Intrinsics.checkExpressionValueIsNotNull(var10002, "applicationContext");
        new File(var10002.getCacheDir(), outputFile != null ? outputFile.getName() : null);
        Log.e("result1", String.valueOf(result));
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), outputFile);
        Part fileToUpload = Part.createFormData("upload2bg", imgName, requestBody);
        ServiceApi var10000 = this.service;
        if (var10000 != null) {
            Call var8 = var10000.postImage2bg(fileToUpload, requestBody);
            if (var8 != null) {
                var8.enqueue((Callback)(new Callback() {
                    public void onResponse(@NotNull Call call, @NotNull Response response) {
                        Intrinsics.checkParameterIsNotNull(call, "call");
                        Intrinsics.checkParameterIsNotNull(response, "response");
                        if (response.code() == 200) {
                        }

                    }

                    public void onFailure(@NotNull Call call, @NotNull Throwable t) {
                        Intrinsics.checkParameterIsNotNull(call, "call");
                        Intrinsics.checkParameterIsNotNull(t, "t");
                        Toast.makeText(CropActivity.this.getApplicationContext(), (CharSequence)"req fail", 0).show();
                        t.printStackTrace();
                        Log.e("파일 업로드 에러 발생", t.getMessage());
                    }
                }));
            }
        }

    }

    private final void cropImage(Uri uri) {
        CropImage.activity(uri).setGuidelines(Guidelines.ON).setCropShape(CropShape.RECTANGLE).start((Activity)this);
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        boolean var6;
        ImageView var12;
        switch(requestCode) {
            case 203:
                ActivityResult result = CropImage.getActivityResult(data);
                if (resultCode == -1) {
                    Intrinsics.checkExpressionValueIsNotNull(result, "result");
                    if (result.getUri() != null) {
                        var6 = false;
                        boolean var7 = false;
                        int var9 = false;
                        var12 = this.imageView;
                        if (var12 != null) {
                            var12.setImageBitmap(result.getBitmap());
                        }

                        var12 = this.imageView;
                        if (var12 != null) {
                            var12.setImageURI(result.getUri());
                        }

                        this.photoUri = result.getUri();
                    }
                } else if (resultCode == 204) {
                    Intrinsics.checkExpressionValueIsNotNull(result, "result");
                    Exception error = result.getError();
                    Toast.makeText((Context)this, (CharSequence)error.getMessage(), 0).show();
                }
                break;
            case 300:
                if (data != null) {
                    Uri var10000 = data.getData();
                    if (var10000 != null) {
                        Uri var4 = var10000;
                        boolean var5 = false;
                        var6 = false;
                        int var8 = false;
                        this.cropImage(var4);
                    }
                }

                this.photoUri = data != null ? data.getData() : null;
                var12 = this.imageView;
                if (var12 != null) {
                    var12.setImageURI(this.photoUri);
                }
                break;
            default:
                this.finish();
        }

    }

    @Nullable
    public final String getPathFromURI(@Nullable Uri contentUri) {
        String res = (String)null;
        String[] proj = new String[]{"_data"};
        ContentResolver var10000 = this.getContentResolver();
        if (contentUri == null) {
            Intrinsics.throwNpe();
        }

        Cursor cursor = var10000.query(contentUri, proj, (String)null, (String[])null, (String)null);
        if (cursor == null) {
            Intrinsics.throwNpe();
        }

        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow("_data");
            res = cursor.getString(column_index);
        }

        cursor.close();
        return res;
    }

    @NotNull
    public final Uri getUriFromDrawable(int drawableId) {
        StringBuilder var10000 = (new StringBuilder()).append("android.resource://").append(this.getPackageName()).append("/drawable/");
        Context var10001 = this.getApplicationContext();
        Intrinsics.checkExpressionValueIsNotNull(var10001, "applicationContext");
        Uri var2 = Uri.parse(var10000.append(var10001.getResources().getResourceEntryName(drawableId)).toString());
        Intrinsics.checkExpressionValueIsNotNull(var2, "Uri.parse(\"android.resou…rceEntryName(drawableId))");
        return var2;
    }

    public void onBackPressed() {
        DrawerLayout var10000 = this.drawerLayout;
        if (var10000 == null) {
            Intrinsics.throwNpe();
        }

        if (var10000.isDrawerOpen(8388611)) {
            var10000 = this.drawerLayout;
            if (var10000 == null) {
                Intrinsics.throwNpe();
            }

            var10000.closeDrawer(8388611);
        } else {
            super.onBackPressed();
        }

    }

    public boolean onNavigationItemSelected(@NotNull MenuItem menuitem) {
        Intrinsics.checkParameterIsNotNull(menuitem, "menuitem");
        Intent intent;
        switch(menuitem.getItemId()) {
            case 1000107:
            default:
                break;
            case 1000423:
                intent = new Intent((Context)this, MainActivity.class);
                this.startActivity(intent);
                break;
            case 1000475:
                intent = new Intent((Context)this, ImageSelectActivity.class);
                this.startActivity(intent);
                break;
            case 1000478:
                intent = new Intent((Context)this, RatingActivity.class);
                this.startActivity(intent);
                break;
            case 1000490:
                intent = new Intent((Context)this, LookBookActivity.class);
                this.startActivity(intent);
                break;
            case 1000584:
                intent = new Intent((Context)this, Closet_MainActivity.class);
                this.startActivity(intent);
        }

        DrawerLayout var10000 = this.drawerLayout;
        if (var10000 == null) {
            Intrinsics.throwNpe();
        }

        var10000.closeDrawer(8388611);
        return true;
    }

    public View _$_findCachedViewById(int var1) {
        if (this._$_findViewCache == null) {
            this._$_findViewCache = new HashMap();
        }

        View var2 = (View)this._$_findViewCache.get(var1);
        if (var2 == null) {
            var2 = this.findViewById(var1);
            this._$_findViewCache.put(var1, var2);
        }

        return var2;
    }

    public void _$_clearFindViewByIdCache() {
        if (this._$_findViewCache != null) {
            this._$_findViewCache.clear();
        }

    }

    @Metadata(
            mv = {1, 1, 16},
            bv = {1, 0, 3},
            k = 1,
            d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\b\u001a\u00020\u00072\b\u0010\t\u001a\u0004\u0018\u00010\nJ\b\u0010\u000b\u001a\u0004\u0018\u00010\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082T¢\u0006\u0002\n\u0000¨\u0006\f"},
            d2 = {"LCutout/CropActivity$Companion;", "", "()V", "PICK_IMAGE_FROM_ALBUM", "", "REQUEST_GET_SINGLE_FILE", "TAG", "", "makeImgName", "context", "Landroid/content/Context;", "makeName", "Lookup_Application.app"}
    )
    public static final class Companion {
        @NotNull
        public final String makeImgName(@Nullable Context context) {
            String id = SaveSharedPreference.getString(context, "ID");
            long now = System.currentTimeMillis();
            Date date = new Date(now);
            SimpleDateFormat mFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
            String time = mFormat.format(date);
            return id + "_" + time + ".png";
        }

        @Nullable
        public final String makeName() {
            long now = System.currentTimeMillis();
            Date date = new Date(now);
            SimpleDateFormat mFormat = new SimpleDateFormat("yyyyMMddHHmm");
            String time = mFormat.format(date);
            return "LookUP" + time;
        }

        private Companion() {
        }

        // $FF: synthetic method
        public Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}
*/