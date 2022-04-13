package br.senai.sp.cotia.hash.fragment;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Random;

import br.senai.sp.cotia.hash.R;
import br.senai.sp.cotia.hash.databinding.FragmentJogoBinding;
import br.senai.sp.cotia.hash.util.PrefsUtil;

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
    // variaveis para o placar
    private int placarJog1 = 0, placarJog2 = 0;
    // variaveis para contar a quantidade de velhas
    private int placarQtdVelha = 0;
    // variavel para definir a quantidade de rodadas
    private String njogada;
    private int qtdJogadas= 0;

    // Alert Dialog
    private AlertDialog alert;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // habilitar o menu
        setHasOptionsMenu(true);

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
        simbJog1 = PrefsUtil.getSimboloJog1(getContext());
        simbJog2 = PrefsUtil.getSimboloJog2(getContext());

        // atualizar o placar com os simbolos
        binding.textJogador1.setText(getResources().getString(R.string.Player_1_Header,simbJog1));
        binding.textJogador2.setText(getResources().getString(R.string.Player_2_Header,simbJog2));

        // atualiza o número de jogadas
        njogada = PrefsUtil.getRodada(getContext());
        qtdJogadas = Integer.parseInt(njogada);

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

    private void atualizaPlacar(){
        binding.textPontJogador1.setText(placarJog1+"");
        binding.textPontJogador2.setText(placarJog2+"");
        binding.tvPlacarVelha.setText(placarQtdVelha+"");
    }

    private void atualizaVez(){
        if (simbolo.equals(simbJog1)){
            binding.textJogador1.setTextColor(Color.YELLOW);
            binding.textPontJogador1.setTextColor(Color.YELLOW);
            binding.textJogador2.setTextColor(Color.WHITE);
            binding.textPontJogador2.setTextColor(Color.WHITE);
        }else{
            binding.textJogador2.setTextColor(Color.YELLOW);
            binding.textPontJogador2.setTextColor(Color.YELLOW);
            binding.textJogador1.setTextColor(Color.WHITE);
            binding.textPontJogador1.setTextColor(Color.WHITE);
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

        // sorteia o próximo
        sorteia();
        // atualiza o jogador
        atualizaVez();
    }

    // metodo que reseta game
    private void resetaGame(){
        placarJog1 = 0;
        placarJog2 = 0;
        placarQtdVelha = 0;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // ALERTA
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Resetar");
        builder.setMessage("Deseja mesmo resetar o jogo?");
        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                placarJog1 = 0;
                placarJog2 = 0;
                placarQtdVelha=0;
                atualizaPlacar();
                renewBoard();
                Toast.makeText(getContext(), "O jogo foi resetado", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getContext(), "O jogo não foi resetado", Toast.LENGTH_SHORT).show();
            }
        });
        // verificar qual item do menu foi selecionado
        switch (item.getItemId()){
            // caso seja a opção de resetar
            case R.id.menu_resetar:
                alert = builder.create();
                alert.show();
                break;
                // caso seja a opção de preferências
            case R.id.menu_prefs:



                NavHostFragment.findNavController(JogoFragment.this).navigate(R.id.action_jogoFragment_to_prefFragment);
        }

        return true;
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

            if (simbolo.equals(simbJog1)){
                placarJog1++;
            }else{
                placarJog2++;
            }
            // atualizar o placar
            atualizaPlacar();
            // reseta
            renewBoard();
        }else if (numJogada == 9){
            // informa que deu velha
            placarQtdVelha++;
            // atualizar o placar
            atualizaPlacar();
            Toast.makeText(getContext(), R.string.old, Toast.LENGTH_LONG).show();
            // reseta
            renewBoard();
        }else{
            // inverte o simbolo
            simbolo = simbolo.equals(simbJog1) ? simbJog2 : simbJog1;
            // atualiza a vez
            atualizaVez();
        }

        // verifica se ganha por placar
        int totalJogadas = (qtdJogadas / 2) + 1;
        if (totalJogadas == placarJog1){
            Toast.makeText(getContext(), R.string.winnerPlay1, Toast.LENGTH_LONG).show();
            placarJog1 = 0;
            placarJog2 = 0;
            placarQtdVelha=0;
            atualizaPlacar();
            renewBoard();
        }else if (totalJogadas == placarJog2){
            Toast.makeText(getContext(), R.string.winnerPlay2, Toast.LENGTH_LONG).show();
            placarJog1 = 0;
            placarJog2 = 0;
            placarQtdVelha=0;
            atualizaPlacar();
            renewBoard();
        }

        Log.w("Button", line+"");
        Log.w("Button",column+"");
    };

    @Override
    public void onStart() {
        super.onStart();
        super.onStart();
        // pega a referência para a activity
        AppCompatActivity minhaActivity = (AppCompatActivity) getActivity();
        // oculta a action bar
        minhaActivity.getSupportActionBar().show();
        // desabilita a seta de retornar
        minhaActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }
}