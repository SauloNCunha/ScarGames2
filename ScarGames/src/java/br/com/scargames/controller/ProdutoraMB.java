package br.com.scargames.controller;

import br.com.scargames.domain.Produtora;
import br.com.scargames.services.ProdutoraService;
import br.com.scargames.util.UtilMessages;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.util.List;

@ManagedBean(name = "produtoraMB")
@SessionScoped
public class ProdutoraMB implements Serializable{

   private Produtora produtora;
    private List<Produtora> produtoras;
    
    public ProdutoraMB() {
        this.listar();
    }
    
    public void listar(){
        ProdutoraService service = new ProdutoraService();
        produtoras = service.listar();
    }
    
    public String novo(){
        produtora = new Produtora();
        return "new.xhtml?faces-redirect=true";
    }
    
    public String inserir(){
        ProdutoraService service = new ProdutoraService();
        if (service.inserir(produtora)){
            UtilMessages.messageInfo("Produtora cadastrada com sucesso!");
            this.listar();
            return "list.xhtml?faces-redirect=true";
        }else{
            UtilMessages.messageError("Ocorreu um erro ao cadastrar a produtora!");
            return null;
        }
    }
    
    public String alterar(){
        ProdutoraService service = new ProdutoraService();
        if (service.alterar(produtora)){
            UtilMessages.messageInfo("Produtora alterada com sucesso!");
            this.listar();
            return "list.xhtml?faces-redirect=true";
        }else{
            UtilMessages.messageError("Ocorreu um erro ao alterar a produtora!");
            return null;
        }
    }
    
    public String carregarDados(Produtora produtora){
        this.produtora = produtora;
        return "alter.xhtml?faces-redirect=true";
    }
    
    public String excluir(Produtora produtora){
        ProdutoraService service = new ProdutoraService();
        if (service.excluir(produtora)){
            UtilMessages.messageInfo("Produtora excluída com sucesso!");
            this.listar();
            return "list.xhtml?faces-redirect=true";
        }else{
            UtilMessages.messageError("Ocorreu um erro ao excluir a produtora!");
            return null;
        }
    }
    
    public String cancelar(){
        return "list.xhtml?faces-redirect=true";
    }

    public List<Produtora> getProdutoras() {
        return produtoras;
    }

    public void setProdutoras(List<Produtora> produtoras) {
        this.produtoras = produtoras;
    }

    public Produtora getProdutora() {
        return produtora;
    }

    public void setProdutora(Produtora produtora) {
        this.produtora = produtora;
    }
}
