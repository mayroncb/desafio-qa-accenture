package br.com.accenture.desafioqa.parte2_frontend.model;

/**
 * Representa os dados gerados aleatoriamente para preenchimento do formulário Practice Form.
 */
public class StudentFormData {

    private final String firstName;
    private final String lastName;
    private final String email;
    private final String gender;
    private final String mobileNumber;
    private final String birthMonth;
    private final String birthYear;
    private final String birthDay;
    private final String subject;
    private final String hobby;
    private final String currentAddress;
    private final String state;
    private final String city;

    public StudentFormData(
            String firstName,
            String lastName,
            String email,
            String gender,
            String mobileNumber,
            String birthMonth,
            String birthYear,
            String birthDay,
            String subject,
            String hobby,
            String currentAddress,
            String state,
            String city) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.gender = gender;
        this.mobileNumber = mobileNumber;
        this.birthMonth = birthMonth;
        this.birthYear = birthYear;
        this.birthDay = birthDay;
        this.subject = subject;
        this.hobby = hobby;
        this.currentAddress = currentAddress;
        this.state = state;
        this.city = city;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getGender() {
        return gender;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public String getBirthMonth() {
        return birthMonth;
    }

    public String getBirthYear() {
        return birthYear;
    }

    public String getBirthDay() {
        return birthDay;
    }

    public String getSubject() {
        return subject;
    }

    public String getHobby() {
        return hobby;
    }

    public String getCurrentAddress() {
        return currentAddress;
    }

    public String getState() {
        return state;
    }

    public String getCity() {
        return city;
    }
}

