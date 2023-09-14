package com.example.hamster.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.hamster.R;
import com.example.hamster.model.sanPhamMoi;

import java.text.DecimalFormat;
import java.util.List;

public class hamsterAdapter extends RecyclerView.Adapter<hamsterAdapter.MyViewHolder> {
    Context context;
    List<sanPhamMoi> array;

    public hamsterAdapter(Context context, List<sanPhamMoi> array) {
        this.context = context;
        this.array = array;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hamster,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        sanPhamMoi sanPhamMoi= array.get(position);
        holder.txttensp.setText(sanPhamMoi.getTensanpham());
        DecimalFormat decimalFormat =new DecimalFormat("###,###,###");
        holder.txtgiasp.setText("Giá: "+decimalFormat.format(Double.parseDouble(sanPhamMoi.getGiasp()))+"Đ");
        holder.txtmota.setText(sanPhamMoi.getMota());
        Glide.with(context).load(sanPhamMoi.getHinhanh()).into(holder.hinhanh);
    }

    @Override
    public int getItemCount() {
        return array.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView txttensp,  txtgiasp,txtmota;
        ImageView hinhanh;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txttensp= itemView.findViewById(R.id.itemHamster_Ten);
            txtgiasp= itemView.findViewById(R.id.itemHamster_Gia);
            txtmota= itemView.findViewById(R.id.itemHamster_MoTa);
            hinhanh= itemView.findViewById(R.id.itemHamster_image);
        }
    }
}
