package com.cdutetc.nanpo.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cdutetc.nanpo.pojo.OrderItem;
import com.cdutetc.nanpo.pojo.OrderRequest;
import com.cdutetc.nanpo.pojo.OrderResponse;

/**
 * 第2课：店里只有服务员一个人：他既背菜谱、又验菜算账、还要顺手"炒菜“
 */
@RestController
public class WaiterController {

    private static final Map<String, Integer> MENU = new HashMap<>();
    static {
        MENU.put("回锅肉", 18);
        MENU.put("鱼香肉丝", 16);
        MENU.put("麻婆豆腐", 12);
        MENU.put("番茄炒蛋", 10);
        MENU.put("蛋炒饭", 8);
        MENU.put("米饭", 1);
    }

    @GetMapping("/menu")
    public Map<String, Integer> getMenu() {
        return MENU;
    }

    // 点单：@RequestBody 把前端提交的 JSON 自动装配成 OrderRequest 对象
    @PostMapping("/order")
    public OrderResponse order(@RequestBody OrderRequest orderRequest) {
        OrderResponse resp = new OrderResponse();
        List<OrderItem> itemList = new ArrayList<>();
        int total = 0;

        for (OrderRequest.DishCount dc : orderRequest.getDishList()) {
            String dishName = dc.getDishName();
            Integer count = dc.getCount();

            // 菜单里没有这道菜：直接抛出运行时异常。
            if (!MENU.containsKey(dishName)) {
                throw new RuntimeException("菜品不存在：" + dishName);
            }
            int unitPrice = MENU.get(dishName);
            int subTotal = unitPrice * count;
            total += subTotal;

            OrderItem item = new OrderItem();
            item.setDishName(dishName);
            item.setCount(count);
            item.setUnitPrice(unitPrice);
            item.setSubTotal(subTotal);
            itemList.add(item);

            System.out.printf("[服务员] 炒好了 %s x %d%n", dishName, count);
        }

        resp.setItemList(itemList);
        resp.setTotalMoney(total);
        resp.setMsg("下单成功，请到取餐口稍等～");
        return resp;
    }
}
