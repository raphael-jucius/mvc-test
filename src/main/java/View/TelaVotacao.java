package View;

import java.awt.Button;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import java.awt.Frame;

public class TelaVotacao extends Frame implements ActionListener {

    private TelaResultado telaResult;
    private TelaResultadoPercentual telaResultPerc;

    private Map <String,Integer>opcoes;
    private ArrayList <String>listaOpcoes; // para obter as opções em ordem

    public TelaVotacao() {
        super("Tela de Votação - Enquete");

        telaResult = new TelaResultado(this);
        telaResult.setLocation(120, 5); // posicao na tela

        telaResultPerc = new TelaResultadoPercentual(this);
        telaResultPerc.setLocation(250, 5); // posicao na tela

        listaOpcoes = new ArrayList<String>();
        this.opcoes = new HashMap<String, Integer>();

        this.adicionaOpcao("Opção 1");
        this.adicionaOpcao("Opção 2");
        this.adicionaOpcao("Opção 3");
        this.adicionaOpcao("Opção 4");
        criarBotoes();

        telaResult.inicializar(listaOpcoes);
        telaResultPerc.inicializar(listaOpcoes);

        this.setSize(100, 120);
        this.setLayout(new GridLayout(0, 1));
        
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                e.getWindow().hide();
                System.exit(0);
            }
        });
        this.show();
        telaResult.show();
        telaResultPerc.show();
    }

    /**
     * Adiciona uma opcao à enquete
     **/
    private void adicionaOpcao(String opcao) {
        listaOpcoes.add(opcao);
        this.opcoes.put(opcao, new Integer(0));
    }

    /**
     * Cria os botoes da tela de votos
     **/
    public void criarBotoes() {
        Button botao;
        for (String opcao : listaOpcoes) {
            botao = new Button(opcao);
            botao.setActionCommand(opcao);
            botao.addActionListener(this);
            this.add(botao);
        }
    }

    /**
     * Método executado ao clicar nos botões
     */
    public void actionPerformed(ActionEvent event) {
        String opcao = event.getActionCommand();
        this.votar(opcao); // incrementando o voto

        // Atualizando a tela de resultados absolutos
        telaResult.novoVoto(opcao, getVotos(opcao));

        // Atualizando a tela de resultados percentuais
        telaResultPerc.novoVoto(opcao, getVotos(opcao), getTotalVotos());
    }

    /**
     * Incrementa o voto da opção entrada
     */
    public void votar(String opcao) {
        int votoAtual = ((Integer) this.opcoes.get(opcao)).intValue();
        this.opcoes.put(opcao, new Integer(++votoAtual));
    }

    /**
     * Retorna a soma dos votos de todas as opções da enquete
     * @return int soma dos votos de todas as opções da enquete
     */
    public int getTotalVotos() {
        int total = 0;
        for (Integer votos : opcoes.values()) {
            total += votos.intValue();
        }
        return total;
    }

    /**
     * Retorna a quantidade de votos de uma opção individual
     */
    public int getVotos(String opcao) {
        return ((Integer) this.opcoes.get(opcao)).intValue();
    }
}