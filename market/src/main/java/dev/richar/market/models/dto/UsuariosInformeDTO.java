package dev.richar.market.models.dto;


import java.math.BigDecimal;

public class UsuariosInformeDTO {

    private Integer idUsuario;
    private String name;
    private BigDecimal total;

    // Constructor que respeta el orden y tipos retornados por la consulta
    public UsuariosInformeDTO(Integer idUsuario, String name, BigDecimal total) {
        this.idUsuario = idUsuario;
        this.name = name;
        this.total = total;
    }

    public UsuariosInformeDTO() {
    }

    // Getters y setters
    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    // Getter adicional para convertir el BigDecimal a Integer
    public Integer getTotalAsInteger() {
        return total != null ? total.intValue() : null;
    }

}
