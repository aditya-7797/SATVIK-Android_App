package com.example.satvik;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {
    private List<OrderItem> orderItemList;
    private Context context;
    private OrderAdapterListener listener;

    public interface OrderAdapterListener {
        void onItemDeleted(OrderItem item);
    }

    public OrderAdapter(List<OrderItem> orderItemList, Context context, OrderAdapterListener listener) {
        this.orderItemList = orderItemList;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_order, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        OrderItem item = orderItemList.get(position);
        holder.textItemName.setText(item.getItemName());
        holder.textItemPrice.setText("Price: Rs " + item.getItemPrice());
        holder.textItemQuantity.setText("Quantity: " + item.getQuantity());
        holder.textItemTotal.setText("Total: Rs " + item.getItemPrice() * item.getQuantity());

        holder.buttonDeleteItem.setOnClickListener(v -> {
            listener.onItemDeleted(item);
            orderItemList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, orderItemList.size());
        });
    }

    @Override
    public int getItemCount() {
        return orderItemList.size();
    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView textItemName, textItemPrice, textItemQuantity, textItemTotal;
        Button buttonDeleteItem;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            textItemName = itemView.findViewById(R.id.textItemName);
            textItemPrice = itemView.findViewById(R.id.textItemPrice);
            textItemQuantity = itemView.findViewById(R.id.textItemQuantity);
            textItemTotal = itemView.findViewById(R.id.textItemTotal);
            buttonDeleteItem = itemView.findViewById(R.id.buttonDeleteItem);
        }
    }
}
