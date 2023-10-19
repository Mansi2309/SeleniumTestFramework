package excelHandlers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.Reporter;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;

public class ExcelUtilities {
	public static String[][] getTableArray(String xlFilePath, String sheetName, String tableName) {
        String[][] tabArray = null;
        try {
            final WorkbookSettings settings = new WorkbookSettings();
            settings.setSuppressWarnings(true);
            final Workbook workbook = Workbook.getWorkbook(new File(xlFilePath), settings);
            final Sheet sheet = workbook.getSheet(sheetName);
            int startRow, startCol, endRow, endCol, ci, cj;
            final Cell tableStart = sheet.findCell(tableName);
            startRow = tableStart.getRow() + 1;
            startCol = tableStart.getColumn();
            final Cell tableEnd = sheet.findCell(tableName, startCol + 1, startRow + 1, 100, 64000, false);
            endRow = tableEnd.getRow();
            endCol = tableEnd.getColumn();
            tabArray = new String[endRow - startRow][endCol - startCol - 1];
            ci = 0;
            for (int i = startRow + 1; i <= endRow; ++i, ++ci) {
                cj = 0;
                for (int j = startCol + 1; j < endCol; j++, cj++) {
                    tabArray[ci][cj] = sheet.getCell(j, i).getContents().trim().toString();
                }
            }
            workbook.close();
        } catch (final Exception e) {
            System.out.println("Table not found. Verify the start tag in the sheet :" + tableName);
            System.out.println(e);
        }
        return (tabArray);
    }
}
