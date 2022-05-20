package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Delivery {

    @Id
    @GeneratedValue
    @Column(name = "deliver_id")
    private Long id;

    @OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY)
    private Order order;

    @Embedded // 내장 타입
    private Address address;

    @Enumerated(EnumType.STRING) // EnumType.ORDINAL --> 칼럼이 숫자로 들어감 --> 나중에 상태가 추가되면 숫자가 밀려서 망함. --> 무조건 STRING
    private DeliveryStatus deliveryStatus; // READY, COMP
}
