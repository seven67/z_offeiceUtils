package person.seven.designMode.construction.FilterPattern;/** * @description: * @author: liuqi create on 2018/8/10 **/public class Person {    public static final String GENDER = "gender";    public static final String MARITALSTATUS = "maritalStatus";    private String name;    private String gender;    private String maritalStatus;    public Person(String name,String gender,String maritalStatus){        this.name = name;        this.gender = gender;        this.maritalStatus = maritalStatus;    }    public String getName() {        return name;    }    public String getGender() {        return gender;    }    public String getMaritalStatus() {        return maritalStatus;    }    public void setName(String name) {        this.name = name;    }    public void setGender(String gender) {        this.gender = gender;    }    public void setMaritalStatus(String maritalStatus) {        this.maritalStatus = maritalStatus;    }}