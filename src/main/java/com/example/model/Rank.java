package com.example.model;

import jakarta.persistence.*;

@Entity
@Table(name = "ranks")
public class Rank {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rank_id")  // Используем rank_id вместо id
    private Long rankId;

    @Column(nullable = false, unique = true)
    private String rank;

    // Конструктор без параметров (требуется Hibernate)
    public Rank() {
    }

    // Новый конструктор с Long
    public Rank(Long rankId) {
        this.rankId = rankId;
    }

    // Геттеры и сеттеры
    public Long getRankId() {
        return rankId;
    }

    public void setRankId(Long rankId) {
        this.rankId = rankId;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    @Override
    public String toString() {
        return "Rank{rankId=" + rankId + ", rank='" + rank + "'}";
    }
}
