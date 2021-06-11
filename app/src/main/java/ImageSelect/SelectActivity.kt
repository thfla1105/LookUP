package ImageSelect

import Cookie.SaveSharedPreference
import Login_Main.activity.LoginActivity.popflag
import Login_Main.activity.LoginActivity.popnum
import Login_Main.activity.MainActivity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import network.RetrofitClient
import network.ServiceApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.R

class SelectActivity :AppCompatActivity(){

    private lateinit var tab: TabLayout
    private lateinit var pager: ViewPager2
    private var purpose:Int = 0
    private lateinit var selectAdapter: selectFragmentAdapter
    private lateinit var context: Context



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.imageselect_main)

        tab = findViewById(R.id.select_tab)
        pager = findViewById(R.id.select_viewpager)

        context=this.applicationContext

        selectAdapter=selectFragmentAdapter(this)
        pager.adapter= selectAdapter
//        viewPager2!!.setAdapter(viewPagerAdapter) //어뎁터 연결

        pager!!.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL) //스크롤방향




        val tabLayoutMediator = TabLayoutMediator(tab, pager, true, TabLayoutMediator.TabConfigurationStrategy { tab, position ->
            // 텝 레이아웃이랑 연결
            when (position) {
                0 -> {

                    tab.text = "친구 모임" //텝레이아웃 상단 타이틀 제목 설정
                }
                1 -> {

                    tab.text = "일상"
                }
                2 -> {

                    tab.text = "운동"
                }
                3 -> {

                    tab.text = "직장/면접"
                }
                4 -> {

                    tab.text = "아르바이트"
                }
                5 -> {

                    tab.text = "기타"
                }
            }
        })

        tabLayoutMediator.attach()


        pager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            //뷰페이저 해당 포지션 위치
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                when (position) {
                    0 -> {
                        purpose=1 //텝레이아웃 상단 타이틀 제목 설정
                    }
                    1 -> {
                        purpose=2
                    }
                    2 -> {
                        purpose=3
                    }
                    3 -> {
                        purpose=4
                    }
                    4 -> {
                        purpose=5
                    }
                    5 -> {
                        purpose=6
                    }
                }
            }
        })
    }

    /*
    final String [] purpose
            = new String[] {"일상","직장/면접","아르바이트","친구 모임/데이트","운동","기타"};

    final String [] acc
            = new String[] {"악세서리 포함O","악세서리 포함X"};
     */
    private var backPressedTime: Long = 0
    override fun onBackPressed() {
        // 2초내 다시 클릭하면 앱 종료
        if (System.currentTimeMillis() - backPressedTime < 2000) {
            finishAffinity()
        }

        // 처음 클릭 메시지
        Toast.makeText(this, "한번 더 누르시면 앱이 종료됩니다.", Toast.LENGTH_SHORT).show()
        backPressedTime = System.currentTimeMillis()
        //finish();
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.select_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Log.e("SelectActivity popnum: ", popnum.toString())
        when (item.itemId) {
            R.id.action_go_main->{
                if(popnum==1){
                    popnum=2
                    popflag=0;
                }
                val intent = Intent(applicationContext, MainActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }


    inner class selectFragmentAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
        override fun getItemCount(): Int = 6

        override fun createFragment(position: Int): Fragment {
            when(position){
                0 ->{//MainFragment.style="casual"
                    //return SelectFragment().newInstance(1)!!

                    return SelectFragment()
                    //return SelectFragment(1)
                }
                1 ->{//MainFragment.style="sporty"
                    //return SelectFragment().newInstance(2)!!}
                    return SelectFragment2()
                    //return SelectFragment(2)
                }
                2 ->{//MainFragment.style="formal"
                    //return MainFragment()}
                    //return SelectFragment().newInstance(3)!!
                    return SelectFragment3()
                    //return SelectFragment(3)
                }
                3 ->{//MainFragment.style="formal"
                    //return MainFragment()}
                    //return SelectFragment().newInstance(4)!!
                    return SelectFragment4()
                    //return SelectFragment(4)
                }
                4 ->{//MainFragment.style="formal"
                    //return MainFragment()}
                    //return SelectFragment().newInstance(5)!!
                    return SelectFragment5()
                    //return SelectFragment(5)
                }
                else->{//MainFragment.style="feminine"
                    //   return MainFragment()}
                    //return SelectFragment().newInstance(6)!!
                    return SelectFragment6()
                    //return SelectFragment(6)
                }
            }

        }
    }



}