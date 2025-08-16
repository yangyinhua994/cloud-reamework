package com.example.dto;

import com.example.groups.Add;
import com.example.groups.Update;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * 小区入驻申请DTO
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@ToString
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class CommunityApplyDTO extends BaseDTO {

    /**
     * 小区ID
     */
    @NotNull(message = "小区ID不能为空", groups = {Add.class})
    private Long communityId;

    /**
     * 申请人ID
     */
    @NotNull(message = "申请人ID不能为空", groups = {Add.class})
    private Long applyUserId;

    /**
     * 申请内容
     */
    private String applyContent;

    /**
     * 申请状态(1:待审核,2:已通过,3:已拒绝)
     */
    private Integer status;
}
