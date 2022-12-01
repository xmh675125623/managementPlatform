package com.jiuzhou.plat.pdfUtil;


import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;

/**
* @author xingmh
* @version 创建时间：2018年10月29日 下午3:30:55
* 类说明
*/
public class PDFUtil {
	/**
     * 获取指定内容与字体的单元格
     * @param string
     * @param font
     * @return
     */
    public static PdfPCell getPDFCell(String string, Font font) {
        //创建单元格对象，将内容与字体放入段落中作为单元格内容
        PdfPCell cell=new PdfPCell(new Paragraph(string,font));
 
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
 
        //设置最小单元格高度
        cell.setMinimumHeight(25);
        return cell;
    }
    
    /**
     * 合并行的静态函数
     * @param str
     * @param font
     * @param i
     * @return
     */
    public static PdfPCell mergeRow(String str,Font font,int i) {
 
        //创建单元格对象，将内容及字体传入
        PdfPCell cell=new PdfPCell(new Paragraph(str,font));
        //设置单元格内容居中
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        //将该单元格所在列包括该单元格在内的i行单元格合并为一个单元格
        cell.setRowspan(i);
 
        return cell;
    }
 
    /**
     * 合并列的静态函数
     * @param str
     * @param font
     * @param i
     * @return
     */
    public static PdfPCell mergeCol(String str,Font font,int i) {
 
        PdfPCell cell=new PdfPCell(new Paragraph(str,font));
        cell.setMinimumHeight(25);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        //将该单元格所在行包括该单元格在内的i列单元格合并为一个单元格
        cell.setColspan(i);
 
        return cell;
    }
    
    
}
