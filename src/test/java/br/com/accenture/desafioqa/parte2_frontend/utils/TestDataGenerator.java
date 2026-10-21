package br.com.accenture.desafioqa.parte2_frontend.utils;

import br.com.accenture.desafioqa.parte2_frontend.model.StudentFormData;
import br.com.accenture.desafioqa.parte2_frontend.model.WebTableRecord;

import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Gerador utilitário de massas de dados aleatórias para os cenários de Frontend.
 */
public final class TestDataGenerator {

    private static final String[] FIRST_NAMES = {"Carlos", "Fernanda", "Gabriel", "Amanda", "Ricardo", "Beatriz", "Leonardo", "Helena"};
    private static final String[] LAST_NAMES = {"Almeida", "Ferreira", "Ribeiro", "Carvalho", "Gomes", "Martins", "Rocha", "Mendes"};
    private static final String[] GENDERS = {"Male", "Female", "Other"};
    private static final String[] SUBJECTS = {"Maths", "Computer Science", "Physics", "English", "Economics"};
    private static final String[] HOBBIES = {"Sports", "Reading", "Music"};
    private static final String[] MONTHS = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October"};

    private static final Map<String, String[]> STATE_CITIES = Map.of(
            "NCR", new String[]{"Delhi", "Gurgaon", "Noida"},
            "Uttar Pradesh", new String[]{"Agra", "Lucknow", "Merrut"},
            "Haryana", new String[]{"Karnal", "Panipat"},
            "Rajasthan", new String[]{"Jaipur", "Jaiselmer"}
    );

    private TestDataGenerator() {
    }

    public static StudentFormData generateRandomStudent() {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        String firstName = FIRST_NAMES[random.nextInt(FIRST_NAMES.length)];
        String lastName = LAST_NAMES[random.nextInt(LAST_NAMES.length)];
        int randomNum = random.nextInt(1000, 9999);
        String email = (firstName + "." + lastName + randomNum + "@accentureqa.com").toLowerCase();
        String gender = GENDERS[random.nextInt(GENDERS.length)];
        String mobileNumber = "9" + random.nextLong(100000000L, 999999999L);
        String birthMonth = MONTHS[random.nextInt(MONTHS.length)];
        String birthYear = String.valueOf(random.nextInt(1990, 2004));
        String birthDay = String.valueOf(random.nextInt(10, 25));
        String subject = SUBJECTS[random.nextInt(SUBJECTS.length)];
        String hobby = HOBBIES[random.nextInt(HOBBIES.length)];
        String address = "Av. Engenheiro Luís Carlos Berrini, " + random.nextInt(100, 2500) + " - São Paulo/SP";

        String[] states = STATE_CITIES.keySet().toArray(new String[0]);
        String state = states[random.nextInt(states.length)];
        String[] cities = STATE_CITIES.get(state);
        String city = cities[random.nextInt(cities.length)];

        return new StudentFormData(
                firstName,
                lastName,
                email,
                gender,
                mobileNumber,
                birthMonth,
                birthYear,
                birthDay,
                subject,
                hobby,
                address,
                state,
                city
        );
    }

    public static WebTableRecord generateRandomWebTableRecord() {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        String firstName = FIRST_NAMES[random.nextInt(FIRST_NAMES.length)];
        String lastName = LAST_NAMES[random.nextInt(LAST_NAMES.length)];
        int suffix = random.nextInt(100, 999);
        String email = (firstName + "." + lastName + suffix + "@qa.com").toLowerCase();
        String age = String.valueOf(random.nextInt(22, 55));
        String salary = String.valueOf(random.nextInt(4000, 15000));
        String department = "QA Automation";

        return new WebTableRecord(firstName, lastName, age, email, salary, department);
    }
}

