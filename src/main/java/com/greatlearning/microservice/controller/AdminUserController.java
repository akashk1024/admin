package com.greatlearning.microservice.controller;

import com.greatlearning.microservice.dto.UserDto;
import com.greatlearning.microservice.entity.Users;
import com.greatlearning.microservice.model.Invoice;
import com.greatlearning.microservice.service.OrderService;
import com.greatlearning.microservice.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@Secured("ADMIN")
public class AdminUserController {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private OrderService orderService;

    @GetMapping("/userlist")
    public ResponseEntity<List<Users>> findAll() {
        List<Users> registeredUserList = userService.findAll();
        return new ResponseEntity<>(registeredUserList, HttpStatus.OK);
    }

    @PostMapping("/registerUserUsingJson")
    public ResponseEntity<Users> register(@RequestBody UserDto userDto) {
        Users savedUser = userService.save(userDto);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    @PostMapping("/registerUserUsingForm")
    public ResponseEntity<Users> registerUsingForm(@RequestParam String userName,
                                                   @RequestParam String password,
                                                   @RequestParam boolean enabled,
                                                   @RequestParam String authority) {

        UserDto userDto = ControllerUtils.buildUserDto(userName, password, enabled, authority);
        Users savedUser = userService.save(userDto);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    @PutMapping("/updateUser")
    public ResponseEntity<UserDto> updateUser(@RequestParam String userName,
                                                   @RequestParam(required = false) String password,
                                                   @RequestParam(required = false) boolean enabled,
                                                   @RequestParam(required = false) String authority) throws Exception {

        UserDto userDto = ControllerUtils.buildUserDto(userName, password, enabled, authority);
        UserDto updatedDto = userService.updateUser(userDto);
        return new ResponseEntity<>(updatedDto, HttpStatus.CREATED);
    }

    @DeleteMapping("/deleteUser")
    public ResponseEntity<String> deleteUser(@RequestParam String userName) {
        boolean isDeleted = userService.delete(userName);
        if(isDeleted){
            return new ResponseEntity<>(userName + " deleted successfully", HttpStatus.ACCEPTED);
        }else {
            return new ResponseEntity<>(userName + " not found", HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping("fetch_topday_bills")
    public List<Invoice> getTodaysBills() {
        return orderService.showTopDayBills();
    }

    @GetMapping("fetch_current_month_sales")
    public String getCurrentMonthSales() {
        double sales = orderService.showCurrentMonthSales();
        return "Current sale for this month is Rs. " + String.valueOf(sales) + " !";
    }

}
