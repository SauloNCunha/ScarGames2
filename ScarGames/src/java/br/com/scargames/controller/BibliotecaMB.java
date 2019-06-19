package br.com.scargames.controller;


import br.com.scargames.domain.Biblioteca;
import br.com.scargames.domain.Jogo;
import br.com.scargames.domain.Usuario;
import br.com.scargames.services.BibliotecaService;
import br.com.scargames.services.JogoService;
import br.com.scargames.util.UtilMessages;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


@ManagedBean(name = "bibliotecaMB")
@SessionScoped
public class BibliotecaMB {
    FacesContext context = FacesContext.getCurrentInstance();
    private UsuarioMB usuariomb = new UsuarioMB();
    
    HttpServletRequest rq = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
    HttpSession sessao = rq.getSession();
    
    private int idUsuario;
    private int idJogo;
    
    private Usuario usuario = new Usuario();
    private Biblioteca biblioteca = new Biblioteca();
    private List<Biblioteca> bibliotecas = new ArrayList<>();
    
    public BibliotecaMB() {
        this.listar();
    }
    
    public void adicionarJogo(Jogo jogo){
        buscarIdJogo(jogo);
        BibliotecaService service = new BibliotecaService();
        
        usuario.setId(Integer.parseInt(String.valueOf(sessao.getAttribute("idUsuario"))));//aqq
        biblioteca.setUsuario(usuario);
        System.out.println("Id do usuario logado : " + usuario.getId());
        jogo.setId(idJogo);
        System.out.println("Id do jogo : " + jogo.getId());
        biblioteca.setJogo(jogo);
        if(service.inserir(biblioteca)){
            context.addMessage(null, new FacesMessage("Successful",  "Adicionado a Biblioteca !!! ") );
        }else{
            context.addMessage(null, new FacesMessage("Error",  "Erro ao adicionar !!! ") );
        }
    }
    
    public void excluir(Biblioteca biblioteca){
        BibliotecaService service = new BibliotecaService();
        if(service.excluir(biblioteca)){
            UtilMessages.messageInfo("Excluido com sucesso !!!");
            this.listar();
        }else{
            UtilMessages.messageInfo("Erro com sucesso !!!");
        }
    }
    
    public String inserir(){
        BibliotecaService service = new BibliotecaService();
        if(service.inserir(biblioteca)){
            UtilMessages.messageInfo("Cadastrado com sucesso !!!");
            this.listar();
            return "list.xhtml?faces-redirect=true";
        }else{
            UtilMessages.messageInfo("Erro ao cadastrar !!!");
            return null;
        }
    }
    
    public void buscarIdJogo(Jogo jogo){
        JogoService service = new JogoService();
        for(Jogo j : service.listar()){
            if(j.getTitulo().equals(jogo.getTitulo())){
                idJogo = j.getId();
            }
        }
    }
    
    public void listar(){
        BibliotecaService service = new BibliotecaService();
        bibliotecas = service.listar();
    }
    
    public String novo(){
        return "new.xhtml?faces-redirect=true";
    }
    
    public String cancelar(){
        return "list.xhtml?faces-redirect=true";
    }

    public int getIdJogo() {
        return idJogo;
    }

    public void setIdJogo(int idJogo) {
        this.idJogo = idJogo;
    }

    public Biblioteca getBiblioteca() {
        return biblioteca;
    }

    public void setBiblioteca(Biblioteca biblioteca) {
        this.biblioteca = biblioteca;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public List<Biblioteca> getBibliotecas() {
        return bibliotecas;
    }

    public void setBibliotecas(List<Biblioteca> bibliotecas) {
        this.bibliotecas = bibliotecas;
    }
    
    
    
    
}
