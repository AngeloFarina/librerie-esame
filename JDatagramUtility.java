/*Farina Angelo 0000788991
/* Classe che mette a disposizione metodi per CREAZIONE- INIZIALIZZAZIONE -
/* - LETTURA - SCRITTURA tramite Datagrammi in java
*/

public class JDatagramUtility{

	public static void main(String args[]){

		//------------------- PARAMETRI PER SERVER -----------------

		private DatagramSocket socket = null;
		private DatagramPacket packet = null;
		private byte[] buf = new byte[256];
		private int serverPort = -1;

		//------------------- PARAMETRI PER CLIENT -----------------

		InetAddress inAddr = null;

		//------------------- PARAMETRI PER SCRITTURA -----------------

		ByteArrayOutputStream boStream;
		DataOutputStream doStream;
		byte[] data = null;
		String richiesta = null;

		//------------------- PARAMETRI PER LETTURA -----------------

		ByteArrayInputStream biStream = null;
		DataInputStream diStream=null;
		String risposta = null;

		//------------------- CONTROLLO PARAMETRI CLIENT -----------------
		//--------- usage: java Client localhost server-port -------------

		try {
			if(args.length == 2) {
				inAddr = InetAddress.getByName(args[0]);
				serverPort = Integer.parseInt(args[1]);
				System.out.println("Interrogazione server: "+inAddr+" in "+serverPort);
			}
			else {
				System.out.println("Usage: java Client localhost server-port");
				System.exit(1);
			}
		} catch (Exception e) {
			System.out.println("Errore params ");
			e.printStackTrace();
			System.exit(1);
		}

		//------------ CONTROLLO PARAMETRI NUMERO PORTA ---------------

		if (serverPort <= 1024 || serverPort > 65535) {
			System.out.println("Numero porta non valida: " + args[0]);
			System.out.println(" 1024 < porta < 65535");
			System.exit(2);
		}

		//------------------- INIZIALIZZAZIONE CLIENT -----------------

		try {
			socket = new DatagramSocket();
			socket.setSoTimeout(30000);
			packet = new DatagramPacket(buf, buf.length,inAddr,dSPort);
			System.out.println("Client avviato!");
			System.out.println("Creata la socket "+socket);
		} catch (SocketException e) {
			System.err.println("errore creazione socket ");
			e.printStackTrace();
			System.exit(1);
		}

		//------------------- INIZIALIZZAZIONE SERVER -----------------

		try {
			//apertura Socket
			socket = new DatagramSocket(port);
			packet = new DatagramPacket(buf, buf.length);
			System.out.println("SwapServer per file " + nomeFile + " avviato con socket port: " + socket.getLocalPort()); 
		} catch (SocketException e) {
			System.out.println("Problemi nella creazione della socket: ");
			e.printStackTrace();
			System.exit(1);
		}

		//------------------- INVIO DATAGRAM -----------------

		try {
			boStream = new ByteArrayOutputStream();
			doStream = new DataOutputStream(boStream);
			doStream.writeUTF(richiesta);
			data = boStream.toByteArray();
			packet.setData(data);
			socket.send(packet);
			System.out.println("Richiesta inviata a "+inAddr+" port: "+dSPort);
		} catch (IOException e) {
			System.err.println("Errore invio datagram");
			e.printStackTrace();
		}

		//------------------- RICEZIONE DATAGRAM -----------------

		try {
			packet.setData(buf);
			socket.receive(packet);
		} catch (IOException e) {
			System.err.println("Errore ricezione datagram");
			e.printStackTrace();
		}

		//------------------- LETTURA DATAGRAM -----------------

		try {
			biStream = new ByteArrayInputStream(packet.getData(),0,packet.getLength());
			diStream = new DataInputStream(biStream);
			risposta = diStream.readUTF();
			System.out.println("Risposta server: " + risposta);
		} catch (IOException e) {
			System.err.println("Errore lettura risposta");
			e.printStackTrace();
		}

		//---------- PER OGNI UTILIZZO RESETTARE LE VARIABILI!! ---------

		datagramPacket = null;
		buf = new byte[256];
		datagramPacket = new DatagramPacket(buf, buf.length, addr, swapServerPort);
		boStream = new ByteArrayOutputStream();
		doStream = new DataOutputStream(boStream);

		//---------- RICORDARSI DI CHIUDERE LA SOCKET!!!! ---------
		System.out.println("\nClient: termino...");
		datagramSocket.close();
	}
}