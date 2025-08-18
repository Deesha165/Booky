package com.example.Bookify.entity.event;

import jakarta.persistence.*;
import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "tags")
public class Tag {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_id")
    private int id;


    @Column(name = "tag_name",nullable = false)
    private String name;


    @Column(name = "occurrence")
    private  int occurrence ;

    public Tag(String name,int occurrence){
        this.name=name;
        this.occurrence=occurrence;
    }

}
