package app.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name= "product_list", uniqueConstraints={@UniqueConstraint(columnNames = {"product_id", "order_id"})})
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductList {
    
    public ProductList(int numberOfProducts, BigDecimal totalPrice) {
   	 this.numberOfProducts = numberOfProducts;
   	 this.totalPrice = totalPrice;
    }
    
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "product_list_id", nullable = false)
    private Integer productListId;
    
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Column(name = "number_of_products")
    private Integer numberOfProducts;
    
    @Column(name = "total_price")
    private BigDecimal totalPrice;
    
}
