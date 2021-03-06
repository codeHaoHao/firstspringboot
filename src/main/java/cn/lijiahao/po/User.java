package cn.lijiahao.po;

public class User extends BaseBean {
    private static final long serialVersionUID = 127255423655316392L;

    private int id;
    private String username;
    private String password;
    private String name;
    private String phoneNum;
    private String email;
    private long dataOfBirth;
    private int age;
    private char gender;
    private String salt;//盐加密
    private char locked;
    private String avatar;//头像图片路径
    private String individualResume;//个人简介


    public User() {
        super();
    }

    public User(int id, String username, String password, String name, String phoneNum, String email, long dataOfBirth, int age,
                char gender, String salt, char locked, String avatar, String individualResume) {
        super();
        this.id = id;
        this.username = username;
        this.password = password;
        this.name = name;
        this.phoneNum = phoneNum;
        this.email = email;
        this.dataOfBirth = dataOfBirth;
        this.age = age;
        this.gender = gender;
        this.salt = salt;
        this.locked = locked;
        this.avatar = avatar;
        this.individualResume = individualResume;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getDataOfBirth() {
        return dataOfBirth;
    }

    public void setDataOfBirth(long dataOfBirth) {
        this.dataOfBirth = dataOfBirth;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public char getLocked() {
        return locked;
    }

    public void setLocked(char locked) {
        this.locked = locked;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getIndividualResume() {
        return individualResume;
    }

    public void setIndividualResume(String individualResume) {
        this.individualResume = individualResume;
    }

    public boolean typeOfLocked() {
        return this.locked == '1' ? true : false;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
