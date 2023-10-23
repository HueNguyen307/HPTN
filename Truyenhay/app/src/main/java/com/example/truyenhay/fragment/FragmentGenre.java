package com.example.truyenhay.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.truyenhay.ListBookActivity;
import com.example.truyenhay.adapter.RecycleViewAdapterGenreList;
import com.example.truyenhay.model.Genre;
import com.example.truyenhay.R;

import java.util.ArrayList;
import java.util.List;

public class FragmentGenre extends Fragment implements RecycleViewAdapterGenreList.ItemListener{
    private RecyclerView recyclerView;
    RecycleViewAdapterGenreList adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_genre,container,false);
    }
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView=view.findViewById(R.id.recycleView);
        adapter=new RecycleViewAdapterGenreList();
        List<Genre> list=new ArrayList<>();
        Genre b=new Genre("Trinh tham");
        list.add(b);
        adapter.setList(list);
        LinearLayoutManager manager=new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        adapter.setItemListener(this);

        Intent intent=new Intent(getActivity(), ListBookActivity.class);


    }
    public void onItemClick(View view, int position) {
        //adapter de click vao dc
        Genre genre=adapter.getItem(position);
        Intent intent=new Intent(getActivity(), ListBookActivity.class);
//        intent.putExtra("book",book);
        startActivity(intent);
    }
}
