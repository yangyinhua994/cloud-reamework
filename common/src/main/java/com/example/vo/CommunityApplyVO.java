package com.example.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class CommunityApplyVO extends BaseVO {
    
    private Long communityId;
    
    private Long applyUserId;
    
    private String applyContent;
    
    private Integer status;
}
