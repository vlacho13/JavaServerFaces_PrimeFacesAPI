/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uniciencia.proweb.forms;

import edu.uniciencia.proweb.daos.UsuarioDao;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;


@ManagedBean
@ViewScoped

public class LoginFormBean {

    private String usuario;
    private String password;
    private String msg;

    public String enviarLimpiar() {
        usuario = "";
        password = "";

        mostrarMsg("LIMPIANDO...");
        return null;
    }
    
    public String enviarLogin() {
        UsuarioDao dao = new UsuarioDao();
        try{
            
        }catch(NullPointerException  ArithmeticException){
            
        }
        
        
        boolean rta = dao.validarUsuario(usuario, password);
        if (!rta) {
            mostrarMsg("Usuario no encontrado");
            return null;
        }
        return "/faces/Inicio.xhtml?faces-redirect=true";
    }

    public void mostrarMsg(String msg) {
        FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_INFO, msg, msg);
        FacesContext.getCurrentInstance().addMessage(null, m);
    }
    
    public void msgEliminar() {
        addMessage("Sistema:", "Usuario eliminado.");
    }
    
    public void addMessage(String summary, String detail) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LoginFormBean() {
    }

}
