package application;

public class Person {
	private int id;
	private String name;
	private String gender;
	private String street;
	private String city;
	private String zip;
	
	public Person(int id, String name, String gender, String street, String city, String zip) {
		super();
		this.id = id;
		this.name = name;
		this.gender = gender;
		this.street = street;
		this.city = city;
		this.zip = zip;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return this.gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getStreet() {
		return this.street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getZip() {
		return this.zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}
	

}
