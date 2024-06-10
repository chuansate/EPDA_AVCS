/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author lowsi
 */
@Stateless
public class StaffFacade extends AbstractFacade<Staff> {

    @PersistenceContext(unitName = "AVCS1-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public StaffFacade() {
        super(Staff.class);
    }
    
    
    public void updateByID(long id, String pwd, char gender){
        Query q = em.createNamedQuery("Staff.updateByID");
        q.setParameter("id", id);
        q.setParameter("pwd", pwd);
        q.setParameter("gender", gender);
        q.executeUpdate();
    }
    
    public void deleteByID(long id){
        Query q = em.createNamedQuery("Staff.deleteByID");
        q.setParameter("id", id);
        q.executeUpdate();
    }
    
}
