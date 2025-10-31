/*
 * Copyright (C) 2016 Pivotal Software, Inc.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package edu.eci.arsw.myrestaurant.restcontrollers;

import edu.eci.arsw.myrestaurant.model.Order;
import edu.eci.arsw.myrestaurant.model.ProductType;
import edu.eci.arsw.myrestaurant.model.RestaurantProduct;
import edu.eci.arsw.myrestaurant.services.OrderServicesException;
import edu.eci.arsw.myrestaurant.services.RestaurantOrderServicesStub;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author hcadavid
 */
@RestController("/orders")
public class OrdersAPIController {

    @Autowired
    private RestaurantOrderServicesStub restaurantOrderServicesStub;

    public OrdersAPIController() {
        restaurantOrderServicesStub = new RestaurantOrderServicesStub();
    }

    @PostMapping("")
    public void addOrderToTable(@RequestBody Order order) {
        try {
            restaurantOrderServicesStub.addNewOrderToTable(order);
        }catch (OrderServicesException e){
            HttpStatus.valueOf(400);
            System.out.println(e.getMessage());
        }
    }

    @GetMapping("")
    public Set<Integer> getAllTableWithOrders() {
        return restaurantOrderServicesStub.getTablesWithOrders();
    }

    @GetMapping("/{id}")
    public Order getTableOrderById(@PathVariable int id) {
        return restaurantOrderServicesStub.getTableOrder(id);
    }

    @GetMapping("/products")
    public Set<String> getAvailableProducts(){
        return restaurantOrderServicesStub.getAvailableProductNames();
    }

    @GetMapping("/products/{name}")
    public RestaurantProduct getAvailableProductsByBame(@PathVariable String name){
        try {
            return restaurantOrderServicesStub.getProductByName(name);
        } catch (OrderServicesException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/tables/{id}")
    public int calculateTableBill(@PathVariable int id){
        try {
            return restaurantOrderServicesStub.calculateTableBill(id);
        } catch (OrderServicesException e) {
            throw new RuntimeException(e);
        }
    }

    @DeleteMapping("/{tableNumber}")
    public void releaseTable(@PathVariable int tableNumber){
        try {
            restaurantOrderServicesStub.releaseTable(tableNumber);
        }catch (OrderServicesException e){
            HttpStatus.valueOf(400);
            System.out.println(e.getMessage());
        }
    }
}