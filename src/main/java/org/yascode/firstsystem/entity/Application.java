package org.yascode.firstsystem.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Table(name = "applications")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Application implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Application { ");
        stringBuilder.append("id : " + this.id +", " );
        stringBuilder.append("code : " + this.code +", " );
        stringBuilder.append("name : " + this.name);
        stringBuilder.append(" }");
        return stringBuilder.toString();
    }

}
