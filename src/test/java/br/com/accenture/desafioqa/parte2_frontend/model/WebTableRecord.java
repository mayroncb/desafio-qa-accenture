package br.com.accenture.desafioqa.parte2_frontend.model;

/**
 * Representa um registro da tabela Web Tables (Elements > Web Tables).
 */
public class WebTableRecord {

    private String firstName;
    private String lastName;
    private String age;
    private String email;
    private String salary;
    private String department;

    public WebTableRecord(String firstName, String lastName, String age, String email, String salary, String department) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.email = email;
        this.salary = salary;
        this.department = department;
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

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    @Override
    public String toString() {
        return String.format("%s %s (%s) - Dept: %s, Salário: %s",
                firstName, lastName, email, department, salary);
    }
}

