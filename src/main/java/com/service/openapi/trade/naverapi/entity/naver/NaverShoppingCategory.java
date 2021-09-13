package com.service.openapi.trade.naverapi.entity.naver;

import java.io.Serializable;

import javax.persistence.*;
import com.service.openapi.trade.naverapi.entity.EntityBaseAudit;
import lombok.*;

@Entity
@Table(name = "naver_shopping_category")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class NaverShoppingCategory extends EntityBaseAudit implements Serializable {
    @Id
    @Column(name = "code", length = 20, nullable = false, columnDefinition = "varchar(20) default '' comment '카테고리 코드'")
    private String code;

    @Column(name = "category_1", length = 128, nullable = false, columnDefinition = "varchar(128) default '' comment '1차 카테고리 정보'")
    private String category1;

    @Column(name = "category_2", length = 128, columnDefinition = "varchar(128) comment '2차 카테고리 정보'")
    private String category2;

    @Column(name = "category_3", length = 128, columnDefinition = "varchar(128) comment '3차 카테고리 정보'")
    private String category3;

    @Column(name = "category_4", length = 128, columnDefinition = "varchar(128) comment '4차 카테고리 정보'")
    private String category4;

    @Builder
    private NaverShoppingCategory(final String code, final String category1, final String category2, final String category3,
                                  final String category4) {
        this.code = code;
        this.category1 = category1;
        this.category2 = category2;
        this.category3 = category3;
        this.category4 = category4;
    }
}
