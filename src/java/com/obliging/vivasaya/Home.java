package com.obliging.vivasaya;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Home extends Fragment {

    FirebaseAuth firebaseAuth;
    RecyclerView recyclerView;
    androidx.appcompat.widget.SearchView searchView;
    ArrayList <ProductsCard> products;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

    ProductAdapter productAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = view.findViewById(R.id.home_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        firebaseAuth = FirebaseAuth.getInstance();
        searchView = view.findViewById(R.id.search_bar);

        DatabaseReference databaseReference  = firebaseDatabase.getReference("all/");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                products = new ArrayList<>();
                for (DataSnapshot dataSnapshot2:dataSnapshot.getChildren()){
                    ProductsCard p = new ProductsCard();
                    p.setProduct(dataSnapshot2.getValue().toString());
                    products.add(p);
                }
                productAdapter = new ProductAdapter(getContext(),products);
                recyclerView.setAdapter(productAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), "OOPS...", Toast.LENGTH_SHORT).show();
            }
        });

       searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
           @Override
           public boolean onQueryTextSubmit(String query) {
               Toast.makeText(getContext(), "Searching for"+query, Toast.LENGTH_SHORT).show();
               DatabaseReference databaseReference  = firebaseDatabase.getReference("state/").child(query);
               databaseReference.addValueEventListener(new ValueEventListener() {
                   @Override
                   public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                       products = new ArrayList<>();
                       for (DataSnapshot dataSnapshot2:dataSnapshot.getChildren()){
                           ProductsCard p = new ProductsCard();
                           p.setProduct(dataSnapshot2.getValue().toString());
                           products.add(p);
                       }
                       productAdapter = new ProductAdapter(getContext(),products);
                       recyclerView.setAdapter(productAdapter);
                   }

                   @Override
                   public void onCancelled(@NonNull DatabaseError databaseError) {
                       Toast.makeText(getActivity(), "OOPS...", Toast.LENGTH_SHORT).show();
                   }
               });
               return false;
           }

           @Override
           public boolean onQueryTextChange(String newText) {
               return false;
           }
       });


        return view;
    }
}
