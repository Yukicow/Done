package hello.practice.domain.item.repository;

import hello.practice.domain.item.Item;

import java.util.List;

public interface ItemRepository {

    public Item save(Item item);

    public Item findById(Long id);

    public List<Item> findAll();

    public List<Item> findAllPub();
    public void clear();


}
