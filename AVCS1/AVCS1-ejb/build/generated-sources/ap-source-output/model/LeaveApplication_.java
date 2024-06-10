package model;

import java.time.LocalDateTime;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2024-03-13T20:35:31")
@StaticMetamodel(LeaveApplication.class)
public class LeaveApplication_ { 

    public static volatile SingularAttribute<LeaveApplication, String> reason;
    public static volatile SingularAttribute<LeaveApplication, String> uname;
    public static volatile SingularAttribute<LeaveApplication, LocalDateTime> leaveDate;
    public static volatile SingularAttribute<LeaveApplication, Long> id;
    public static volatile SingularAttribute<LeaveApplication, String> userType;
    public static volatile SingularAttribute<LeaveApplication, String> status;

}