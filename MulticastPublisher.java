import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class MulticastPublisher {
    public static void main(String[] args) {
        try {
            int porta = 1111; //primeiro deve ser defindo a porta que será usada
            InetAddress group = InetAddress.getByName("230.0.0.0"); //depois definir o IP onde estará o grupo
            MulticastSocket socket = new MulticastSocket(porta); //no momento de instaciação do multicast, deve passar a porta que será usada como argumento
            BufferedReader scanNovo = new BufferedReader(new InputStreamReader(System.in)); //para ler mensagens que o usuário escrever

            byte[] buf = new byte[256]; //como pacotes só enviam bytes, deve ser criado uma variável tipo byte para colocar a mensagem
                                        //o tamnho dessa variável é 256
            socket.joinGroup(group);    //o usuário está se juntando ao grupo multicast no ip informado
            System.out.println("Se juntou ao Multicast: '" +group+ "'");
            System.out.println("Escreva algo para mandar:");
            String mensagem = scanNovo.readLine(); //linha para ler a mensagem que o usuário quiser mandar
            buf = mensagem.getBytes(); //a variável buf irá receber a mensagem escrita e pegar os bytes para enviar como pacote

            DatagramPacket pacote = new DatagramPacket(buf, buf.length,group,porta); //a instanciação de pacote é criada
                                                                                    // é passado a mensagem, o tamanho dela, a ip do grupo e a porta

            socket.send(pacote); //o pacote está sendo enviado
            System.out.println("Enviado");
            socket.leaveGroup(group); //o usuário deixa o grupo multicast
            socket.close(); //o a conexão de socket é fechada


        } catch (Exception e) {
            System.out.println(e); //caso algum erro ocorra, mostrar na tela
                }
    }
}
