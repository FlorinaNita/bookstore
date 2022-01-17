package sda.store.entities;

import javax.persistence.*;

@Entity
@Table(name = "carts")
public class CartEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cartId;

    private Integer userId;
    private Integer bookId;
    private Integer quantity;


    @ManyToOne
    @JoinColumn(name = "bookId", insertable = false, updatable = false)
    private BookEntity books;


    @ManyToOne
    @JoinColumn(name = "userId", insertable = false, updatable = false)
    private UserEntity userEntity;

    public double getTotal() {
        return getQuantity() * books.getPrice();
    }

    public BookEntity getBooks() {
        return books;
    }

    public void setBooks(BookEntity books) {
        this.books = books;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public Integer getCartId() {
        return cartId;
    }

    public void setCartId(Integer cartId) {
        this.cartId = cartId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

}

