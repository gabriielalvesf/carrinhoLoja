/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.javamagazine.jee6.carrinhodecompras.services;

import br.com.javamagazine.jee6.carrinhodecompras.entity.Cliente;
import br.com.javamagazine.jee6.carrinhodecompras.exception.ClienteExistenteException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

/**
 *
 * @author User
 */

@Stateless
public class ClienteServices {
    
    @PersistenceContext
    private EntityManager em;
    
    public Cliente findByEmailAndSenha(String email, String senha) {
        Query query = em.createQuery("select c from Cliente c where c.email = :email"
                + "and c.senha = :senha");
        query.setParameter("email", email);
        query.setParameter("senha", senha);
        try {
            return (Cliente) query.getSingleResult();
        } catch(NoResultException e) {
            return null;
        }
    }
    
    public Cliente adicionar(Cliente cliente) throws ClienteExistenteException {
        try {
            em.persist(cliente);
            return cliente;
        } catch(PersistenceException e) {
            throw new ClienteExistenteException();
        }
    }
}
