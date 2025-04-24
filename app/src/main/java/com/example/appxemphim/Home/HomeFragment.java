package com.example.appxemphim.Home;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appxemphim.MainActivity;
import com.example.appxemphim.R;
import com.example.appxemphim.UI.Activity.SearchActivity;

import java.util.Arrays;
import java.util.List;

public class HomeFragment extends Fragment{
    RecyclerView recyclerPopular, recyclerOnly, recyclerRetro;
    LinearLayoutManager layoutManagerPopular, layoutManagerOnly, layoutManagerRetro;
    List<Integer> popular, only, retro;
    AdapterPopular adapterPopular, adapterRetro, adapterOnly;
    ImageButton btn_search;
    Button btn_category;
    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerPopular = view.findViewById(R.id.recyclerView_popular);
        layoutManagerPopular = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerPopular.setLayoutManager(layoutManagerPopular);
        recyclerPopular.setNestedScrollingEnabled(false);
        popular =  Arrays.asList(R.drawable.placeholder_poster, R.drawable.placeholder_poster, R.drawable.placeholder_poster, R.drawable.placeholder_poster, R.drawable.placeholder_poster, R.drawable.placeholder_poster, R.drawable.placeholder_poster, R.drawable.placeholder_poster, R.drawable.placeholder_poster, R.drawable.placeholder_poster, R.drawable.placeholder_poster, R.drawable.placeholder_poster, R.drawable.placeholder_poster, R.drawable.placeholder_poster);
        adapterPopular = new AdapterPopular(popular);
        recyclerPopular.setAdapter(adapterPopular);

        recyclerRetro = view.findViewById(R.id.recyclerView_retrotv);
        layoutManagerRetro = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerRetro.setLayoutManager(layoutManagerRetro);
        recyclerRetro.setNestedScrollingEnabled(false);
        retro = Arrays.asList(R.drawable.placeholder_poster, R.drawable.placeholder_poster, R.drawable.placeholder_poster, R.drawable.placeholder_poster, R.drawable.placeholder_poster, R.drawable.placeholder_poster, R.drawable.placeholder_poster, R.drawable.placeholder_poster, R.drawable.placeholder_poster, R.drawable.placeholder_poster, R.drawable.placeholder_poster, R.drawable.placeholder_poster, R.drawable.placeholder_poster, R.drawable.placeholder_poster);
        adapterRetro = new AdapterPopular(retro);
        recyclerRetro.setAdapter(adapterRetro);

        recyclerOnly = view.findViewById(R.id.recyclerView_only_app);
        layoutManagerOnly = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerOnly.setLayoutManager(layoutManagerOnly);
        recyclerOnly.setNestedScrollingEnabled(false);
        only  = Arrays.asList(R.drawable.placeholder_poster, R.drawable.placeholder_poster, R.drawable.placeholder_poster, R.drawable.placeholder_poster, R.drawable.placeholder_poster, R.drawable.placeholder_poster, R.drawable.placeholder_poster, R.drawable.placeholder_poster, R.drawable.placeholder_poster, R.drawable.placeholder_poster, R.drawable.placeholder_poster, R.drawable.placeholder_poster, R.drawable.placeholder_poster, R.drawable.placeholder_poster);
        adapterOnly = new AdapterPopular(only);
        recyclerOnly.setAdapter(adapterOnly);

        btn_search = view.findViewById(R.id.btn_cancelled);
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                startActivity(intent);
            }
        });

        btn_category = view.findViewById(R.id.btn_categories);
        btn_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] genres = {"Hành động", "Tình cảm", "Hài hước", "Khoa học", "Hoạt hình"};
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Chọn theer loa phim")
                        .setItems(genres, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Xử lý khi người dùng chọn thể loại phim
                        String selectedGenre = genres[which];
                        Toast.makeText(getActivity(), "Bạn chọn: " + selectedGenre, Toast.LENGTH_SHORT).show();
                    }
                })
               .setNegativeButton("Hủy", null);

                // Hiển thị Dialog
                builder.create().show();
            }
        });

        return view;
    }
}
