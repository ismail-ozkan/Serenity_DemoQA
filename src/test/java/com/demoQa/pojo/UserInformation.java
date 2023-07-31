package com.demoQa.pojo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
@Getter
@Setter
@ToString
public class UserInformation {

    private String userId;
    private String username;
    private List<Book> books;
}