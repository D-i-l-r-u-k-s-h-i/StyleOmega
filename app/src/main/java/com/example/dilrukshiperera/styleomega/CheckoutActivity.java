package com.example.dilrukshiperera.styleomega;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dilrukshiperera.styleomega.Models.Orders;
import com.example.dilrukshiperera.styleomega.Models.Product;
import com.example.dilrukshiperera.styleomega.Models.ProductOrders;

import java.util.List;

public class CheckoutActivity extends AppCompatActivity {
    private CheckBox defAddressCB, anotherAddCB;
    private EditText anotherAddET;
    private TextView order_totalTV,itemTotalTV,delchargesTV;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        spinner = findViewById(R.id.spinner);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.payment_type, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        this.order_totalTV = findViewById(R.id.orderTotalTV);
        this.itemTotalTV = findViewById(R.id.ItemsTV);
        this.delchargesTV = findViewById(R.id.delevery_chargesTV);

        this.anotherAddET = findViewById(R.id.anotherAddressET);

        if(getIntent().hasExtra("ordertotal")){
            double itemTotal= getIntent().getDoubleExtra("ordertotal",0.0);
            double deleveryCharges = Double.parseDouble(delchargesTV.getText().toString());

            double order_total = itemTotal + deleveryCharges;
            itemTotalTV.setText(""+itemTotal);

            order_totalTV.setText("Rs." + order_total);
        }
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.defaultAddRB:
                if (checked)
                    anotherAddET.setVisibility(View.GONE);
                    //take the value as the delivery info ..blah blah
                    break;
            case R.id.anotherAddressRB:
                if (checked)
                    anotherAddET.setVisibility(View.VISIBLE);
                    break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.checkout_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.confirm:
                if(getIntent().hasExtra("orderID")){
                    long orderID=getIntent().getLongExtra("orderID",0);

                    List <ProductOrders> porder=ProductOrders.find(ProductOrders.class,
                            "orderrid=?", String.valueOf(orderID));

                    for (ProductOrders po:porder) {
                        Product product=Product.findById(Product.class,po.getProductid());
                        int porderQty=Integer.valueOf(po.getQuantity());
                        int prodQty=Integer.valueOf(product.getProd_quantity());

                        if(porderQty<=prodQty && prodQty!=0){
                            int quantity=prodQty-porderQty;
                            //update the product quantity if purchased.
                            product.setProd_quantity(quantity);
                            product.save();
                        }
                        else{
                            Toast.makeText(this,"only "+prodQty+
                                " of products is available at the moment. Sorry.",
                                    Toast.LENGTH_SHORT).show();
                        }
                        if(prodQty==0){
                            //delete product from db
                            product.delete();
                            po.delete();
                        }
                    }
                    //set the OrderStatus to PURCHASED
                    Orders order=Orders.findById(Orders.class,orderID);
                    order.setOrder_status("PURCHASED");
                    order.save();
                }

                startActivity(new Intent(CheckoutActivity.this,HomeActivity.class));
                Toast.makeText(CheckoutActivity.this,"Purchase confirmed.",
                        Toast.LENGTH_SHORT).show();
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
