package com.example.satvik;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Bill extends AppCompatActivity implements OrderAdapter.OrderAdapterListener {
    private static final String TAG = "BillActivity";

    private RecyclerView recyclerViewOrders;
    private OrderAdapter orderAdapter;
    private List<OrderItem> orderItemList;
    private String userMobileNumber;

    private String suppliers_Mobile_Number;
    private TextView textGrandTotal;
    private double grandTotal = 0.0;

    private Button place_order;
    private String userName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);

        Intent i = getIntent();
        userMobileNumber = i.getStringExtra("user_no");
        suppliers_Mobile_Number = i.getStringExtra("suppliers_No");
        Toast.makeText(this, userMobileNumber, Toast.LENGTH_SHORT).show();
        Toast.makeText(this, suppliers_Mobile_Number, Toast.LENGTH_SHORT).show();

        recyclerViewOrders = findViewById(R.id.recyclerViewOrders);
        textGrandTotal = findViewById(R.id.textGrandTotal);
        place_order = findViewById(R.id.buttonPlaceOrder);
        recyclerViewOrders.setLayoutManager(new LinearLayoutManager(this));
        orderItemList = new ArrayList<>();

        loadOrdersFromDatabase();

        orderAdapter = new OrderAdapter(orderItemList, this, this);
        recyclerViewOrders.setAdapter(orderAdapter);

        fetchUserNameAndStoreOrders();
    }

    private void loadOrdersFromDatabase() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users")
                .document(userMobileNumber)
                .collection("ordering_history")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String itemName = document.getString("itemName");
                            double itemPrice;
                            if (document.get("itemPrice") instanceof String) {
                                itemPrice = Double.parseDouble(document.getString("itemPrice"));
                            } else {
                                itemPrice = document.getDouble("itemPrice");
                            }

                            int quantity = 0;
                            Object quantityObject = document.get("quantity");

                            if (quantityObject == null) {
                                Log.e(TAG, "Field 'quantity' is missing in document: " + document.getId());
                            } else if (quantityObject instanceof String) {
                                try {
                                    quantity = Integer.parseInt((String) quantityObject);
                                } catch (NumberFormatException e) {
                                    Log.e(TAG, "Failed to parse quantity as integer: " + quantityObject, e);
                                }
                            } else if (quantityObject instanceof Long) {
                                quantity = ((Long) quantityObject).intValue();
                            } else if (quantityObject instanceof Double) {
                                quantity = ((Double) quantityObject).intValue();
                            } else if (quantityObject instanceof Integer) {
                                quantity = (Integer) quantityObject;
                            } else {
                                Log.e(TAG, "Field 'quantity' is not a valid number type: " + quantityObject.getClass().getName());
                                throw new RuntimeException("Field 'quantity' is not a valid number type");
                            }

                            String documentId = document.getId();
                            String supplierContact = document.getString(suppliers_Mobile_Number);

                            OrderItem item = new OrderItem(itemName, itemPrice, quantity, userMobileNumber, documentId, supplierContact);
                            orderItemList.add(item);
                            grandTotal += itemPrice * quantity;
                        }
                        orderAdapter.notifyDataSetChanged();
                        updateGrandTotalText();
                    } else {
                        Log.e(TAG, "Error getting documents: ", task.getException());
                    }
                });
    }

    @Override
    public void onItemDeleted(OrderItem item) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users")
                .document(userMobileNumber)
                .collection("ordering_history")
                .document(item.getDocumentId())
                .delete()
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(Bill.this, "Item deleted successfully", Toast.LENGTH_SHORT).show();
                    grandTotal -= item.getItemPrice() * item.getQuantity();
                    updateGrandTotalText();
                })
                .addOnFailureListener(e -> Log.e(TAG, "Error deleting item", e));
    }

    private void updateGrandTotalText() {
        textGrandTotal.setText("Grand Total: Rs " + grandTotal);
    }

    private void fetchUserNameAndStoreOrders() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users")
                .document(userMobileNumber)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        userName = documentSnapshot.getString("fullname");
                        Log.d(TAG, "User name fetched: " + userName);
                        place_order.setOnClickListener(v -> fetchUserDataAndStoreUnderSupplier(userName));
                    } else {
                        Log.e(TAG, "User document does not exist");
                    }
                })
                .addOnFailureListener(e -> Log.e(TAG, "Error fetching user name", e));
    }

    private void fetchUserDataAndStoreUnderSupplier(String userName) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();


        db.collection("users")
                .document(userMobileNumber)
                .collection("ordering_history")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        int itemCounter = 1;
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String itemName = document.getString("itemName");

                            double itemPrice = 0.0;
                            Object itemPriceObject = document.get("itemPrice");
                            if (itemPriceObject instanceof Number) {
                                itemPrice = ((Number) itemPriceObject).doubleValue();
                            } else if (itemPriceObject instanceof String) {
                                try {
                                    itemPrice = Double.parseDouble((String) itemPriceObject);
                                } catch (NumberFormatException e) {
                                    Log.e(TAG, "Failed to parse itemPrice as double: " + itemPriceObject, e);
                                }
                            } else {
                                Log.e(TAG, "Field 'itemPrice' is not a valid number type: " + itemPriceObject);
                                throw new RuntimeException("Field 'itemPrice' is not a valid number type");
                            }

                            long quantity = 0;
                            Object quantityObject = document.get("quantity");
                            if (quantityObject instanceof Number) {
                                quantity = ((Number) quantityObject).longValue();
                            } else if (quantityObject instanceof String) {
                                try {
                                    quantity = Long.parseLong((String) quantityObject);
                                } catch (NumberFormatException e) {
                                    Log.e(TAG, "Failed to parse quantity as long: " + quantityObject, e);
                                }
                            } else {
                                Log.e(TAG, "Field 'quantity' is not a valid number type: " + quantityObject);
                                throw new RuntimeException("Field 'quantity' is not a valid number type");
                            }

                            String Suppliers_Contact = document.getString("Suppliers_Contact");

                            Map<String, Object> orderData = new HashMap<>();
                            orderData.put("itemName", itemName);
                            orderData.put("itemPrice", itemPrice);
                            orderData.put("quantity", quantity);
                            orderData.put("userMobileNumber", userMobileNumber);
                            orderData.put("userName", userName);

                            String itemId = "item" + itemCounter;

                            db.collection("suppliers_acc")
                                    .document(Suppliers_Contact)
                                    .collection("new_orders")
                                    .document(itemId)
                                    .set(orderData)
                                    .addOnSuccessListener(documentReference -> Log.d(TAG, "Order placed for supplier: " + Suppliers_Contact))
                                    .addOnFailureListener(e -> Log.e(TAG, "Error placing order for supplier: " + Suppliers_Contact, e));

                            itemCounter++;
                        }
                    } else {
                        Log.e(TAG, "Error getting documents: ", task.getException());
                    }
                });
    }
}
