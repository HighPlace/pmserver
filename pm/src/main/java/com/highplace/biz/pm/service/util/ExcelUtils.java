package com.highplace.biz.pm.service.util;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.springframework.core.io.ClassPathResource;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class ExcelUtils {

    //是否是2003的excel，返回true是2003
    public static boolean isExcel2003(String filePath)  {
        return filePath.matches("^.+\\.(?i)(xls)$");
    }

    //是否是2007的excel，返回true是2007
    public static boolean isExcel2007(String filePath)  {
        return filePath.matches("^.+\\.(?i)(xlsx)$");
    }

    //验证EXCEL文件
    public static boolean validateExcel(String filePath){
        if (filePath == null || !(isExcel2003(filePath) || isExcel2007(filePath))){
            return false;
        }
        return true;
    }

    //获取excel模板
    public static InputStream getTemplates(String tempName) throws IOException {
        //return new FileInputStream(ResourceUtils.getFile("classpath:excel-templates/" + tempName));
        //return new FileInputStream(ResourceUtils.getFile("classpath:" + tempName));
        return new ClassPathResource("excel-templates/" + tempName).getInputStream();
    }

    private static ExcelUtils eu = new ExcelUtils();
    private ExcelUtils(){}

    public static ExcelUtils getInstance() {
        return eu;
    }
    /**
     * 处理对象转换为Excel
     * @param template
     * @param objs
     * @param clz
     * @param isClasspath
     * @return
     */
    private ExcelTemplate handlerObj2Excel (String template, List objs, Class clz, boolean isClasspath)  {
        ExcelTemplate et = ExcelTemplate.getInstance();
        try {
            if(isClasspath) {
                et.readTemplateByClasspath(template);
            } else {
                et.readTemplateByPath(template);
            }
            List<ExcelHeader> headers = getHeaderList(clz);
            Collections.sort(headers);
            //输出标题
            et.createNewRow();
            for(ExcelHeader eh:headers) {
                et.createCell(eh.getTitle());
            }
            //输出值
            for(Object obj:objs) {
                et.createNewRow();
                for(ExcelHeader eh:headers) {
                    //              Method m = clz.getDeclaredMethod(mn);
                    //              Object rel = m.invoke(obj);
                    et.createCell(BeanUtils.getProperty(obj,getMethodName(eh)));
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return et;
    }
    /**
     * 根据标题获取相应的方法名称
     * @param eh
     * @return
     */
    private String getMethodName(ExcelHeader eh) {
        String mn = eh.getMethodName().substring(3);
        mn = mn.substring(0,1).toLowerCase()+mn.substring(1);
        return mn;
    }
    /**
     * 将对象转换为Excel并且导出，该方法是基于模板的导出，导出到流
     * @param datas 模板中的替换的常量数据
     * @param template 模板路径
     * @param os 输出流
     * @param objs 对象列表
     * @param clz 对象的类型
     * @param isClasspath 模板是否在classPath路径下
     */
    public void exportObj2ExcelByTemplate(Map<String,String> datas, String template, OutputStream os, List objs, Class clz, boolean isClasspath) {
        try {
            ExcelTemplate et = handlerObj2Excel(template, objs, clz, isClasspath);
            et.replaceFinalData(datas);
            et.wirteToStream(os);
            os.flush();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * 将对象转换为Excel并且导出，该方法是基于模板的导出，导出到一个具体的路径中
     * @param datas 模板中的替换的常量数据
     * @param template 模板路径
     * @param outPath 输出路径
     * @param objs 对象列表
     * @param clz 对象的类型
     * @param isClasspath 模板是否在classPath路径下
     */
    public void exportObj2ExcelByTemplate(Map<String,String> datas,String template,String outPath,List objs,Class clz,boolean isClasspath) {
        ExcelTemplate et = handlerObj2Excel(template, objs, clz, isClasspath);
        et.replaceFinalData(datas);
        et.writeToFile(outPath);
    }

    /**
     * 将对象转换为Excel并且导出，该方法是基于模板的导出，导出到流,基于Properties作为常量数据
     * @param prop 基于Properties的常量数据模型
     * @param template 模板路径
     * @param os 输出流
     * @param objs 对象列表
     * @param clz 对象的类型
     * @param isClasspath 模板是否在classPath路径下
     */
    public void exportObj2ExcelByTemplate(Properties prop, String template, OutputStream os, List objs, Class clz, boolean isClasspath) {
        ExcelTemplate et = handlerObj2Excel(template, objs, clz, isClasspath);
        et.replaceFinalData(prop);
        et.wirteToStream(os);
    }
    /**
     * 将对象转换为Excel并且导出，该方法是基于模板的导出，导出到一个具体的路径中,基于Properties作为常量数据
     * @param prop 基于Properties的常量数据模型
     * @param template 模板路径
     * @param outPath 输出路径
     * @param objs 对象列表
     * @param clz 对象的类型
     * @param isClasspath 模板是否在classPath路径下
     */
    public void exportObj2ExcelByTemplate(Properties prop,String template,String outPath,List objs,Class clz,boolean isClasspath) {
        ExcelTemplate et = handlerObj2Excel(template, objs, clz, isClasspath);
        et.replaceFinalData(prop);
        et.writeToFile(outPath);
    }

    private Workbook handleObj2Excel(List objs, Class clz) {
        Workbook wb = new HSSFWorkbook();
        try {
            Sheet sheet = wb.createSheet();
            //sheet.autoSizeColumn(1, true);//自适应列宽度,合并的单元格使用
            //sheet.autoSizeColumn(1);//自适应列宽度
            Row r = sheet.createRow(0);
            List<ExcelHeader> headers = getHeaderList(clz);
            Collections.sort(headers);
            //写标题，并设置样式为居中加粗
            CellStyle style = wb.createCellStyle();
            Font font = wb.createFont();
            font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
            style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            style.setFont(font);
            Cell cell;
            for(int i=0;i<headers.size();i++) {
                cell = r.createCell(i);
                cell.setCellValue(headers.get(i).getTitle());
                cell.setCellStyle(style);
                //sheet.autoSizeColumn(i);//自适应列宽度
                sheet.setColumnWidth(i, headers.get(i).getTitle().getBytes().length*1*256);//中文适用
            }
            //写数据
            Object obj = null;
            String cellValue;
            for(int i=0;i<objs.size();i++) {
                r = sheet.createRow(i+1);
                obj = objs.get(i);
                for(int j=0;j<headers.size();j++) {
                    cellValue = BeanUtils.getProperty(obj, getMethodName(headers.get(j)));
                    r.createCell(j).setCellValue(cellValue);
                    //if(StringUtils.isNotEmpty(cellValue)) sheet.setColumnWidth(j, cellValue.getBytes().length*1*256);//中文适用
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return wb;
    }
    /**
     * 导出对象到Excel，不是基于模板的，直接新建一个Excel完成导出，基于路径的导出
     * @param outPath 导出路径
     * @param objs 对象列表
     * @param clz 对象类型
     */
    public void exportObj2Excel(String outPath,List objs,Class clz) {
        Workbook wb = handleObj2Excel(objs, clz);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(outPath);
            wb.write(fos);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(fos!=null) fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * 导出对象到Excel，不是基于模板的，直接新建一个Excel完成导出，基于流
     * @param os 输出流
     * @param objs 对象列表
     * @param clz 对象类型
     */
    public void exportObj2Excel(OutputStream os,List objs,Class clz) {
        try {
            Workbook wb = handleObj2Excel(objs, clz);
            wb.write(os);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * 从类路径读取相应的Excel文件到对象列表
     * @param path 类路径下的path
     * @param clz 对象类型
     * @param readLine 开始行，注意是标题所在行
     * @param tailLine 底部有多少行，在读入对象时，会减去这些行
     * @return
     */
    public List<Object> readExcel2ObjsByClasspath(String path,Class clz,int readLine,int tailLine) {
        Workbook wb = null;
        try {
            wb = new HSSFWorkbook(ExcelUtils.getTemplates(path));
            return handlerExcel2Objs(wb, clz, readLine,tailLine);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 从文件路径读取相应的Excel文件到对象列表
     * @param path 文件路径下的path
     * @param clz 对象类型
     * @param readLine 开始行，注意是标题所在行
     * @param tailLine 底部有多少行，在读入对象时，会减去这些行
     * @return
     */
    public List<Object> readExcel2ObjsByPath(String path,Class clz,int readLine,int tailLine) {
        Workbook wb = null;
        try {
            wb = new HSSFWorkbook(ExcelUtils.getTemplates(path));
            return handlerExcel2Objs(wb, clz, readLine,tailLine);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 从类路径读取相应的Excel文件到对象列表，标题行为0，没有尾行
     * @param path 路径
     * @param clz 类型
     * @return 对象列表
     */
    public List<Object> readExcel2ObjsByClasspath(String path,Class clz) {
        return this.readExcel2ObjsByClasspath(path, clz, 0,0);
    }
    /**
     * 从文件路径读取相应的Excel文件到对象列表，标题行为0，没有尾行
     * @param path 路径
     * @param clz 类型
     * @return 对象列表
     */
    public List<Object> readExcel2ObjsByPath(String path,Class clz) {
        return this.readExcel2ObjsByPath(path, clz,0,0);
    }

    private String getCellValue(Cell c) {
        String o = null;
        switch (c.getCellType()) {
            case Cell.CELL_TYPE_BLANK:
                o = ""; break;
            case Cell.CELL_TYPE_BOOLEAN:
                o = String.valueOf(c.getBooleanCellValue()); break;
            case Cell.CELL_TYPE_FORMULA:
                o = String.valueOf(c.getCellFormula()); break;
            case Cell.CELL_TYPE_NUMERIC:
                o = String.valueOf(c.getNumericCellValue()); break;
            case Cell.CELL_TYPE_STRING:
                o = c.getStringCellValue(); break;
            default:
                o = null;
                break;
        }
        return o;
    }

    private List<Object> handlerExcel2Objs(Workbook wb,Class clz,int readLine,int tailLine) {
        Sheet sheet = wb.getSheetAt(0);
        List<Object> objs = null;
        try {
            Row row = sheet.getRow(readLine);
            objs = new ArrayList<Object>();
            Map<Integer,String> maps = getHeaderMap(row, clz);
            if(maps==null||maps.size()<=0) throw new RuntimeException("要读取的Excel的格式不正确，检查是否设定了合适的行");
            for(int i=readLine+1;i<=sheet.getLastRowNum()-tailLine;i++) {
                row = sheet.getRow(i);
                Object obj = clz.newInstance();
                for(Cell c:row) {
                    int ci = c.getColumnIndex();
                    String mn = maps.get(ci).substring(3);
                    mn = mn.substring(0,1).toLowerCase()+mn.substring(1);
                    BeanUtils.copyProperty(obj,mn, this.getCellValue(c));
                }
                objs.add(obj);
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return objs;
    }

    private List<ExcelHeader> getHeaderList(Class clz) {
        List<ExcelHeader> headers = new ArrayList<ExcelHeader>();
        Method[] ms = clz.getDeclaredMethods();
        for(Method m:ms) {
            String mn = m.getName();
            if(mn.startsWith("get")) {
                if(m.isAnnotationPresent(ExcelResources.class)) {
                    ExcelResources er = m.getAnnotation(ExcelResources.class);
                    headers.add(new ExcelHeader(er.title(),er.order(),mn));
                }
            }
        }
        return headers;
    }

    private Map<Integer,String> getHeaderMap(Row titleRow,Class clz) {
        List<ExcelHeader> headers = getHeaderList(clz);
        Map<Integer,String> maps = new HashMap<Integer, String>();
        for(Cell c:titleRow) {
            String title = c.getStringCellValue();
            for(ExcelHeader eh:headers) {
                if(eh.getTitle().equals(title.trim())) {
                    maps.put(c.getColumnIndex(), eh.getMethodName().replace("get","set"));
                    break;
                }
            }
        }
        return maps;
    }

}
