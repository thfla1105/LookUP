package Closet.ViewPagerAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.R;
import java.util.ArrayList;

import Closet.Data_Type;
import Closet.ViewHolder_Top.Hoodie_ViewHolder;
import Closet.ViewHolder_Top.Hoodie_ViewHolder;
import Closet.ViewHolder_Top.Longblouse_ViewHolder;
import Closet.ViewHolder_Top.Longblouse_ViewHolder;
import Closet.ViewHolder_Top.Longshirt_ViewHolder;
import Closet.ViewHolder_Top.Longsleeve_ViewHolder;
import Closet.ViewHolder_Top.Shortblouse_ViewHolder;
import Closet.ViewHolder_Top.Shortshirt_ViewHolder;
import Closet.ViewHolder_Top.Shortsleeve_ViewHolder;
import Closet.ViewHolder_Top.Sleeveless_ViewHolder;
import Closet.ViewHolder_Top.Sweater_ViewHolder;
import Closet.ViewHolder_Top.Vest_ViewHolder;
import Closet.data.ImageResponse;

public class ViewPagerAdapter_Top extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    LayoutInflater inflater;
    ArrayList<Data_Type> mdata; //데이터 모델 받아오기
    //public ArrayList<ImageResponse> datalist;
    public ViewPagerAdapter_Top(Context context, ArrayList<Data_Type> mdata) {
        this.context = context;
        this.mdata = mdata;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) { //viewType = getItemViewType 지정해준 Data_Type 에 type
            case 1: { // A 뷰홀더
                View view = inflater.inflate(R.layout.closet_viewholder, parent, false);

                return new Shortsleeve_ViewHolder(view,viewType);
            }

            case 2:{// B 뷰홀더
                View view = inflater.inflate(R.layout.closet_viewholder,  parent, false);

                return new Longsleeve_ViewHolder(view,viewType);
            }
            case 3:{// C 뷰홀더
                View view = inflater.inflate(R.layout.closet_viewholder, parent, false);

                return new Shortshirt_ViewHolder(view,viewType);

            }
            case 4:{// D 뷰홀더
                View view = inflater.inflate(R.layout.closet_viewholder, parent, false);

                return new Longshirt_ViewHolder(view,viewType);

            }
            case 5:{// E 뷰홀더
                View view = inflater.inflate(R.layout.closet_viewholder, parent, false);

                return new Sweater_ViewHolder(view,viewType);

            }
            case 6:{// E 뷰홀더
                View view = inflater.inflate(R.layout.closet_viewholder, parent, false);

                return new Hoodie_ViewHolder(view,viewType);

            }
            case 7:{// E 뷰홀더
                View view = inflater.inflate(R.layout.closet_viewholder, parent, false);

                return new Shortblouse_ViewHolder(view,viewType);

            }
            case 8:{// E 뷰홀더
                View view = inflater.inflate(R.layout.closet_viewholder, parent, false);

                return new Longblouse_ViewHolder(view,viewType);

            }
            case 9:{// E 뷰홀더
                View view = inflater.inflate(R.layout.closet_viewholder, parent, false);

                return new Sleeveless_ViewHolder(view,viewType);

            }
            case 10:{// E 뷰홀더
                View view = inflater.inflate(R.layout.closet_viewholder, parent, false);

                return new Vest_ViewHolder(view,viewType);

            }
        }

        return null;

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        //String[] imgUrls=datalist.get(position).getUrl();
    }

    @Override
    public int getItemCount() {
        return mdata.size();
    }

    @Override
    public int getItemViewType(int position) {
        switch (mdata.get(position).getType()){ //데이터 모델에서 Type 을 받아들여 ItemViewType으로 씀
            case 1: // A
                return 1;
            case 2: // B
                return 2;
            case 3:// C
                return 3;
            case 4:// D
                return 4;
            case 5:// E
                return 5;
            case 6:// F
                return 6;
            case 7:// G
                return 7;
            case 8:// H
                return 8;
            case 9:// I
                return 9;
            case 10:// J
                return 10;

        }
        return 1;
    }

}