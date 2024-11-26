/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clases;

import java.util.List;

/**
 *
 * @author Anthony
 */
public class Transaccion {
    private List<LibroDiario> registros; // Listado de registros del libro diario

    // Métodos para agregar y validar registros
    public boolean validarDebeHaberIguales() {
        double totalDebe = 0;
        double totalHaber = 0;
        for (LibroDiario registro : registros) {
            totalDebe += registro.getDebe();
            totalHaber += registro.getHaber();
        }
        return totalDebe == totalHaber; // Validación de que deben y haber sean iguales
    }
}

