package br.senai.sp.cotia.hash.fragment;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Random;

import br.senai.sp.cotia.hash.R;
import br.senai.sp.cotia.hash.databinding.FragmentJogoBinding;

public class JogoFragment extends Fragment {

    // variaveis para acessar os elementos da view
    private FragmentJogoBinding binding;
    //vetor de botoes para referenciar os botoes
    private Button[] botoes;
    // matriz de String que representa os botões
    private String[][] tabuleiro;
    // variaveis para os simbolos
    private String simbJog1, simbJog2, simbolo;
    // variavel Randow para sortear quem inicia
    private Random random;

    private int numJogada = 0;

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

        // intanciar o tabuleiro
        tabuleiro = new String[3][3];

        // preenche a Matriz com ""

        for (String[] vetor: tabuleiro){
            Arrays.fill(vetor, "");
        }

        // define os simbolos do jogador 1 e do jogador 2
        simbJog1 = "X";
        simbJog2 = "O";

        // instacia o Random
        random = new Random();

        // sorteia quem inicia o jogo
        sorteia();

        // retorna a view root do biding
        return binding.getRoot();
    }

    public void sorteia(){
        // se gerar um valor verdadeiro, jogador 1 começa,
        // caso contrario jogador 2 começa
        if(random.nextBoolean()){
          simbolo = simbJog1;
        }else {
            simbolo = simbJog2;
        }
    }

    private void atualizaVez(){
        if (simbolo.equals(simbJog1)){
            binding.textJogador1.setBackgroundResource(R.color.white);
            binding.textPontJogador1.setBackgroundResource(R.color.white);
            binding.textJogador1.setTextColor(Color.BLACK);
            binding.textPontJogador1.setTextColor(Color.BLACK);
        }else{
            binding.textJogador2.setBackgroundResource(R.color.white);
            binding.textPontJogador2.setBackgroundResource(R.color.white);
            binding.textJogador2.setTextColor(Color.BLACK);
            binding.textPontJogador2.setTextColor(Color.BLACK);
        }
    }

    private boolean venceu(){
        // verifica se venceu nas linhas
        for (int li = 0; li < 3; li++){
            if (tabuleiro[li][0].equals(simbolo) && tabuleiro[li][1].equals(simbolo) && tabuleiro[li][2].equals(simbolo)){
                return true;
            }
        }

        for (int col = 0; col < 3; col++){
            if (tabuleiro[0][col].equals(simbolo) && tabuleiro[1][col].equals(simbolo) && tabuleiro[2][col].equals(simbolo)){
                return true;
            }
        }

        // verifica se venceu nas diagonais

        if (tabuleiro[0][0].equals(simbolo) && tabuleiro[1][1].equals(simbolo) && tabuleiro[2][2].equals(simbolo)){
            return true;
        }

        if (tabuleiro[0][2].equals(simbolo) && tabuleiro[1][1].equals(simbolo) && tabuleiro[2][0].equals(simbolo)){
            return true;
        }

        return false;
    }

    private void renewBoard(){


        // percorrer os botões do vetor, voltando backgroud inicial,
        // tornando-os clicáveis novamente e limpando os textos

        for (Button botao : botoes){
            botao.setClickable(true);
            botao.setText("");
            botao.setBackgroundColor(getResources().getColor(R.color.white));
        }

        for (String[] vetor: tabuleiro){
            Arrays.fill(vetor, "");
        }
        // zerar o número de jogadas
        numJogada = 0;
    }

    private View.OnClickListener listenerBotoes = btPress -> {
        String nameButton = getContext().getResources().getResourceName(btPress.getId());
        // incrementa o número de jogadas
        numJogada++;
        //extrai a posição através do nome do botão
        String position = nameButton.substring(nameButton.length()-2);
        // extrai linha e coluna da String posição
        int line = Character.getNumericValue(position.charAt(0));
        int column = Character.getNumericValue(position.charAt(1));
        //  preencher a posição da matriz com o simbolo "da vez"
        tabuleiro[line][column] = simbolo;
        // faz um casting de Viewpra Button
        Button botao = (Button) btPress;
        // "seta" o simbolo no botão pressionado
        botao.setText(simbolo);
        // trocar o backgroud do botão
        botao.setBackgroundColor(Color.BLACK);
        // desabilitar o botão que foi pressionado
        botao.setClickable(false);


        if (numJogada >= 5 && venceu()){
            botao.setClickable(false);
            Toast.makeText(getContext(), R.string.winner, Toast.LENGTH_LONG).show();
            renewBoard();
        }else if (numJogada == 9){
            //informa que deu velha
            Toast.makeText(getContext(), R.string.old, Toast.LENGTH_LONG).show();
            // reseta
            renewBoard();
        }else{
            //  inverte o simbolo
            simbolo = simbolo.equals(simbJog1) ? simbJog2 : simbJog1;

            // atualiza a vez
            atualizaVez();
        }



        Log.w("Button", line+"");
        Log.w("Button",column+"");
    };


}