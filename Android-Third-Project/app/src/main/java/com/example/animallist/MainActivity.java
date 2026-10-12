package com.example.animallist;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements AnimalAdapter.OnAnimalClickListener {
    private static final String STATE_SELECTED_ID = "selected_animal_id";

    private AnimalAdapter adapter;
    private TextView selectedAnimalText;
    private long selectedId = RecyclerView.NO_ID;
    private long nextAnimalId = 7L;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        View root = findViewById(R.id.main);
        ViewCompat.setOnApplyWindowInsetsListener(root, (view, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            view.setPadding(
                    systemBars.left,
                    systemBars.top,
                    systemBars.right,
                    systemBars.bottom
            );
            return insets;
        });

        selectedAnimalText = findViewById(R.id.selected_animal_text);
        RecyclerView animalList = findViewById(R.id.animal_list);
        animalList.setLayoutManager(new LinearLayoutManager(this));
        animalList.setHasFixedSize(true);

        List<Animal> animals = Arrays.asList(
                new Animal(1L, "Lion", R.drawable.animal_lion),
                new Animal(2L, "Tiger", R.drawable.animal_tiger),
                new Animal(3L, "Monkey", R.drawable.animal_monkey),
                new Animal(4L, "Dog", R.drawable.animal_dog),
                new Animal(5L, "Cat", R.drawable.animal_cat),
                new Animal(6L, "Elephant", R.drawable.animal_elephant)
        );

        adapter = new AnimalAdapter(animals, this);
        animalList.setAdapter(adapter);

        if (savedInstanceState != null) {
            selectedId = savedInstanceState.getLong(STATE_SELECTED_ID, RecyclerView.NO_ID);
            if (selectedId != RecyclerView.NO_ID) {
                adapter.setSelectedId(selectedId);
            }
        }
    }

    @Override
    public void onAnimalClick(Animal animal) {
        selectedId = animal.getId();
        adapter.setSelectedId(selectedId);
        selectedAnimalText.setText(getString(R.string.selected_animal, animal.getName()));
        Toast.makeText(this, animal.getName(), Toast.LENGTH_SHORT).show();

        Animal addedAnimal = new Animal(
                nextAnimalId++,
                animal.getName(),
                animal.getImageResId()
        );
        adapter.addAnimal(addedAnimal);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putLong(STATE_SELECTED_ID, selectedId);
        super.onSaveInstanceState(outState);
    }
}

