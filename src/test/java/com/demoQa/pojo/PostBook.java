package com.demoQa.pojo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class PostBook {

    private String userId;
    private List<Isbn> collectionOfIsbns;

}