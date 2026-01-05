/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package QLTV.Domain;

/**
 *
 * @author dinhd
 */
public class Sach {
    private String masach, matg, manxb, matl, tensach, mann, mavitri;
    private int namxb;
    private Integer soluong;

    public Sach() {
    }

    public Sach(String masach, String matg, String manxb, String matl, String tensach,int namxb, Integer soluong, String mann, String mavitri) {
        this.masach = masach;
        this.matg = matg;
        this.manxb = manxb;
        this.matl = matl;
        this.tensach = tensach;
        this.namxb = namxb;
        this.soluong = soluong;
        this.mann = mann;
        this.mavitri = mavitri;
}


    

    public String getMasach() {
        return masach;
    }

    public void setMasach(String masach) {
        this.masach = masach;
    }

    public String getMatg() {
        return matg;
    }

    public void setMatg(String matg) {
        this.matg = matg;
    }

    public String getManxb() {
        return manxb;
    }

    public void setManxb(String manxb) {
        this.manxb = manxb;
    }

    public String getMatl() {
        return matl;
    }

    public void setMatl(String matl) {
        this.matl = matl;
    }

    public String getTensach() {
        return tensach;
    }

    public void setTensach(String tensach) {
        this.tensach = tensach;
    }


    public String getMann() {
        return mann;
    }

    public void setMann(String mann) {
        this.mann = mann;
    }

    public String getMavitri() {
        return mavitri;
    }

    public void setMavitri(String mavitri) {
        this.mavitri = mavitri;
    }

    public int getNamxb() {
        return namxb;
    }

    public void setNamxb(int namxb) {
        this.namxb = namxb;
    }

    public Integer getSoluong() {
        return soluong;
    }

    public void setSoluong(Integer soluong) {
        this.soluong = soluong;
    }

     
}    
    