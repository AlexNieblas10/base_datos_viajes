package org.base_datos_viajes.model;

import org.bson.types.ObjectId;
import java.time.LocalDateTime;

/**
 * Entidad que representa un adeudo de un conductor
 *
 * @author Camila Zubia 00000244825
 */
public class Adeudo {

    private ObjectId id;
    private ObjectId conductorId;  // Referencia al conductor
    private int monto;
    private String concepto;
    private LocalDateTime fecha;
    private boolean pagado;

    // Constructor sin argumentos (requerido por POJO Codec de MongoDB)
    public Adeudo() {
    }

    public Adeudo(ObjectId conductorId, int monto, String concepto, LocalDateTime fecha) {
        this.conductorId = conductorId;
        this.monto = monto;
        this.concepto = concepto;
        this.fecha = fecha;
        this.pagado = false;
    }

    // Getters y Setters
    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public ObjectId getConductorId() {
        return conductorId;
    }

    public void setConductorId(ObjectId conductorId) {
        this.conductorId = conductorId;
    }

    public int getMonto() {
        return monto;
    }

    public void setMonto(int monto) {
        this.monto = monto;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public boolean isPagado() {
        return pagado;
    }

    public void setPagado(boolean pagado) {
        this.pagado = pagado;
    }

    @Override
    public String toString() {
        return "Adeudo{" +
                "id=" + id +
                ", conductorId=" + conductorId +
                ", monto=" + monto +
                ", concepto='" + concepto + '\'' +
                ", fecha=" + fecha +
                ", pagado=" + pagado +
                '}';
    }
}
