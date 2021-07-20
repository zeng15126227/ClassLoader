package test;

import lombok.Data;

import java.util.Optional;
import java.util.concurrent.ThreadPoolExecutor;

public class nullable {
    public static void main(String[] args) {

        //new Person().getCity().trim();
        Person p = new Person();
        //p.setCity("sx");
        System.out.println(Optional.ofNullable(p)
                .map(Person::getCity)
                .orElse("hhhhhhhh"));
    }
}

@Data
class Person{
    String name;
    String passwd;
    String city;
}
