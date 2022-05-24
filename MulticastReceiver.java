import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MulticastReceiver{
    public static void main(String[] args) {
        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("HH:mm"); //para anunciar o momento que a mensagem chegou, foi criado um formatador de data
        try {
            int porta = 1111; //a porta que será usada para criação do multicast
            MulticastSocket socket = new MulticastSocket(porta); //instanciação da classe multicast com a porta sendo passada como argumento
            byte[] buf = new byte[256]; // variável que irá armazenar a mensagem recebida do usuário em bytes
            
            InetAddress group = InetAddress.getByName("230.0.0.0"); //o ip que será usado para receber as mensagens em multicast
            socket.joinGroup(group); //se juntando ao grupo multicast
            while(true){ //um while para continuar aberto o servidor enquanto não for enviado a mensagem que o finalizará
                System.out.println("Esperando mensagem. Envie 'end' para finalizar a conexão...");
                DatagramPacket mensagem = new DatagramPacket(buf, buf.length); //criando o pacote que irá receber o que o usuário enviar
                                                                               //deve ser passado como argumento a variavel byte e o tamanho da mesma
                socket.receive(mensagem); // a mensagem recebida será armazenada em "mensagem"
                String recebido = new String(mensagem.getData(),0,mensagem.getLength()); //a mnesagem em bytes será passada para String para ser mostrada em tela
                System.out.println("Mensagem recebida: '" +recebido + "', as " + formatador.format(LocalDateTime.now())); //mensagem mostrada em tela com a data que chegou
                if("end".equals(recebido)){ //caso seja enviado "end", o servidor sairá do while e ira fechar o grupo e fechar a conexão nas linhas abaixo
                    break;
                }
            }

            socket.leaveGroup(group);
            socket.close();
        } catch (Exception e) {
            System.out.println(e); //caso algum erro ocorra, mostrar na tela
        }
    }
}