package com.sfs.pbserver.entity;

import lombok.Data;
import javax.persistence.*;

@Data
@Entity
public class Bar extends AbstracEntity{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 40,nullable = false)
    private String name;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(columnDefinition = "Text")
    private String info;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    private String avatar;  //头像

    public Bar() {
    }

    public Bar(Integer id) {
        this.id = id;
    }
}
