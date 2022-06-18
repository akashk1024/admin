package com.greatlearning.microservice.service;

import com.greatlearning.microservice.dto.ItemEntryDto;
import com.greatlearning.microservice.entity.Items;
import com.greatlearning.microservice.entity.Orders;
import com.greatlearning.microservice.repository.OrdersRepository;
import com.greatlearning.microservice.entity.MenuItem;
import com.greatlearning.microservice.model.Invoice;
import com.greatlearning.microservice.util.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrderService {

    @Autowired
    private OrdersRepository ordersRepository;
    @Autowired
    private LoggedInUserService loggedInUserService;

    @Autowired
    private MenuItemServiceImpl menuItemService;
    @Autowired
    private ItemService itemService;

    public Orders save(Integer itemId, Integer quantity) throws Exception {

        List<Items> items = new ArrayList<>();
        items.add(Items.builder().itemId(itemId).build());
        Orders orders = Orders.builder()
                .userName(loggedInUserService.getUserName())
                .items(items)
                .amount(menuItemService.findByItemId(itemId).getPrice() * quantity)
                .build();
        for(Items item : items){
            item.setOrders(orders);
        }
        orders = ordersRepository.save(orders);
        itemService.save(items);
        return orders;
    }

    public Orders save(List<ItemEntryDto> itemEntry) throws Exception {
        List<Items> items = new ArrayList<>();
        double amount = 0;
        for (ItemEntryDto itemEntryDto : itemEntry) {
            Items newItems = Items.builder().itemId(itemEntryDto.getItemId()).build();
            items.add(newItems);
            amount = amount + menuItemService.findByItemId(itemEntryDto.getItemId()).getPrice() * itemEntryDto.getQuantity();
        }
        log.info("item" + items);
        Orders orders = Orders.builder()
                .userName(loggedInUserService.getUserName())
                .items(items)
                .amount(amount)
                .build();
        orders = ordersRepository.save(orders);
        return orders;
    }

    public List<Orders> findAll() {
        return ordersRepository.findAll();
    }

    public Orders findByOrderId(Long orderId) throws Exception {
        Optional<Orders> orders = ordersRepository.findById(orderId);
        if (orders.isPresent()) {
           return orders.get();
        } else
            throw new Exception("User not present");
    }

    public void deleteOrder(long orderId) {
        ordersRepository.deleteById(orderId);
    }

    public String String(Long orderId) throws Exception {
        Optional<Orders> orders = ordersRepository.findById(orderId);
        if (orders.isPresent()) {
            ordersRepository.delete(orders.get());
        } else
            throw new Exception("Order not found");
        return orderId + " deleted successfully";
    }

    public List<Invoice> showTopDayBills() {
        return ordersRepository.findAll().stream()
                .filter(order -> order.getOrderDate().after(CommonUtils.removeTime(new Date())))
                .map(o -> new Invoice(o.getOrderId(), o.getItems().toString(), o.getAmount())).collect(Collectors.toList());


    }

    public double showCurrentMonthSales() {
        return ordersRepository.findAll().stream()
                .filter(order -> order.getOrderDate().after(CommonUtils.firstDateOfMonth()))
                .mapToDouble(Orders::getAmount).sum();
    }


    public Orders getOrderById(long orderId) {
        return ordersRepository.findById(orderId).orElse(null);
    }

    public List<Orders> getAllOrders() {
        return ordersRepository.findAll();
    }

    public Orders updateOrder(Long orderId, int itemId, int newQuantity) {
        Orders order = ordersRepository.getById(orderId);
        List<Items> items = order.getItems();
        double updatedAmount = getUpdatedAmount(newQuantity, order, items, itemId);
        List<Items> newItems = getUpdateItems(newQuantity, items, itemId, order);
        order.setItems(newItems);
        order.setAmount(updatedAmount);
        return ordersRepository.saveAndFlush(order);
    }

    private List<Items> getUpdateItems(int newQuantity, List<Items> items, int itemId, Orders orders) {

        boolean notFound = true;
        Items newItem = Items.builder().itemId(itemId).orders(orders)
                .quantity(newQuantity).build();

        items.stream().forEach(item -> {
            if(item.getItemId() == itemId){
                item.setQuantity(newQuantity);
                item.setOrders(orders);
            }
        });

        if(notFound) {
            items.add(newItem) ;
        }

        return null;
    }

    public double getByOrderId(long orderId) {
        Orders orders = ordersRepository.getById(orderId);
        return orders.getAmount();

    }

    private double getUpdatedAmount(int newQuantity, Orders orders, List<Items> items, int itemId) {
        double updatedAmount = orders.getAmount();
            for(Items entry: items) {
                if (entry.getItemId() == itemId) {
                    MenuItem menuItem = menuItemService.getMenuItemById(entry.getItemId());
                    updatedAmount = updatedAmount + menuItem.getPrice() * (newQuantity - entry.getQuantity());
                }
            }
        return updatedAmount;
    }


}
