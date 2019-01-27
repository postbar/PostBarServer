package com.sfs.pbserver.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import javax.persistence.*;
@Data
@Entity
public class UserFocus extends AbstracEntity{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @JsonProperty(value = "user_from")
    @OneToOne
    private User userFrom;

    @JsonProperty(value = "user_to")
    @OneToOne
    private User userTo;
}
