package com.example.appxemphim.UI.Fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.appxemphim.Model.GenreModel;
import com.example.appxemphim.R;
import com.example.appxemphim.Repository.GenreRepository;
import com.example.appxemphim.UI.Utils.Resource;
import com.example.appxemphim.ViewModel.GenreViewModel;
import com.example.appxemphim.ViewModel.GenreViewModelFactory;

import java.util.ArrayList;
import java.util.List;

public class CategoryDialogFragment extends DialogFragment {

    private List<String> genres = new ArrayList<>();
    private OnGenreSelectedListener listener;
    private GenreViewModel viewModel;
    private ArrayAdapter<String> adapter;

    public interface OnGenreSelectedListener {
        void onGenreSelected(String genre);
    }

    public CategoryDialogFragment(OnGenreSelectedListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());

        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.dialog_listview_category, null);

        ListView listView = view.findViewById(R.id.listview_category);
        ImageButton btnClose = view.findViewById(R.id.cancel_listview_category);

        adapter = new ArrayAdapter<String>(requireContext(), android.R.layout.simple_list_item_1, genres) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView textView = view.findViewById(android.R.id.text1);
                textView.setTextColor(ContextCompat.getColor(requireContext(), R.color.onSurface));
                return view;
            }
        };
        listView.setBackgroundColor(Color.TRANSPARENT);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, itemView, position, id) -> {
            String selectedGenre = genres.get(position);
            if (listener != null) {
                listener.onGenreSelected(selectedGenre);
            }
            dismiss();
        });

        btnClose.setOnClickListener(v -> dismiss());

        builder.setView(view);

        setupViewModel();

        return builder.create();
    }

    private void setupViewModel() {
        GenreRepository repository = new GenreRepository();
        GenreViewModelFactory factory = new GenreViewModelFactory(repository);
        viewModel = new ViewModelProvider(this, factory).get(GenreViewModel.class);

        viewModel.genre.observe(this, new Observer<Resource<List<GenreModel>>>() {
            @Override
            public void onChanged(Resource<List<GenreModel>> resource) {
                if (resource == null) return;

                switch (resource.getStatus()) {
                    case SUCCESS:
                        if (resource.getData() != null) {
                            List<String> genreNames = new ArrayList<>();
                            for (GenreModel genre : resource.getData()) {
                                genreNames.add(genre.getName());
                            }
                            genres.clear();
                            genres.addAll(genreNames);
                            adapter.notifyDataSetChanged();
                        }
                        break;

                    case ERROR:
                        // TODO: Bạn có thể show Toast hoặc Log lỗi
                        break;

                    case LOADING:
                        // TODO: Có thể hiển thị ProgressBar nếu cần
                        break;
                }
            }
        });


        viewModel.loadGenre(); // gọi API lấy dữ liệu genre
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
