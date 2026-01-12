package com.example.vo;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@ToString
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class ApiInfoVO extends BaseVO {
    private String apiDesc;
    private String apiUrl;
    private String apiMethod;
    private String classApiDesc;
    private String interfaceApiDesc;
}
