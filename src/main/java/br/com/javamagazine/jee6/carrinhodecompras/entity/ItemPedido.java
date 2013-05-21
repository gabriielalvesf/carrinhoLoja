/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.javamagazine.jee6.carrinhodecompras.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

/**
 *
 * @author User
 */
@Embeddable
public class ItemPedido implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @ManyToOne
    @JoinColumn(name = "id_produto")
    private Produto produto;
    
    @Column(name = "preco_unitario")
    @NotNull
    private Double precoUnitario;
    
    @NotNull
    private Integer quantidade;
    
    @Column(name = "preco_total")
    @NotNull
    private Double precoTotal;
    
    public ItemPedido() {}
    
    public ItemPedido(Produto produto) {
        this.produto = produto;
    }
    
    public ItemPedido(Produto produto, Integer quantidade) {
        this.produto = produto;
        this.precoUnitario = produto.getPreco();
        this.quantidade = quantidade;
        calcularTotal();
    }
    
    public void calcularTotal() {
        precoTotal = precoUnitario * quantidade;
    }
    
    public void atualizarQuantidade(Integer novaQuantidade) {
        this.quantidade = novaQuantidade;
        calcularTotal();
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Double getPrecoUnitario() {
        return precoUnitario;
    }

    public void setPrecoUnitario(Double precoUnitario) {
        this.precoUnitario = precoUnitario;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public Double getPrecoTotal() {
        return precoTotal;
    }

    public void setPrecoTotal(Double precoTotal) {
        this.precoTotal = precoTotal;
    }
    
    public int compareTo(ItemPedido o) {
        return produto.getTitulo().compareTo(o.getProduto().getTitulo());
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + (this.produto != null ? this.produto.hashCode() : 0);
        hash = 79 * hash + (this.precoUnitario != null ? this.precoUnitario.hashCode() : 0);
        hash = 79 * hash + (this.precoTotal != null ? this.precoTotal.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ItemPedido other = (ItemPedido) obj;
        if (this.produto != other.produto && (this.produto == null || !this.produto.equals(other.produto))) {
            return false;
        }
        if (this.precoUnitario != other.precoUnitario && (this.precoUnitario == null || !this.precoUnitario.equals(other.precoUnitario))) {
            return false;
        }
        if (this.quantidade != other.quantidade && (this.quantidade == null || !this.quantidade.equals(other.quantidade))) {
            return false;
        }
        if (this.precoTotal != other.precoTotal && (this.precoTotal == null || !this.precoTotal.equals(other.precoTotal))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ItemPedido{" + "produto=" + produto + ", precoUnitario=" + precoUnitario + ", quantidade=" + quantidade + ", precoTotal=" + precoTotal + '}';
    }
    
    
}
