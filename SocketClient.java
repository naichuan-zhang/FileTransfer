import java.net.*;
import java.io.*;

public class SocketClient {
	private ClientSocket cs = null;
	private String ip = "localhost";
	private int port = 8821;
	private String sendMessage = "Windows";
	
	public SocketClient() {
		try {
			if (createConnection()) {
				sendMessage();
				getMessage();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private boolean createConnection() {
		cs = new ClientSocket(ip, port);
		try {
			cs.CreateConnection();
			System.out.print("Connecting Server successfully!\n");
			return true;
		}
		catch (Exception e) {
			System.out.print("Connecting failed!\n");
			return false;
		}
	}
	
	private void sendMessage() {
		if (cs == null) {
			return;
		}
		try {
			cs.sendMessage(sendMessage);
		}
		catch (Exception e) {
			System.out.println("Sending message failed!");
		}
	}
	
	private void getMessage() {
		if (cs == null) {
			return;
		}
		DataInputStream inputStream = null;
		try {
			inputStream = cs.getMessageStream();
		}
		catch (Exception e) {
			System.out.println("Get message failed!");
			return;
		}
		
		try {
			String savePath = "e:\\";
			int bufferSize = 8192;
			byte[] buf = new byte[bufferSize];
			int passedlen = 0;
			long len = 0;
			
			savePath += inputStream.readUTF();
			DataOutputStream fileOut = new DataOutputStream(new BufferedOutputStream(new BufferedOutputStream(new FileOutputStream(savePath))));
			len = inputStream.readLong();
			System.out.println("length of file: " + len);
			System.out.println("Start receiving file!");
			
			while (true) {
				int read = 0;
				if (inputStream != null) {
					read = inputStream.read(buf);
				}
				passedlen += read;
				if (read == -1) {
					break;
				}
				System.out.println("File has been received " + (passedlen * 100 / len) + "%\n");
				fileOut.write(buf, 0, read);
			}
			System.out.println("Receive successfully, file has been saved at " + savePath + "\n");
			fileOut.close();
		}
		catch (Exception e) {
			System.out.println("Received message is wrong");
		}
	} 
	
	public static void main(String[] args) {
		new SocketClient();
	}
}