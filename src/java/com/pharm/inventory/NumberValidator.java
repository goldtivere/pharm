/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pharm.inventory;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author Gold
 */
@FacesValidator("numberValidator")
public class NumberValidator implements Validator {

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        if (value == null) {
            return; // Let required="true" handle.
        }

        UIInput startNumComponent = (UIInput) component.getAttributes().get("startNumComponent");

     
        Double startDate =Double.parseDouble((startNumComponent.getValue()).toString());

         Double endDate = Double.parseDouble((String) value);
         

        if (endDate > startDate) {
            startNumComponent.setValid(false);
            throw new ValidatorException(new FacesMessage(
                FacesMessage.SEVERITY_ERROR, "Unit Price cannot be greater than Amount", null));
        }
    }

}
