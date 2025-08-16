package com.example.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

@Data
@Builder
@ToString
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("community_apply")
public class CommunityApply extends BaseEntity {
    
    private Long communityId;
    
    private Long applyUserId;
    
    private String applyContent;
    
    private Integer status = 1;
}
