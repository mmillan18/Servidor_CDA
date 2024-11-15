package Servidor_CDA.Servidor_CDA.services;

import Servidor_CDA.Servidor_CDA.PDFGenerator;
import Servidor_CDA.Servidor_CDA.model.CertificadoTecnicoMecanica;
import Servidor_CDA.Servidor_CDA.model.Revision;
import Servidor_CDA.Servidor_CDA.model.Vehiculo;
import Servidor_CDA.Servidor_CDA.notification.EmailService;
import Servidor_CDA.Servidor_CDA.repository.RevisionRepository;
import Servidor_CDA.Servidor_CDA.repository.VehiculoRepository;
import com.itextpdf.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import java.util.Date;
import java.util.List;

@Service
public class RevisionService {

    @Autowired
    private RevisionRepository revisionRepository;

    @Autowired
    private VehiculoRepository vehiculoRepository;

    @Autowired
    private CertificadoTecnicoMecanicaService certificadoService;

    @Autowired
    private PDFGenerator pdfGenerator;

    @Autowired
    private EmailService emailService;

    // Método para crear o actualizar una revisión, generar el certificado y enviar el correo
    public Revision createOrUpdateRevision(Long vehiculoId, Revision revision) throws MessagingException, DocumentException {
        // Buscar el vehículo asociado
        Vehiculo vehiculo = vehiculoRepository.findById(String.valueOf(vehiculoId))
                .orElseThrow(() -> new RuntimeException("Vehículo no encontrado con ID: " + vehiculoId));

        // Asignar el vehículo a la revisión
        revision.setVehiculo(vehiculo);
        Revision savedRevision = revisionRepository.save(revision);

        // Crear y guardar el certificado técnico mecánico
        CertificadoTecnicoMecanica certificado = new CertificadoTecnicoMecanica();
        certificado.setFechaEmision(new Date());
        certificado.setFechaVencimiento(calcularFechaVencimiento(revision.isResultadoRevision()));
        certificado.setRevision(savedRevision);
        CertificadoTecnicoMecanica savedCertificado = certificadoService.createCertificado(certificado);

        // Generar el PDF del certificado
        byte[] pdfBytes = pdfGenerator.generateCertificadoPDF(savedCertificado);

        // Enviar el correo electrónico con el PDF adjunto
        String email = vehiculo.getUsuario().getCorreo();
        String subject = "Certificado Técnico Mecánica";
        emailService.sendNotificationWithAttachment(email, subject, "certificadoTemplate",
                revision.isResultadoRevision(), revision.getFechaRevision(), pdfBytes);

        return savedRevision;
    }

    // Método para calcular la fecha de vencimiento del certificado
    private Date calcularFechaVencimiento(boolean resultadoRevision) {
        int dias = resultadoRevision ? 365 : 15;
        Date fechaVencimiento = new Date();
        fechaVencimiento.setTime(fechaVencimiento.getTime() + (long) dias * 24 * 60 * 60 * 1000);
        return fechaVencimiento;
    }

    // Método para obtener todas las revisiones
    public List<Revision> getAllRevisions() {
        return revisionRepository.findAll();
    }

    // Método para obtener una revisión por su ID
    public Revision getRevisionById(Long id) {
        return revisionRepository.findById(id).orElse(null);
    }
}
