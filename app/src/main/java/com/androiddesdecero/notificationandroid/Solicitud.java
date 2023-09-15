package com.androiddesdecero.notificationandroid;

public class Solicitud {
    private String curp;
    private String nombre;
    private String apellidos;
    private String domicilio;
    private double ingresoFamiliar;
    private String tipoCredito;

    public Solicitud() {

    }

    public Solicitud(String curp, String nombre, String apellidos, String domicilio, double ingresoFamiliar, String tipoCredito) {
        this.curp = curp;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.domicilio = domicilio;
        this.ingresoFamiliar = ingresoFamiliar;
        this.tipoCredito = tipoCredito;
    }

    public String getCurp() {
        return curp;
    }

    public void setCurp(String curp) {
        this.curp = curp;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public double getIngresoFamiliar() {
        return ingresoFamiliar;
    }

    public void setIngresoFamiliar(double ingresoFamiliar) {
        this.ingresoFamiliar = ingresoFamiliar;
    }

    public String getTipoCredito() {
        return tipoCredito;
    }

    public void setTipoCredito(String tipoCredito) {
        this.tipoCredito = tipoCredito;
    }

    public double calcularInteres() {
        double interes = 0;
        switch (tipoCredito) {
            case "MEDIO":
                if (ingresoFamiliar >= 36000) {
                    interes = 10;
                } else {
                    interes = 15;
                }
                break;
            case "PARCIAL":
                if (ingresoFamiliar >= 75000) {
                    interes = 8;
                } else {
                    interes = 12;
                }
                break;
            case "TOTAL":
                if (ingresoFamiliar >= 1120000) {
                    interes = 6;
                } else {
                    interes = 10;
                }
                break;
        }
        return interes;
    }

    public boolean validarIngreso() {
        boolean valido = false;
        switch (tipoCredito) {
            case "MEDIO":
                if (ingresoFamiliar >= 36000) {
                    valido = true;
                }
                break;
            case "PARCIAL":
                if (ingresoFamiliar >= 75000) {
                    valido = true;
                }
                break;
            case "TOTAL":
                if (ingresoFamiliar >= 1120000) {
                    valido = true;
                }
                break;
        }
        return valido;
    }
}

