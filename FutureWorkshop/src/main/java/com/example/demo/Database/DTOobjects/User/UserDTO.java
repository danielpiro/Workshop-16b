package com.example.demo.Database.DTOobjects.User;

import com.example.demo.ShoppingCart.ShoppingCart;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "user")
//no need to save guests!
public class UserDTO {

    @Id
    private String name;

    private String password;

    @Column(name = "logged_in")
    private boolean loggedIn = false;



    @Override
    public int hashCode() {
        return Objects.hash(name, password, loggedIn);
    }
}
