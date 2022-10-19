package hello.practice.domain.item.service;


import hello.practice.domain.item.Item;
import hello.practice.domain.item.form.UpdateForm;
import hello.practice.domain.item.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService{

    private final ItemRepository itemRepository;


    public Item save(Item item){
        return itemRepository.save(item);
    }


    public Item findById(Long id) {
        return itemRepository.findById(id);
    }


    public List<Item> findAll() {
        return itemRepository.findAll();
    }


    public List<Item> findAllPub() {
        return itemRepository.findAllPub();
    }


    public Item updateItem(Long itemId, UpdateForm updateParam){
        Item item = updateAndGet(itemId, updateParam);
        return item;
    }

    private Item updateAndGet(Long itemId, UpdateForm updateParam) {
        Item findItem = itemRepository.findById(itemId);
        findItem.setItemName(updateParam.getItemName());
        findItem.setPrice(updateParam.getPrice());
        findItem.setQuantity(updateParam.getQuantity());
        findItem.setDeliveryCode(updateParam.getDeliveryCode());
        findItem.setPub(updateParam.getPub());
        return findItem;
    }

}
