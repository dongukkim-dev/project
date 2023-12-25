package com.example.project.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class SalesDto {

    private List<SaleDto> yearSales;
    private List<SaleDto> monthSales;
    private List<SaleDto> dailySales;
}
