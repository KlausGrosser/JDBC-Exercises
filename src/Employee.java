public class Employee {
    private String firstName;
    private String lastName;
    private String email;
    private long contact;

    public Employee() {

    }

    public Employee(String firstName, String lastName, String email) {
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setEmail(email);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getContact() {
        return contact;
    }

    public void setContact(long contact) {
        this.contact = contact;
    }

}
