package com.example.managewine.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.managewine.R;
import com.example.managewine.model.Manufacturer;
import java.util.List;

public class ProducerAdapter extends RecyclerView.Adapter<ProducerAdapter.ViewHolder> {

    private List<Manufacturer> list;
    private Context mCtx;
    private CLicklistener cLicklistener;
    public interface CLicklistener{
        void onClickUpdateProducer(Manufacturer manufacturer);
        void onClickDeleteProducer(Manufacturer manufacturer);
    }
    public void setData(List<Manufacturer> list){
        this.list = list;
        notifyDataSetChanged();
    }
    public ProducerAdapter(List<Manufacturer> list, Context mCtx, CLicklistener cLicklistener) {
        this.list = list;
        this.mCtx = mCtx;
        this.cLicklistener = cLicklistener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_producer, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ProducerAdapter.ViewHolder holder, int position) {
        Manufacturer myList = list.get(position);
        holder.textViewID.setText(myList.getId()+ "");
        holder.textViewName.setText(myList.getName());
        holder.textViewDescreption.setText(myList.getDescription());

        holder.imgOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //creating a popup menu
                PopupMenu popup = new PopupMenu(mCtx, holder.imgOption);
                //inflating menu from xml resource
                popup.inflate(R.menu.options_item);
                //adding click listener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {

                            case R.id.update:
                                cLicklistener.onClickUpdateProducer(myList);
                                break;
                            case R.id.delete:
                                cLicklistener.onClickDeleteProducer(myList);
                                break;
                        }
                        return false;
                    }
                });
                //displaying the popup
                popup.show();
            }
        });
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewID;
        public TextView textViewName;
        public TextView textViewDescreption;
        public ImageView imgOption;

        public ViewHolder(View itemView) {
            super(itemView);
            textViewID = (TextView) itemView.findViewById(R.id.tv_producer_id);
            textViewName = (TextView) itemView.findViewById(R.id.tv_producer_name);
            textViewDescreption = (TextView) itemView.findViewById(R.id.tv_producer_description);
            imgOption = (ImageView) itemView.findViewById(R.id.img_option);
        }
    }
}