package poi.ivyphlox.com.poivender.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.bumptech.glide.Glide;

import java.io.File;
import java.util.List;

import poi.ivyphlox.com.poivender.R;
import poi.ivyphlox.com.poivender.model.ImageModelBussiness;


public class ImageListAdapter extends RecyclerView.Adapter<ImageListAdapter.MyViewHolder> {

    private List<ImageModelBussiness> todayscollectionList;
    private RecyclerView recyclerView ;
    private Context mContext;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView ivBusiness;
//        private RelativeLayout llmain;

        public MyViewHolder(View view) {
            super(view);
//            llmain =  view.findViewById(R.id.llmain);
            ivBusiness =  view.findViewById(R.id.ivBusiness);

        }

    }

    public static interface ClickListener{
        public void onClick(View view, int position);
        public void onLongClick(View view, int position);
    }

    public ImageListAdapter(List<ImageModelBussiness> todayscollectionList,
                            RecyclerView recyclerView,
                            Context mContext) {
        this.todayscollectionList = todayscollectionList;
        this.recyclerView = recyclerView;
        this.mContext = mContext;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_image, parent, false);

        return new MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ImageModelBussiness todayscollectionModel = todayscollectionList.get(position);
//        Glide.with(mContext)
//                .load(new File(todayscollectionModel.getPath())).into(holder.ivBusiness);

        holder.ivBusiness.setImageURI(todayscollectionModel.getUri());


    }

    @Override
    public int getItemCount() {
        return todayscollectionList.size();
    }
}