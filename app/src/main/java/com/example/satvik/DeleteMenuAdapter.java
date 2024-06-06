package com.example.satvik;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class DeleteMenuAdapter extends RecyclerView.Adapter<DeleteMenuAdapter.DeleteMenuViewHolder> {

    private List<DeleteMenuItem> menuItems;
    private Context context;
    private FirebaseFirestore db;
    private String supplierMobileNo;

    public DeleteMenuAdapter(Context context, List<DeleteMenuItem> menuItems, String supplierMobileNo) {
        this.context = context;
        this.menuItems = menuItems;
        this.db = FirebaseFirestore.getInstance();
        this.supplierMobileNo = supplierMobileNo;
    }

    @NonNull
    @Override
    public DeleteMenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_delete_menu, parent, false);
        return new DeleteMenuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DeleteMenuViewHolder holder, int position) {
        DeleteMenuItem menuItem = menuItems.get(position);
        holder.bind(menuItem);
    }

    @Override
    public int getItemCount() {
        return menuItems.size();
    }

    class DeleteMenuViewHolder extends RecyclerView.ViewHolder {
        private ImageView foodItemImg;
        private TextView foodItemName;
        private TextView foodItemPrice;
        private Button deleteButton;

        public DeleteMenuViewHolder(@NonNull View itemView) {
            super(itemView);
            foodItemImg = itemView.findViewById(R.id.delete_food_item_img);
            foodItemName = itemView.findViewById(R.id.delete_food_item_name);
            foodItemPrice = itemView.findViewById(R.id.delete_food_item_price);
            deleteButton = itemView.findViewById(R.id.deleteButton);
        }

        public void bind(DeleteMenuItem menuItem) {
            foodItemName.setText(menuItem.getName());
            foodItemPrice.setText(menuItem.getPrice());
            Glide.with(context).load(menuItem.getImgURL()).into(foodItemImg);

            deleteButton.setOnClickListener(v -> {
                // Remove the item from the list and notify the adapter
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    menuItems.remove(position);
                    notifyItemRemoved(position);

                    // Delete the item from Firestore
                    db.collection("suppliers_acc")
                            .document(supplierMobileNo)
                            .collection("menu")
                            .document(menuItem.getDocumentId()) // Assumes that DeleteMenuItem has a getDocumentId() method
                            .delete()
                            .addOnSuccessListener(aVoid -> {
                                Toast.makeText(context, "Item deleted", Toast.LENGTH_SHORT).show();
                            })
                            .addOnFailureListener(e -> {
                                Toast.makeText(context, "Error deleting item", Toast.LENGTH_SHORT).show();
                            });
                }
            });
        }
    }
}
