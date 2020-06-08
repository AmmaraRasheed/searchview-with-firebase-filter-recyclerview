package com.example.searchview;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ExampleAdapter adapter;
    private List<ExampleItem> exampleList=new ArrayList<>();

    DatabaseReference fb=FirebaseDatabase.getInstance().getReference().child("Data");




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fillExampleList();
//        setUpRecyclerView();
    }
    private void fillExampleList() {

        fb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds:dataSnapshot.getChildren()){
                    String name=ds.child("name").getValue().toString();
                    ExampleItem item=new ExampleItem(R.drawable.kid,name);
                    exampleList.add(item);
                }
                RecyclerView recyclerView = findViewById(R.id.recyclerView);
                recyclerView.setHasFixedSize(true);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
                adapter = new ExampleAdapter(exampleList);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


//        exampleList.add(new ExampleItem(R.drawable.kddd, "Ali"));
//        exampleList.add(new ExampleItem(R.drawable.kid, "Uzma"));
//        exampleList.add(new ExampleItem(R.drawable.kiddd,  "Aslam"));
//        exampleList.add(new ExampleItem(R.drawable.kddd, "Sara"));
//        exampleList.add(new ExampleItem(R.drawable.kid,  "Ammara"));
//        exampleList.add(new ExampleItem(R.drawable.kidd,  "EasyCoding"));
//        exampleList.add(new ExampleItem(R.drawable.kiddd,  "Rasheed"));
//        exampleList.add(new ExampleItem(R.drawable.kddd,  "Areeb"));
//        exampleList.add(new ExampleItem(R.drawable.kiddd,  "Khan"));
    }
//    private void setUpRecyclerView() {
//        RecyclerView recyclerView = findViewById(R.id.recyclerView);
//        recyclerView.setHasFixedSize(true);
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
//        adapter = new ExampleAdapter(exampleList);
//        recyclerView.setLayoutManager(layoutManager);
//        recyclerView.setAdapter(adapter);
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.main_menu,menu);
        MenuItem menuItem=menu.findItem(R.id.search_questions);
        SearchView searchView=(SearchView)menuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapter.getFilter().filter(s);
                return false;
            }
        });
        return true;
    }
}
