package edu.com.uce.application.interceptor;

import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;

@MedirTiempo
@Interceptor
public class MedirTiempoInterceptor {

    @AroundInvoke
    public Object medir(InvocationContext context) throws Exception {
        String nombreMetodo = context.getMethod().getName();
        long inicio = System.currentTimeMillis();
        
        Object resultado = context.proceed(); 
        
        long fin = System.currentTimeMillis();
        System.out.println("Método: " + nombreMetodo + " - Tiempo: " + (fin - inicio) + " ms");
        
        return resultado;
    }
}