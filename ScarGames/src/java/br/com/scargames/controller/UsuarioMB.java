package br.com.scargames.controller;

import br.com.scargames.domain.Usuario;
import br.com.scargames.services.UsuarioService;
import br.com.scargames.util.UtilMessages;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.util.List;

@ManagedBean(name = "usuarioMB")
@SessionScoped
public class UsuarioMB implements Serializable{

    private Usuario usuario;
    private String email;
    private String senha;
    
    public UsuarioMB() {
        this.listar();
        
    }
    
    
    private List<Usuario> usuarios;   
    
    public void listar(){
        UsuarioService service = new UsuarioService();
        usuarios = service.listar();
    }
    
    public void inicializarHibernate(){
        UsuarioService service = new UsuarioService();
        service.inicializarHibernate();
        this.listar();
        System.out.println("Tamanho: " + usuarios.size());
    }
    
    public String autenticar(){
        UsuarioService service = new UsuarioService();
        usuario = new Usuario(email, senha);
        if (service.autenticar(usuario)){
            return "/private/index.xhtml?faces-redirect=true";
        }else{
            UtilMessages.messageError("Dados inválidos!");
            return null;
        }
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }
    
    
    
    
}
