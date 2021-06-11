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
import Closet.ViewHolder_Dress.Dress_ViewHolder;

public class ViewPagerAdapter_Dress extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    LayoutInflater inflater;
    ArrayList<Data_Type> mdata; //데이터 모델 받아오기

    public ViewPagerAdapter_Dress(Context context, ArrayList<Data_Type> mdata) {
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

                return new Dress_ViewHolder(view,viewType);
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

        }
        return 1;
    }



}
