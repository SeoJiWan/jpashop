package jpabook.jpashop.repository;

import jpabook.jpashop.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em;


    public void save(Item item) {
        if (item.getId() == null) { // item 은 JPA 에 저장하기 전까지 id 값이 없다.
            em.persist(item);
        }
        else {
            em.merge(item); // merge --> 업데이트로 이해..
        }
    }

    public Item findOne(Long id) {
        return em.find(Item.class, id);
    }

    public List<Item> findAll() { // 단 건은 em 메서드를 사용하나 전체 조회는 JPQL 작성
        return em.createQuery("select i from Item i", Item.class)
                .getResultList();
    }
}
