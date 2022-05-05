import java.util.ArrayList;
import java.util.List;

public class DHCP {

	List<String> excludedIP;
	List<NetworkDevice> reserved;
	List<NetworkDevice> dhcp;

	
	public DHCP(List<String> excludedIP, List<NetworkDevice> reserved, List<NetworkDevice> dhcp) {
		this.excludedIP = excludedIP;
		this.reserved = reserved;
		this.dhcp = dhcp;
	}
	
	public void requestIP(String mac) {
		int endOfIP=100;
		for (NetworkDevice networkDevice : dhcp) { //mac c�mnek van e m�r foglal�sa (dhcp) 
			if(networkDevice.mac.equals(mac)) {
				System.out.println("m�r van IP c�me");
				return;
			}
		}
		for (NetworkDevice networkDevice : reserved) { //mac c�m fenntartott e (reserved) 
			if(networkDevice.mac.equals(mac)) {
				System.out.println("resered IP c�m");
				NetworkDevice nd=new NetworkDevice(mac, networkDevice.ip);
				dhcp.add(nd);
				return;
			}
		}
		
		boolean ipKiosztva=false;
		
		while(endOfIP<199) {      			//ip c�m ellen�rz�se �s kioszt�sa
			System.out.println("192.168.10."+endOfIP);
			if(excludedIP.contains("192.168.10."+endOfIP)) {	//ip a kiz�rtak k�z�tt van e 
				endOfIP+=1;
				System.out.println("192.168.10."+endOfIP);
				continue;
			}
			
			boolean flag=false;
			
			for (NetworkDevice networkDevice : dhcp) {	//ip ki van e m�r osztva (dhcp) 
				if(networkDevice.ip.equals("192.168.10."+endOfIP)) {
					System.out.println("ip ki van osztva");
				endOfIP+=1;
				flag=true;
				break;
				}
			}
			if(flag==true)
				continue;
			
			for (NetworkDevice networkDevice : reserved) {	//ip c�m fenntartott e (reserved) 
				if(networkDevice.ip.equals("192.168.10."+endOfIP)) {
				endOfIP+=1;
				flag=true;
				break;
				}
			}
			if(flag==true)
				continue;
			
			NetworkDevice nd=new NetworkDevice(mac, ("192.168.10."+endOfIP));	//ip c�m kioszt�sa
			System.out.println("�j IP c�m kiosztva "+"192.168.10."+endOfIP);
			dhcp.add(nd);
			ipKiosztva=true;
			break;
			}
		
		if(!ipKiosztva){
			System.out.println("sikertelen ip kioszt�s");
			}
		}
	
	public List<NetworkDevice> status(){
		return dhcp;
	}
	
	public void releaseIP(String ip) {
		ArrayList<NetworkDevice> toRemove=new ArrayList<NetworkDevice>();
		for (NetworkDevice networkDevice : dhcp) {
			if(ip.equals(networkDevice.ip)){
			toRemove.add(networkDevice);
			}
		}
		dhcp.removeAll(toRemove);
	}

	
	
}
