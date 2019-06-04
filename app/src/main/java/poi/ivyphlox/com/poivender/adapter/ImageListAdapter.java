package poi.ivyphlox.com.poivender.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import java.util.List;

import poi.ivyphlox.com.poivender.R;


public class ImageListAdapter extends RecyclerView.Adapter<ImageListAdapter.MyViewHolder> {

    private List<String> todayscollectionList;
    private RecyclerView recyclerView ;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView ivBusiness;
        private LinearLayout llmain;

        public MyViewHolder(View view) {
            super(view);
            llmain =  view.findViewById(R.id.llmain);
            ivBusiness =  view.findViewById(R.id.ivBusiness);

        }

    }

    public static interface ClickListener{
        public void onClick(View view, int position);
        public void onLongClick(View view, int position);
    }

    public ImageListAdapter(List<String> todayscollectionList, RecyclerView recyclerView) {
        this.todayscollectionList = todayscollectionList;
        this.recyclerView = recyclerView;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_image, parent, false);

        return new MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        String todayscollectionModel = todayscollectionList.get(position);



    }

    @Override
    public int getItemCount() {
        return todayscollectionList.size();
    }
}