import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import java.io.*;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangbing on 16/9/20.
 */
public class ExeclUtil {

    public static void main(String [] args) throws IOException {
//        write();
        Map<Integer, List<String>> mapData = readExcelUseForeach();
        List<String> listData = Lists.newArrayList();
        listData.add("1");
        listData.add("1");
        listData.add("1");
        listData.add("1");
        listData.add("1");
        listData.add("1");
        listData.add("1");
        listData.add("1");
        listData.add("1");
        listData.add("1");
        write(mapData, listData);
    }

    public static void write(Map<Integer, List<String>> dataMap, List<String> dataList) throws IOException {
        //创建一个Excel(or new XSSFWorkbook())
        Workbook wb = new HSSFWorkbook();
        //创建表格
        Sheet sheet = wb.createSheet("测试Sheet_01");
        //创建行
        //创建样式
        CellStyle cs = wb.createCellStyle();
        cs.setAlignment(CellStyle.ALIGN_CENTER);
        cs.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        cs.setBorderBottom(CellStyle.BORDER_DOTTED);
        cs.setBorderLeft(CellStyle.BORDER_THIN);
        cs.setBorderRight(CellStyle.BORDER_THIN);
        cs.setBorderTop(CellStyle.BORDER_THIN);

        for (Map.Entry<Integer, List<String>> elem : dataMap.entrySet()){
            Row row = sheet.createRow(elem.getKey());
            List<String> elemList = elem.getValue();

            int rowIndex = 0;
            for (String value : elemList){
                Cell cell = row.createCell(rowIndex);
                cell.setCellValue(value);
                rowIndex ++;
            }

            Cell cell = row.createCell(rowIndex);
            cell.setCellValue(dataList.get(elem.getKey()));
        }

        FileOutputStream fos = new FileOutputStream("武汉市在建项目1.xls");
        wb.write(fos);
        if(null != fos){
            fos.close();
        }
    }

    public static Map<Integer, List<String>> readExcelUseForeach() throws InvalidFormatException, IOException {

        Map<Integer, List<String>> map = Maps.newHashMap();

        Workbook workbook = new HSSFWorkbook(new FileInputStream(new File("武汉市在建项目1.xls")));
        for(Row row : workbook.getSheetAt(0)){
            List<String> list = Lists.newArrayList();
            for(Cell cell : row){
                list.add(getCellValue(cell));
                System.out.print(getCellValue(cell) + "----");
            }
            map.put(row.getRowNum(), list);
            System.out.println();
        }

        return map;
    }

    /**
     * 获取单元格内的数据值
     */
    private static String getCellValue(Cell cell){
        String str = null;
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_BLANK:
                str = ""; break;
            case Cell.CELL_TYPE_BOOLEAN:
                str = String.valueOf(cell.getBooleanCellValue()); break;
            case Cell.CELL_TYPE_FORMULA:
                str = String.valueOf(cell.getCellFormula()); break;
            case Cell.CELL_TYPE_NUMERIC:
                str = String.valueOf(cell.getNumericCellValue()); break;                //不要科学计数法
            case Cell.CELL_TYPE_STRING:
                str = cell.getStringCellValue(); break;
            default:
                str = null;
                break;
        }
        return str;
    }
}
