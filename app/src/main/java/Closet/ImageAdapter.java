package Closet;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.collection.CircularArray;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.R;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import Closet.data.ImageData;
import Closet.data.ImageResponse;

//extends RecyclerView.Adapter<ImageAdapter.DataViewHolder>
public class ImageAdapter {
    /*
    private final ArrayList<ImageResponse> dataList;
    private LayoutInflater mInflater;

    public ImageAdapter(@NonNull Context context, ArrayList<ImageResponse> data) {
        mInflater = LayoutInflater.from(context);
        dataList = data;
    }

    @NonNull
    @Override
    public DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.closet_viewholder, parent, false);
        return new DataViewHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageAdapter.DataViewHolder holder, int position) {
        String[] imgUrl = dataList.get(position).getUrl();

        holder.name.setText(name);
        Picasso.get().load(imgUrl).into( holder.image);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class DataViewHolder extends RecyclerView.ViewHolder {
        public final ImageView image;
        public GridView gView;
        public final TextView name;
        public final TextView data;
        final DataAdapter mAdapter;

        public DataViewHolder(@NonNull View itemView, DataAdapter adapter) {
            super(itemView);
            mAdapter = adapter;
            image = itemView.findViewById(R.id.iv_image);
            name = itemView.findViewById(R.id.tv_name);
            data = itemView.findViewById(R.id.tv_value);

        }
    }
    */
}

