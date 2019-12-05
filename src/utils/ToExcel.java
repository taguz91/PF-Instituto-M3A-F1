/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

/**
 *
 * @author MrRainx
 */
public class ToExcel {

    /**
     * @author datojava.blogspot.com
     */
    // Reporte Supervision de verificaciones
    private DataModel dataModelCabecera;
    private DataModel dataModelContenido;

    public DataModel getDataModelCabecera() {
        return dataModelCabecera;
    }

    public void setDataModelCabecera(DataModel dataModelCabecera) {
        this.dataModelCabecera = dataModelCabecera;
    }

    public DataModel getDataModelContenido() {
        return dataModelContenido;
    }

    public void setDataModelContenido(DataModel dataModelContenido) {
        this.dataModelContenido = dataModelContenido;
    }

    //----------------------------------------------------------------
    public void exportarExcel(List columns, List rows, String reportName) {

        // Llenar la cabecera y el contenido del excel
        this.setDataModelCabecera(new ListDataModel(columns));
        this.setDataModelContenido(new ListDataModel(rows));
        // Hacer el excel con su cabecera y contenido
        HSSFWorkbook hssfWorkbook = obtenerExcel(dataModelContenido,
                dataModelCabecera, "hola1");

        try {
            // Guardar el Archivo
            FileOutputStream fileOutputStream = new FileOutputStream(reportName + ".xls");
            hssfWorkbook.write(fileOutputStream);
            fileOutputStream.close();

            File file = new File(reportName + ".xls");

            // Abrir el archivo
            Desktop.getDesktop().open(file);

        } catch (IOException e) {
            Logger.getLogger(ToExcel.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    /**
     *
     * @param contenidoCeldas
     * @param cabecerasCeldas
     * @param nombreHoja
     * @return HSSFWorkbook
     */
    public static HSSFWorkbook obtenerExcel(DataModel contenidoCeldas,
            DataModel cabecerasCeldas, String nombreHoja) {

        HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
        HSSFSheet hssfSheet = hssfWorkbook.createSheet(nombreHoja);
        int numeroFila = 0;
        int numeroColumna = 0;

        // Crear una nueva fila
        HSSFRow hssfRow = hssfSheet.createRow(numeroFila++);

        // Estilo de la cabecera
        HSSFCellStyle hssfCellStyleCabecera = hssfWorkbook.createCellStyle();
        hssfCellStyleCabecera.setFillPattern(HSSFCellStyle.NO_FILL);
        hssfCellStyleCabecera.setFillBackgroundColor(new HSSFColor.BLUE_GREY()
                .getIndex());

        // Crear la fuente de la cabecera
        HSSFFont hssfFont = hssfWorkbook.createFont();
        hssfFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        hssfFont.setColor(HSSFColor.BLACK.index);

        // Aplicarle la fuente al estilo de la cabecera
        hssfCellStyleCabecera.setFont(hssfFont);

        String columnaCabecera;

        // Creacion de la celda
        HSSFCell hssfCell = null;

        // Pasar los nombres de cabeceras a una lista
        List cabecerasExcel = (List) cabecerasCeldas.getWrappedData();

        // Agregar los nombres de las cabeceras a el excel
        for (int i = 0; i < cabecerasExcel.size(); i++) {
            columnaCabecera = (String) cabecerasExcel.get(i);
            hssfCell = hssfRow.createCell((short) numeroColumna++);
            hssfCell.setCellValue(columnaCabecera);

            // Agregar el estilo que creamos antes
            hssfCell.setCellStyle(hssfCellStyleCabecera);
            //hssfCell.setEncoding(HSSFCell.ENCODING_UTF_16);

        }

        // Pasar el contenido del excel a una lista
        List contenidoExcel = (List) contenidoCeldas.getWrappedData();
        List fila = null;
        Object valor;

        for (int i = 0; i < contenidoExcel.size(); i++) {
            // Obtener el contenido por fila
            fila = (List) contenidoExcel.get(i);

            // Crear la fila
            hssfRow = hssfSheet.createRow(numeroFila++);
            numeroColumna = 0;
            for (int x = 0; x < fila.size(); x++) {
                // Obtener el valor de cada celda
                valor = fila.get(x);

                // Insertarlo en la celda
                hssfCell = hssfRow.createCell((short) numeroColumna++);
                //hssfCell.setEncoding(HSSFCell.ENCODING_UTF_16);
                hssfCell.setCellValue((String) valor);
            }
        }
        return hssfWorkbook;
    }

}
