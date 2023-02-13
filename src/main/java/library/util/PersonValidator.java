package library.util;


import library.models.Person;
import library.services.PeopleService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@AllArgsConstructor
public class PersonValidator implements Validator {


    private final PeopleService peopleService;

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;
        if (peopleService.show(person.getEmail()).isPresent()) {
            errors.rejectValue("email", "", "This email already exist in the database.");
        }

    }
}