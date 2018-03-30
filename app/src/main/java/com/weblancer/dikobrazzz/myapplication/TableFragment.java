package com.weblancer.dikobrazzz.myapplication;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class TableFragment extends Fragment {
    public RecyclerView rv;
    private View view;
    private List<Table> tables = new ArrayList<>();
    private DatabaseAdapter adapter;
    protected DataAdapter mAdapter;
    private Button reset;

    public TableFragment() {
    }

    public static TableFragment newInstance() {
        TableFragment fragment = new TableFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new DatabaseAdapter(getContext());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_table, container, false);

        rv = (RecyclerView) view.findViewById(R.id.recylcerView);

        reset = (Button) view.findViewById(R.id.reset);

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter.open();
                adapter.clear();
                rv.setAdapter(new DataAdapter(getContext(), adapter.getUsers()));
                adapter.close();
            }
        });

        rv.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter.open();
        rv.setAdapter(new DataAdapter(getContext(), adapter.getUsers()));
        adapter.close();

        return view;
    }

    public void ref(){
        adapter.open();
        rv.setAdapter(new DataAdapter(getContext(), adapter.getUsers()));
        adapter.close();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser)
    {
        if(isVisibleToUser){
            this.ref();
        }
    }

}
