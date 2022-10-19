package hello.practice.domain.item;

public enum DeliveryCode {

    QUICK("빠른 배송"), NORMAL("일반 배송"), SLOW("느린 배송");

    public String description;

    DeliveryCode(String description) {
        this.description = description;
    }
}
