package Servidor_CDA.Servidor_CDA;

import Servidor_CDA.Servidor_CDA.model.QR;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.ByteArrayOutputStream;
import java.util.List;

public class QRReportPDFGenerator {

    public static byte[] generateReport(List<QR> reportes, String titulo) {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Document document = new Document();
            PdfWriter.getInstance(document, out);
            document.open();

            // Agregar título
            document.add(new Paragraph(titulo, FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18)));
            document.add(new Paragraph(" ")); // Espacio en blanco

            // Tabla de reportes
            PdfPTable table = new PdfPTable(2);
            table.setWidthPercentage(100);
            table.setWidths(new int[]{1, 3});

            PdfPCell hcell;
            hcell = new PdfPCell(new Phrase("ID"));
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(hcell);

            hcell = new PdfPCell(new Phrase("Quejas y Recomendaciones"));
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(hcell);

            for (QR qr : reportes) {
                PdfPCell cell;

                cell = new PdfPCell(new Phrase(String.valueOf(qr.getId())));
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(qr.getQuejasRecomendaciones()));
                cell.setPaddingLeft(5);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                table.addCell(cell);
            }

            document.add(table);
            document.close();

            return out.toByteArray();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
