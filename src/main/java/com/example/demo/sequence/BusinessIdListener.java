package com.example.demo.sequence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

import com.example.demo.entity.CartItem;
import com.example.demo.entity.Category;
import com.example.demo.entity.JWTToken;
import com.example.demo.entity.Order;
import com.example.demo.entity.OrderItem;
import com.example.demo.entity.Product;
import com.example.demo.entity.ProductImage;
import com.example.demo.entity.User;

@Component
public class BusinessIdListener extends AbstractMongoEventListener<Object> {

    @Autowired
    private SequenceGeneratorService sequenceGenerator;

    @Override
    public void onBeforeConvert(BeforeConvertEvent<Object> event) {

        Object source = event.getSource();

        if (source instanceof User user && user.getUserId() == null) {
            user.setUserId(sequenceGenerator.generateSequence(User.SEQUENCE_NAME));
        }

        else if (source instanceof Product product && product.getProductId() == null) {
            product.setProductId(sequenceGenerator.generateSequence(Product.SEQUENCE_NAME));
        }

        else if (source instanceof Category category && category.getCategoryId() == null) {
            category.setCategoryId(sequenceGenerator.generateSequence(Category.SEQUENCE_NAME));
        }

        else if (source instanceof Order order && order.getOrderId() == null) {
            order.setOrderId(sequenceGenerator.generateSequence(Order.SEQUENCE_NAME));
        }

        else if (source instanceof OrderItem item && item.getOrderItemId() == null) {
            item.setOrderItemId(sequenceGenerator.generateSequence(OrderItem.SEQUENCE_NAME));
        }

        else if (source instanceof CartItem cart && cart.getCartId() == null) {
            cart.setCartId(sequenceGenerator.generateSequence(CartItem.SEQUENCE_NAME));
        }

        else if (source instanceof ProductImage img && img.getImageId() == null) {
            img.setImageId(sequenceGenerator.generateSequence(ProductImage.SEQUENCE_NAME));
        }

        else if (source instanceof JWTToken token && token.getTokenId() == null) {
            token.setTokenId(sequenceGenerator.generateSequence(JWTToken.SEQUENCE_NAME));
        }
        
    }
}

