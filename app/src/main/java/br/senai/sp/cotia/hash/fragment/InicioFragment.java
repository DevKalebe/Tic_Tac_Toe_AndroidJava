package br.senai.sp.cotia.hash.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import br.senai.sp.cotia.hash.R;
import br.senai.sp.cotia.hash.databinding.FragmentInicioBinding;


public class InicioFragment extends Fragment {

    private FragmentInicioBinding binding;
    private Button buttonStart;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentInicioBinding.inflate(inflater, container, false);

        binding.buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(InicioFragment.this)
                        .navigate(R.id.action_inicioFragment_to_jogoFragment);
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        // pega a referência para a activity
        AppCompatActivity minhaActivity = (AppCompatActivity) getActivity();
        // oculta a action bar
        minhaActivity.getSupportActionBar().hide();

    }
}