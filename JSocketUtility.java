/*Farina Angelo 0000788991
/* Classe che mette a disposizione metodi per CREAZIONE- INIZIALIZZAZIONE -
/* - LETTURA - SCRITTURA tramite Socket in java
*/

public class JSocketUtility{
	public static void main(String[] args){

		//------------------- PARAMETRI PER CLIENT -----------------

		InetAddress inAddr = null 	//solo client
		int serverPort = -1;
		Socket socket = null;
        DataInputStream inSock = null;
        DataOutputStream outSock = null;

        //------------------- INIZIALIZZAZIONE CLIENT -----------------

        try {
            socket = new Socket(inAddr, serverPort);
            socket.setSoTimeout(30000);
            System.out.println("Creata la socket: " + socket);
            inSock = new DataInputStream(socket.getInputStream());
            outSock = new DataOutputStream(socket.getOutputStream());
        } catch (IOException ioe) {
            System.out.println("Problemi nella creazione degli stream su socket: ");
            ioe.printStackTrace();
            System.out.print("\n^D(Unix)/^Z(Win)+invio per uscire, solo invio per continuare: ");
            System.exit(1);
        }

        //------------------- PARAMETRI PER SERVER -----------------

        ServerSocket serverSocket = null;
        Socket clientSocket = null;

        //------------------- INIZIALIZZAZIONE SERVER -----------------

        try {
            serverSocket = new ServerSocket(port);
            serverSocket.setReuseAddress(true);
            System.out.println("Server: avviato ");
            System.out.println("Server: creata la server socket: " + serverSocket);
        } catch (Exception e) {
            System.err
                    .println("Server: problemi nella creazione della server socket: "+ e.getMessage());
            e.printStackTrace();
            serverSocket.close();
            System.exit(1);
        }

         try {
            while (true) {
                System.out.println("Server: in attesa di richieste...\n");

                try {
                    clientSocket = serverSocket.accept(); //bloccante!!!
                    System.out.println("Server: connessione accettata: " + clientSocket);
                } catch (Exception e) {
                    System.err
                            .println("Server: problemi nella accettazione della connessione: "
                                    + e.getMessage());
                    e.printStackTrace();
                    continue;
                }
                try {
		            inSock = new DataInputStream(clientSocket.getInputStream());
		            outSock = new DataOutputStream(clientSocket.getOutputStream());
		        } catch (IOException ioe) {
            System.out.println("Problemi nella creazione degli stream di input/output su socket: ");
            ioe.printStackTrace();
            return;
        }
            }// while true
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println("Server: termino...");
            System.exit(2);
        }
	}
}