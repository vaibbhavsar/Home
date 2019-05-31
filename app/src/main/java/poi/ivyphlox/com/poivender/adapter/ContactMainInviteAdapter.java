package poi.ivyphlox.com.poivender.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
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

public class ContactMainInviteAdapter extends RecyclerView.Adapter<ContactMainInviteAdapter.MyViewHolder> {

    private Context mContext;
    private static RecyclerViewClickListener itemListener;
    public List<Contact> contactModels = new ArrayList<>();
    public FollowersDetailsAdapterListener onClickListener;
    private int flag = 0;
    private Contact contact;

    public static List<String> contactSelected = new ArrayList<>();
    public static List<Integer> contactSelectedInt = new ArrayList<>();




    /**
     * here we are developed horizontal scrolling RecyclerView
     * for to the Map screen
     * not it is hide from UI bt may be in future We need this
     */
    public ContactMainInviteAdapter(Context context, List<Contact> contactModels, FollowersDetailsAdapterListener onClickListener, int flag) {
        this.mContext = context;
        this.contactModels = contactModels;
        this.onClickListener = onClickListener;
        itemListener = (RecyclerViewClickListener)mContext;
        this.flag = flag;

    }

    /**
     * here we are developed horizontal scrolling RecyclerView
     * for to the Map screen
     * not it is hide from UI bt may be in future We need this
     */
    public ContactMainInviteAdapter(Context context, List<Contact> contactModels, FollowersDetailsAdapterListener onClickListener) {
        this.mContext = context;
        this.contactModels = contactModels;
        this.onClickListener = onClickListener;

    }

    public interface FollowersDetailsAdapterListener {

        void iconTextViewOnClick(View v, int position);

        void iconImageViewOnClick(View v, int position);

    }


    @Override
    public ContactMainInviteAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact_main_invite, parent, false);

        return new MyViewHolder(view);
    }

    ContactMainInviteAdapter.MyViewHolder holder;
    @SuppressLint("ResourceType")
    @Override
    public void onBindViewHolder( ContactMainInviteAdapter.MyViewHolder holder, final int position) {
        try {

            this.holder=holder;
            final Contact contact = contactModels.get(position);
            holder.tvName.setText(contact.getName());
            holder.tvPhone.setText(contact.getPhoneNumber());
            //holder.chkIsselected.setChecked(false);
            //holder.chkIsselected.setEnabled(false);

            if (flag == 4) {


                boolean isSelected=false;
                for (String item : contactSelected) {
                    if (item.equalsIgnoreCase(contact.getPhoneNumber())) {
                        isSelected=true;

                    }
                }
//                for (Integer item : contactSelectedInt) {
////                    int item1=item;
//                    Integer integer=position;
//                    if (item.equals(integer)) {
//                        isSelected=true;
//
//                    }
//                }
                //holder.chkIsselected.setChecked(isSelected);



            if(contact.isSelected()) {
                holder.chkIsselected.setChecked(true);
            }else {
                holder.chkIsselected.setChecked(false);

            }

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        itemListener.recyclerViewListClicked(position);
                    }
                });
                holder.chkIsselected.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        itemListener.recyclerViewListClicked(position);
                    }
                });



            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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


    public class MyViewHolder extends RecyclerView.ViewHolder{


        private LinearLayout llMain;
        private TextView tvName;
        private TextView tvPhone;
        private CheckBox chkIsselected;
        private LinearLayout llmain;

        public MyViewHolder(View itemView) {
            super(itemView);

            llMain = itemView.findViewById(R.id.llmain);
            chkIsselected = itemView.findViewById(R.id.chkIsselected);
            tvName = itemView.findViewById(R.id.tvName);
            tvPhone = itemView.findViewById(R.id.tvPhone);
            llmain = itemView.findViewById(R.id.llmain);

            if (flag == 4) {
                chkIsselected.setVisibility(View.VISIBLE);
            }


        }


    }

    public interface RecyclerViewClickListener {

        void recyclerViewListClicked(int position);
    }


}



