/*
created by sknobla
finalized by suerding
 */
package com.example.playfit.dao;

import com.example.playfit.dto.ProductDTO;
import com.example.playfit.dto.UserDTO;

import java.util.ArrayList;
import java.util.List;

public class ProductDAOimpl implements ProductDAO {


    private static List<ProductDTO> products = new ArrayList<>();


    static {

        ProductDTO product = new ProductDTO();
        //Add User
       product.setProductID(1);
       product.setProductName("Banane");
       product.setLocation("Bonn");
       product.setPointRate(5);

        products.add(product);

    }

    @Override
    public List<ProductDTO> list() {
        return products;
    }
}
