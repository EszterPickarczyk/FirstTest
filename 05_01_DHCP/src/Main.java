import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		List<String> excludedIP=new ArrayList<>();
		
		try (FileReader fr=new FileReader("C:\\Users\\Mice\\Documents\\Java\\OKJ_vizsgák\\DHCP server\\excluded.csv");
				BufferedReader br=new BufferedReader(fr)){
			
			String line;
			
			while((line=br.readLine())!=null){
				excludedIP.add(line);
			}
			
		} catch (IOException ioe) {
			System.out.println(ioe);
		}
		
		List<NetworkDevice> reserved=new ArrayList<>();
		
		try (FileReader fr=new FileReader("C:\\Users\\Mice\\Documents\\Java\\OKJ_vizsgák\\DHCP server\\reserved.csv");
				BufferedReader br=new BufferedReader(fr)){
			
			String line;
			
			while((line=br.readLine())!=null){
				String[] elements=line.split(";");
				NetworkDevice nd=new NetworkDevice(elements[0], elements[1]);
				reserved.add(nd);
			}
			
		} catch (IOException ioe) {
			System.out.println(ioe);
		}
		
	List<NetworkDevice> dhcp=new ArrayList<>();
		
		try (FileReader fr=new FileReader("C:\\Users\\Mice\\Documents\\Java\\OKJ_vizsgák\\DHCP server\\dhcp.csv");
				BufferedReader br=new BufferedReader(fr)){
			
			String line;
			
			while((line=br.readLine())!=null){
				String[] elements=line.split(";");
				NetworkDevice nd=new NetworkDevice(elements[0], elements[1]);
				dhcp.add(nd);
			}
			
		} catch (IOException ioe) {
			System.out.println(ioe);
		}
		
	List<String[]> test=new ArrayList<>();
	
	DHCP dhcpServer=new DHCP(excludedIP, reserved, dhcp);
	
		try (FileReader fr=new FileReader("C:\\Users\\Mice\\Documents\\Java\\OKJ_vizsgák\\DHCP server\\test.csv");
				BufferedReader br=new BufferedReader(fr)){
			
			String line;
			
			while((line=br.readLine())!=null){
				String[] temp=line.split(";");
				String[] elements={temp[0], temp[1]};
				test.add(elements);
				if(temp[0].equals("request")) {
					dhcpServer.requestIP(temp[1]);
				}
				else dhcpServer.releaseIP(temp[1]);
			}
			
		} catch (IOException ioe) {
			System.out.println(ioe);
		}

		try (FileWriter fw=new FileWriter("C:\\Users\\Mice\\Documents\\Java\\OKJ_vizsgák\\DHCP server\\dhcp_kesz.csv");
				BufferedWriter br=new BufferedWriter(fw)){
			
			for (NetworkDevice networkDevice : dhcpServer.status()) {
				br.write(networkDevice.mac+";"+networkDevice.ip);
				br.newLine();
			}
			br.close();
			
		} catch (IOException ioe) {
			System.out.println(ioe);
		}
	}

}
