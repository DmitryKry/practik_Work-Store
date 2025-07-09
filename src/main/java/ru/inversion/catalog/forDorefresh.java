/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.inversion.catalog;

/**
 *
 * @author admin
 */
public class forDorefresh {
    static private boolean productCheak;
    static private boolean SuppliersCheak;

    public static void setProductCheak(boolean productCheak) {
        forDorefresh.productCheak = productCheak;
    }

    public static void setSuppliersCheak(boolean SuppliersCheak) {
        forDorefresh.SuppliersCheak = SuppliersCheak;
    }

    public static boolean getProductCheak() {
        return productCheak;
    }

    public static boolean getSuppliersCheak() {
        return SuppliersCheak;
    }
}
