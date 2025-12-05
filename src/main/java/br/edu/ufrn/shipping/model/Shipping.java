package br.edu.ufrn.shipping.model;

import java.time.Instant;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "shippings")
public class Shipping {

    @Id
    private String id;

    @Indexed
    private String orderId;
    
    private String address;

    @Indexed(direction = IndexDirection.DESCENDING)
    private Instant createdAt;

    public Shipping(String orderId, String address) {
        this.orderId = orderId;
        this.address = address;
        this.createdAt = Instant.now();
    }

    public String getId() {
        return id;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getAddress() {
        return address;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }
    
}
