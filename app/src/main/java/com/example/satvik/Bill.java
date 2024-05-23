package com.example.satvik;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Bill extends AppCompatActivity {

    private LinearLayout itemContainer;
    private TextView textGrandTotal;
    private Button buttonProceedForPayment;
    private Button buttonPlaceOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);

        itemContainer = findViewById(R.id.itemContainer);
        textGrandTotal = findViewById(R.id.textGrandTotal);
        buttonProceedForPayment = findViewById(R.id.buttonProceedForPayment);
        buttonPlaceOrder = findViewById(R.id.buttonPlaceOrder);

        Intent i = getIntent();
        String menuItem = i.getStringExtra("menu_item");
        String menuPriceString = i.getStringExtra("item_price");

        if (menuItem != null && menuPriceString != null) {
            try {
                double menuPrice = Double.parseDouble(menuPriceString);
                addItem(menuItem, menuPrice, 1);
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Invalid price format", Toast.LENGTH_SHORT).show();
            }
        }

        // Set onClickListeners for buttons
        buttonProceedForPayment.setOnClickListener(v -> {
            // Proceed for payment action
        });

        buttonPlaceOrder.setOnClickListener(v -> {
            // Place order action
        });

        Toast.makeText(Bill.this, menuItem, Toast.LENGTH_SHORT).show();
        Toast.makeText(Bill.this, menuPriceString, Toast.LENGTH_SHORT).show();
    }

    private void addItem(String name, double price, int quantity) {
        // Inflate item layout
        LinearLayout itemView = (LinearLayout) getLayoutInflater().inflate(R.layout.item_layout, null);

        // Set item details
        TextView itemName = itemView.findViewById(R.id.textItemName);
        TextView itemPrice = itemView.findViewById(R.id.textItemPrice);
        EditText itemQuantity = itemView.findViewById(R.id.editItemQuantity);
        TextView itemAmount = itemView.findViewById(R.id.textItemAmount);
        Button deleteButton = itemView.findViewById(R.id.buttonDeleteItem);

        itemName.setText(name);
        itemPrice.setText(String.format("%.2f", price));
        itemQuantity.setText(String.valueOf(quantity));
        double amount = price * quantity;
        itemAmount.setText(String.format("%.2f", amount));

        // Set onClickListener for delete button
        deleteButton.setOnClickListener(v -> {
            // Delete item action
            itemContainer.removeView(itemView);
            updateGrandTotal();
        });

        // Add item to container
        itemContainer.addView(itemView);

        // Update grand total
        updateGrandTotal();
    }

    private void updateGrandTotal() {
        double grandTotal = 0.0;

        for (int i = 0; i < itemContainer.getChildCount(); i++) {
            LinearLayout itemView = (LinearLayout) itemContainer.getChildAt(i);
            TextView itemAmount = itemView.findViewById(R.id.textItemAmount);
            String amountString = itemAmount.getText().toString();
            grandTotal += Double.parseDouble(amountString);
        }

        textGrandTotal.setText("Grand Total: " + String.format("%.2f", grandTotal));
    }
}
