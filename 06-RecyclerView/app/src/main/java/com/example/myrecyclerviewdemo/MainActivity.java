package com.example.myrecyclerviewdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);

        List<Item> items = new ArrayList<Item>();
        items.add(new Item("John wick", "2.0", "18 Ene", R.drawable.jw));
        items.add(new Item("Robert j", "3.5", "20 Feb", R.drawable.rj));
        items.add(new Item("Alice Smith", "4.2", "15 Mar", R.drawable.as));
        items.add(new Item("Michael Johnson", "1.8", "10 Abr", R.drawable.mj));
        items.add(new Item("Emily Davis", "5.0", "5 May", R.drawable.ed));
        items.add(new Item("Sophia Wilson", "3.7", "8 Jun", R.drawable.sw));
        items.add(new Item("James Taylor", "6.1", "12 Jul", R.drawable.jt));
        items.add(new Item("Olivia Martinez", "2.3", "20 Ago", R.drawable.om));
        items.add(new Item("Benjamin Anderson", "4.8", "25 Sep", R.drawable.ba));
        items.add(new Item("Emma Thomas", "1.9", "3 Oct", R.drawable.ed));
        items.add(new Item("William Garcia", "3.3", "15 Nov", R.drawable.wg));
        items.add(new Item("Ava Hernandez", "2.5", "22 Dic", R.drawable.ah));
        items.add(new Item("Liam Lopez", "4.0", "2 Ene", R.drawable.ll));
        items.add(new Item("Mia Gonzalez", "5.5", "14 Feb", R.drawable.mj));
        items.add(new Item("Noah Perez", "2.5", "28 Mar", R.drawable.np));
        items.add(new Item("John wick", "2.0", "18 Ene", R.drawable.jw));
        items.add(new Item("Robert j", "3.5", "20 Feb", R.drawable.rj));
        items.add(new Item("Alice Smith", "4.2", "15 Mar", R.drawable.as));
        items.add(new Item("Michael Johnson", "1.8", "10 Abr", R.drawable.mj));
        items.add(new Item("Emily Davis", "5.0", "5 May", R.drawable.ed));
        items.add(new Item("Sophia Wilson", "3.7", "8 Jun", R.drawable.sw));
        items.add(new Item("James Taylor", "6.1", "12 Jul", R.drawable.jt));
        items.add(new Item("Olivia Martinez", "2.3", "20 Ago", R.drawable.om));

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new MyAdapter(getApplicationContext(),items));

    }
}