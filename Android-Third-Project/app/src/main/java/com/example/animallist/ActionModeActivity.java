package com.example.animallist;

import android.content.Context;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ActionModeActivity extends AppCompatActivity {
    private final List<String> items = new ArrayList<>(
            Arrays.asList("One", "Two", "Three", "Four", "Five", "Six")
    );

    private ListView listView;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_action_mode);

        View root = findViewById(R.id.action_mode_screen);
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

        listView = findViewById(R.id.action_mode_list);
        adapter = new ActionModeAdapter(this, items);
        listView.setAdapter(adapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        listView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
            @Override
            public void onItemCheckedStateChanged(
                    ActionMode mode,
                    int position,
                    long id,
                    boolean checked
            ) {
                updateActionModeTitle(mode);
            }

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                mode.getMenuInflater().inflate(R.menu.action_mode_menu, menu);
                updateActionModeTitle(mode);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                updateActionModeTitle(mode);
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                if (item.getItemId() != R.id.action_delete) {
                    return false;
                }

                int removedCount = 0;
                for (int index = listView.getCheckedItemPositions().size() - 1; index >= 0; index--) {
                    int position = listView.getCheckedItemPositions().keyAt(index);
                    if (listView.getCheckedItemPositions().valueAt(index)
                            && position >= 0
                            && position < items.size()) {
                        items.remove(position);
                        removedCount++;
                    }
                }
                adapter.notifyDataSetChanged();
                listView.clearChoices();
                mode.finish();
                Toast.makeText(
                        ActionModeActivity.this,
                        getString(R.string.action_mode_deleted, removedCount),
                        Toast.LENGTH_SHORT
                ).show();
                return true;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {
                listView.clearChoices();
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void updateActionModeTitle(ActionMode mode) {
        mode.setTitle(getString(
                R.string.action_mode_selected_count,
                listView.getCheckedItemCount()
        ));
    }

    private static final class ActionModeAdapter extends ArrayAdapter<String> {
        private final LayoutInflater inflater;

        ActionModeAdapter(Context context, List<String> items) {
            super(context, 0, items);
            inflater = LayoutInflater.from(context);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.item_action_mode, parent, false);
                holder = new ViewHolder(
                        convertView.findViewById(R.id.action_mode_icon),
                        convertView.findViewById(R.id.action_mode_label)
                );
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.icon.setImageResource(android.R.drawable.sym_def_app_icon);
            holder.label.setText(getItem(position));
            return convertView;
        }
    }

    private static final class ViewHolder {
        private final ImageView icon;
        private final TextView label;

        ViewHolder(ImageView icon, TextView label) {
            this.icon = icon;
            this.label = label;
        }
    }
}
