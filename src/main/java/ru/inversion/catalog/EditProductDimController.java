package ru.inversion.catalog;

import java.math.BigDecimal;
import javafx.event.ActionEvent;
import ru.inversion.fx.form.JInvFXFormController;
import ru.inversion.fx.form.controls.*;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import ru.inversion.bicomp.util.ParamMap;
import ru.inversion.db.expr.SQLExpressionException;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.layout.GridPane;
import ru.inversion.dataset.XXIDataSet;
import javafx.stage.Stage;
import ru.inversion.fx.form.valid.Validator;
/**
 * @author  admin
 * @since   Mon Jun 16 14:39:40 MSK 2025
 */
public class EditProductDimController extends JInvFXFormController <PProductDim> 
{  
//
//
//
//    @FXML JInvLongField PRODUCT_ID;
    @FXML private JInvTextField PRODUCT_NAME;
//    @FXML JInvLongField CATEGORY;
    @FXML private JInvTextField PRICE;
    @FXML private JInvTextField STOCK_QUANTITY;

    @FXML private JInvComboBox productCategoryComboBox;
    @FXML private JInvComboBox productSuppliersComboBox;
    private boolean cheakBox;
    private List<PSuppliersDim> supplierses;
    private List<PCategoryDim> categores;
//
    static private String reg_Price = "^\\d+(\\.\\d{2})?$";
    static private String reg_Count = "[0-9]+";
    private static final Pattern pattern_Price = Pattern.compile(reg_Price);
    private static final Pattern pattern_Count = Pattern.compile(reg_Count);
// Initializes the controller class.
//
     private final XXIDataSet<PSuppliersDim> dsSupplierSet = new XXIDataSet<> ();    
     private final XXIDataSet<PCategoryDim> dsPCategorySet = new XXIDataSet<> ();    
//
// initDataSet
//    
    private void initDataSet () throws Exception 
    {
        dsSupplierSet.setTaskContext (getTaskContext ());
        dsSupplierSet.setRowClass (PSuppliersDim.class);
        
        dsPCategorySet.setTaskContext (getTaskContext ());
        dsPCategorySet.setRowClass (PCategoryDim.class);
    }

    private static boolean isValidPATTERN(String element, Pattern anotherPattern) {
        if (element == null) {
            return false;
        }
        Matcher matcher = anotherPattern.matcher(element);
        return matcher.matches();
    }
    
    
    
    @Override
    public boolean onOK() {
        cheakBox = true;
        PSuppliersDim SupplersOnly = supplierses.stream()
                        .filter(s -> (s.getFIRST_NAME() + " " + s.getLAST_NAME())
                                .equals(productSuppliersComboBox.getSelectionModel().getSelectedItem()))
                        .findFirst().orElse(null);

        PCategoryDim categoryOnly = categores.stream()
                        .filter(c -> (c.getCATEGORY_NAME())
                                .equals(productCategoryComboBox.getSelectionModel().getSelectedItem()))
                        .findFirst().orElse(null);            
        BigDecimal decimalTemp = new BigDecimal(PRICE.getText());
        if (dataObject.getPRODUCT_NAME() == null){
            try {
                new ParamMap()
                        .add("p_name", PRODUCT_NAME.getText())
                        .add("p_category", categoryOnly.getCATEGORY_DIM_ID())
                        .add("p_price", decimalTemp)
                        .add("p_stock_quantity", STOCK_QUANTITY.getText())
                        .add("p_supplier", SupplersOnly.getID())
                        .exec(this, "addNewProduct");
            } catch (SQLExpressionException ex) {
                Logger.getLogger(EditProductDimController.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println(ex.getMessage());
                return false;
            }
        }
        else{
            try {
                new ParamMap()
                        .add("p_id", dataObject.getPRODUCT_ID())
                        .add("p_name", PRODUCT_NAME.getText())
                        .add("p_category", categoryOnly.getCATEGORY_DIM_ID())
                        .add("p_price", decimalTemp)
                        .add("p_stock_quantity", STOCK_QUANTITY.getText())
                        .add("p_supplier", SupplersOnly.getID())
                        .exec(this, "updateProductsNew");
            } catch (SQLExpressionException ex) {
                Logger.getLogger(EditProductDimController.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println(ex.getMessage());
                return false;
            }
        }
        this.getFXEntity().commit();
        return true;
    }
    
    private void SupplersBooksComboBox() {
        Set<String> uniqueSuppliers = new LinkedHashSet<>();
        List<PSuppliersDim> findOfUsers = new ArrayList<>();// LinkedHashSet сохраняет порядок
        for (PSuppliersDim supplier : dsSupplierSet.getRows()) 
            findOfUsers.add(supplier);
        productSuppliersComboBox.setEditable(true);
        productSuppliersComboBox.getEditor().textProperty().addListener((obs, oldVal, newVal) -> {
            if (!productSuppliersComboBox.getItems().contains(newVal)) {
                if (!uniqueSuppliers.isEmpty())
                    uniqueSuppliers.clear();
                int count = 0;
                for (PSuppliersDim suppliers : dsSupplierSet.getRows()) {
                    for (char item : newVal.toCharArray()) {
                        if (Character.toLowerCase(item) == Character.toLowerCase((suppliers.getFIRST_NAME() + " " + suppliers.getLAST_NAME()).charAt(count))) {
                            if (count < newVal.length()) {
                                count++;
                                if (count == (newVal.length())) {
                                    uniqueSuppliers.add(suppliers.getFIRST_NAME() + " " + suppliers.getLAST_NAME());
                                    count = 0;
                                }
                            }
                        }
                        else {
                            count = 0;
                            break;
                        }
                    }
                }       
                productSuppliersComboBox.getItems().clear();
                productSuppliersComboBox.getItems().setAll(uniqueSuppliers);
                productSuppliersComboBox.show();

                // Показываем выпадающий список при вводе
                if (newVal.isEmpty()) {
                    productSuppliersComboBox.getItems().clear();
                    uniqueSuppliers.clear();
                    for (PSuppliersDim supplierItem : findOfUsers){
                        uniqueSuppliers.add(supplierItem.getFIRST_NAME() + " " + supplierItem.getLAST_NAME());
                    }
                    productSuppliersComboBox.getItems().addAll(uniqueSuppliers);                                
                    productSuppliersComboBox.show();
                }
            }
        });
        // Устанавливаем первое значение по умолчанию, если список не пуст
        if (!productSuppliersComboBox.getItems().isEmpty()) {
            productSuppliersComboBox.getSelectionModel().selectFirst();
        }
    } 


    private void CategoryBooksComboBox() {
        Set<String> uniqueSuppliers = new LinkedHashSet<>();
        List<PCategoryDim> findOfUsers = new ArrayList<>();// LinkedHashSet сохраняет порядок
        for (PCategoryDim category : dsPCategorySet.getRows()) 
            findOfUsers.add(category);
        productCategoryComboBox.setEditable(true);
        productCategoryComboBox.getEditor().textProperty().addListener((obs, oldVal, newVal) -> {
            if (!productCategoryComboBox.getItems().contains(newVal)) {
                if (!uniqueSuppliers.isEmpty())
                    uniqueSuppliers.clear();
                int count = 0;
                for (PCategoryDim category : dsPCategorySet.getRows()) {
                    for (char item : newVal.toCharArray()) {
                        if (Character.toLowerCase(item) == Character.toLowerCase((category.getCATEGORY_NAME().charAt(count)))) {
                            if (count < newVal.length()) {
                                count++;
                                if (count == (newVal.length())) {
                                    uniqueSuppliers.add(category.getCATEGORY_NAME());
                                    count = 0;
                                }
                            }
                        }
                        else {
                            count = 0;
                            break;
                        }
                    }
                }       
                productCategoryComboBox.getItems().clear();
                productCategoryComboBox.getItems().setAll(uniqueSuppliers);
                productCategoryComboBox.show();

                // Показываем выпадающий список при вводе
                if (newVal.isEmpty()) {
                    productCategoryComboBox.getItems().clear();
                    uniqueSuppliers.clear();
                    for (PCategoryDim category : findOfUsers){
                        uniqueSuppliers.add(category.getCATEGORY_NAME());
                    }
                    productCategoryComboBox.getItems().addAll(uniqueSuppliers);                                
                    productCategoryComboBox.show();
                }
            }
        });

        // Устанавливаем первое значение по умолчанию, если список не пуст
        if (!productSuppliersComboBox.getItems().isEmpty()) {
            productSuppliersComboBox.getSelectionModel().selectFirst();
        }
    } 
    
    @Override
    protected void init () throws Exception 
    {
        initDataSet();
        dsSupplierSet.executeQuery();
        dsPCategorySet.executeQuery();
        if (dataObject.getFIRST_NAME() != null){
            productSuppliersComboBox.getItems().clear();
            supplierses = new ArrayList<>();
            for (PSuppliersDim item : dsSupplierSet.getRows()){
                supplierses.add(item);
            }
            productSuppliersComboBox.getItems().addAll(dataObject.getFIRST_NAME() + " " + dataObject.getLAST_NAME());
            productSuppliersComboBox.getSelectionModel().selectFirst();
            productSuppliersComboBox.setDisable(true);
            STOCK_QUANTITY.setText(dataObject.getSTOCK_QUANTITY().toString());
        }
        else productComboBox();
        categoryComboBox();
        SupplersBooksComboBox();
        CategoryBooksComboBox();
        
        getValidMan().bindValidators2Control(PRODUCT_NAME,(value)-> {
            if (value==null)
                return new Validator.Result(value, bundle.getString("VALIDATOR.ERROR"));
            return null;
        });
        
        getValidMan().bindValidators2Control(PRICE,(value)-> {
            if (value==null)
                return new Validator.Result(value, bundle.getString("VALIDATOR.ERROR"));
            if (!isValidPATTERN((String) value, pattern_Price)){
                return new Validator.Result(value,String.format(bundle.getString("VALIDATOR.PRICE"), value));
            }
            return null;
        });
        
        getValidMan().bindValidators2Control(STOCK_QUANTITY,(value)-> {
            if (value==null)
                return new Validator.Result(value, bundle.getString("VALIDATOR.ERROR"));
            if (!isValidPATTERN((String) value, pattern_Count)){
                return new Validator.Result(value,String.format(bundle.getString("VALIDATOR.COUNT"), value));
            }
            return null;
        });
        super.init (); 
    }       
    
    private void productComboBox(){
        productSuppliersComboBox.getItems().clear();
        Set<String> unigTetles = new LinkedHashSet<>();
        supplierses = new ArrayList<>();
        for (PSuppliersDim item : dsSupplierSet.getRows()){
            unigTetles.add(item.getFIRST_NAME() + " " + item.getLAST_NAME());
            supplierses.add(item);
        }
        productSuppliersComboBox.getItems().addAll(unigTetles);
        if (!productSuppliersComboBox.getItems().isEmpty())
            productSuppliersComboBox.getSelectionModel().selectFirst();
    }
    
    private void categoryComboBox(){
        productCategoryComboBox.getItems().clear();
        Set<String> unigTetles = new LinkedHashSet<>();
        categores = new ArrayList<>();
        for (PCategoryDim item : dsPCategorySet.getRows()){
            unigTetles.add(item.getCATEGORY_NAME());
            categores.add(item);
        }
        productCategoryComboBox.getItems().addAll(unigTetles);
        if (!productCategoryComboBox.getItems().isEmpty())
            productCategoryComboBox.getSelectionModel().selectFirst();
    }
}

