/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.javamagazine.jee6.carrinhodecompras.web;

import java.util.MissingResourceException;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

/**
 *
 * @author User
 */
@Named
@ApplicationScoped
public class UtilsMB {
    
    private ResourceBundle bundle;
    
    @PostConstruct
    public void init() {
        this.bundle = ResourceBundle.getBundle("messages", FacesContext.getCurrentInstance().getViewRoot().getLocale());
    }
    
    public String getMessage(String chave) {
        String message = null;
        try {
            message = bundle.getString(chave);
        } catch(MissingResourceException e) {
            return "?? chave" + chave + "inexistente ??";
        }
        return message;
    }
    
}
