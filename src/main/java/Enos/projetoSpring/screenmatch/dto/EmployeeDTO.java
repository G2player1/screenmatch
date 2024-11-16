package Enos.projetoSpring.screenmatch.dto;

import Enos.projetoSpring.screenmatch.enums.EmployeePosition;

public record EmployeeDTO(Long id,String name, EmployeePosition employeePosition) {
}
