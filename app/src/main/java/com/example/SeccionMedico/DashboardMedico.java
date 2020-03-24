package com.example.SeccionMedico;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.FragmentsMedico.MedicoBuscarFragment;
import com.example.FragmentsMedico.MedicoHomeFragment;
import com.example.FragmentsMedico.MedicoPerfilFragment;
import com.example.proyectofinal.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class DashboardMedico extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_medico);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navbar2);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragm,
                new MedicoHomeFragment()).commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch (item.getItemId()){

                        case R.id.nav_home:
                            selectedFragment = new MedicoHomeFragment();
                            break;
                        case R.id.nav_buscar:
                            selectedFragment = new MedicoBuscarFragment();
                            break;
                        case R.id.nav_perfil:
                            selectedFragment = new MedicoPerfilFragment();
                            break;
                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragm,
                            selectedFragment).commit();

                    return true;
                }
            };
}
