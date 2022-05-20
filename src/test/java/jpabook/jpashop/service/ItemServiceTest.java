package jpabook.jpashop.service;

import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest // @RunWith, @SpringBootTest ~ 스프링이랑 인티그레이션해서 스프링부트를 올려서 테스트 가능., junit5 는 필요 X
@Transactional // 테스트 후 rollback --> 테스트라서 db 에 데이터 안 남게 하기 위함.
public class ItemServiceTest {

    @Autowired ItemService itemService;
    @Autowired ItemRepository itemRepository;

    @Test
    public void 아이템_등록(){
        // given
        Item item = new Item();
        item.addStock(3);

        // when
        int stockQuantity = item.getStockQuantity();
        Long id = item.getId();
        System.out.println("id = " + id);

        // then
        assertEquals(stockQuantity, 3);
    }
}