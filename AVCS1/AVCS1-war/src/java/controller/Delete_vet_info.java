/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import JSP_utils.ListsOfRecords;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Appointment;
import model.AppointmentFacade;
import model.Expertise;
import model.ExpertiseFacade;
import model.Vet;
import model.VetFacade;

/**
 *
 * @author lowsi
 */
@WebServlet(name = "Delete_vet_info", urlPatterns = {"/Delete_vet_info"})
public class Delete_vet_info extends HttpServlet {

    @EJB
    private ExpertiseFacade expertiseFacade;

    @EJB
    private AppointmentFacade appointmentFacade;

    @EJB
    private VetFacade vetFacade;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        List<Vet> allvets = vetFacade.findAll();
        long vetIDToBeDeleted = 0l;
        for (Vet v: allvets) {
            // check if the Delete button of each record is clicked
            if (request.getParameter(Long.toString(v.getId())) != null) {
                vetIDToBeDeleted = v.getId();
                break;
            }
        }
        Vet vetToBeDeleted = vetFacade.find(vetIDToBeDeleted);
        
        // when you are deleting this record, rmb to handle related tables such as
        // tables WorkingRota and Appointment: these contain the usernames instead of Vet ID, so dun need to care about them. 
        // Vet_Expertise and Vet_Appointment: these contain reference to the Vet ID.
        request.getRequestDispatcher("managing_staff/manage_vet_info.jsp").include(request, response);
        try (PrintWriter out = response.getWriter()) {
            if (vetToBeDeleted != null) {               
                try {
                    //do the database backend logic here!
                    //VetID are foreign keys in the table Expertise and table Appointment
                    // so have to remove the records from tables `Vet_Expertise` and `Vet_Appointment`
                    ArrayList<Long> expertiseID_ofVetToBeDeleted = new ArrayList<Long>();
                    for (Expertise exp: vetToBeDeleted.getExpertises()) {
                        expertiseID_ofVetToBeDeleted.add(exp.getId());
                    }
                    vetToBeDeleted.getExpertises().clear();
                    
                    ArrayList<Long> aptID_ofVetToBeDeleted = new ArrayList<Long>();
                    for (Appointment apt: vetToBeDeleted.getAppointments()) {
                        aptID_ofVetToBeDeleted.add(apt.getId());
                    }
                    vetToBeDeleted.getAppointments().clear();
                    vetFacade.edit(vetToBeDeleted);
                    // then, replace the vet_uname in tables `Appointment` and `WorkingRota` with "original_vet_uname (Deleted)"
                    if (aptID_ofVetToBeDeleted.size() > 0) {
                        for (long ID: aptID_ofVetToBeDeleted) {
                            Appointment apt = appointmentFacade.find(ID);
                            String new_vet_uname = apt.getVet_uname() + " (Deleted)";
                            apt.setVet_uname(new_vet_uname);
                            appointmentFacade.edit(apt);
                        }
                    } else {
                        System.out.println("No appointments to delete for the vet!");
                    }
                    // didn't do the replacements of the vet username for table `Appointment` becoz in this system, the staffs are not allowed to modify the working rota
                    // so even if the staffs are notified that the vet has been deleted and the vet is in the incoming working rota, the staffs can do nothing
                    
                    // then delete the associated expertises from the table Expertise
                    if (expertiseID_ofVetToBeDeleted.size() > 0) {
                        for (long ID: expertiseID_ofVetToBeDeleted) {
                            Expertise exp = expertiseFacade.find(ID);
                            expertiseFacade.remove(exp);
                        }
                    } else {
                        System.out.println("No expertises to delete for the vet!");
                    }
                    // then delete the vet from the table Vet
                    vetFacade.remove(vetToBeDeleted);
                    out.println("Deleted the vet successfully!");
                    
                } catch (Exception ex) {
                    ex.printStackTrace();
                    out.println("Failed to delete the vet, try again!");
                }
                
                
                List<Vet> updatedAllVets = vetFacade.findAll();
                out.println(new ListsOfRecords().listOfVets(updatedAllVets));
            } else {
                out.println("Failed to find the desired vet, try again!");
                List<Vet> allVets = vetFacade.findAll();
                out.println(new ListsOfRecords().listOfVets(allVets));
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
