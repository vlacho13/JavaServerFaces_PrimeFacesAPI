package edu.uniciencia.proweb.daos;

import edu.uniciencia.proweb.persistence.Usuario;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import org.primefaces.event.RowEditEvent;

@ManagedBean
@ViewScoped

public class UsuarioDao implements Serializable {

    
    private List<Usuario> listaMem;
    private int id;
    private String usuario;
    private String clave;
    private String correo;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public EntityManagerFactory getEmf() {
        return emf;
    }

    public void setEmf(EntityManagerFactory emf) {
        this.emf = emf;
    }   
    
    public List<Usuario> getListaMem() {
        return listaMem;
    }

    public void setListaMem(List<Usuario> listaMem) {
        this.listaMem = listaMem;
    }
    

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("ProgWebPU");    
    
    @PostConstruct
    public void init() {
        listaMem = getListaUsuario();
    }
    
    public List<Usuario> getListaUsuario() {
        EntityManager em = emf.createEntityManager();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT o from Usuario o");
        List<Usuario> lista = em.createQuery(sql.toString()).getResultList();
        if (lista != null && lista.size() > 0) {           
            return lista;
        } else {            
            return null;
        }
    }      
    
    public void eliminar() {
        eliminarUsuario(id);
    }
    
    public void eliminarUsuario(Integer id) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Usuario u = em.find(Usuario.class, id);
            em.remove(u);
            em.getTransaction().commit();
            init();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Sistema:", "Usuario Eliminado"));
        } catch (Exception e) {
            em.getTransaction().rollback();
        }
    }

    public void guardarUsuario() {
        EntityManager em = emf.createEntityManager();
        Usuario u = new Usuario();
        u.setIdUsuario(id);
        u.setUsuario(usuario);
        u.setClave(clave);
        u.setCorreo(correo);
        em.merge(u);
        try {
            em.getTransaction().begin();
            em.persist(u);
            em.getTransaction().commit();
            init();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Sistema:", "Usuario Creado Exitosamente"));
        } catch (Exception e) {
            em.getTransaction().rollback();
        }
    }

    public void actualizarUsuario(Usuario usu) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Usuario u = em.find(Usuario.class, usu.getIdUsuario());
            u.setIdUsuario(usu.getIdUsuario());
            u.setUsuario(usu.getUsuario());
            u.setClave(usu.getClave());
            u.setCorreo(usu.getCorreo());
            em.merge(u);
            em.getTransaction().commit();
            System.out.println("ok");
        } catch (Exception e) {
            System.out.println("error");
            em.getTransaction().rollback();
        }
    }

    public void actualizarAceptar(RowEditEvent event) {
        Usuario u = (Usuario) event.getObject();             
        actualizarUsuario(u);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Sistema:", "Usuario Actualizado"));
    }

    public void actualizarCancelar(RowEditEvent event) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Sistema:", "Cancelado"));
    }

    public boolean validarUsuario(String usu, String pass) {
        EntityManager em = emf.createEntityManager();
        StringBuilder sql = new StringBuilder();
        sql.append("select o from Usuario o ");
        sql.append("where o.usuario = :p1 ");
        sql.append("and o.clave = :p2 ");
        Query q = em.createQuery(sql.toString());
        q.setParameter("p1", usu);
        q.setParameter("p2", pass);

        List<Usuario> lista = q.getResultList();
        if (lista.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    public void persist(Object object) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(object);
            em.getTransaction().commit();
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", e);
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }

    public void actualizarUsuarioOriginal(Usuario usu) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Usuario u = em.find(Usuario.class, usu.getIdUsuario());
            u.setIdUsuario(usu.getIdUsuario());
            u.setUsuario(usu.getUsuario());
            u.setClave(usu.getClave());
            u.setCorreo(usu.getCorreo());
            em.merge(u);
            em.getTransaction().commit();
            System.out.println("ok");
        } catch (Exception e) {
            System.out.println("error");
            em.getTransaction().rollback();
        }
    }

}
