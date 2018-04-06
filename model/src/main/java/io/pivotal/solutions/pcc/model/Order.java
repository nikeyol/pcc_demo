package io.pivotal.solutions.pcc.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;


@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Data
@Entity(name = "order")
@Table(name = "my_order")
public class Order implements Serializable {
    @Id
    private String id;

    private String orderDescription;
}
