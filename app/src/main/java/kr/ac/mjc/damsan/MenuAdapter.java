package kr.ac.mjc.damsan;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ViewHolder> {

    List<Menu> mMenuList;
    public MenuAdapter(List<Menu> menuList) {
        this.mMenuList = menuList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_menu, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Menu menu = this.mMenuList.get(position);
        holder.bind(menu);
    }

    @Override
    public int getItemCount() {
        return mMenuList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageIv;
        TextView foodTv;
        TextView foodpriceTv;
        TextView foodtextTv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageIv = itemView.findViewById(R.id.image_iv);
            foodTv = itemView.findViewById(R.id.food_tv);
            foodpriceTv = itemView.findViewById(R.id.food_price_tv);
            foodtextTv = itemView.findViewById(R.id.food_text_tv);
        }
        public void bind(Menu menu) {
            foodTv.setText(menu.getName());
            foodpriceTv.setText(menu.getPrice());
            foodtextTv.setText(menu.getText());
            Glide.with(imageIv).load(menu.getImageUrl()).into(imageIv);
        }
    }
}
