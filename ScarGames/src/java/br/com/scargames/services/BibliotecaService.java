package br.com.scargames.services;

import br.com.scargames.dao.BibliotecaDao;
import br.com.scargames.domain.Biblioteca;

import java.util.List;

public class BibliotecaService {
     BibliotecaDao bibliotecaDao = new BibliotecaDao();
    
    public List<Biblioteca> listar(){
        return bibliotecaDao.lista();
    }
    
    public List<Biblioteca> listaPorUsuario(Integer id){
        return bibliotecaDao.listaPorUsuario(id);
    }
    
    public Biblioteca consultar(Integer id ){
        return bibliotecaDao.consulta(id);
    }
    
    public Boolean inserir(Biblioteca biblioteca){
        return bibliotecaDao.inserir(biblioteca);
    }
    
    public Boolean alterar(Biblioteca biblioteca){
        return bibliotecaDao.alterar(biblioteca);
    }
    
    public Boolean excluir(Biblioteca biblioteca){
        return bibliotecaDao.excluir(biblioteca);
    }
    
   
}
