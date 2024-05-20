import java.io.Serializable;

public class Client implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String name;
	
	public Client(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}

}
