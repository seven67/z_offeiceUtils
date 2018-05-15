package person.seven.pdf;

import com.google.common.collect.Maps;
import org.apache.poi.xwpf.converter.pdf.PdfConverter;
import org.apache.poi.xwpf.converter.pdf.PdfOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import person.seven.excel.ExcelReadUtil;
import person.seven.word.WordReplaceUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

/**
 * @author liuqi
 * @create 2017-11-06
 **/
public class Word2Excel {

    private static final String FILE_PATH = "";
    private static final String USER_NAME = "user_name";
    private static final String SEVEN = "seven";
    private static final String PDF_STUFF = ".pdf";


    public static void main(String[] args) {
        /*  读取excel */
        readExcel();
        /*  替换word生成word */
        word2word();
        /*  替换word生成pdf */
        word2pdf();

    }

    private static void word2pdf() {
        Map<String,Object> param = Maps.newHashMap();
        param.put(USER_NAME, SEVEN);
        XWPFDocument doc = WordReplaceUtil.generateWord(param, FILE_PATH);
        String outPath = new StringBuffer().append(FILE_PATH).append(PDF_STUFF).toString();
        OutputStream fopts = null;
        try {
            fopts = new FileOutputStream(outPath);
            PdfOptions pdfOptions = PdfOptions.create();
            PdfConverter.getInstance().convert(doc, fopts, pdfOptions);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private static void word2word() {
        Map<String,Object> param = Maps.newHashMap();
        param.put(USER_NAME, SEVEN);
        XWPFDocument doc = WordReplaceUtil.generateWord(param, FILE_PATH);
        try {
            doc.write(new FileOutputStream(new File("")));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void readExcel() {
        try {
            List<String[]> list = ExcelReadUtil.readExcel(new File(FILE_PATH));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
