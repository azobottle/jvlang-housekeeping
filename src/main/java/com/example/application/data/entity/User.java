package com.example.application.data.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

@Entity
@Table(name = "t_user", indexes = @Index(columnList = "openid"))
public class User extends AbstractEntity {
    @NotEmpty
    private String name;

    private String openid;

    private String iconURL;

    @NotEmpty
    private String phoneNumber;

    @NotEmpty
    private String password;

    @OneToMany(mappedBy = "shifu")
    private List<ShifuSchedule> shifuScheduleList;

    @OneToMany(mappedBy = "customer")
    private List<Order> ordersOfCustomer;

    @OneToMany(mappedBy = "shifu")
    private List<Order> ordersOfShifu;

    @ManyToMany
    @JoinTable(name = "t_user_role", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roleList;

    @ManyToMany
    @JoinTable(name = "t_shifu_service", joinColumns = @JoinColumn(name = "shifu_id"),
            inverseJoinColumns = @JoinColumn(name = "learnedServiceId"))
    private List<Service> learnedServiceList;
}
