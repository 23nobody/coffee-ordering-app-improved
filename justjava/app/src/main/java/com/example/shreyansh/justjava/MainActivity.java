package com.example.shreyansh.justjava;

/* IMPORTANT: Make sure you are using the correct package name.
        * This example uses the package name:
        * package com.example.android.justjava
        * If you get an error when copying this code into Android studio, update it to match teh package name found
        * in the project's AndroidManifest.xml file.
        **/


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;
/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    int quantity = 0;
    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        CheckBox whippedCream = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean WC= whippedCream.isChecked();
        CheckBox chocolate = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean choc = chocolate.isChecked();
        EditText name = (EditText) findViewById(R.id.name_input);
        String person = name.getText().toString();
        String priceMessage= creatOrderSummary(quantity,WC,choc, person);
        composeEmail(priceMessage);
               displayMessage(priceMessage);

    }
    public void composeEmail(String  priceMessage   ){
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));

        intent.putExtra(Intent.EXTRA_TEXT,priceMessage);
        intent.putExtra(Intent.EXTRA_SUBJECT, "Order this for me:\n");
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    public void increment(View view) {
        if(quantity>=100) {
            Toast toast = Toast.makeText(getApplicationContext(),"YOu cannot order more than 100", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }
         quantity++;
        display(quantity);

    }
    public void decrement(View view) {

        if(quantity<=0) {
            Toast toast = Toast.makeText(getApplicationContext(),"YOu cannot order less than 0", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }
         quantity--;
        display(quantity);

    }
    public int calprice(int quantity, int base)
    {
        int price = quantity*base;

        return price;
    }


    private String creatOrderSummary(int quantity, boolean WC, boolean choc,String person){
        int base =5;
        if(WC)
            base= base+1;
        if(choc)
            base+=2;
        int price = calprice(quantity, base);

        return "Name: " + person + "\nQuantity: " + quantity + "\nTotal: $" + price + "\nWhipped Cream: "+ WC+ "\nChocolate: "+ choc + "\nThank You!";
    }
    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }
    /**
     * This method displays the given price on the screen.
     */
    private void displayPrice(int number) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(NumberFormat.getCurrencyInstance().format(number));
    }
    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }
}
