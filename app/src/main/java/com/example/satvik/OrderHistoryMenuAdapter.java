package com.example.satvik;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class OrderHistoryMenuAdapter extends RecyclerView.Adapter<OrderHistoryMenuAdapter.OrderHistoryMenuViewHolder> {
    private List<OrderHistoryMenuItem> menuItems;
    private Context context;

    public OrderHistoryMenuAdapter(List<OrderHistoryMenuItem> menuItems, Context context) {
        this.menuItems = menuItems;
        this.context = context;
    }

    @NonNull
    @Override
    public OrderHistoryMenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.order_history_view, parent, false);
        return new OrderHistoryMenuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderHistoryMenuViewHolder holder, int position) {
        final OrderHistoryMenuItem menuItem = menuItems.get(position);
        holder.orderedDate.setText("Ordered date: " + menuItem.getOrderedDate());
        holder.itemName.setText("Item Name: " + menuItem.getItemName());
        holder.itemPrice.setText("Price: " + menuItem.getItemPrice());
        holder.quantity.setText("Quantity: " + menuItem.getQuantity());
        holder.supplierName.setText("Supplier's Name: " + menuItem.getSuppliers_Name());
        holder.contact.setText("Contact: " + menuItem.getSuppliers_Contact());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showContactDialog(menuItem.getSuppliers_Contact());
            }
        });
    }

    @Override
    public int getItemCount() {
        return menuItems.size();
    }

    class OrderHistoryMenuViewHolder extends RecyclerView.ViewHolder {
        TextView orderedDate, itemName, itemPrice, supplierName, contact, quantity;

        OrderHistoryMenuViewHolder(@NonNull View itemView) {
            super(itemView);
            orderedDate = itemView.findViewById(R.id.ordered_date);
            itemName = itemView.findViewById(R.id.item_name);
            itemPrice = itemView.findViewById(R.id.item_price);
            supplierName = itemView.findViewById(R.id.suppliers_Name);
            contact = itemView.findViewById(R.id.contact);
            quantity = itemView.findViewById(R.id.quantity);
        }
    }

    private void showContactDialog(final String supplierContact) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Contact Supplier");
        builder.setMessage("Do you want to call or send an SMS to the supplier?");
        builder.setPositiveButton("Call", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Make a call
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + supplierContact));
                context.startActivity(intent);
            }
        });
        builder.setNegativeButton("SMS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Send SMS
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("smsto:" + supplierContact));
                context.startActivity(intent);
            }
        });
        builder.setNeutralButton("Cancel", null);
        builder.show();
    }
}
