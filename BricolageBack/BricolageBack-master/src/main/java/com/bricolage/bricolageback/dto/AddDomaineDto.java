package com.bricolage.bricolageback.dto;


import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Getter
@Setter
@Builder
@ToString
public class AddDomaineDto {
    private int id;
    private String domaine;
    private String description;
}
