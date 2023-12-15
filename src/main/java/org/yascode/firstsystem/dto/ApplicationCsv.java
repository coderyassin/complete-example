package org.yascode.firstsystem.dto;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApplicationCsv implements Serializable {
    private Long id;
    private String code;
    private String name;

}
