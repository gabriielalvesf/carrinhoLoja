/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.javamagazine.jee6.carrinhodecompras.web;

import br.com.javamagazine.jee6.carrinhodecompras.entity.Cliente;
import br.com.javamagazine.jee6.carrinhodecompras.entity.ItemPedido;
import br.com.javamagazine.jee6.carrinhodecompras.entity.Pedido;
import br.com.javamagazine.jee6.carrinhodecompras.entity.Produto;
import br.com.javamagazine.jee6.carrinhodecompras.exception.ClienteExistenteException;
import br.com.javamagazine.jee6.carrinhodecompras.exception.ClienteNaoEncontradoException;
import br.com.javamagazine.jee6.carrinhodecompras.services.ClienteServices;
import br.com.javamagazine.jee6.carrinhodecompras.services.PedidoServices;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author User
 */

@Named
@SessionScoped
public class CarrinhoDeComprasMB implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private Pedido pedidoCarrinho;
    private Long idPedidoGerado;
    private Cliente cliente;
    private Produto produtoRemover;
    
    @Inject
    private transient ClienteServices clienteServices;
    
    @Inject
    private transient PedidoServices pedidoServices;
    
    @Inject
    private transient UtilsMB utilsMB;
    
    @PostConstruct
    public void init() {
        pedidoCarrinho = new Pedido();
        cliente = new Cliente();
    }
    
    public String adicionarItem(Produto produto) {
        pedidoCarrinho.adicionarItem(produto, 1);
        return "carrinho?faces-redirect=true";
    }
    
    public void removerItem() {
        pedidoCarrinho.removerItem(produtoRemover);
    }
    
    public void atualizarQuantidadeItem(Produto produto, Integer novaQuantidade) {
        pedidoCarrinho.atualizaQuantidade(produto, novaQuantidade);
    }
    
    public String fecharPedidoUsuarioExistente() {
        return fecharPedido();
    }
    
    public String fecharPedidoNovoUsuario() {
        try {
            cliente = clienteServices.adicionar(cliente);
        }  catch(ClienteExistenteException e) {
            adicionarMensagem(FacesMessage.SEVERITY_ERROR, "cliente-existente");
            return null;
        }
        return fecharPedido();
    }
    
    private String fecharPedido() {
        try {
            pedidoCarrinho = pedidoServices.criarPedido(pedidoCarrinho, cliente.getEmail(), cliente.getSenha());
            idPedidoGerado = pedidoCarrinho.getId();
            init();
            return "pedidoFechado?faces-redirect=true";
        } catch(ClienteNaoEncontradoException e) {
            adicionarMensagem(FacesMessage.SEVERITY_ERROR, "cliente não encontrado");
            return null;
        }
    }
    
    private void adicionarMensagem(FacesMessage.Severity severidade, String chave) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        facesContext.addMessage(null, new FacesMessage(severidade, utilsMB.getMessage(chave), null));
    }
    
    public void recalcularTotal(ItemPedido itemPedido) {
        itemPedido.calcularTotal();
        pedidoCarrinho.calcularTotal();
    }
    
    public boolean temItens() {
        return pedidoCarrinho.getItens().size() > 0;
    }

    public Pedido getPedidoCarrinho() {
        return pedidoCarrinho;
    }

    public void setPedidoCarrinho(Pedido pedidoCarrinho) {
        this.pedidoCarrinho = pedidoCarrinho;
    }

    public Long getIdPedidoGerado() {
        return idPedidoGerado;
    }

    public void setIdPedidoGerado(Long idPedidoGerado) {
        this.idPedidoGerado = idPedidoGerado;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Produto getProdutoRemover() {
        return produtoRemover;
    }

    public void setProdutoRemover(Produto produtoRemover) {
        this.produtoRemover = produtoRemover;
    }

    public ClienteServices getClienteServices() {
        return clienteServices;
    }

    public void setClienteServices(ClienteServices clienteServices) {
        this.clienteServices = clienteServices;
    }

    public PedidoServices getPedidoServices() {
        return pedidoServices;
    }

    public void setPedidoServices(PedidoServices pedidoServices) {
        this.pedidoServices = pedidoServices;
    }

    public UtilsMB getUtilsMB() {
        return utilsMB;
    }

    public void setUtilsMB(UtilsMB utilsMB) {
        this.utilsMB = utilsMB;
    }
    
    
}
