package poi.ivyphlox.com.poivender.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import poi.ivyphlox.com.poivender.R;
import poi.ivyphlox.com.poivender.model.ContactModelProfilePOJO;

/**
 * Created by dirgha-dev-05 on 6/4/18.
 */

public class EditContactAdapter extends RecyclerView.Adapter<EditContactAdapter.MyViewHolder> {

    private Context mContext;
    private static RecyclerViewClickListener itemListener;
    List<ContactModelProfilePOJO> contactModels = new ArrayList<>();
    List<ContactModelProfilePOJO> contactModels1 = new ArrayList<>();
    public FollowersDetailsAdapterListener onClickListener;


    /**
     * here we are developed horizontal scrolling RecyclerView
     * for to the Map screen
     * not it is hide from UI bt may be in future We need this
     */
    public EditContactAdapter(Context context, List<ContactModelProfilePOJO> contactModels) {
        this.mContext = context;
        this.contactModels = contactModels;

    }

    public interface FollowersDetailsAdapterListener {

        void iconTextViewOnClick(View v, int position);

        void iconImageViewOnClick(View v, int position);

    }


    @Override
    public EditContactAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_edit_contact_main, parent, false);

        return new MyViewHolder(view);
    }

    @SuppressLint("ResourceType")
    @Override
    public void onBindViewHolder(final EditContactAdapter.MyViewHolder holder, final int position) {

        final ContactModelProfilePOJO contact = contactModels.get(position);
        holder.edtMobCode.setText(contact.getCode());
        holder.edtMobile.setText(contact.getContact());
        holder.edtMobile.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                contact.setContact(cs.toString());
                contact.setCode(holder.edtMobCode.getText().toString());
                contactModels.set(position, contact);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                contact.setContact(arg0.toString());
                contact.setCode(holder.edtMobCode.getText().toString());
                contactModels.set(position, contact);
            }
        });


    }

    public List<ContactModelProfilePOJO> getContactModelProfilePOJO()
    {
        return contactModels;
    }


    @Override
    public int getItemCount() {
        return contactModels.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        private EditText edtMobCode;
        private EditText edtMobile;


        public MyViewHolder(View itemView) {
            super(itemView);

            edtMobCode = itemView.findViewById(R.id.edtMobCode);
            edtMobile = itemView.findViewById(R.id.edtMobile);

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



