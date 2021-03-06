package br.com.scargames.services;

import br.com.scargames.dao.UsuarioDao;
import br.com.scargames.domain.Usuario;
import br.com.scargames.util.HashMaker;
import java.util.List;

public class UsuarioService {
   
    UsuarioDao usuarioDao = new UsuarioDao();
	
    public void inicializarHibernate(){
        usuarioDao.inicializarHibernate();
    }
	
    public List<Usuario> listar(){
        return usuarioDao.lista();
    }
    
    public Usuario consultar(Integer id ){
        return usuarioDao.consulta(id);
    }
    
    public Boolean inserir(Usuario usuario){
        usuario.setSenha(HashMaker.stringHexa(HashMaker.gerarHash(usuario.getSenha())));
        return usuarioDao.inserir(usuario);
    }
    
    public Boolean alterar(Usuario usuario){
        usuario.setSenha(HashMaker.stringHexa(HashMaker.gerarHash(usuario.getSenha())));
        return usuarioDao.alterar(usuario);
    }
    
    public Boolean excluir(Usuario usuario){
        return usuarioDao.excluir(usuario);
    }
    
    public Boolean autenticar(Usuario usuario){
        Usuario usuarioBanco = usuarioDao.consultaPorEmail(usuario.getEmail());
        if(usuarioBanco == null){
            return false;
        }else{
            String senhaCriptografada = HashMaker.stringHexa(HashMaker.gerarHash(usuario.getSenha()));
            if(!senhaCriptografada.equals(usuarioBanco.getSenha())){
                return false;
            }else{
                return true;
            }
        }
    }    
}
