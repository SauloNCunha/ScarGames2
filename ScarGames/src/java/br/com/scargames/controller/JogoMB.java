package br.com.scargames.controller;


import br.com.scargames.domain.Jogo;
import br.com.scargames.services.JogoService;
import br.com.scargames.util.UtilMessages;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.util.List;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

@ManagedBean(name = "jogoMB")
@SessionScoped
public class JogoMB implements Serializable{

     public JogoMB() {
        this.listar();
    }
    
    private Jogo jogo;
    private List<Jogo> jogos;
    private String valor = "";
    
    public void formatarValor() {  
        DecimalFormat df = (DecimalFormat) DecimalFormat.getCurrencyInstance(Locale.US);
        ((DecimalFormat)df).applyPattern("0.00");
        double numeroFormatar = Double.parseDouble(valor);
        
        String numero = df.format(numeroFormatar);
        valor = numero;
    }
    
    public void listar(){
        JogoService service = new JogoService();
        jogos = service.listar();
    }
    
    public String novo(){
        jogo = new Jogo();
        return "new.xhtml?faces-redirect=true";
    }
    
    public String inserir(){
        JogoService service = new JogoService();
        if (service.inserir(jogo)){
            UtilMessages.messageInfo("Jogo cadastrada com sucesso!");
            this.listar();
            return "list.xhtml?faces-redirect=true";
        }else{
            UtilMessages.messageError("Ocorreu um erro ao cadastrar a jogo!");
            return null;
        }
    }
    
    public String alterar(){
        JogoService service = new JogoService();
        if (service.alterar(jogo)){
            UtilMessages.messageInfo("Jogo alterada com sucesso!");
            this.listar();
            return "list.xhtml?faces-redirect=true";
        }else{
            UtilMessages.messageError("Ocorreu um erro ao alterar a jogo!");
            return null;
        }
    }
    
    public String carregarDados(Jogo jogo){
        this.jogo = jogo;
        return "alter.xhtml?faces-redirect=true";
    }
    
    public String excluir(Jogo jogo){
        JogoService service = new JogoService();
        if (service.excluir(jogo)){
            UtilMessages.messageInfo("Jogo excluída com sucesso!");
            this.listar();
            return "list.xhtml?faces-redirect=true";
        }else{
            UtilMessages.messageError("Ocorreu um erro ao excluir a jogo!");
            return null;
        }
    }
    
    public String cancelar(){
        return "list.xhtml?faces-redirect=true";
    }

    public List<Jogo> getJogos() {
        return jogos;
    }

    public void setJogos(List<Jogo> jogos) {
        this.jogos = jogos;
    }

    public Jogo getJogo() {
        return jogo;
    }

    public void setJogo(Jogo jogo) {
        this.jogo = jogo;
    }
    
}
