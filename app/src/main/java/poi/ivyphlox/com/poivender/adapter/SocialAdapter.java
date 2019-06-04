package poi.ivyphlox.com.poivender.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import poi.ivyphlox.com.poivender.R;
import poi.ivyphlox.com.poivender.model.Social;

/**
 * Created by dirgha-dev-05 on 6/4/18.
 */

public class SocialAdapter extends RecyclerView.Adapter<SocialAdapter.MyViewHolder> {

    private Context mContext;
    private static RecyclerViewClickListener itemListener;
    List<Social> contactModels = new ArrayList<>();
    public FollowersDetailsAdapterListener onClickListener;


    /**
     * here we are developed horizontal scrolling RecyclerView
     * for to the Map screen
     * not it is hide from UI bt may be in future We need this
     */
    public SocialAdapter(Context context, List<Social> contactModels) {
        this.mContext = context;
        this.contactModels = contactModels;


    }

    public interface FollowersDetailsAdapterListener {

        void iconTextViewOnClick(View v, int position);

        void iconImageViewOnClick(View v, int position);

    }


    @Override
    public SocialAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_edit_social_main, parent, false);

        return new MyViewHolder(view);
    }

    @SuppressLint("ResourceType")
    @Override
    public void onBindViewHolder(final SocialAdapter.MyViewHolder holder, final int position) {

        final Social contact = contactModels.get(position);
//        holder.edtSocial.setText(contact.getLink());
//        holder.edtSocial.setHint(contact.getName());
//        holder.edtSocial.addTextChangedListener(new TextWatcher() {
//
//            @Override
//            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
//                contact.setLink(cs.toString());
//               // contact.setName(holder.edtSocial.getHint().toString());
//                contactModels.set(position, contact);
//            }
//
//            @Override
//            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable arg0) {
//                contact.setLink(arg0.toString());
//               // contact.setName(holder.edtSocial.getHint().toString());
//                contactModels.set(position, contact);
//            }
//        });

    }



    @Override
    public int getItemCount() {
        return contactModels.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        private EditText edtSocial;


        public MyViewHolder(View itemView) {
            super(itemView);

            edtSocial = itemView.findViewById(R.id.edtSocial);

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
    public List<Social> getContactModelProfilePOJO()
    {
        return contactModels;
    }


}



