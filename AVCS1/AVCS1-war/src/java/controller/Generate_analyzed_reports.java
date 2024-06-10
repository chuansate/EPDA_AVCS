/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Receptionist;
import model.ReceptionistFacade;
import model.Staff;
import model.StaffFacade;
import model.Vet;
import model.VetFacade;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
/**
 *
 * @author lowsi
 */

@WebServlet(name = "Generate_analyzed_reports", urlPatterns = {"/Generate_analyzed_reports"})
public class Generate_analyzed_reports extends HttpServlet {

    @EJB
    private ReceptionistFacade receptionistFacade;

    @EJB
    private VetFacade vetFacade;

    @EJB
    private StaffFacade staffFacade;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private void savePieChart(DefaultPieDataset dataset, String chartTitle, int widthPieChart, int heightPieChart, String chartsSavedDirectory, String chartName) throws IOException {
        

        JFreeChart chart = ChartFactory.createPieChart(
           chartTitle,   // chart title
           dataset,          // data
           true,             // include legend
           true,
           false);

        File chartFile = new File(chartsSavedDirectory + chartName);
        
        ChartUtils.saveChartAsPNG(chartFile, chart , widthPieChart , heightPieChart);
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        List<Staff> stfs = staffFacade.findAll();
        int stfMaleCount = 0;
        int stfFemaleCount = 0;
        for (Staff s: stfs) {
            if (s.getGender() == 'M') {
                stfMaleCount++;
            } else if (s.getGender() == 'F'){
                stfFemaleCount++;
            } 
        }
        
        List<Vet> vets = vetFacade.findAll();
        int vetMaleCount = 0;
        int vetFemaleCount = 0;
        int vetDefaultGenderCount = 0;
        for (Vet v: vets) {
            if (v.getGender() == 'M') {
                vetMaleCount++;
            } else if (v.getGender() == 'F'){
                vetFemaleCount++;
            } else {
                vetDefaultGenderCount++;
            }
        }
        
        List<Receptionist> receptionists = receptionistFacade.findAll();
        int receptionistMaleCount = 0;
        int receptionistFemaleCount = 0;
        int receptionistDefaultGenderCount = 0;
        for (Receptionist re: receptionists) {
            if (re.getGender() == 'M') {
                receptionistMaleCount++;
            } else if (re.getGender() == 'F'){
                receptionistFemaleCount++;
            } else {
                receptionistDefaultGenderCount++;
            }
        }
        
        int vetLessThan20Count = 0;
        int vet21_30Count = 0;
        int vet31_40Count = 0;
        int vetMoreThan40Count = 0;
        for (Vet v: vets) {
            if (v.getAge() > 40) {
                vetMoreThan40Count++;
            } else if (v.getAge() > 30){
                vet31_40Count++;
            } else if (v.getAge() > 20){
                vet21_30Count++;
            } else {
                vetLessThan20Count++;
            }
        }
        
        int receptionistLessThan20Count = 0;
        int receptionist21_30Count = 0;
        int receptionist31_40Count = 0;
        int receptionistMoreThan40Count = 0;
        for (Receptionist r: receptionists) {
            if (r.getAge() > 40) {
                receptionistMoreThan40Count++;
            } else if (r.getAge() > 30){
                receptionist31_40Count++;
            } else if (r.getAge() > 20){
                receptionist21_30Count++;
            } else {
                receptionistLessThan20Count++;
            }
        }
        
        
        
        
        String chartsSavedDirectory = "C:\\Users\\User\\Documents\\NetBeansProjects\\EDPA_AVCS\\AVCS1\\AVCS1-war\\web\\";
        String chart1Name = "chart1.png";
        String chart2Name = "chart2.png";
        String chart3Name = "chart3.png";
        String chart4Name = "chart4.png";
        String chart5Name = "chart5.png";
        request.getRequestDispatcher("managing_staff/generate_analyzed_reports.jsp").include(request, response);
        try (PrintWriter out = response.getWriter()) {
            out.println("<h3>Report of Genders Among Staffs</h3>");
            int totalStfCount = stfs.size();
            out.println("<p>Staff male count: "+ stfMaleCount +", Percentage: "+ (stfMaleCount*100.0)/totalStfCount +"%</p>");
            out.println("<p>Staff female count: "+ stfFemaleCount +", Percentage: "+ (stfFemaleCount*100.0)/totalStfCount +"%</p>");
            DefaultPieDataset dataset = new DefaultPieDataset();
            dataset.setValue("Male", new Double( (stfMaleCount*100.0)/totalStfCount ) ); // category, and percentage of area on pie chart
            dataset.setValue("Female", new Double((stfFemaleCount*100.0)/totalStfCount ) );
            try {
                savePieChart(dataset, "Genders Among Staffs", 600, 400, chartsSavedDirectory, chart1Name);
                out.println("<img src=\""+ chart1Name +"\" alt=\"Pie chart of Genders Among Staffs\">");
            } catch (Exception ex) {
                out.println("Failed to save the pie chart!");
            }
            
            
            int totalVetCount = vets.size();
            out.println("<h3>Report of Genders Among Vets</h3>");  
            out.println("<p>Vet male count: "+ vetMaleCount +", Percentage: "+ (vetMaleCount*100.0)/totalVetCount +"%</p>");
            out.println("<p>Vet female count: "+ vetFemaleCount +", Percentage: "+ (vetFemaleCount*100.0)/totalVetCount +"%</p>");
            out.println("<p>Vet default gender count: "+ vetDefaultGenderCount +", Percentage: "+ (vetDefaultGenderCount*100.0)/totalVetCount +"%</p>");
            DefaultPieDataset dataset2 = new DefaultPieDataset();
            dataset2.setValue("Male", new Double( (vetMaleCount*100.0)/totalVetCount ) ); // category, and percentage of area on pie chart
            dataset2.setValue("Female", new Double((vetFemaleCount*100.0)/totalVetCount ) );
            dataset2.setValue("Default Gender", new Double((vetDefaultGenderCount*100.0)/totalVetCount ) );
            try {
                savePieChart(dataset2, "Genders Among Vets", 600, 400, chartsSavedDirectory, chart2Name);
                out.println("<img src=\""+ chart2Name +"\" alt=\"Pie chart of Genders Among Vets\">");
            } catch (Exception ex) {
                out.println("Failed to save the pie chart!");
            }
            
            int totalReceptionistCount = receptionists.size();
            out.println("<h3>Report of Genders Among Receptionists</h3>");
            out.println("<p>Receptionist male count: "+ receptionistMaleCount +", Percentage: "+ (receptionistMaleCount*100.0)/totalReceptionistCount +"%</p>");
            out.println("<p>Receptionist female count: "+ receptionistFemaleCount +", Percentage: "+ (receptionistFemaleCount*100.0)/totalReceptionistCount +"%</p>");
            out.println("<p>Receptionist default gender count: "+ receptionistDefaultGenderCount +", Percentage: "+ (receptionistDefaultGenderCount*100.0)/totalReceptionistCount +"%</p>");
            DefaultPieDataset dataset3 = new DefaultPieDataset();
            dataset3.setValue("Male", new Double( (receptionistMaleCount*100.0)/totalReceptionistCount ) ); // category, and percentage of area on pie chart
            dataset3.setValue("Female", new Double((receptionistFemaleCount*100.0)/totalReceptionistCount ) );
            dataset3.setValue("Default Gender", new Double((receptionistDefaultGenderCount*100.0)/totalReceptionistCount ) );
            try {
                savePieChart(dataset3, "Genders Among Receptionist", 600, 400, chartsSavedDirectory, chart3Name);
  
                out.println("<img src=\""+ chart3Name +"\" alt=\"Pie chart of Genders Among Receptionists\">");
            } catch (Exception ex) {
                out.println("Failed to save the pie chart!");
            }
            
            
            out.println("<h3>Report of Age Groups Among Vets</h3>");
            out.println("<p>Less than 20 count: "+ vetLessThan20Count +", Percentage: "+ (vetLessThan20Count*100.0)/totalVetCount +"%</p>");
            out.println("<p>From 21 to 30 count: "+ vet21_30Count +", Percentage: "+ (vet21_30Count*100.0)/totalVetCount +"%</p>");
            out.println("<p>From 31 to 40 count: "+ vet31_40Count +", Percentage: "+ (vet31_40Count*100.0)/totalVetCount +"%</p>");
            out.println("<p>More than 40 count: "+ vetMoreThan40Count +", Percentage: "+ (vetMoreThan40Count*100.0)/totalVetCount +"%</p>");            
            DefaultPieDataset dataset4 = new DefaultPieDataset();
            dataset4.setValue("Less than 20", new Double( (vetLessThan20Count*100.0)/totalVetCount ) ); // category, and percentage of area on pie chart
            dataset4.setValue("From 21 to 30", new Double((vet21_30Count*100.0)/totalVetCount ) );
            dataset4.setValue("From 31 to 40", new Double((vet31_40Count*100.0)/totalVetCount ) );
            dataset4.setValue("More than 40", new Double((vetMoreThan40Count*100.0)/totalVetCount ) );

            try {
                savePieChart(dataset4, "Age Group Among Vets", 600, 400, chartsSavedDirectory, chart4Name);
                out.println("<img src=\""+ chart4Name +"\" alt=\"Pie chart of Age Groups Among Vets\">");
            } catch (Exception ex) {
                out.println("Failed to save the pie chart!");
            }
            
           
            out.println("<h3>Report of Age Groups Among Receptionists</h3>");
            out.println("<p>Less than 20 count: "+ receptionistLessThan20Count +", Percentage: "+ (receptionistLessThan20Count*100.0)/totalReceptionistCount +"%</p>");
            out.println("<p>From 21 to 30 count: "+ receptionist21_30Count +", Percentage: "+ (receptionist21_30Count*100.0)/totalReceptionistCount +"%</p>");
            out.println("<p>From 31 to 40 count: "+ receptionist31_40Count +", Percentage: "+ (receptionist31_40Count*100.0)/totalReceptionistCount +"%</p>");
            out.println("<p>More than 40 count: "+ receptionistMoreThan40Count +", Percentage: "+ (receptionistMoreThan40Count*100.0)/totalReceptionistCount +"%</p>");            
            DefaultPieDataset dataset5 = new DefaultPieDataset();
            dataset5.setValue("Less than 20", new Double( (receptionistLessThan20Count*100.0)/totalReceptionistCount ) ); // category, and percentage of area on pie chart
            dataset5.setValue("From 21 to 30", new Double((receptionist21_30Count*100.0)/totalReceptionistCount ) );
            dataset5.setValue("From 31 to 40", new Double((receptionist31_40Count*100.0)/totalReceptionistCount ) );
            dataset5.setValue("More than 40", new Double((receptionistMoreThan40Count*100.0)/totalReceptionistCount ) );

            try {
                savePieChart(dataset5, "Age Group Among Receptionists", 600, 400, chartsSavedDirectory, chart5Name);
                out.println("<img src=\""+ chart5Name +"\" alt=\"Pie chart of Age Groups Among Receptionists\">");
            } catch (Exception ex) {
                out.println("Failed to save the pie chart!");
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
