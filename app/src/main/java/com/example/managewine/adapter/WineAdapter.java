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

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.managewine.R;
import com.example.managewine.model.Manufacturer;
import com.example.managewine.model.Wine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WineAdapter extends RecyclerView.Adapter<WineAdapter.ViewHolder> {
    private List<Wine> list;

    private List<Manufacturer> manufacturers;
    private Context mCtx;

    private boolean isReport;

    Map<Long, String> map;
    private WineAdapter.CLicklistener cLicklistener;
    public interface CLicklistener{
        void onClickUpdate(Wine wine);
        void onClickDelete(Wine wine);
    }

    public void setData(List<Wine> list){
        this.list = list;
        notifyDataSetChanged();
    }

    public WineAdapter(List<Wine> list, List<Manufacturer> manufacturers ,Context mCtx, boolean isRepot ,WineAdapter.CLicklistener cLicklistener) {
        this.list = list;
        this.mCtx = mCtx;
        this.manufacturers = manufacturers;
        this.cLicklistener = cLicklistener;
        this.isReport = isRepot;

        map = new HashMap<>();
        for (Manufacturer m: manufacturers) {
            map.put(m.getId(), m.getName());
        }
    }

    @Override
    public WineAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_wine, parent, false);
        return new WineAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(WineAdapter.ViewHolder holder, int position) {
        Wine myList = list.get(position);
        holder.textViewID.setText(myList.getId()+ "");
        holder.textViewName.setText(myList.getName());
        holder.textViewNongDo.setText(myList.getAlcoholContent() + "");

        if (isReport == true){
            holder.imgOption.setVisibility(View.GONE);
        }else{
            holder.imgOption.setVisibility(View.VISIBLE);
        }

        if (map.get(myList.getProductionCountry()) ==  null){
            holder.textViewNSX.setText("Đã xoá");
        }else{
            holder.textViewNSX.setText(map.get(myList.getProductionCountry()));
        }

        holder.textViewSX.setText(myList.getYearsAged()+"");

        Glide.with(mCtx)
                .load(myList.getImage())
                .apply(new RequestOptions().placeholder(R.drawable.baseline_downloading_24)) // Ảnh thay thế khi đang tải
                .into(holder.imgWine);

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
                                cLicklistener.onClickUpdate(myList);
                                break;
                            case R.id.delete:
                                cLicklistener.onClickDelete(myList);
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
        public TextView textViewNSX;
        public TextView textViewNongDo;
        public TextView textViewSX;
        public ImageView imgWine;
        public ImageView imgOption;

        public ViewHolder(View itemView) {
            super(itemView);
            textViewID = (TextView) itemView.findViewById(R.id.tv_wine_id);
            textViewName = (TextView) itemView.findViewById(R.id.tv_wine_name);
            textViewSX = (TextView) itemView.findViewById(R.id.tv_wine_year);
            textViewNongDo = (TextView) itemView.findViewById(R.id.tv_wine_nongdo);
            textViewNSX = (TextView) itemView.findViewById(R.id.tv_wine_sx);
            imgWine = (ImageView) itemView.findViewById(R.id.img_wine);
            imgOption = (ImageView) itemView.findViewById(R.id.img_option_wine);
        }
    }
}
