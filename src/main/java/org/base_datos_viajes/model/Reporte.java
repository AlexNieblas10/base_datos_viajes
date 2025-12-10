package org.base_datos_viajes.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

import org.bson.types.ObjectId;

/**
 * POJO representando un reporte de usuario.
 * Se guarda en la colección "reportes" en MongoDB.
 */
public class Reporte implements Serializable {

    private static final long serialVersionUID = 1L;

    private ObjectId id;
    private ObjectId reportanteId;  // Usuario que reporta
    private ObjectId reportadoId;   // Usuario reportado
    private ObjectId viajeId;       // Viaje donde ocurrió el incidente
    private String motivo;          // Motivo del reporte
    private String comentario;      // Comentario adicional
    private String foto;            // Ruta o nombre de archivo de evidencia
    private String estado;          // Pendiente, Aceptado, Apelado, Cancelado
    private LocalDateTime fechaReporte;

    public Reporte() {
        this.estado = "PENDIENTE";
        this.fechaReporte = LocalDateTime.now();
    }

    public Reporte(ObjectId reportanteId, ObjectId reportadoId, ObjectId viajeId,
                   String motivo, String comentario, String foto) {
        this.reportanteId = reportanteId;
        this.reportadoId = reportadoId;
        this.viajeId = viajeId;
        this.motivo = motivo;
        this.comentario = comentario;
        this.foto = foto;
        this.estado = "PENDIENTE";
        this.fechaReporte = LocalDateTime.now();
    }

    // Getters y setters
    public ObjectId getId() { return id; }
    public void setId(ObjectId id) { this.id = id; }

    public ObjectId getReportanteId() { return reportanteId; }
    public void setReportanteId(ObjectId reportanteId) { this.reportanteId = reportanteId; }

    public ObjectId getReportadoId() { return reportadoId; }
    public void setReportadoId(ObjectId reportadoId) { this.reportadoId = reportadoId; }

    public ObjectId getViajeId() { return viajeId; }
    public void setViajeId(ObjectId viajeId) { this.viajeId = viajeId; }

    public String getMotivo() { return motivo; }
    public void setMotivo(String motivo) { this.motivo = motivo; }

    public String getComentario() { return comentario; }
    public void setComentario(String comentario) { this.comentario = comentario; }

    public String getFoto() { return foto; }
    public void setFoto(String foto) { this.foto = foto; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public LocalDateTime getFechaReporte() { return fechaReporte; }
    public void setFechaReporte(LocalDateTime fechaReporte) { this.fechaReporte = fechaReporte; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reporte reporte = (Reporte) o;
        return Objects.equals(id, reporte.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Reporte{" +
                "id=" + id +
                ", reportanteId=" + reportanteId +
                ", reportadoId=" + reportadoId +
                ", viajeId=" + viajeId +
                ", motivo='" + motivo + '\'' +
                ", comentario='" + comentario + '\'' +
                ", estado='" + estado + '\'' +
                ", fechaReporte=" + fechaReporte +
                '}';
    }
}
