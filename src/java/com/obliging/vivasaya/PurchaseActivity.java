package com.obliging.vivasaya;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PurchaseActivity extends AppCompatActivity {

    TextView textView,priceView;
    Toolbar toolbar;
    SeekBar seekBar;
    TextView progress;
    Button buy;
    FirebaseAuth firebaseAuth =FirebaseAuth.getInstance();
    FirebaseUser firebaseUser;
    FirebaseDatabase firebaseDatabase;
    int rs=23;
    DatabaseReference databaseReference,userref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.activtiy_purchase);
        seekBar = findViewById(R.id.seekBar_cmp);
        progress = findViewById(R.id.progress_numb);
        textView = findViewById(R.id.product_buy);
        priceView = findViewById(R.id.Rupees);
        buy = findViewById(R.id.buy_now);
        textView.setText(getIntent().getStringExtra("product"));
        toolbar = findViewById(R.id.purchase_bar);
        toolbar.setTitle(getIntent().getStringExtra("product"));
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                progress.setText("Kg's:"+i);
                priceView.setText("\u20B9-"+(rs*i));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseUser = firebaseAuth.getCurrentUser();
                databaseReference = FirebaseDatabase.getInstance().getReference().child("users");
                userref = FirebaseDatabase.getInstance().getReference().child("users").child(firebaseUser.getUid());
                DatabaseReference newPro = userref.push();
                //push data area
                newPro.child("product").setValue(textView.getText());
                newPro.child("quantity").setValue(progress.getText().toString());
                newPro.child("price").setValue(priceView.getText().toString());
                databaseReference.child(firebaseUser.getUid()).child("Email").setValue(firebaseUser.getEmail())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(PurchaseActivity.this, "Your Order for "+textView.getText()+"is PLaced Successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(PurchaseActivity.this,MainActivity.class));
                    }
                });
            }
        });
    }

}
