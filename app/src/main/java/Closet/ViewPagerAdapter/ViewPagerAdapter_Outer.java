package Closet.ViewPagerAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.R;
import java.util.ArrayList;

import Closet.Data_Type;
import Closet.ViewHolder_Bottom.Jeans_ViewHolder;
import Closet.ViewHolder_Bottom.Longskirts_ViewHolder;
import Closet.ViewHolder_Bottom.Shorts_ViewHolder;
import Closet.ViewHolder_Bottom.Shortskirts_ViewHolder;
import Closet.ViewHolder_Bottom.Slacks_ViewHolder;
import Closet.ViewHolder_Bottom.Trainingpants_ViewHolder;
import Closet.ViewHolder_Bottom.Trainingshorts_ViewHolder;
import Closet.ViewHolder_Outer.Biker_ViewHolder;
import Closet.ViewHolder_Outer.Blazer_ViewHolder;
import Closet.ViewHolder_Outer.Cardigan_ViewHolder;
import Closet.ViewHolder_Outer.Coat_ViewHolder;
import Closet.ViewHolder_Outer.Denim_ViewHolder;
import Closet.ViewHolder_Outer.Fleece_ViewHolder;
import Closet.ViewHolder_Outer.Parka_ViewHolder;

public class ViewPagerAdapter_Outer extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    LayoutInflater inflater;
    ArrayList<Data_Type> mdata; //데이터 모델 받아오기

    public ViewPagerAdapter_Outer(Context context, ArrayList<Data_Type> mdata) {
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

                return new Cardigan_ViewHolder(view,viewType);
            }

            case 2:{// B 뷰홀더
                View view = inflater.inflate(R.layout.closet_viewholder,  parent, false);

                return new Fleece_ViewHolder(view,viewType);
            }
            case 3:{// C 뷰홀더
                View view = inflater.inflate(R.layout.closet_viewholder, parent, false);

                return new Biker_ViewHolder(view,viewType);

            }
            case 4:{// D 뷰홀더
                View view = inflater.inflate(R.layout.closet_viewholder, parent, false);

                return new Denim_ViewHolder(view,viewType);

            }
            case 5:{// E 뷰홀더
                View view = inflater.inflate(R.layout.closet_viewholder, parent, false);

                return new Blazer_ViewHolder(view,viewType);

            }
            case 6:{// E 뷰홀더
                View view = inflater.inflate(R.layout.closet_viewholder, parent, false);

                return new Coat_ViewHolder(view,viewType);

            }
            case 7:{// E 뷰홀더
                View view = inflater.inflate(R.layout.closet_viewholder, parent, false);

                return new Parka_ViewHolder(view,viewType);

            }
        }

        return null;

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

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

        }
        return 1;
    }



}