package hello.practice.domain.item.repository;

import hello.practice.domain.item.Item;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository
public class ItemRepositoryImpl implements ItemRepository{

    private final Map<Long, Item> store = new ConcurrentHashMap<>();
    private static long sequence = 0L;


    @Override
    public Item save(Item item) {
        item.setId(++sequence);
        store.put(sequence, item);
        return item;
    }

    @Override
    public Item findById(Long id) {
        return store.get(id);
    }

    @Override
    public List<Item> findAll(){
        return new ArrayList<>(store.values());
    }


    @Override
    public List<Item> findAllPub() {
        return new ArrayList<>(store.values()
                .stream()
                .filter(item -> item.getPub())
                .collect(Collectors.toList()));
    }

    @Override
    public void clear() {
        store.clear();
    }
}
