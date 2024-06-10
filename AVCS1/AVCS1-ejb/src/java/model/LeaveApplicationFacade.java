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
public class LeaveApplicationFacade extends AbstractFacade<LeaveApplication> {

    @PersistenceContext(unitName = "AVCS1-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public LeaveApplicationFacade() {
        super(LeaveApplication.class);
    }
    public List<LeaveApplication> searchPendingLeaveApplications(){
        Query q = em.createNamedQuery("LeaveApplication.searchPendingLeaveApplications");
        List<LeaveApplication> result = q.getResultList();
        if(result.size()>0){
            return result;
        }
        return null;
    }
    
    public List<LeaveApplication> searchLeaveApplicationsByVet(String vetUname){
        Query q = em.createNamedQuery("LeaveApplication.searchLeaveApplicationsByVet");
        q.setParameter("uname", vetUname);
        List<LeaveApplication> result = q.getResultList();
        if(result.size()>0){
            return result;
        }
        return null;
    }
    
}
