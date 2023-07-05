package com.tenminute.interview_feed.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

// 해시태그 엔티티 (한 게시글에 여러 해시태그 달 수 있음)

@Entity
@Getter
@Setter
@Table(name = "hashtag")
@NoArgsConstructor
public class Hashtag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // 해시태그 명
    @Column(nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "hashtag")
    private List<TagPostTable> tagPostTableList = new ArrayList<>();

    public Hashtag(String name){
        this.name = name;
    }
}
