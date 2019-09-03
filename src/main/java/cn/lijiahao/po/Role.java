package cn.lijiahao.po;

public class Role extends BaseBean{

	private static final long serialVersionUID = -7029055107794308347L;
	
	private int id;
	private String name;
	private char available;
	
	
	public Role() {
		super();
	}
	public Role(int id, String name, char available) {
		super();
		this.id = id;
		this.name = name;
		this.available = available;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public char getAvailable() {
		return available;
	}
	public void setAvailable(char available) {
		this.available = available;
	}
	
	public boolean isAvailable() {
		return this.available=='1'?true:false;
	}
	
}
