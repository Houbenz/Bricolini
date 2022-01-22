package com.bricolage.bricolageback.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Getter
@Setter
@ToString
public class AvisDto {
    long id;
    private int rate;
    private String message;
}
