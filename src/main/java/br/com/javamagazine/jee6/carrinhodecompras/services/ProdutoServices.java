/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.javamagazine.jee6.carrinhodecompras.services;

import br.com.javamagazine.jee6.carrinhodecompras.entity.Produto;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author User
 */

@Stateless
public class ProdutoServices {
    
    @PersistenceContext
    private EntityManager em;
    
    public List<Produto> findAll() {
        return em.createQuery("select p from Produto p order by p.titulo").getResultList();
    }
    
}
