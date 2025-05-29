package com.example.recycleview.activities;

import android.os.Bundle;
import androidx.appcompat.widget.SearchView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recycleview.R;
import com.example.recycleview.classes.CustomeAdapter;
import com.example.recycleview.classes.MyData;
import com.example.recycleview.Models.DataModel;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recycler;
    private CustomeAdapter adapter;
    private ArrayList<DataModel> characterList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        applyWindowInsets();

        recycler = findViewById(R.id.recycleView);
        characterList = loadCharacters();
        adapter = new CustomeAdapter(characterList);

        setupRecyclerView();
        setupSearch();
    }

    private void applyWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets bars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(bars.left, bars.top, bars.right, bars.bottom);
            return insets;
        });
    }

    private ArrayList<DataModel> loadCharacters() {
        ArrayList<DataModel> list = new ArrayList<>();
        for (int i = 0; i < MyData.nameArray.length; i++) {
            list.add(new DataModel(
                    MyData.nameArray[i],
                    MyData.descriptionArray[i],
                    MyData.drawableArray[i],
                    MyData.id_[i]
            ));
        }
        return list;
    }

    private void setupRecyclerView() {
        recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.setItemAnimator(new DefaultItemAnimator());
        recycler.setAdapter(adapter);
    }

    private void setupSearch() {
        SearchView search = findViewById(R.id.searchView);
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.filter(newText);
                return false;
            }
        });
    }
}
