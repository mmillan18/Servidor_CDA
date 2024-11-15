package Servidor_CDA.Servidor_CDA;

import Servidor_CDA.Servidor_CDA.model.CertificadoTecnicoMecanica;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;

public class PDFGenerator {

    public static byte[] generateCertificadoPDF(CertificadoTecnicoMecanica certificado) throws DocumentException {
        Document document = new Document();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, byteArrayOutputStream);

        document.open();
        document.add(new Paragraph("Certificado Técnico Mecánico"));
        document.add(new Paragraph("ID Certificado: " + certificado.getId()));
        document.add(new Paragraph("Fecha de Emisión: " + certificado.getFechaEmision()));
        document.add(new Paragraph("Fecha de Vencimiento: " + certificado.getFechaVencimiento()));
        document.add(new Paragraph("Resultado de la Revisión: " + (certificado.getRevision().isResultadoRevision() ? "Aprobado" : "Reprobado")));
        document.close();

        return byteArrayOutputStream.toByteArray();
    }
}
