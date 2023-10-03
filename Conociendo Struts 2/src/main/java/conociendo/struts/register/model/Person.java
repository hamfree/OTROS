package conociendo.struts.register.model;

public class Person {
    private String firstName;
    private String lastname;
    private String email;
    private int age;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Persona [firstName=").append(firstName).append(", lastname=").append(lastname)
                .append(", email=").append(email).append(", age=").append(age).append("]");
        return builder.toString();
    }

}
