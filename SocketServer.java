import java.net.*;
import java.io.*;

public class SocketServer {
	int port = 8821;
	void start() {
		Socket s = null;
		try {
			ServerSocket ss = new ServerSocket(port);
			while (true) {
				String filePath = "g:\\testSocket.txt";
				File fi = new File (filePath);
				System.out.println("Length of file: " + (int) fi.length());
				s = ss.accept();
				System.out.println("Create connection");
				DataInputStream dis = new DataInputStream(new BufferedInputStream(s.getInputStream()));
				dis.readByte();
				
				DataInputStream fis = new DataInputStream(new BufferedInputStream(new FileInputStream(filePath)));
				DataOutputStream ps = new DataOutputStream(s.getOutputStream());
				
				ps.writeUTF(fi.getName());
				ps.flush();
				ps.writeLong((long) fi.length());
				ps.flush();
				int bufferSize = 8192;
				byte[] buf = new byte[bufferSize];
				while (true) {
					int read = 0;
					if (fis != null) {
						read = fis.read(buf);
					}
					if (read == -1) {
						break;
					}
					ps.write(buf, 0, read);
				}
				ps.flush();
				fis.close();
				s.close();
				System.out.println("File Transmission done");
			}
		}
		
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void main(String [] args) {
		new SocketServer().start();
	}
}