package com.ab.aplikacje_biznesowe;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.awt.print.PrinterJob;
import java.awt.print.PrinterException;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.PageRanges;
import javax.print.attribute.standard.Sides;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.interactive.viewerpreferences.PDViewerPreferences;
import org.apache.pdfbox.printing.PDFPageable;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.stream.Stream;

import static com.ab.aplikacje_biznesowe.HelloApplication.logger;


public class PdfCreator {
    private Font getDefaultFont() {
        return FontFactory.getFont(FontFactory.TIMES_ROMAN, BaseFont.CP1250, false);
    }

    public void createPdf(Collection<User> users) throws IOException, DocumentException {
        // tworzenie tabelki
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream("iTextTable.pdf"));
        document.open();
        Font font = FontFactory.getFont(FontFactory.TIMES_ROMAN, BaseFont.CP1250, false, 16);
        addTitle(document, font);

        PdfPTable table = new PdfPTable(new float[] { 9, 18, 25, 30, 18 });
        addTableHeader(table, new String[] { "ID", "Imię", "Nazwisko", "Adres", "Miasto" });
        Font baseFont = getDefaultFont();
        int i = 0;
        for (User user: users) {
            i++;
            if(i % 46 == 0 && i < 50)
            {
                logger.info("Headear");
                addTableHeader(table, new String[] { "ID", "Imię", "Nazwisko", "Adres", "Miasto" });
            } else if (i % 46 == 0 && i > 85) {

                logger.info("Headear");
                addTableHeader(table, new String[] { "ID", "Imię", "Nazwisko", "Adres", "Miasto" });
            }

            table.addCell(new PdfPCell(new Phrase(user.getId().toString(), baseFont)));
            table.addCell(new PdfPCell(new Phrase(user.getFirst_name(), baseFont)));
            table.addCell(new PdfPCell(new Phrase(user.getLast_name(), baseFont)));
            table.addCell(new PdfPCell(new Phrase(user.getAddress(), baseFont)));
            table.addCell(new PdfPCell(new Phrase(user.getCity(), baseFont)));
        }


        document.add(table);
        document.close();
    }

    private void addTitle(Document document, Font font) throws DocumentException {
        Chunk chunk = new Chunk("Lista użytkowników", font);
        Phrase phrase = new Phrase();
        phrase.add(chunk);
        Paragraph para = new Paragraph();
        para.add(phrase);
        para.setAlignment(Element.ALIGN_CENTER);
        document.add(para);
        addNewLine(document);
    }

    public void printWithDialog(PDDocument document) throws PrinterException
    {
        try {
            PrinterJob job = PrinterJob.getPrinterJob();
            job.setPageable(new PDFPageable(document));
            if (job.printDialog())
            {
                job.print();
            }
        } catch (Exception e) {
            logger.info("Problem z wydrukiem." + e.toString());
        }

    }
    public void print(PDDocument document) throws PrinterException
    {
        PrinterJob job = PrinterJob.getPrinterJob();
        job.setPageable(new PDFPageable(document));
        PrintRequestAttributeSet attr = new HashPrintRequestAttributeSet();
        attr.add(new PageRanges(1, 1)); // pages 1 to 1

        PDViewerPreferences vp = document.getDocumentCatalog().getViewerPreferences();
        if (vp != null && vp.getDuplex() != null)
        {
            String dp = vp.getDuplex();
            if (PDViewerPreferences.DUPLEX.DuplexFlipLongEdge.toString().equals(dp))
            {
                attr.add(Sides.TWO_SIDED_LONG_EDGE);
            }
            else if (PDViewerPreferences.DUPLEX.DuplexFlipShortEdge.toString().equals(dp))
            {
                attr.add(Sides.TWO_SIDED_SHORT_EDGE);
            }
            else if (PDViewerPreferences.DUPLEX.Simplex.toString().equals(dp))
            {
                attr.add(Sides.ONE_SIDED);
            }
        }
        if (job.printDialog(attr))
        {
            job.print(attr);
        }
    }

    private void addTableHeader(PdfPTable table, String[] headers) {
        Stream.of(headers).forEach(columnTitle -> {
            PdfPCell header = new PdfPCell();
            header.setBackgroundColor(BaseColor.LIGHT_GRAY);
            header.setBorderWidth(1.25f);
            header.setPhrase(new Phrase(columnTitle, getDefaultFont()));
            table.addCell(header);
        });
    }

    private void addNewLine(Document document) throws DocumentException {
        document.add(new Paragraph("\n"));
    }
}
