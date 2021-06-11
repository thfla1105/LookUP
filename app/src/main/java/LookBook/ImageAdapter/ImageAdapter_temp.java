package LookBook.ImageAdapter;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.R;
import java.util.ArrayList;

import ImageSelect.SelectActivity;
import Login_Main.activity.MainActivity;
import LookBook.ImageAdapter.ListItem_temp;
import LookBook.activity.LookBookResultActivity;
import LookBook.activity.MergeActivity3;

public class ImageAdapter_temp extends BaseAdapter {
    ArrayList<ListItem_temp> items=new ArrayList<>();
    Context context;

    public void addItem(ListItem_temp item){
        items.add(item);
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position){
        return items.get(position);
    }

    @Override
    public long getItemId(int position){
        return position;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        context=viewGroup.getContext();
        ListItem_temp listItemTemp=items.get(i);

        if(view==null){
            LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view=inflater.inflate(R.layout.lookbook_listitem_temp, viewGroup, false);
        }

        ImageButton imageView=(ImageButton)view.findViewById(R.id.image_view);
        imageView.setImageURI(listItemTemp.getUri());
        //TextView numText=view.findViewById(R.id.numText);
        //numText.setText(listItemTemp.getNumber());

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context.getApplicationContext(), "룩북 저장이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context.getApplicationContext(), MainActivity.class);
                context.startActivity(intent);
            }
        });



        return view;
    }
}
