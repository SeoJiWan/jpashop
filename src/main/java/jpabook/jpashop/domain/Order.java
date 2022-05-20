package jpabook.jpashop.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.tomcat.jni.Local;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders") // orders 로 네이밍 수정. --> 아니면 order 로 들어감
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // == protected Order() {} --> createOrder() 생성메서드 사용을 위한 new 로 생성 막기
public class Order {

    @Id @GeneratedValue
    @Column(name = "order_id") // order 의 id 로 맞춤. 다른이유는 나중에 설명
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) // Order 랑 Member 랑 다:1 관계
    @JoinColumn(name = "member_id") // 매핑을 뭘로 할거냐.. --> Fk = "member_id"
    private Member member;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL) // CascadeType.ALL --> Order 가 persist 될 때 orderItems 도 persist 강제로 날려줌.
    private List<OrderItems> orderItems = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    private LocalDateTime orderDate; // 주문시간

    @Enumerated(EnumType.STRING)
    private OrderStatus status; // 주문상태 [ORDER, CANCEL]


    // == 연관관계 메서드 == //
    public void setMember(Member member) {
        this.member = member;
        member.getOrders().add(this);
    }

    public void addOrderItem(OrderItems orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this);
    }


    // == 생성 메서드 == //
    public static Order createOrder(Member member, Delivery delivery, OrderItems... orderItems) {
        Order order = new Order();
        order.setMember(member);
        order.setDelivery(delivery);
        for (OrderItems orderItem : orderItems) {
            order.addOrderItem(orderItem);
        }
        order.setStatus(OrderStatus.ORDER);
        order.setOrderDate(LocalDateTime.now());
        return order;
        // --> order 와 연관관계가 있는 것들(상태, 주문시간 등) 세팅
        // --> 이런 스타일 작성 --> 생성하는 지점 있으면 여기만 수정
    }

    // == 비즈니스 로직 == //
    /**
     * 주문 취소
     */
    public void cancel() {
        if (delivery.getDeliveryStatus() == DeliveryStatus.COMP) {
            throw new IllegalStateException("이미 배송완료된 상품은 취소가 불가능합니다.");
        }

        this.setStatus(OrderStatus.CANCEL);
        for (OrderItems orderItem : orderItems) {
            orderItem.cancel();
        }

    }

    // == 조회 로직 == //
    /**
     * 전체 주문 가격 조회
     */
    public int getTotalPrice() {
        int totalPrice = 0;
        for (OrderItems orderItem : orderItems) {
            totalPrice += orderItem.getTotalPrice(); // 주문 시 : 수량 * 가격
        }
        return totalPrice;
    }


}
