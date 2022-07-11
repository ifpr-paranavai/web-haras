package com.api.apiwebharas.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "imagem")
public class Imagem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "url_s3")
    private String urlS3;

    @ManyToOne
    @JoinColumn(name = "cavalo")
    private Cavalo cavalo;
}
