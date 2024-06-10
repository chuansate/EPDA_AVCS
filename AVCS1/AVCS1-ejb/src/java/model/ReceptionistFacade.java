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
public class ReceptionistFacade extends AbstractFacade<Receptionist> {

    @PersistenceContext(unitName = "AVCS1-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ReceptionistFacade() {
        super(Receptionist.class);
    }
    
    public List<Receptionist> searchNotApproved(){
        Query q = em.createNamedQuery("Receptionist.searchNotApproved");
        List<Receptionist> result = q.getResultList();
        if(result.size()>0){
            return result;
        }
        return null;
    }
    
    public List<Receptionist> searchByUname(String uname){
        Query q = em.createNamedQuery("Receptionist.searchByUname");
        q.setParameter("uname", uname);
        List<Receptionist> result = q.getResultList();
        if(result.size()>0){
            return result;
        }
        return null;
    }
    
    public void approveRegistration(long id, boolean approved){
        Query q = em.createNamedQuery("Receptionist.approveRegistration");
        q.setParameter("id", id);
        q.setParameter("approved", approved);
        q.executeUpdate();
    }
    
}
