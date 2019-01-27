package com.sfs.pbserver.entity;

import lombok.Data;
import javax.persistence.*;

@Data
@Entity
public class BarBrowseHistory extends AbstracEntity{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    private Bar bar;

    @OneToOne
    private User user;
}
