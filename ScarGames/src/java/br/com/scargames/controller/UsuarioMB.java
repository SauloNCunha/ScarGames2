package br.com.scargames.controller;

import br.com.scargames.domain.Endereco;
import br.com.scargames.domain.Usuario;
import br.com.scargames.services.EnderecoService;
import br.com.scargames.services.UsuarioService;
import br.com.scargames.util.UtilMessages;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@ManagedBean(name = "usuarioMB")
@SessionScoped
public class UsuarioMB {
    private int idUsuario;
    private int idUser;
    
    private Usuario usuario = new Usuario();
    private Endereco endereco = new Endereco();
    
    private List<Usuario> usuarios;
    private List<Endereco> enderecos;
    
    public String email;
    public String senha;
    
    public UsuarioMB() {
        enderecos = new ArrayList<>();
        this.listar();
    }
    
    public void inicializarHibernate(){
        UsuarioService service = new UsuarioService();
        service.inicializarHibernate();
    }
    
    public String autenticar(){
        UsuarioService usuarioService = new UsuarioService();
        usuario = new Usuario(email,senha);
        if(usuarioService.autenticar(usuario)){
            buscarIdUsuario();
            return "/private/index.xhtml?faces-redirect=true";
        }else{
            UtilMessages.messageError("Dados Invalidos !");
            return null;
        }
    }
    
    public String inserir(){
        UsuarioService service = new UsuarioService();
        if(service.inserir(usuario)){
            buscarIdUsuarioeCadastrarEnderecos(usuario);
            UtilMessages.messageInfo("Cadastrado com sucesso !!!");
            this.listar();
            return "list.xhtml?faces-redirect=true";
           
        }else{
            return null;
        }
    }
    
    public void buscarIdUsuarioeCadastrarEnderecos(Usuario usuario){
        UsuarioService service = new UsuarioService();
        EnderecoService serviceEndereco = new EnderecoService();
        for(Usuario u : service.listar()){
            if(u.getEmail().equals(usuario.getEmail())){
                idUser = u.getId();
            }
        }
        usuario = new Usuario();
        usuario.setId(idUser);
        for(Endereco e : enderecos){
            e.setUsuario(usuario);
            serviceEndereco.inserir(e);
        }
    }
    
    public String adicionarNovoEndereco(){
        int idusuario = 0;
        UsuarioService service = new UsuarioService();
        EnderecoService serviceEndereco = new EnderecoService();
        for(Usuario u : service.listar()){
            if(u.getEmail().equals(usuario.getEmail())){
                idusuario = u.getId();
            }
        }
        
        usuario.setId(idusuario);
        endereco.setUsuario(usuario);
        System.out.println("Usuario : " + endereco.getUsuario().getEmail());
        System.out.println("Endereco(Logradouro) : " + endereco.getLogradouro());
        if(serviceEndereco.inserir(endereco)){
            enderecos.add(endereco);
            return "alter.xhtml?faces-redirect=true";
        }else{
            UtilMessages.messageInfo("Erro ao inserir !!!");
            return null;
        }
        
    }
    
    public String alterar(){
        UsuarioService service = new UsuarioService();
        if(service.alterar(usuario)){
            UtilMessages.messageInfo("Alterado com sucesso !!!");
            this.listar();
            return "list.xhtml?faces-redirect=true";
        }else{
            return null;
        }
    }
    
    public String excluir(Usuario usuario){
        UsuarioService service = new UsuarioService();
        if(service.excluir(usuario)){
            UtilMessages.messageInfo("Excluido com sucesso !!!");
            this.listar();
            return "list.xhtml?faces-redirect=true";
        }else{
            return null;
        }
    }
    public String carregarDados(Usuario usuario){
        EnderecoService service = new EnderecoService();
        List<Endereco> listendereco = new ArrayList<>();
        for(Endereco e : service.listaPorUsuario(usuario.getId())){
            listendereco.add(e);
        }
        this.usuario = usuario;
        this.enderecos = listendereco;
        return "alter.xhtml?faces-redirect=true";
    }
    
    public void carregarDadosEndereco(Endereco endereco){
        System.out.println("ENTROu");
        this.endereco = endereco;
    }
    
    public void listar(){
        UsuarioService service = new UsuarioService();
        usuarios = service.listar();
    }
    
    public void inserirEndereco(){
        enderecos.add(endereco);
    }
    
    public void alterarEndereco(){
       EnderecoService service = new EnderecoService();
       if(service.alterar(endereco)){
           UtilMessages.messageInfo("Alterado com sucesso !!!");
           this.listar();

       }else{
           UtilMessages.messageInfo("Erro ao alterar !!!");
       }
    }
    
    public void excluirEndereco(Endereco endereco){
        if(enderecos.remove(endereco)){
            System.out.println("Excluido com sucesso !!!");
        }else{
            System.out.println("Ocorreu um erro ao excluir o endereço !!!");
        }
    }
    
    public void deletarEndereco(Endereco endereco){
        EnderecoService service = new EnderecoService();
       if(service.excluir(endereco)){
           enderecos.remove(endereco);
           UtilMessages.messageInfo("Exluido com sucesso !!!");
           this.listar();

       }else{
           UtilMessages.messageInfo("Erro ao excluir !!!");
       }
    }
    
    public void buscarIdUsuario(){
        UsuarioService service = new UsuarioService();
        for(Usuario u : service.listar()){
            if(u.getEmail().equals(email)){
                idUsuario = u.getId();
                HttpServletRequest rq = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
                HttpSession sessao = rq.getSession();
                sessao.setAttribute("idUsuario", idUsuario);
            }
        }
    }
    
    public void novoEndereco(){
        endereco = new Endereco();
    }
    
    public String novoEnderecoUpdateUser(){
        endereco = new Endereco();
        return "novoEndereco.xhtml?faces-redirect=true";
        
    }
    
    public String novo(){
        usuario = new Usuario();
        enderecos = new ArrayList<>();
        
        return "new.xhtml?faces-redirect=true";
    }
    
    public String cancelar(){
        return "list.xhtml?faces-redirect=true";
    }
    
    public String voltarAlter(){
        return "alter.xhtml?faces-redirect=true";
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

    public List<Endereco> getEnderecos() {
        return enderecos;
    }

    public void setEnderecos(List<Endereco> enderecos) {
        this.enderecos = enderecos;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }
    
    

}
