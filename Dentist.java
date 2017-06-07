//Kamil Markiewicz, R00139423, Assignment1 Dentist Class, V5.9

/**
 * Responsible for storing dentist data.
 * Extends Person class.
 * @author	Kamil Markiewicz
 * @version	2.0
 */
public class Dentist extends Person{

	private String password;

	/**
	 * Constructor
	 * @param name		Name of dentist
	 * @param address	Address of dentist
	 * @param password	Password of dentist
	 */
	public Dentist(String name, String address, String password){
		super(name, address);
		this.password = password;
	}

	/**
	 * Gets password of dentist
	 * @return	Password of dentist
	 */
	public String getPassword() {
		return password;
	}
}