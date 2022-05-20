package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;

    @Embedded // 내장 타입
    private Address address;

    @OneToMany(mappedBy = "member") // Member 랑 Order 랑 1:다 양방향 관계   // mappedBy --> order table 에 있는 member 필드에 의해 매핑
    private List<Order> orders = new ArrayList<>();
}
