package com.if5.todolist.models.dtos.responses.systeme;

import com.if5.todolist.models.entities.systeme.ModeleImpression;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Copyright (c) 2024, Iforce5, All Right Reserved.
 * https://iforce5.com
 * <p>
 * When: @created 02/March/2024 -- 4:41 PM
 *
 * @author name :  Serges KAMGA on 3/2/2024
 * @author email : kamgakamga93@gmail.com /  serge.kamga@iforce5.com
 * Project : @project todolist
 * Package : @package com.if5.todolist.models.dtos.responses.systeme
 **/
@Data
@Builder
public class ShortModeleImpressionDTO {
    private Long id;
    private Long idEtat;
    private String nomModele;
    private Long idTransactionImprimable;
    private int modeleDefaut; //  <0=Non, 1=Oui>

    public static ShortModeleImpressionDTO buildFromEntity(ModeleImpression entity) {
        return ShortModeleImpressionDTO.builder()
                .id(entity.getId())
                .idEtat(entity.getEtatImprimable().getId())
                .nomModele(entity.getNomModele())
                .idTransactionImprimable(entity.getIdTransactionImprimable())
                .modeleDefaut(entity.getModeleDefaut())
                .build();
    }

    public static List<ShortModeleImpressionDTO> buildFromEntityList(List<ModeleImpression> entityList) {
        return entityList.stream().map(ShortModeleImpressionDTO::buildFromEntity).collect(Collectors.toList());

    }
}
