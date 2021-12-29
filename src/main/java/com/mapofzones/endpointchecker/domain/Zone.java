package com.mapofzones.endpointchecker.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "zones", schema = "public")
public class Zone {
    @Id
    @Column(name = "chain_id")
    @NonNull
    private String chainId;

}
