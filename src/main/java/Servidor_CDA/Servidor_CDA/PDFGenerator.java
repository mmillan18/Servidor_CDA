package Servidor_CDA.Servidor_CDA;

import Servidor_CDA.Servidor_CDA.model.CertificadoTecnicoMecanica;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class PDFGenerator {

    public static byte[] generateCertificadoPDF(CertificadoTecnicoMecanica certificado) throws DocumentException, IOException {
        Document document = new Document();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, byteArrayOutputStream);
        Font font = FontFactory.getFont(FontFactory.TIMES_BOLDITALIC, 24);
        Font font2 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 14);
        Font font3 = FontFactory.getFont(FontFactory.TIMES_BOLD, 18);
        Image image = Image.getInstance("src/data/Logo.jfif"); // Reemplaza con la ruta de tu imagen

        document.open();
        document.add(new Paragraph("Certificado Técnico Mecánico                       CDA", font));
        document.add(new Paragraph(" "));
        document.add(new Paragraph(" "));
        document.add(new Paragraph("ID Certificado: " + certificado.getId(),font2));
        document.add(new Paragraph("Fecha de Emisión: " + certificado.getFechaEmision(),font2));
        document.add(new Paragraph("Fecha de Vencimiento: " + certificado.getFechaVencimiento(),font2));
        document.add(new Paragraph(" "));
        document.add(new Paragraph("Resultado de la Revisión: " + (certificado.getRevision().isResultadoRevision() ? "Aprobado" : "Reprobado"),font3));
        image.scaleToFit(200, 200); // Escala la imagen (opcional)
        image.setAlignment(Image.ALIGN_BOTTOM); // Alinea la imagen al centro (opcional)
        document.add(image); // Añade la imagen al documento
        document.close();

        return byteArrayOutputStream.toByteArray();
    }
}
