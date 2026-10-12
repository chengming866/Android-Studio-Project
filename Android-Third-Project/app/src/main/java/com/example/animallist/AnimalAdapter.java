package com.example.animallist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class AnimalAdapter extends RecyclerView.Adapter<AnimalAdapter.AnimalViewHolder> {
    public interface OnAnimalClickListener {
        void onAnimalClick(Animal animal);
    }

    private final List<Animal> animals = new ArrayList<>();
    private final OnAnimalClickListener clickListener;
    private long selectedId = RecyclerView.NO_ID;

    public AnimalAdapter(List<Animal> initialAnimals, OnAnimalClickListener clickListener) {
        animals.addAll(initialAnimals);
        this.clickListener = clickListener;
        setHasStableIds(true);
    }

    @NonNull
    @Override
    public AnimalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_animal, parent, false);
        return new AnimalViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AnimalViewHolder holder, int position) {
        Animal animal = animals.get(position);
        holder.nameText.setText(animal.getName());
        holder.animalImage.setImageResource(animal.getImageResId());
        holder.animalImage.setContentDescription(animal.getName());
        holder.itemView.setActivated(animal.getId() == selectedId);
    }

    @Override
    public long getItemId(int position) {
        return animals.get(position).getId();
    }

    @Override
    public int getItemCount() {
        return animals.size();
    }

    public void setSelectedId(long newSelectedId) {
        long previousSelectedId = selectedId;
        selectedId = newSelectedId;
        notifyItemChangedById(previousSelectedId);
        notifyItemChangedById(selectedId);
    }

    public void addAnimal(Animal animal) {
        animals.add(animal);
        notifyItemInserted(animals.size() - 1);
    }

    private void notifyItemChangedById(long id) {
        if (id == RecyclerView.NO_ID) {
            return;
        }
        for (int i = 0; i < animals.size(); i++) {
            if (animals.get(i).getId() == id) {
                notifyItemChanged(i);
                return;
            }
        }
    }

    class AnimalViewHolder extends RecyclerView.ViewHolder {
        private final TextView nameText;
        private final ImageView animalImage;

        AnimalViewHolder(@NonNull View itemView) {
            super(itemView);
            nameText = itemView.findViewById(R.id.animal_name);
            animalImage = itemView.findViewById(R.id.animal_image);

            itemView.setOnClickListener(view -> {
                int position = getBindingAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    clickListener.onAnimalClick(animals.get(position));
                }
            });
        }
    }
}

