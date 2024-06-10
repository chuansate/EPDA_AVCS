/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JSP_utils;

import java.util.ArrayList;
import java.util.List;
import model.Expertise;
import model.Vet;

/**
 *
 * @author lowsi
 */
public class DropDownMenus {
    public String expertisesDropDown_JSP(String dropDownID, String dropDownName) {
        String s = "";
        s = s + "<select id=\"" + dropDownID + "\" name=\""+ dropDownName +"\">";
        s = s + "<option value=\"-\">-</option>";
        s = s + "<option value=\"Fish\">Fish</option>";
        s = s + "<option value=\"Reptiles\">Reptiles</option>";
        s = s + "<option value=\"Dogs\">Dogs</option>";
        s = s + "<option value=\"Cats\">Cats</option>";
        s = s + "<option value=\"Birds\">Birds</option>";
        s = s + "<option value=\"Chickens\">Chickens</option>";
        s = s + "<option value=\"Horses\">Horses</option>";
        s = s + "</select>";
        return s;
    }
    
    public String vetsDropDown_JSP(int row_index, int col_index, List<Vet> vets) {
        String dropDown_vets_JSP = "<select id=\"vet\" name=\""+ Integer.toString(row_index) + "," + Integer.toString(col_index) +"\">\n";
        for (Vet vet: vets) {
            ArrayList<Expertise> expertisesByVet = vet.getExpertises();
            String expertises_string = "";
            for (Expertise exp: expertisesByVet) {
                if (expertisesByVet.get(expertisesByVet.size() - 1).getExpertise_name().equals(exp.getExpertise_name())) {                                   
                    expertises_string = expertises_string + exp.getExpertise_name();
                } else{
                    expertises_string = expertises_string + exp.getExpertise_name() + ", ";
                }
            }
            dropDown_vets_JSP = dropDown_vets_JSP + "<option value=\""+ vet.getUname() +"\"> Vet: "+ vet.getUname() + ", Expertises: " + expertises_string +"</option>";
        }
        
        dropDown_vets_JSP  = dropDown_vets_JSP + "</select>";
        return dropDown_vets_JSP;
    }
    
    public String paymentMethodsDropDown_JSP(String dropDownID, String dropDownName) {
        String s = "";
        s = s + "<select id=\"" + dropDownID + "\" name=\""+ dropDownName +"\">";
        s = s + "<option value=\"cash\">Cash</option>";
        s = s + "<option value=\"debit_card\">Debit Card</option>";
        s = s + "<option value=\"credit_card\">Credit Card</option>";
        s = s + "<option value=\"e_wallet\">E-wallet</option>";
        s = s + "</select>";
        return s;
    }
}
