package jpabook.jpashop.domain.item;

import jpabook.jpashop.domain.Category;
import jpabook.jpashop.exception.NotEnoughStockException;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) // 상속관계 전략 --> 부모클래스에서 설정
@DiscriminatorColumn(name = "dype") // 싱글 테이블이라 저장할 때 구분이 되어야함 --> 구분자 만드는 것 (자식클래스에 @DiscriminatorValue 로 네이밍)
@Getter @Setter
public class Item {

    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    @ManyToMany(mappedBy = "items") // 연관된 FK 필드명
    private List<Category> categories = new ArrayList<>();


    // == 비즈니스 로직 == // --> 간단한 로직은 엔티티에 추가. --> 응집도 상승, 관리편함
    // stock 증가
    public void addStock(int quantity) {
        this.stockQuantity += quantity;
    }

    public void removeStock(int quantity) {
        int restStock = this.stockQuantity - quantity;
        if (restStock < 0) {
            throw new NotEnoughStockException("need more stock");
        }
        this.stockQuantity = restStock;
    }
}
