/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.javamagazine.jee6.carrinhodecompras.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

/**
 *
 * @author User
 */
@Entity
public class Pedido implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Temporal(TemporalType.TIMESTAMP)
    @NotNull
    private Date data;
    
    @ManyToOne
    @JoinColumn(name = "id_cliente")
    @NotNull
    private Cliente cliente;
    
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "item_pedido", joinColumns = @JoinColumn(name = "id_pedido"))
    private Set<ItemPedido> itens;
    
    @Column(name = "total")
    @NotNull
    private Double valorTotal;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Set<ItemPedido> getItens() {
        if(itens == null) {
            itens = new HashSet<ItemPedido>();
        }
        return itens;
    }

    public void setItens(Set<ItemPedido> itens) {
        this.itens = itens;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }
    
    public List<ItemPedido> getItensOrdenadosEmLista() {
        return new ArrayList<ItemPedido>(getItens());
    }
    
    public void adicionarItem(Produto produto, Integer quantidade) {
        ItemPedido itemExistente = getItem(produto);
        if(itemExistente != null) {
            atualizaQuantidade(produto, itemExistente.getQuantidade() + quantidade);
        } else {
            getItens().add(new ItemPedido(produto, quantidade));
            calcularTotal();
        }
    }
    
    public void removerItem(Produto produto) {
        getItens().remove(new ItemPedido(produto));
        calcularTotal();
    }
    
    public ItemPedido getItem(Produto produto) {
        ItemPedido itemAProcurar = new ItemPedido(produto);
        for (ItemPedido item : getItens()) {
            if(item.equals(itemAProcurar)) {
                return item;
            }
        }
        return null;
    }
    
    public void atualizaQuantidade(Produto produto, Integer novaQuantidade) {
        ItemPedido item = getItem(produto);
        if(item == null) {
            throw new IllegalArgumentException("Item n�o encontrado para produto " + produto);
        }
        item.atualizarQuantidade(novaQuantidade);
        calcularTotal();
    }
    
    public void calcularTotal() {
        valorTotal = 0D;
        for(ItemPedido item : getItens()) {
            valorTotal += item.getPrecoTotal();
        }
    }
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pedido)) {
            return false;
        }
        Pedido other = (Pedido) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.javamagazine.jee6.carrinhodecompras.entity.Pedido[ id=" + id + " ]";
    }
    
}
