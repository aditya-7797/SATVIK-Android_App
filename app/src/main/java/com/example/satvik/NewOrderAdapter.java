package com.example.satvik;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class NewOrderAdapter extends RecyclerView.Adapter<NewOrderAdapter.ViewHolder> {
    private List<NewOrder> newOrderList;
    private Context context;
    private String suppliersContact;

    public NewOrderAdapter(List<NewOrder> newOrderList, Context context, String suppliersContact) {
        this.newOrderList = newOrderList;
        this.context = context;
        this.suppliersContact = suppliersContact;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.new_order_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NewOrder newOrder = newOrderList.get(position);
        holder.itemName.setText("Item Name: " + newOrder.getItemName());
        holder.itemQuantity.setText("Quantity: " + newOrder.getQuantity());
        holder.userName.setText("Ordered by: " + newOrder.getUserName());
        holder.userContact.setText("Contact No: " + newOrder.getUserContact());

        holder.confirmOrderButton.setOnClickListener(v -> {
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("suppliers_acc")
                    .document(suppliersContact)
                    .collection("new_orders")
                    .document(newOrder.getDocumentId())
                    .delete()
                    .addOnSuccessListener(aVoid -> {
//                        Toast.makeText(context, "Order confirmed", Toast.LENGTH_SHORT).show();
                        sendSms(newOrder.getUserContact(), "Your order has been confirmed.");
                        newOrderList.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, newOrderList.size());
                    })
                    .addOnFailureListener(e -> Log.e("NewOrderAdapter", "Error confirming order", e));
        });
    }

    @Override
    public int getItemCount() {
        return newOrderList.size();
    }

    private void sendSms(String phoneNumber, String message) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber, null, message, null, null);
//            Toast.makeText(context, "SMS sent to " + phoneNumber, Toast.LENGTH_SHORT).show();
        } else {
            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.SEND_SMS}, 1);
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView itemName, itemQuantity, userName, userContact;
        public TextView confirmOrderButton;

        public ViewHolder(View view) {
            super(view);
            itemName = view.findViewById(R.id.item_name);
            itemQuantity = view.findViewById(R.id.item_quantity);
            userName = view.findViewById(R.id.username);
            userContact = view.findViewById(R.id.user_contact);
            confirmOrderButton = view.findViewById(R.id.view_order);
        }
    }
}
