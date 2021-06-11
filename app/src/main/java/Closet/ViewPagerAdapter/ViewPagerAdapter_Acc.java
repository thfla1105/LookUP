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
import Closet.ViewHolder_Acc.Beanie_ViewHolder;
import Closet.ViewHolder_Acc.Cap_ViewHolder;
import Closet.ViewHolder_Acc.Scarf_ViewHolder;

public class ViewPagerAdapter_Acc extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    LayoutInflater inflater;
    ArrayList<Data_Type> mdata; //데이터 모델 받아오기

    public ViewPagerAdapter_Acc(Context context, ArrayList<Data_Type> mdata) {
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

                return new Scarf_ViewHolder(view,viewType);
            }

            case 2:{// B 뷰홀더
                View view = inflater.inflate(R.layout.closet_viewholder,  parent, false);

                return new Cap_ViewHolder(view,viewType);
            }
            case 3:{// C 뷰홀더
                View view = inflater.inflate(R.layout.closet_viewholder, parent, false);

                return new Beanie_ViewHolder(view,viewType);

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

        }
        return 1;
    }



}