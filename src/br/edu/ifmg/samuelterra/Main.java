package br.edu.ifmg.samuelterra;

/**
 * Classe Main
 *
 * Representa a classe principal da aplicação
 *
 */
public class Main {

    /**
     * Método main
     * Este método é responsável por instanciar um novo chat informando tais
     * parâmentros como o nome do grupo e o nome do usuário que irá utilizar
     * o canal para se comunicar
     *
     * @param args          Vetor de String que é recebido por parâmetro do terminal
     * @throws Exception
     */
    public static void main(String[] args) throws Exception{
        new Chat("Chat","Samuel").start();
    }
}
