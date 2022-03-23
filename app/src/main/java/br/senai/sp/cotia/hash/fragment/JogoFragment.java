package br.senai.sp.cotia.hash.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import br.senai.sp.cotia.hash.R;
import br.senai.sp.cotia.hash.databinding.FragmentJogoBinding;

public class JogoFragment extends Fragment {

    // variaveis para acessar os elementos da view
    private FragmentJogoBinding binding;

    //vetor de botoes para referenciar os botoes
    private Button[] botoes;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // instaciar o biding
        binding = FragmentJogoBinding.inflate(inflater, container, false);

        //instanciar vetor
        botoes = new Button[9];

        // associar o vetor dos botões
        botoes[0] = binding.bt00;
        botoes[1] = binding.bt01;
        botoes[2] = binding.bt02;
        botoes[3] = binding.bt10;
        botoes[4] = binding.bt11;
        botoes[5] = binding.bt12;
        botoes[6] = binding.bt20;
        botoes[7] = binding.bt21;
        botoes[8] = binding.bt22;

        // associa o listener aos botões
        for (Button bt:botoes){
            bt.setOnClickListener(listenerBotoes);
        }

        // retorna a view root do biding
        return binding.getRoot();
    }

    private View.OnClickListener listenerBotoes = btPress -> {
        String nameButton = getContext().getResources().getResourceName(btPress.getId());
        //extrai a posição através do nome do botão
        String position = nameButton.substring(nameButton.length()-2);
        // extrai linha e coluna da String posição
        int line = Character.getNumericValue(position.charAt(0));
        int column = Character.getNumericValue(position.charAt(1));

        Log.w("Button", line+"");
        Log.w("Button",column+"");
    };

}