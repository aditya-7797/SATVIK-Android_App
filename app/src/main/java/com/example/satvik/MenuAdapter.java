package com.example.satvik;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuViewHolder> {
    private List<MenuItem2> menuItems;
    private Context context;
    private FirebaseFirestore firestore;
    private static final String TAG = "MenuAdapter";

    public MenuAdapter(List<MenuItem2> menuItems, Context context) {
        this.menuItems = menuItems;
        this.context = context;
        this.firestore = FirebaseFirestore.getInstance(); // Initialize Firestore here
    }

    @NonNull
    @Override
    public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.menu_view, parent, false);
        return new MenuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuViewHolder holder, int position) {
        MenuItem2 menuItem = menuItems.get(position);
        holder.nameTextView.setText(menuItem.getName());
        holder.priceTextView.setText(menuItem.getPrice());

        // Load image from URL using Picasso with placeholder and error handling
        Picasso.get()
                .load(menuItem.getImageUrl())
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return menuItems.size();
    }

    class MenuViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView nameTextView;
        TextView priceTextView;
        Button orderButton;

        MenuViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.food_item_img);
            nameTextView = itemView.findViewById(R.id.food_item_name);
            priceTextView = itemView.findViewById(R.id.food_item_price);
            orderButton = itemView.findViewById(R.id.order);

            orderButton.setOnClickListener(v -> {
                int position = getAdapterPosition();
                MenuItem2 menuItem = menuItems.get(position);

                // Show a dialog to input quantity
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Enter Quantity");

                // Set up the input
                final EditText input = new EditText(context);
                input.setInputType(android.text.InputType.TYPE_CLASS_NUMBER);
                builder.setView(input);

                // Set up the buttons
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String quantity = input.getText().toString();
                        if (quantity.isEmpty()) {
                            Toast.makeText(context, "Please enter a quantity", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        // Navigate to Bill activity and pass the necessary data
                        String supplierMobileNo = menuItem.getMobile();
                        String usermobileNo = menuItem.getUser_mobile();
                        String username = menuItem.getUser_name();

                        Intent intent = new Intent(context, Bill.class);
                        intent.putExtra("menu_item", menuItem.getName());
                        intent.putExtra("item_price", menuItem.getPrice());
                        intent.putExtra("user_no", menuItem.getUser_mobile());
                        intent.putExtra("suppliers_No", menuItem.getMobile());
                        intent.putExtra("quantity", quantity);
                        context.startActivity(intent);

                        LocalDate currentDate = LocalDate.now();
                        String dateString = currentDate.toString();

                        // Create a reference to the "orders" subcollection under the supplier's mobile number
                        CollectionReference ordersRef = firestore.collection("suppliers_acc")
                                .document(supplierMobileNo)
                                .collection("orders");

                        // Create a reference to the current date collection under "orders"
                        CollectionReference dateRef = ordersRef.document(dateString).collection("users");

                        // Create a reference to the user's mobile number collection under the date document
                        CollectionReference userRef = dateRef.document(usermobileNo).collection("items");

                        // Create a new order document with item name, price, and quantity
                        Map<String, Object> order = new HashMap<>();
                        order.put("itemName", nameTextView.getText().toString());
                        order.put("itemPrice", priceTextView.getText().toString());
                        order.put("quantity", quantity);

                        // Add the order to the "items" subcollection with a custom document ID
                        userRef.document("item" + (position + 1))
                                .set(order)
                                .addOnSuccessListener(aVoid -> {
                                    Log.d(TAG, "Order added successfully with ID: " + supplierMobileNo);
                                    Toast.makeText(v.getContext(), "Order Placed Successfully", Toast.LENGTH_SHORT).show();
                                })
                                .addOnFailureListener(e -> {
                                    Log.e(TAG, "Error adding order", e);
                                    Toast.makeText(v.getContext(), "Failed to place order", Toast.LENGTH_SHORT).show();
                                });

                        // Add the order to the user's ordering history
                        CollectionReference ordersHistoryRef = firestore.collection("users")
                                .document(usermobileNo)
                                .collection("ordering_history");

                        Map<String, Object> orderingHistory = new HashMap<>();
                        orderingHistory.put("orderedDate", dateString);
                        orderingHistory.put("itemName", nameTextView.getText().toString());
                        orderingHistory.put("itemPrice", priceTextView.getText().toString());
                        orderingHistory.put("quantity", quantity);
                        orderingHistory.put("Suppliers_Name", username);
                        orderingHistory.put("Suppliers_Contact", supplierMobileNo);

                        ordersHistoryRef.document("item" + (position + 1))
                                .set(orderingHistory)
                                .addOnSuccessListener(aVoid -> {
                                    Log.d(TAG, "Order history added successfully for user: " + usermobileNo);
                                })
                                .addOnFailureListener(e -> {
                                    Log.e(TAG, "Error adding order history", e);
                                });
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
            });
        }
    }
}
