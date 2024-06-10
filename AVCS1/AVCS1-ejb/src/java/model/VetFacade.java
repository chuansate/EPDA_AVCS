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
public class VetFacade extends AbstractFacade<Vet> {

    @PersistenceContext(unitName = "AVCS1-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public VetFacade() {
        super(Vet.class);
    }
    
    public List<Vet> searchNotApproved(){
        Query q = em.createNamedQuery("Vet.searchNotApproved");
        List<Vet> result = q.getResultList();
        if(result.size()>0){
            return result;
        }
        return null;
    }
    
    public List<Vet> searchByUname(String uname){
        Query q = em.createNamedQuery("Vet.searchByUname");
        q.setParameter("uname", uname);
        List<Vet> result = q.getResultList();
        if(result.size()>0){
            return result;
        }
        return null;
    }
    
    public void approveRegistration(long id, boolean approved){
        Query q = em.createNamedQuery("Vet.approveRegistration");
        q.setParameter("id", id);
        q.setParameter("approved", approved);
        q.executeUpdate();
    }
   
    public void editProfile (String uname, String pwd, String email_adr, String contact_num, String nationality, int age, char gender) {
        Query q = em.createNamedQuery("Vet.editProfile");
        q.setParameter("uname", uname);
        q.setParameter("pwd", pwd);
        q.setParameter("email_adr", email_adr);
        q.setParameter("contact_num", contact_num);
        q.setParameter("nationality", nationality);
        q.setParameter("age", age);
        q.setParameter("gender", gender);
        q.executeUpdate();
    }
    
}
