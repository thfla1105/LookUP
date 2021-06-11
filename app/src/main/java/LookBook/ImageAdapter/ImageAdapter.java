package LookBook.ImageAdapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.R;
import java.util.ArrayList;

import Login_Main.activity.MainActivity;

public class ImageAdapter extends BaseAdapter {
    public ArrayList<ListItem_temp> itemList = new ArrayList<>();
    public String id;
    //Context context;

    //public ImageAdapter(Context con){
       // final Context context = con;
   // }

    public void addItem(ListItem_temp item){
        itemList.add(item);
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<ListItem_temp> getItemList() {
        return itemList;
    }

    public String getId() {
        return id;
    }

    public void setItemList(ArrayList<ListItem_temp> itemList) {
        this.itemList = itemList;
    }

    @Override
    public int getCount() {
        return itemList.size();
    }

    @Override
    public Object getItem(int position) {
        return itemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        //context=parent.getContext();
        ListItem_temp listItemTemp=itemList.get(position);

        final Context context = parent.getContext();
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.lookbook_listitem_temp, parent, false);
        }

        ImageButton imageView=(ImageButton)convertView.findViewById(R.id.image_view);
        imageView.setImageURI(listItemTemp.getUri());

        if(listItemTemp.getUri()!=null) {
            imageView.setImageURI(listItemTemp.getUri());

            /*
            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .placeholder(R.mipmap.ic_launcher_round)
                    .error(R.mipmap.ic_launcher_round);
            Glide.with(context).load(userItem.getUrl()).apply(options).into(img);
             */
        }

        //ImageView img = convertView.findViewById(R.id.image_view);
        //img.setPadding(8, 8, 8, 8);
        //img.setScaleType(ImageView.ScaleType.CENTER_CROP);

        //Data userItem = itemList.get(position);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context.getApplicationContext(), "룩북 저장이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context.getApplicationContext(), MainActivity.class);
                context.startActivity(intent);
            }
        });


        //if read image from server, use this code to use glide library

        //img.setImageResource(userItem.getUrl());

        return convertView;
    }

    /*
    //style정보 포함할지
    public void addList(Uri uri){
        Data item = new Data(uri);
        itemList.add(item);
    }

     */
    //Add menuActivityList listview information
}
