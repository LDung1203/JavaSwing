/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package QLTV.Domain;

/**
 *
 * @author Admin
 */

public class NgonNgu {
    private String maNN;
    private String tenNN;

    public NgonNgu() {}

    public NgonNgu(String maNN, String tenNN) {
        this.maNN = maNN;
        this.tenNN = tenNN;
    }

    public String getMaNN() {
        return maNN;
    }

    public void setMaNN(String maNN) {
        this.maNN = maNN;
    }

    public String getTenNN() {
        return tenNN;
    }

    public void setTenNN(String tenNN) {
        this.tenNN = tenNN;
    }
        @Override
    public String toString() {
        return tenNN;
    }
}
