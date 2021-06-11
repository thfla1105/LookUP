package ImageSelect

import Cookie.SaveSharedPreference
import Login_Main.activity.MainActivity
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.ActionMode
import androidx.fragment.app.Fragment
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

class SelectFragment3() : Fragment(), ActionMode.Callback {

    private var selectedPostItems: MutableList<PostItem> = mutableListOf()
    private var actionMode: ActionMode? = null
    private lateinit var adapter: PostsAdapter
    private var tracker: SelectionTracker<PostItem>? = null
    private var prePostItems: MutableList<PostItem> = mutableListOf()


    private var userId: String? = null
    private var itemSelection: SharedPreferences? = null
    private var itemSelectionEditor: SharedPreferences.Editor? = null
    private var itm:List<String>?=null

    //private var gpurpose: Int ?=null

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {

        super.onCreate(savedInstanceState)
        //setContentView(R.layout.itemselect_selectactivity1)

        val view = inflater.inflate(R.layout.itemselect_selectactivity1, container, false)


        userId = SaveSharedPreference.getString(this.context?.applicationContext, "ID")
        itemSelection = context?.getSharedPreferences("Situation3", MODE_PRIVATE)
        getItemData(userId!!,3)
        itemSelectionEditor = itemSelection?.edit()



        val postsRecyclerView: RecyclerView = view.findViewById(R.id.postsRecyclerView)
        postsRecyclerView.isNestedScrollingEnabled = false
        postsRecyclerView.layoutManager =
                StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

        val postItems: MutableList<PostItem> = mutableListOf()

        for (i in 1..40 step 1) {
            var item: String = "style" + i
            var item_image = getResources().getIdentifier(item, "drawable", context?.getPackageName())
            postItems.add(PostItem(i, 3, item_image))
        }




        adapter = PostsAdapter(this.context!!, postItems)

        //viewPagerAdapter = styleFragmentAdapter(this)//뷰페이저 어뎁터 생성



        //adapter = PostsAdapter(this, postItems)
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
                var postitemdata = PostItemData(userId, imagelist, 3)

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
                            var postitemdata = PostItemData(userId, imagelist, 3)
                            setItemUpdate(postitemdata)

                            if (actionMode == null) actionMode = (activity as AppCompatActivity?)!!.startSupportActionMode(this@SelectFragment3)

                            actionMode?.title =
                                    "${selectedPostItems.size}"


                        }
                    }
                })
        return view
    }


    override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
        getItemData(userId!!,3)
        when (item?.itemId) {
            R.id.action_view_delete -> {
                /*Toast.makeText(
                        applicationContext,
                        selectedPostItems.toString(),
                        Toast.LENGTH_LONG
                ).show()*/

                getItemData(userId!!,3)
                //데이터 전달하기
                val intent = Intent(context?.applicationContext, SelectActivity::class.java)
                startActivity(intent)

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
                    itm=serviceData.imageList.toString().split(',')


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
    fun newInstance(gpurpose: Int):SelectFragment? {
        val fragment = SelectFragment()
        val args = Bundle()
        this.gpurpose= gpurpose
        args.putInt("purpose", gpurpose)
        fragment.setArguments(args)
        return fragment
    }*/


}





