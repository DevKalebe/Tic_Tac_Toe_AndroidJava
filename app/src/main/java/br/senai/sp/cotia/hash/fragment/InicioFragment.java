package br.senai.sp.cotia.hash.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.senai.sp.cotia.hash.R;
import br.senai.sp.cotia.hash.databinding.FragmentInicioBinding;


public class InicioFragment extends Fragment {

    private FragmentInicioBinding binding;

    public InicioFragment() {

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding.buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(InicioFragment.this)
                        .navigate(R.id.action_inicioFragment_to_jogoFragment);
            }
        });

        return inflater.inflate(R.layout.fragment_inicio, container, false);
    }
}