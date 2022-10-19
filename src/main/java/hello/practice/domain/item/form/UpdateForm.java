package hello.practice.domain.item.form;

import hello.practice.domain.item.DeliveryCode;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


@Data
public class UpdateForm {

    @NotNull(message = "상품 번호 오류입니다.")
    private Long id;

    @NotNull(message = "작성자 오류입니다.")
    private String writerId;

    @NotEmpty(message = "아이템 이름을 입력해 주세요.")
    private String itemName;

    @NotNull
    @Range(min = 1000, max = 1000000, message = "1000~1000000까지 입력 가능합니다.")
    private Integer price;

    @NotNull
    @Max(999)
    private Integer quantity;

    @NotNull(message = "배송 방식을 선택해 주세요.")
    private DeliveryCode deliveryCode;

    private Boolean pub;

}
