package model;

import java.time.LocalDateTime;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2024-03-13T20:35:31")
@StaticMetamodel(Appointment.class)
public class Appointment_ { 

    public static volatile SingularAttribute<Appointment, Character> gender;
    public static volatile SingularAttribute<Appointment, String> diagnosis;
    public static volatile SingularAttribute<Appointment, Boolean> completed;
    public static volatile SingularAttribute<Appointment, String> email_adr;
    public static volatile SingularAttribute<Appointment, LocalDateTime> scheduled_time;
    public static volatile SingularAttribute<Appointment, String> prognosis;
    public static volatile SingularAttribute<Appointment, String> customer_uname;
    public static volatile SingularAttribute<Appointment, String> pet_name;
    public static volatile SingularAttribute<Appointment, Integer> customer_rating;
    public static volatile SingularAttribute<Appointment, String> vet_uname;
    public static volatile SingularAttribute<Appointment, String> contact_num;
    public static volatile SingularAttribute<Appointment, String> nationality;
    public static volatile SingularAttribute<Appointment, String> species;
    public static volatile SingularAttribute<Appointment, Integer> price;
    public static volatile SingularAttribute<Appointment, Boolean> paid;
    public static volatile SingularAttribute<Appointment, Long> id;
    public static volatile SingularAttribute<Appointment, Integer> age;

}