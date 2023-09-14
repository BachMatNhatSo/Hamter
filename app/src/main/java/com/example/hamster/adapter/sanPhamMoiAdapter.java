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

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.util.List;

public class sanPhamMoiAdapter extends RecyclerView.Adapter<sanPhamMoiAdapter.MyViewHolder> {
    Context context;
    List<sanPhamMoi> array;

    public sanPhamMoiAdapter(Context context, List<sanPhamMoi> array) {
        this.context = context;
        this.array = array;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sanpham_moi,parent,false);
        return new MyViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        sanPhamMoi sanPhamMoi =array.get(position);
        holder.txtten.setText(sanPhamMoi.getTensanpham());
        DecimalFormat decimalFormat =new DecimalFormat("###,###,###");
        holder.txtgia.setText("Giá: "+decimalFormat.format(Double.parseDouble(sanPhamMoi.getGiasp()))+"Đ");
        Glide.with(context).load(sanPhamMoi.getHinhanh()).into(holder.imgHinhAnh);
    }

    @Override
    public int getItemCount() {
        return array.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtgia,txtten;
        ImageView imgHinhAnh;
            public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtgia=itemView.findViewById(R.id.itemsp_gia);
                txtten=itemView.findViewById(R.id.itemsp_ten);
                imgHinhAnh=itemView.findViewById(R.id.itemsp_image);
        }
    }
}
