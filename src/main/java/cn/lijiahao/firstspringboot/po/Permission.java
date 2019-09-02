package cn.lijiahao.firstspringboot.po;

public class Permission extends BaseBean {
	private static final long serialVersionUID = 6399858600085049390L;
	
	private int id;
	private String name;//资源名称
	private String type;//资源类型，例如：menu、button等
	private String url;//访问url地址
	private String percode;//权限代码字符串
	private int parentid;//父节点id
	private String parentids;//父节点id列表
	private String sortstring;//排序号
	private char available;//是否可用:1为可用，0为不可用
	
	
	public Permission() {
		super();
	}
	public Permission(int id, String name, String type, String url, String percode, int parentid, String parentids,
			String sortstring, char available) {
		super();
		this.id = id;
		this.name = name;
		this.type = type;
		this.url = url;
		this.percode = percode;
		this.parentid = parentid;
		this.parentids = parentids;
		this.sortstring = sortstring;
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getPercode() {
		return percode;
	}
	public void setPercode(String percode) {
		this.percode = percode;
	}
	public int getParentid() {
		return parentid;
	}
	public void setParentid(int parentid) {
		this.parentid = parentid;
	}
	public String getParentids() {
		return parentids;
	}
	public void setParentids(String parentids) {
		this.parentids = parentids;
	}
	public String getSortstring() {
		return sortstring;
	}
	public void setSortstring(String sortstring) {
		this.sortstring = sortstring;
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
