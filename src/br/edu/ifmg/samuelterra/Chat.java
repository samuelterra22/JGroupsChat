package br.edu.ifmg.samuelterra;

import org.jgroups.JChannel;
import org.jgroups.Message;
import org.jgroups.ReceiverAdapter;
import org.jgroups.View;

import java.util.Scanner;

/**
 * Classe Chat
 *
 * Implementa um chat simples usando a biblioteca JGroups
 *
 */
public class Chat extends ReceiverAdapter {

    /* variáveis da classe */
    private JChannel canal;
    private String nomeCanal;
    private String nickname;

    /**
     * Contrutor da classe Chat
     * Recebe como parâmetro o nome do canal e o nickname do usuário que irá
     * executar o algorítmo. O nome do canal deve ser o mesmo para todos os usuários
     * que desejam estar no mesmo grupo. O nickname representa apenas um apelido a ser
     * mostrado durante o chat.
     *
     * @param   nomeCanal    String contendo o nome desejado do canal
     * @param   nickname     String com o apelido do usuário que irá executar o algoritmo
     */
    public Chat(String nomeCanal, String nickname) {
        /* defini o nome do canal e o apelido do usuário do chat */
        this.nomeCanal = nomeCanal;
        this.nickname = nickname;
    }

    /**
     * Método start
     * Este método não recebe parâmetros. Deve ser o método utilizado para iniciar uma
     * instância do chat.
     * Esse método é reposável por:
     *  -> Instânciar um novo canal de acordo com o nome que foi inicializado no construtor da classe
     *  -> Setar quem erá receber as mensagens enviadas
     *  -> Executar o loop que enviará as mensagens
     *  -> Fechar o canal quando o chat terminar
     */
    public void start() throws Exception{
        /* instancia um novo canal */
        canal = new JChannel();
        /* seta qual classe vai receber as mensagens */
        canal.setReceiver(this);
        /* realiza a conecxão do canal de acordo com o nome */
        canal.connect(nomeCanal);
        /* chama o método que realiza a leitura e envio das mensagens */
        eventLoop();
        /* ao terminar de executar, fecha o canal */
        canal.close();
    }

    /**
     * Método eventLoop
     * Este método é executado após o canal ser criado. Nele, o usuário escreve a mensagem
     * desejada e tal mensagem é enviada através do canal para todos os usuários no grupo.
     * O método só termina a execução apoś o recebimento de uma mensagem contendo a de
     * saida "quit".
     */
    private void eventLoop(){
        /* instancia um leitor para que seja possivel capturar irnfomações do teclado */
        Scanner teclado = new Scanner(System.in);
        String msg = "";
        boolean continua = true;

        /* fica em loop ate que receba a tag para fechar o chat */
        while(continua){
            System.out.print(">");
            System.out.flush();
            try{
                /* obtem a mensagem lida do teclado */
                msg = teclado.nextLine();
                /*trata comandos do protocolo*/
                if (msg.contains("quit"))
                    continua = false;

                /* monta com pacote (Message) para que possa ser enviada */
                Message pacote = new Message(null, nickname+" disse: "+msg);
                canal.send(pacote);

            }catch(Exception e){
                System.out.println("Erro: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    /**
     * Método receive
     * Este método é responsável por receber a mensagem enviada no canal.
     * Recebe como parêmetro a mensagem, sendo ela um pacote contendo muito mais que uma
     * simples String representando a mensagem. Apenas executa um print da mensagem que
     * foi enviado ao grupo.
     *
     * @param pacote    Parâmetro do tipo Message que contém a mensagem em formato String
     *                  e muito mais outras informações.
     */
    public void receive(Message pacote){
        /* printa na tela a mensagem enviada no chat */
        System.out.println((String) pacote.getObject());
    }

    /**
     * Método viewAccepted
     * Este método é responsável por printar na tela do chat uma informação de que um novo
     * usuário está utilizando o grupo. Recebe como parametro uma View contendo a lista de
     * membros do grupo atualizado após a inserção do novo usuário.
     * O método apenas realiza um print com a lista dos membros do grupo
     *
     * @param v
     */
    public void viewAccepted(View v) {
        /* printa na tela informado ao grupo que um novo usuário está na conversa */
        System.out.println("Novo membro no grupo "+ v.getMembers() + " !");
    }

}

