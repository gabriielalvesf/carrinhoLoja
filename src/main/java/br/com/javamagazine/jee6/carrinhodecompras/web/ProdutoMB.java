/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.javamagazine.jee6.carrinhodecompras.web;

import br.com.javamagazine.jee6.carrinhodecompras.entity.Produto;
import br.com.javamagazine.jee6.carrinhodecompras.services.ProdutoServices;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author User
 */
@Named
@RequestScoped
public class ProdutoMB {
    
    @Inject
    private ProdutoServices produtoServices;
    
    private List<Produto> produtos;
    
    @PostConstruct
    public void init() {
        produtos = produtoServices.findAll();
    }
    
    public List<Produto> getProdutos() {
        return produtos;
    }
    
}
