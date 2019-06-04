package poi.ivyphlox.com.poivender.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import poi.ivyphlox.com.poivender.R;
import poi.ivyphlox.com.poivender.model.Contact;

/**
 * Created by dirgha-dev-05 on 6/4/18.
 */

public class MemberAdapter extends RecyclerView.Adapter<MemberAdapter.MyViewHolder> {

    private Context mContext;
    private static RecyclerViewClickListener itemListener;
    List<Contact> contactModels = new ArrayList<>();
    public FollowersDetailsAdapterListener onClickListener;


    /**
     * here we are developed horizontal scrolling RecyclerView
     * for to the Map screen
     * not it is hide from UI bt may be in future We need this
     */
    public MemberAdapter(Context context, List<Contact> contactModels) {
        this.mContext = context;
        this.contactModels = contactModels;


    }

    public interface FollowersDetailsAdapterListener {

        void iconTextViewOnClick(View v, int position);

        void iconImageViewOnClick(View v, int position);

    }


    @Override
    public MemberAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_member_main, parent, false);

        return new MyViewHolder(view);
    }

    @SuppressLint("ResourceType")
    @Override
    public void onBindViewHolder(final MemberAdapter.MyViewHolder holder, final int position) {

        final Contact contact = contactModels.get(position);
        holder.tvName.setText(contact.getName());
        holder.tvPhone.setText(contact.getPhoneNumber());

        if(contact.isSelected())
        {
            holder.llmain.setBackgroundResource(R.color.selected);

        }
        holder.llmain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!contact.isSelected()) {
                    contact.setSelected(true);
                    holder.llmain.setBackgroundResource(R.color.selected);
                }else {
                    contact.setSelected(false);
                    holder.llmain.setBackgroundResource(R.color.colorBottomWhite);
                }
            }
        });

    }

    private void sendSMS(String phoneNumber, String message) {

        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber, null, message, null, null);
        Toast.makeText(mContext, "SMS sent.",
                Toast.LENGTH_LONG).show();
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
        private LinearLayout llmain;

        public MyViewHolder(View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tvName);
            tvPhone = itemView.findViewById(R.id.tvPhone);
            tvInvite = itemView.findViewById(R.id.tvinvite);
            ivPin = itemView.findViewById(R.id.ivpin);
            llmain = itemView.findViewById(R.id.llmain);
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



