package styleList


import Login_Main.activity.LoginActivity.popnum
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import styleList.data.StyleType
import styleList.ui.main.MainFragment
import java.R
import java.util.*

class RatingActivity : AppCompatActivity() {

    private lateinit var mdata //데이터 모델
            : ArrayList<StyleType>
    private lateinit var style :String

    private lateinit var tabLayout:TabLayout
    private lateinit var viewPager2:ViewPager2
    private lateinit var viewPagerAdapter:styleFragmentAdapter



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.stylelist_ui_ratingactiviy)

        tabLayout = findViewById(R.id.style_tab)
        viewPager2 = findViewById(R.id.style_viewpager)

        Log.e("RatingActivity popnum: ", popnum.toString())
        if(popnum==0){
            popnum=1
        }


        viewPagerAdapter = styleFragmentAdapter(this)//뷰페이저 어뎁터 생성

        viewPager2.adapter= viewPagerAdapter
//        viewPager2!!.setAdapter(viewPagerAdapter) //어뎁터 연결

        viewPager2!!.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL) //스크롤방향


        val tabLayoutMediator = TabLayoutMediator(tabLayout, viewPager2, true, TabLayoutMediator.TabConfigurationStrategy { tab, position ->
            // 텝 레이아웃이랑 연결
            when (position) {
                0 -> {
                    tab.text = "Casual" //텝레이아웃 상단 타이틀 제목 설정
                }
                1 -> {
                    tab.text = "Sporty"
                }
                2 -> {
                    tab.text = "Formal"
                }
                3 -> {
                    tab.text = "Feminine"
                }
            }
        })

        tabLayoutMediator.attach()


        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            //뷰페이저 해당 포지션 위치
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                when (position) {
                    0 -> {
                        MainFragment.style="casual"
                    }
                    1 -> {
                        MainFragment.style="sporty"
                    }
                    2 -> {
                        MainFragment.style="formal"
                    }
                    3 -> {
                        MainFragment.style="feminine"
                    }
                }
            }
        })

    }

    private var backPressedTime: Long = 0
    override fun onBackPressed() {
        // 2초내 다시 클릭하면 앱 종료
        if (System.currentTimeMillis() - backPressedTime < 2000) {
            finishAffinity()
        }

        // 처음 클릭 메시지
        Toast.makeText(this, "한번 더 누르시면 앱이 종료됩니다.", Toast.LENGTH_SHORT).show()
        backPressedTime = System.currentTimeMillis()
        finish();
    }

    inner class styleFragmentAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
        override fun getItemCount(): Int = 4

        override fun createFragment(position: Int): Fragment {
            when(position){
                0 ->{//MainFragment.style="casual"
                    return MainFragment().newInstance("casual")!!
                    //return MainFragment()
                }
                1 ->{//MainFragment.style="sporty"
                    return MainFragment().newInstance("sporty")!!}
                    //return MainFragment()}
                2 ->{//MainFragment.style="formal"
                        //return MainFragment()}
                    return MainFragment().newInstance("formal")!!}
                else->{//MainFragment.style="feminine"
                         //   return MainFragment()}
                    return MainFragment().newInstance("feminine")!!}
            }

        }
    }




}
