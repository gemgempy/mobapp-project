package com.example.iclean.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.iclean.Handler.SQLite;
import com.example.iclean.R;
import com.example.iclean.activity.ChangeProfileActivity;
import com.example.iclean.activity.HomeActivity;

import java.util.HashMap;

public class ProfileFragment extends Fragment {

    // inisiasi awal awal
    TextView tvName, tvEmail,tvPhone;
    Button btnChangeProfile, btnSignOut, mapp, button123;
    private Object ProfileFragment;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile, container, false);

        inisialisasi(v);
        mainButton();

        return v;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private void inisialisasi(View v){
        tvName = v.findViewById(R.id.tv_name);
        tvEmail = v.findViewById(R.id.tv_email);
        tvPhone = v.findViewById(R.id.tv_phone);

        btnChangeProfile = v.findViewById(R.id.btn_change_profile);
        btnSignOut = v.findViewById(R.id.btn_sign_out);

        mapp = v.findViewById(R.id.buat_map);
        button123 = v.findViewById(R.id.button123);

        // Mengambil intent untuk diisi data profil dari session.
        HomeActivity activity = (HomeActivity) getActivity();

        String noHP_USER = activity.getUSERDATA_NOHAPE();
        String id_data_User = activity.getUSERDATA_ID(); // ga dipake

        SQLite dbEntry = new SQLite(getActivity());
        HashMap<String, String> user = dbEntry.getUserDetails();
        // Mendapatkan data user dari SQLite
        String name = user.get("nama");
        String email = user.get("email");

        tvName.setText(name);
        tvEmail.setText(email);
        tvPhone.setText(noHP_USER);

    }

    private void mainButton(){
        btnChangeProfile.setOnClickListener(new View.OnClickListener() { // btn buat ke change profil activity
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), ChangeProfileActivity.class);
                startActivity(i);
            }
        });

        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SQLite dbEntry = new SQLite(getActivity());
                HomeActivity activity = (HomeActivity) getActivity();

                // Melakukan penghapusan data di SQLite jika pengguna melakukan logout
                // sehingga tidak terdapat penyimpanan pada local database
                dbEntry.deleteUsers();


                activity.pencet();

            }
        });

        mapp.setOnClickListener(new View.OnClickListener() { // buat nampilin map lokasi laundry
            @Override
            public void onClick(View v) {

                HomeActivity activity = (HomeActivity) getActivity();

                activity.tombol_map();
            }
        });

        button123.setOnClickListener(new View.OnClickListener() { // Untuk membuka websites iclean Laundry
            @Override
            public void onClick(View v) {

                HomeActivity activity = (HomeActivity) getActivity();
                activity.tombol_webview();

            }
        });
    }
}