package edu.com.uce.application.interceptor;

import edu.com.uce.application.service.AuditoriaService;
import edu.com.uce.domain.model.Auditoria;
import jakarta.inject.Inject;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;
import java.time.LocalDateTime;
import java.util.Arrays;

@Auditar
@Interceptor
public class AuditarInterceptor {

    @Inject
    private AuditoriaService auditoriaService;

    @AroundInvoke
    public Object auditar(InvocationContext context) throws Exception {
        String nombreMetodo = context.getMethod().getName();
        String entidad = context.getMethod().getDeclaringClass().getSimpleName();
        Object[] parametros = context.getParameters();
        String detalle = Arrays.toString(parametros);

        long tiempoInicio = System.currentTimeMillis();

        Object resultado = null;
        try {
            resultado = context.proceed();
            guardarAuditoria(entidad, nombreMetodo, detalle, tiempoInicio, parametros, null);
        } catch (Exception e) {
            guardarAuditoria(entidad, nombreMetodo, detalle, tiempoInicio, parametros, e.getMessage());
            throw e;
        }

        return resultado;
    }

    private void guardarAuditoria(String entidad, String metodo, String detalle,
                                  long tiempoInicio, Object[] parametros, String error) {
        try {
            Auditoria auditoria = new Auditoria();
            auditoria.setEntidad(entidad);
            auditoria.setOperacion(determinarOperacion(metodo));

            String detalleFinal = error != null ? detalle + " | Error: " + error : detalle;
            auditoria.setDetalle(detalleFinal);

            auditoria.setFechaHora(LocalDateTime.now());
            long tiempoEjecucion = System.currentTimeMillis() - tiempoInicio;
            auditoria.setTiempoEjecucionMs(tiempoEjecucion);

            String registroId = null;
            if (parametros != null && parametros.length > 0 && parametros[0] != null) {
                Object first = parametros[0];
                if (first instanceof Integer || first instanceof Long || first instanceof String) {
                    registroId = first.toString();
                }
            }
            auditoria.setRegistroId(registroId);

            this.auditoriaService.guardar(auditoria);
        } catch (Exception e) {
            System.err.println("Error al guardar auditoría: " + e.getMessage());
        }
    }

    private String determinarOperacion(String nombreMetodo) {
        String metodoLower = nombreMetodo.toLowerCase();
        if (metodoLower.contains("registrar") || metodoLower.contains("guardar") || metodoLower.contains("crear")) {
            return "INSERT";
        } else if (metodoLower.contains("actualizar") || metodoLower.contains("modificar") ||
                   metodoLower.contains("editar") || metodoLower.contains("cancelar") ||
                   metodoLower.contains("completar") || metodoLower.contains("liberar")) {
            return "UPDATE";
        } else if (metodoLower.contains("eliminar") || metodoLower.contains("borrar") || metodoLower.contains("delete")) {
            return "DELETE";
        } else if (metodoLower.contains("buscar") || metodoLower.contains("listar") ||
                   metodoLower.contains("obtener") || metodoLower.contains("find") || metodoLower.contains("contar")) {
            return "SELECT";
        }
        return "UNKNOWN";
    }
}