
public class NetworkDevice {

	String mac;
	String ip;
	
	public NetworkDevice(String mac, String ip) {
		super();
		this.mac = mac;
		this.ip = ip;
	}

	@Override
	public String toString() {
		return "NetworkDevice [MAC=" + mac + ", IP=" + ip + "]";
	}
	
	
	
}
