package com.ab.aplikacje_biznesowe;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Map;
import java.util.stream.Stream;

public class PdfCreator {
    private Font getDefaultFont() {
        return FontFactory.getFont(FontFactory.TIMES_ROMAN, BaseFont.CP1250, false);
    }

    public void PrintAllUsers(Collection<User> users) throws IOException, DocumentException {
        // tworzenie tabelki
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream("iTextTable.pdf"));
        document.open();
        Font font = FontFactory.getFont(FontFactory.TIMES_ROMAN, BaseFont.CP1250, false, 16);
        Chunk chunk = new Chunk("Lista użytkowników", font);
        Phrase phrase = new Phrase();
        phrase.add(chunk);
        Paragraph para = new Paragraph();
        para.add(phrase);
        para.setAlignment(Element.ALIGN_CENTER);
        document.add(para);
        addNewLine(document);
        PdfPTable table = new PdfPTable(new float[] { 12, 18, 25, 30, 15 });
        addTableHeader(table, new String[] { "ID", "Imię", "Nazwisko", "Adres", "Miasto" });
        Font baseFont = getDefaultFont();
        for (User user: users) {
            table.addCell(new PdfPCell(new Phrase(user.getId().toString(), baseFont)));
            table.addCell(new PdfPCell(new Phrase(user.getFirst_name(), baseFont)));
            table.addCell(new PdfPCell(new Phrase(user.getLast_name(), baseFont)));
            table.addCell(new PdfPCell(new Phrase(user.getAddress(), baseFont)));
            table.addCell(new PdfPCell(new Phrase(user.getCity(), baseFont)));
        }
        document.add(table);
        document.close();
    }

    public static void main(String[] args) throws DocumentException, IOException {
        var creator = new PdfCreator();
        var users = new ArrayList<User>();
        users.add(new User(1, "Tuptas", "Hashtag", "NS", "Tuptasowo", 1, "", false, "", "", "", true));
        users.add(new User(2, "Tuptas2", "Hashtag2", "NS", "Zielona Góra", 2, "", false, "", "", "", true));
        creator.PrintAllUsers(users);
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
