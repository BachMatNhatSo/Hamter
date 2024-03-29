package com.example.hamster.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.hamster.Interface.IImageClickListener;
import com.example.hamster.R;
import com.example.hamster.model.EventBus.TinhTongEvent;
import com.example.hamster.model.gioHang;
import com.example.hamster.utils.utils;

import org.greenrobot.eventbus.EventBus;

import java.text.DecimalFormat;
import java.util.List;

public class gioHangAdapter extends RecyclerView.Adapter<gioHangAdapter.MyViewHolder> {
    Context context;
    List<gioHang> gioHangList;

    public gioHangAdapter(Context context, List<gioHang> gioHangList) {
        this.context = context;
        this.gioHangList = gioHangList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_giohang,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        gioHang gioHang = gioHangList.get(position);
        holder.item_GioHang_TenSP.setText(gioHang.getTensp());
        holder.item_GioHang_SoLuong.setText(gioHang.getSoluong() +" ");
        Glide.with(context).load(gioHang.getHinhsp()).into(holder.item_GioHang_Image);
        DecimalFormat decimalFormat =new DecimalFormat("###,###,###");
        holder.item_GioHang_Gia.setText("Giá: "+decimalFormat.format((gioHang.getGiasp()))+"Đ");
        long gia = gioHang.getSoluong() * gioHang.getGiasp();
        holder.item_GioHang_Gia_Tong_Tren_SP.setText(decimalFormat.format(gia));
        holder.setiImageClickListener(new IImageClickListener() {
           @Override
           public void onImageClick(View view, int pos, int giatri) {
               if(giatri ==1 ){
                   if(gioHangList.get(pos).getSoluong() > 1){
                       int soluongmoi = gioHangList.get(pos).getSoluong() - 1;
                       gioHangList.get(pos).setSoluong(soluongmoi);
                       //
                       holder.item_GioHang_SoLuong.setText(gioHangList.get(pos).getSoluong() +" ");
                       long gia = gioHangList.get(pos).getSoluong() * gioHangList.get(pos).getGiasp();
                       holder.item_GioHang_Gia_Tong_Tren_SP.setText(decimalFormat.format(gia));
                       EventBus.getDefault().postSticky(new TinhTongEvent());
                   }
                   else if(gioHangList.get(pos).getSoluong() == 1){
                       AlertDialog.Builder builder = new AlertDialog.Builder(view.getRootView().getContext());
                       builder.setTitle("Thông Báo");
                       builder.setMessage("Bạn Có Muốn Xóa sản phẩm này khỏi giỏ hàng? ");
                       builder.setPositiveButton("Đồng Ý", new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialogInterface, int i) {
                               utils.mangGioHang.remove(pos);
                               notifyDataSetChanged();
                               EventBus.getDefault().postSticky(new TinhTongEvent());
                           }
                       });
                       builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                           }
                       });
                       builder.show();

                   }
               }else if(giatri == 2){
                   if(gioHangList.get(pos).getSoluong()<11){
                       int soluongmoi = gioHangList.get(pos).getSoluong()+1;
                       gioHangList.get(pos).setSoluong(soluongmoi);
                   }
                   holder.item_GioHang_SoLuong.setText(gioHangList.get(pos).getSoluong() +" ");
                   long gia = gioHangList.get(pos).getSoluong() * gioHangList.get(pos).getGiasp();
                   holder.item_GioHang_Gia_Tong_Tren_SP.setText(decimalFormat.format(gia));
                   EventBus.getDefault().postSticky(new TinhTongEvent());
               }
             //

           }
       });
    }

    @Override
    public int getItemCount() {
        return gioHangList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView item_GioHang_Image,img_Cong, img_tru;
        TextView item_GioHang_TenSP,item_GioHang_Gia,item_GioHang_SoLuong,item_GioHang_Gia_Tong_Tren_SP;
        IImageClickListener iImageClickListener;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            item_GioHang_Image =itemView.findViewById(R.id.item_GioHang_Image);
            item_GioHang_TenSP=itemView.findViewById(R.id.item_GioHang_TenSP);
            item_GioHang_Gia=itemView.findViewById(R.id.item_GioHang_Gia);
            item_GioHang_SoLuong=itemView.findViewById(R.id.item_GioHang_SoLuong);
            item_GioHang_Gia_Tong_Tren_SP=itemView.findViewById(R.id.item_GioHang_Gia_Tong_Tren_SP);
            img_tru = itemView.findViewById(R.id.item_GioHang_Tru);
            img_Cong = itemView.findViewById(R.id.item_GioHang_Cong);

            img_Cong.setOnClickListener(this);
            img_tru.setOnClickListener(this);

        }

        public void setiImageClickListener(IImageClickListener iImageClickListener) {
            this.iImageClickListener = iImageClickListener;
        }

        @Override
        public void onClick(View view) {
            if(view == img_tru){
                iImageClickListener.onImageClick(view,getAdapterPosition(),1);
            }else if(view == img_Cong){
                iImageClickListener.onImageClick(view , getAdapterPosition(),2);
            }
        }
    }
}
