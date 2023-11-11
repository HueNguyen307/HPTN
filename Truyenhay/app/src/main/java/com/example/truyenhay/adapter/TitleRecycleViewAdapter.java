package com.example.truyenhay.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.truyenhay.R;
import com.example.truyenhay.model.Book;

import java.util.List;

public class TitleRecycleViewAdapter extends RecyclerView.Adapter<TitleRecycleViewAdapter.ViewHolder>{
    private List<Book> list;
    private ItemListener itemListener;

    public void setList(List<Book> list) {
        this.list = list;
        notifyDataSetChanged();
    }
    public Book getTitle(int position){
        return list.get(position);
    }

    public void setItemListener(ItemListener itemListener) {
        this.itemListener = itemListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_recycleview_title_manager, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Book book=list.get(position);
        holder.name.setText(book.getName());
        holder.des.setText("mo ta");
        holder.free.setText("???");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name, des, free;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.itemRecycleViewTitleName);
            des=itemView.findViewById(R.id.itemRecycleViewTitleDes);
            free=itemView.findViewById(R.id.itemRecycleViewTitleFree);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(itemListener!=null){
                itemListener.onItemClick(view,getAdapterPosition());
            }
        }
    }
    public interface ItemListener{
        void onItemClick(View view, int position);
    }
}
