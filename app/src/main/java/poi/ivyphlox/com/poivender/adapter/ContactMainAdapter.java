package poi.ivyphlox.com.poivender.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import poi.ivyphlox.com.poivender.R;
import poi.ivyphlox.com.poivender.activity.ContactDetailsActivity;
import poi.ivyphlox.com.poivender.model.Contact;

/**
 * Created by dirgha-dev-05 on 6/4/18.
 */

public class ContactMainAdapter extends RecyclerView.Adapter<ContactMainAdapter.MyViewHolder> {

    private Context mContext;
    private static RecyclerViewClickListener itemListener;
    public List<Contact> contactModels = new ArrayList<>();
    public FollowersDetailsAdapterListener onClickListener;
    private int flag = 0;
    private Contact contact;

    public static List<String> contactSelected = new ArrayList<>();


    /**
     * here we are developed horizontal scrolling RecyclerView
     * for to the Map screen
     * not it is hide from UI bt may be in future We need this
     */
    public ContactMainAdapter(Context context, List<Contact> contactModels, FollowersDetailsAdapterListener onClickListener, int flag) {
        this.mContext = context;
        this.contactModels = contactModels;
        this.onClickListener = onClickListener;
        this.flag = flag;

    }

    /**
     * here we are developed horizontal scrolling RecyclerView
     * for to the Map screen
     * not it is hide from UI bt may be in future We need this
     */
    public ContactMainAdapter(Context context, List<Contact> contactModels, FollowersDetailsAdapterListener onClickListener) {
        this.mContext = context;
        this.contactModels = contactModels;
        this.onClickListener = onClickListener;

    }

    public interface FollowersDetailsAdapterListener {

        void iconTextViewOnClick(View v, int position);

        void iconImageViewOnClick(View v, int position);

    }


    @Override
    public ContactMainAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact_main, parent, false);

        return new MyViewHolder(view);
    }

    @SuppressLint("ResourceType")
    @Override
    public void onBindViewHolder(final ContactMainAdapter.MyViewHolder holder, final int position) {

        contact = contactModels.get(position);
        holder.tvName.setText(contact.getName());
        holder.tvPhone.setText(contact.getPhoneNumber());
        if (contact.get_status().equals("true")) {
            holder.ivPin.setVisibility(View.VISIBLE);
            holder.tvInvite.setVisibility(View.GONE);
        } else {
            holder.tvInvite.setVisibility(View.VISIBLE);
            holder.ivPin.setVisibility(View.GONE);
        }
        holder.ivPin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickListener.iconImageViewOnClick(holder.tvInvite, position);
            }
        });

        holder.tvInvite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                sendSMS(contact.getPhoneNumber(),"Download POI application for Contact sharing with locations. https://drive.google.com/file/d/1RHJvuisqQLWTGY8IP6VdTWtiKGV-SQm-/view?usp=drivesdk");
                onClickListener.iconTextViewOnClick(holder.tvInvite, position);
                if (holder.tvInvite.isSelected()) {
                    holder.llMain.setBackgroundResource(R.color.white);
                    holder.tvInvite.setSelected(false);
                    contactSelected.remove(contact.getPhoneNumber());

                } else {
                    holder.llMain.setBackgroundResource(R.color.selected_trans);
                    holder.tvInvite.setSelected(true);
                    contactSelected.add(contact.getPhoneNumber());

                }
            }
        });
        if (flag == 1) {
            holder.tvInvite.setVisibility(View.GONE);
//            holder.ivPin.setVisibility(View.GONE);
        }
        if (flag == 3) {
            holder.llMain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mContext.startActivity(new Intent(mContext,
                            ContactDetailsActivity.class)
                            .putExtra("mobile",
                                    contactModels.get(position).getPhoneNumber())
                            .putExtra("name",
                                    contactModels.get(position).getName()));
                }
            });
        }
        if (flag == 4) {

            holder.tvInvite.setVisibility(View.GONE);

            if(contact.isSelected()) {
                holder.chkIsselected.setChecked(true);
            }else {
                holder.chkIsselected.setChecked(false);

            }
//            for(String string:contactSelected)
//            {
//                if(string.equalsIgnoreCase(contact.getPhoneNumber()))
//                {
//                    holder.chkIsselected.setChecked(true);
//
//                }
//            }

            holder.chkIsselected.setOnCheckedChangeListener(
                    new CompoundButton.OnCheckedChangeListener() {

                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            if (isChecked) {
                                Contact contact1=new Contact();
                                contact.setSelected(true);
                                //holder.llmain.setBackgroundResource(R.color.selected);
                                holder.chkIsselected.setChecked(true);


                                contactSelected.add(contact.getPhoneNumber());

                                contactModels.set(position,contact1);
                                Log.e("contactTrue",contact1.isSelected()+"");
                              //  notifyItemChanged(position);

                            } else {
                                Contact contact1=new Contact();
                                contact.setSelected(false);
                                //holder.llmain.setBackgroundResource(R.color.selected);
                                holder.chkIsselected.setChecked(false);


                                contactModels.set(position,contact1);
                                Log.e("contactFalse",contact1.isSelected()+"");
                              //  notifyItemChanged(position);
                            }
                        }
                    }
            );
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


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        private LinearLayout llMain;
        private TextView tvName;
        private TextView tvPhone;
        private TextView tvInvite;
        private ImageView ivPin;
        private CheckBox chkIsselected;
        private LinearLayout llmain;

        public MyViewHolder(View itemView) {
            super(itemView);

            llMain = itemView.findViewById(R.id.llmain);
            chkIsselected = itemView.findViewById(R.id.chkIsselected);
            tvName = itemView.findViewById(R.id.tvName);
            tvPhone = itemView.findViewById(R.id.tvPhone);
            tvInvite = itemView.findViewById(R.id.tvinvite);
            ivPin = itemView.findViewById(R.id.ivpin);
            llmain = itemView.findViewById(R.id.llmain);

            if(flag==4)
            {
                chkIsselected.setVisibility(View.VISIBLE);
            }

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
//              itemListener.recyclerViewListClicked(view, this.getLayoutPosition());


        }
    }

    public interface RecyclerViewClickListener {

        public void recyclerViewListClicked(View v, int position);
    }


}



