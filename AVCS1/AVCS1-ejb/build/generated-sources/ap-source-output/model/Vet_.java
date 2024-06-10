package model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.Appointment;
import model.Expertise;
import model.LeaveApplication;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2024-03-13T20:35:31")
@StaticMetamodel(Vet.class)
public class Vet_ { 

    public static volatile ListAttribute<Vet, Expertise> expertises;
    public static volatile ListAttribute<Vet, Appointment> appointments;
    public static volatile SingularAttribute<Vet, String> uname;
    public static volatile SingularAttribute<Vet, Character> gender;
    public static volatile SingularAttribute<Vet, String> email_adr;
    public static volatile SingularAttribute<Vet, Boolean> approved;
    public static volatile SingularAttribute<Vet, String> contact_num;
    public static volatile SingularAttribute<Vet, Integer> base_salary;
    public static volatile SingularAttribute<Vet, String> nationality;
    public static volatile ListAttribute<Vet, LeaveApplication> leaveApplications;
    public static volatile SingularAttribute<Vet, Long> id;
    public static volatile SingularAttribute<Vet, String> pwd;
    public static volatile SingularAttribute<Vet, Integer> age;

}