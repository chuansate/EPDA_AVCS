/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author lowsi
 */
@Stateless
public class AppointmentFacade extends AbstractFacade<Appointment> {

    @PersistenceContext(unitName = "AVCS1-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public List<Appointment> searchPendingAppointmentsByVet(String vet_uname){
        Query q = em.createNamedQuery("Appointment.searchPendingAppointmentsByVet");
        q.setParameter("vet_uname", vet_uname);
        List<Appointment> result = q.getResultList();
        if(result.size()>0){
            return result;
        }
        return null;
    }
    
    public List<Appointment> searchCompletedAppointmentsByVet(String vet_uname){
        Query q = em.createNamedQuery("Appointment.searchCompletedAppointmentsByVet");
        q.setParameter("vet_uname", vet_uname);
        List<Appointment> result = q.getResultList();
        if(result.size()>0){
            return result;
        }
        return null;
    }
    
    
    public List<Appointment> searchCompletedAppointments(){
        Query q = em.createNamedQuery("Appointment.searchCompletedAppointments");
        List<Appointment> result = q.getResultList();
        if(result.size()>0){
            return result;
        }
        return null;
    }
    
    public List<Appointment> searchPendingAppointments(){
        Query q = em.createNamedQuery("Appointment.searchPendingAppointments");
        List<Appointment> result = q.getResultList();
        if(result.size()>0){
            return result;
        }
        return null;
    }
    
    public AppointmentFacade() {
        super(Appointment.class);
    }
    
}
