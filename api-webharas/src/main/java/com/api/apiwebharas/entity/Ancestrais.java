package com.api.apiwebharas.entity;

import javax.persistence.*;

@Entity
@Table(name = "ancestrais")
public class Ancestrais {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "descricao")
    private String descricao;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ancestrais_pai_id")
    private Ancestrais ancestraisPai;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ancestrais_mae_id")
    private Ancestrais ancestraisMae;
}
