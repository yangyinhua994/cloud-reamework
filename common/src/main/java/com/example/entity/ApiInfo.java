package com.example.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@ToString
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class ApiInfo extends BaseEntity{
    private String apiDesc;
    private String apiUrl;
    private String apiMethod;
    private String classApiDesc;
    private String interfaceApiDesc;
}
