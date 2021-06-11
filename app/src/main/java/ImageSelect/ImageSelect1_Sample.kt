package ImageSelect

import Cookie.SaveSharedPreference
import Login_Main.activity.MainActivity
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.ActionMode
import androidx.recyclerview.selection.SelectionPredicates
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.selection.StorageStrategy
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import network.RetrofitClient
import network.ServiceApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.R

class SelectActivity1() : AppCompatActivity(), ActionMode.Callback {

    private var selectedPostItems: MutableList<PostItem> = mutableListOf()
    private var actionMode: ActionMode? = null
    private lateinit var adapter: PostsAdapter
    private var tracker: SelectionTracker<PostItem>? = null
    private var prePostItems: MutableList<PostItem> = mutableListOf()


    private var userId: String? = null
    private var itemSelection: SharedPreferences? = null
    private var itemSelectionEditor: SharedPreferences.Editor? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.itemselect_selectactivity1)


        itemSelection = getSharedPreferences("Situation1", MODE_PRIVATE)
        itemSelectionEditor = itemSelection?.edit()



        userId = SaveSharedPreference.getString(this.application.applicationContext, "ID")

        val postsRecyclerView: RecyclerView = findViewById(R.id.postsRecyclerView)
        postsRecyclerView.isNestedScrollingEnabled = false
        postsRecyclerView.layoutManager =
                StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

        val postItems: MutableList<PostItem> = mutableListOf()

        for (i in 1..40 step 1) {
            var item: String = "style" + i
            var item_image = getResources().getIdentifier(item, "drawable", getPackageName())
            postItems.add(PostItem(i, 1, item_image))
        }

        adapter = PostsAdapter(this, postItems)
        postsRecyclerView.adapter = adapter

        tracker = SelectionTracker.Builder<PostItem>(
                "mySelection",
                postsRecyclerView,
                MyItemKeyProvider(adapter),
                MyItemDetailsLookup(postsRecyclerView),
                StorageStrategy.createParcelableStorage(PostItem::class.java)
        ).withSelectionPredicate(
                SelectionPredicates.createSelectAnything()
        ).build()



//        for (item in prePostItems) {
//            adapter.tracker?.isSelected(item)
//        }


        //count 0 아닐때,,
        if (itemSelection!!.getInt("cnt", 0) > 0) {
            var itemListStr: List<String> =
                    itemSelection?.getString("data", null).toString().split(",")


            for (i in itemListStr) {
                tracker?.select(postItems[i.toInt() - 1])
                var imagelist = mutableListOf<Int>()
                imagelist.add(postItems[i.toInt() - 1].imageID)
                var postitemdata = PostItemData(userId, imagelist, 1)

                setItemUpdate(postitemdata)
            }
        }

        adapter.tracker = tracker

        tracker?.addObserver(
                object : SelectionTracker.SelectionObserver<Long>() {
                    override fun onSelectionChanged() {
                        super.onSelectionChanged()
                        tracker?.let {

                            selectedPostItems = it.selection.toMutableList()
                            Log.e("Selected ImageList: ", selectedPostItems.toString())
                            var imagelist = mutableListOf<Int>()

                            for (item in selectedPostItems) {
                                imagelist.add(item.imageID)
                            }
                            var postitemdata = PostItemData(userId, imagelist, 1)
                            setItemUpdate(postitemdata)

                            if (actionMode == null) actionMode =
                                    startSupportActionMode(this@SelectActivity1)
                            actionMode?.title =
                                    "${selectedPostItems.size}"

                            /*if (selectedPostItems.isEmpty()) {
                                actionMode?.finish()
                            } else {
                                if (actionMode == null) actionMode =
                                        startSupportActionMode(this@SelectActivity)
                                actionMode?.title =
                                        "${selectedPostItems.size}"
                            }*/
                        }
                    }
                })
    }

    override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_view_delete -> {
                /*Toast.makeText(
                        applicationContext,
                        selectedPostItems.toString(),
                        Toast.LENGTH_LONG
                ).show()*/

                //데이터 전달하기
                val intent = Intent(applicationContext, MainActivity::class.java)
                startActivity(intent)
                getItemData(userId!!, 1)
            }
        }
        return true
    }

    override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        mode?.let {
            val inflater: MenuInflater = it.menuInflater
            inflater.inflate(R.menu.action_mode_menu, menu)
            return true
        }
        return false

    }

    override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        return true
    }

    override fun onDestroyActionMode(mode: ActionMode?) {
        //adapter.tracker?.clearSelection()  //back 누르면 초기화됨
        adapter.notifyDataSetChanged()
        actionMode = null
    }

    fun getItemData(userID: String, purpose: Int) {
        val service: ServiceApi? = RetrofitClient.getClient().create(ServiceApi::class.java)
        service?.getPostItemData(userID, purpose)?.enqueue(object : Callback<PostItemDataResponse> {
            override fun onFailure(call: Call<PostItemDataResponse>, t: Throwable) {
                print("Fail Load Item")
            }

            override fun onResponse(
                    call: Call<PostItemDataResponse>,
                    response: Response<PostItemDataResponse>
            ) {
                val serviceData: PostItemDataResponse? = response.body()
                if (serviceData != null && serviceData.imageList != null) {


                    //선택된 갯수, 몇번째 아이템을 선택했는지!!!
                    itemSelectionEditor?.putInt("cnt", serviceData.imageList.size)
                    itemSelectionEditor?.putString(
                            "data",
                            serviceData.imageList.toString().replace("[", "").replace("]", "")
                                    .replace(" ", "")
                    )


                    itemSelectionEditor?.apply()


//                    for (i in serviceData.imageList) {
//                        var item: String = "style" + i
//                        var item_image =
//                            getResources().getIdentifier(item, "drawable", getPackageName())
//                        prePostItems.add(PostItem(i, 1, item_image))
//                    }


                }

            }


        })


    }


    fun setItemUpdate(data: PostItemData) {
        val servcie: ServiceApi? = RetrofitClient.getClient().create(ServiceApi::class.java)
        servcie?.setStylePurpose(data)?.enqueue(object : Callback<PostItemDataResponse?> {

            override fun onFailure(call: Call<PostItemDataResponse?>, t: Throwable) {
                print("Fail Load Rating")
            }


            override fun onResponse(
                    call: Call<PostItemDataResponse?>,
                    response: Response<PostItemDataResponse?>
            ) {
                var result: PostItemDataResponse = response.body()!!
                if (response.body() != null) {
                    result = response.body()!!

                } else {
                    Log.v("알림", "response 값이 없습니다.")

                }
            }

        })
    }

    /*
    override fun onSaveInstanceState(outState: Bundle) {
        if (outState != null) {
            super.onSaveInstanceState(outState)
        }
        outState.getParcelableArray(prePostItems.toString())
        /* 맨 처음에는 저장되어있는 Bundle 데이터가 없으므로, outState가 null일 수 있다.
           따라서 outState 뒤에 ? 를 붙여 safeCall 한다. */
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        setContentView(R.layout.itemselect_selectactivity1)
    }*/

}





