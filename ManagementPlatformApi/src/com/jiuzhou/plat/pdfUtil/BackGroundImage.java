package com.jiuzhou.plat.pdfUtil;

import java.io.IOException;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

/**
* @author xingmh
* @version 创建时间：2018年10月29日 下午3:54:46
* 为每一页pdf都生成背景图片
*/
public class BackGroundImage extends PdfPageEventHelper {

	private String picPath = "";
	 
    public String getPicPath() {
        return picPath;
    }
 
    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }
 
    public BackGroundImage(){
 
    }
 
    public BackGroundImage(String path){
        this.picPath = path;
    }
 
    @Override
    public void onStartPage(PdfWriter pdfWriter, Document document) {
        try {
            Image image = Image.getInstance(picPath);
            image.setAlignment(Image.UNDERLYING);
            image.setAbsolutePosition(0,0);
            image.scaleAbsolute(595,842);
            document.add(image);
        } catch (BadElementException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
 
        super.onStartPage(pdfWriter, document);
    }

}
