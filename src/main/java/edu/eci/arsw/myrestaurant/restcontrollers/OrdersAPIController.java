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
import edu.eci.arsw.myrestaurant.model.RestaurantProduct;
import edu.eci.arsw.myrestaurant.services.OrderServicesException;
import edu.eci.arsw.myrestaurant.services.RestaurantOrderServicesStub;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author hcadavid
 */
@RestController
@RequestMapping("/orders")
public class OrdersAPIController {

    @Autowired
    private RestaurantOrderServicesStub restaurantOrderServicesStub;

    public OrdersAPIController() {
        restaurantOrderServicesStub = new RestaurantOrderServicesStub();
    }

    @RequestMapping(value = "",method = RequestMethod.POST)
    public void addOrderToTable(@RequestBody Order order) {
        try {
            restaurantOrderServicesStub.addNewOrderToTable(order);
        }catch (OrderServicesException e){
            HttpStatus.valueOf(400);
            System.out.println(e.getMessage());
        }
    }

    @RequestMapping(value = "",method = RequestMethod.GET)
    public Set<Integer> getAllTableWithOrders() {
        return restaurantOrderServicesStub.getTablesWithOrders();
    }

    @RequestMapping(value = "/products",method = RequestMethod.GET)
    public Set<String> getAvailableProducts(){
        return restaurantOrderServicesStub.getAvailableProductNames();
    }

    @RequestMapping(value = "/products/{name}",method = RequestMethod.GET)
    public RestaurantProduct getAvailableProductsByBame(@PathVariable("name")  String name){
        try {
            return restaurantOrderServicesStub.getProductByName(name);
        } catch (OrderServicesException e) {
            throw new RuntimeException(e);
        }
    }

    @RequestMapping(value = "{id}",method = RequestMethod.GET)
    public Order getTableOrderById(@PathVariable("id") int id) {
        return restaurantOrderServicesStub.getTableOrder(id);
    }

    @RequestMapping(value = "/tables/total",method = RequestMethod.GET)
    public Set<Order> calculateTableBillTotal(){
        return restaurantOrderServicesStub.calculateTableBillTotal();
    }


    @RequestMapping(value = "/tables/{id}",method = RequestMethod.GET)
    public int calculateTableBill(@PathVariable("id")  int id){
        try {
            return restaurantOrderServicesStub.calculateTableBill(id);
        } catch (OrderServicesException e) {
            throw new RuntimeException(e);
        }
    }

    @RequestMapping(value = "/{tableNumber}",method = RequestMethod.DELETE)
    public void releaseTable(@PathVariable("tableNumber") int tableNumber){
        try {
            restaurantOrderServicesStub.releaseTable(tableNumber);
        }catch (OrderServicesException e){
            HttpStatus.valueOf(400);
            System.out.println(e.getMessage());
        }
    }
}