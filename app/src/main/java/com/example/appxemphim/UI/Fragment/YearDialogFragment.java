package com.example.appxemphim.UI.Fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import com.example.appxemphim.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class YearDialogFragment extends DialogFragment {

    private List<String> genres;
    private OnGenreSelectedListener listener;

    public interface OnGenreSelectedListener {
        void onGenreSelected(String genre);
    }

    public YearDialogFragment(OnGenreSelectedListener listener) {
        this.listener = listener;
        this.genres = new ArrayList<>();
        for (int year = 2000; year <= 2025; year++) {
            this.genres.add(String.valueOf(year));
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());

        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.dialog_listview_category, null);

        ListView listView = view.findViewById(R.id.listview_category);
        ImageButton btnClose = view.findViewById(R.id.cancel_listview_category);

        // Adapter cho ListView
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_list_item_1,
                genres
        ){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView textView = (TextView) view.findViewById(android.R.id.text1);
                textView.setTextColor(ContextCompat.getColor(requireContext(), R.color.onSurface));

                return view;
            }
        };
        listView.setBackgroundColor(Color.TRANSPARENT);
        listView.setAdapter(adapter);

        // Xử lý click item trong ListView
        listView.setOnItemClickListener((parent, itemView, position, id) -> {
            String selectedGenre = genres.get(position);
            if (listener != null) {
                listener.onGenreSelected(selectedGenre);

            }
            dismiss();
        });

        btnClose.setOnClickListener(v -> dismiss());

        builder.setView(view);
        return builder.create();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setLayout(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
            );
            getDialog().getWindow().setBackgroundDrawableResource(android.R.color.white);
        }
    }
}

