package com.example.entity;

import lombok.*;

@Data
@Builder
@ToString
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class CommunityApply extends BaseEntity {
    
    private Long communityId;
    
    private Long applyUserId;
    
    private String applyContent;
    
    private Integer status = 1;
}
