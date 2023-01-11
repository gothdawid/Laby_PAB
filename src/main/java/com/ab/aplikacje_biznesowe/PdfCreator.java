package com.ab.aplikacje_biznesowe;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Stream;

public class PdfCreator {
    public void PrintAllUsers(Collection<User> users) throws FileNotFoundException, DocumentException {
        // tworzenie tabelki
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream("iTextTable.pdf"));
        document.open();
        Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
        Chunk chunk = new Chunk("Users", font);
        document.add(chunk);
        PdfPTable table = new PdfPTable(6);
        addTableHeader(table, new String[] { "Id", "First Name", "Last Name", "Address", "City", "Group Id" });
        for (User user: users) {
            table.addCell(user.getId().toString());
            table.addCell(user.getFirst_name());
            table.addCell(user.getLast_name());
            table.addCell(user.getAddress());
            table.addCell(user.getCity());
            table.addCell(user.getGroup_id().toString());
        }
        document.add(table);
        document.close();
    }

    public static void main(String[] args) throws DocumentException, FileNotFoundException {
        var creator = new PdfCreator();
        var users = new ArrayList<User>();
        users.add(new User(1, "Tuptas", "Hashtag", "NS", "Tuptasowo", 1, "", false, "", "", "", true));
        users.add(new User(2, "Tuptas2", "Hashtag2", "NS", "Tuptasowo", 2, "", false, "", "", "", true));
        creator.PrintAllUsers(users);
    }

    private void addTableHeader(PdfPTable table, String[] headers) {
        Stream.of(headers).forEach(columnTitle -> {
            PdfPCell header = new PdfPCell();
            header.setBackgroundColor(BaseColor.LIGHT_GRAY);
            header.setBorderWidth(2);
            header.setPhrase(new Phrase(columnTitle));
            table.addCell(header);
        });
    }
}
