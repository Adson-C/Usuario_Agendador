package com.adsonsa.useradson.business.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TelefoneDTO {


    private Long id;
    private String numero;

    private String ddd;
}
