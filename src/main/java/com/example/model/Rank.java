package com.example.model;

import jakarta.persistence.*;

@Entity
@Table(name = "ranks")
public class Rank {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rank_id")  
    private Long rankId;

    @Column(nullable = false, unique = true)
    private String rank;

    
    public Rank() {
    }

    
    public Rank(Long rankId) {
        this.rankId = rankId;
    }

    
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
