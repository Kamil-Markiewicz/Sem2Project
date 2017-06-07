/**
 * Responsible for storing personal data.
 * @author	Kamil Markiewicz
 * @version	2.0
 */
public class Person {

	private String name;
	private String address;

	/**
	 * Constructor that creates a person with given details.
	 * @param name		Name of person
	 * @param address	Address of person
	 */
	public Person(String name, String address){
		this.name = name;
		this.address = address;
	}

	/**
	 * Gets name of person
	 * @return	Name of person
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets persons name.
	 * @param name	Name of person
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets address of person.
	 * @return	Address of person
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * Sets address of person.
	 * @param address	Address of person
	 */
	public void setAddress(String address) {
		this.address = address;
	}
}