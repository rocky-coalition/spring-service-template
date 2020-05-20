package com.coalition.sample.database.model;


import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "sample_data")
public class SampleData {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String data;

}