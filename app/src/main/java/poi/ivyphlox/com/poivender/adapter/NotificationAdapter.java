package poi.ivyphlox.com.poivender.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import poi.ivyphlox.com.poivender.R;
import poi.ivyphlox.com.poivender.model.NotificationModel;

/**
 * Created by dirgha-dev-05 on 6/4/18.
 */

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.MyViewHolder> {

    private Context mContext;
    private static RecyclerViewClickListener itemListener;
    List<NotificationModel> contactModels = new ArrayList<>();
    public FollowersDetailsAdapterListener onClickListener;


    /**
     * here we are developed horizontal scrolling RecyclerView
     * for to the Map screen
     * not it is hide from UI bt may be in future We need this
     */
    public NotificationAdapter(Context context, List<NotificationModel> contactModels) {
        this.mContext = context;
        this.contactModels = contactModels;


    }

    public interface FollowersDetailsAdapterListener {

        void iconTextViewOnClick(View v, int position);

        void iconImageViewOnClick(View v, int position);

    }


    @Override
    public NotificationAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notification_main, parent, false);

        return new MyViewHolder(view);
    }

    @SuppressLint("ResourceType")
    @Override
    public void onBindViewHolder(final NotificationAdapter.MyViewHolder holder, final int position) {

        final NotificationModel contact = contactModels.get(position);
        holder.tvName.setText(contact.getSender_mobile()+" ");
        holder.tvPhone.setText("1 hour ago");

    }



    @Override
    public int getItemCount() {
        return contactModels.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        private TextView tvName;
        private TextView tvPhone;
        private TextView tvInvite;
        private ImageView ivPin;

        public MyViewHolder(View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tvName);
            tvPhone = itemView.findViewById(R.id.tvPhone);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            //  itemListener.recyclerViewListClicked(view, this.getLayoutPosition());


        }
    }

    public interface RecyclerViewClickListener {

        public void recyclerViewListClicked(View v, int position);
    }


}



