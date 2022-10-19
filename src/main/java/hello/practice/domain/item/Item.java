package hello.practice.domain.item;


import lombok.Data;

@Data
public class Item {

    private Long id;

    private String writerId;
    private String itemName;
    private Integer price;
    private Integer quantity;

    private DeliveryCode deliveryCode;
    private Boolean pub;

    public Item(String itemName,String writerId, Integer price, Integer quantity, DeliveryCode deliveryCode, Boolean pub) {
        this.itemName = itemName;
        this.writerId = writerId;
        this.price = price;
        this.quantity = quantity;
        this.deliveryCode = deliveryCode;
        this.pub = pub;
    }

    public Item() {
    }
}
