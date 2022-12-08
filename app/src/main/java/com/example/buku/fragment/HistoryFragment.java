package com.example.buku.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.buku.R;
import com.example.buku.adapter.HistoryItemAdapter;
import com.example.buku.dto.BookDTO;

import java.util.ArrayList;

public class HistoryFragment extends Fragment {

    RecyclerView rvHistoryTransaction;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_history, container, false);

        inisialisasi(v, getContext());
        loadData(container.getContext());

        return v;
    }

    private void loadData(Context context){
        ArrayList<BookDTO> data = new ArrayList<>();

        HistoryItemAdapter adapter = new HistoryItemAdapter(context, data);
        rvHistoryTransaction.setAdapter(adapter);
    }

    private void inisialisasi(View v, Context context){
        rvHistoryTransaction = v.findViewById(R.id.rv_history_transaction);
        rvHistoryTransaction.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL,true));
    }
}