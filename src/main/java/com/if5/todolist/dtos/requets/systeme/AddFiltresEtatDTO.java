package com.if5.todolist.dtos.requets.systeme;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Copyright (c) 2024, Iforce5, All Right Reserved.
 * https://iforce5.com
 * <p>
 * When: @created 02/March/2024 -- 4:18 PM
 *
 * @author name :  Serges KAMGA on 3/2/2024
 * @author email : kamgakamga93@gmail.com /  serge.kamga@iforce5.com
 * Project : @project todolist
 * Package : @package com.if5.todolist.models.dtos.requests.systeme
 **/
@Data
@ToString
@Builder
public class AddFiltresEtatDTO {
    @NotEmpty
    List<SimpleFiltreDTO> filtres;
    @NotNull
    private Long idEtat;
}
