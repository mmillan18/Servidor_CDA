package Servidor_CDA.Servidor_CDA.services;

import Servidor_CDA.Servidor_CDA.PDFGenerator;
import Servidor_CDA.Servidor_CDA.model.CertificadoTecnicoMecanica;
import Servidor_CDA.Servidor_CDA.model.Empleado;
import Servidor_CDA.Servidor_CDA.model.Revision;
import Servidor_CDA.Servidor_CDA.model.Vehiculo;
import Servidor_CDA.Servidor_CDA.notification.EmailService;
import Servidor_CDA.Servidor_CDA.repository.EmpleadoRepository;
import Servidor_CDA.Servidor_CDA.repository.RevisionRepository;
import Servidor_CDA.Servidor_CDA.repository.VehiculoRepository;
import com.itextpdf.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
public class RevisionService {

    @Autowired
    private RevisionRepository revisionRepository;

    @Autowired
    private VehiculoRepository vehiculoRepository;

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Autowired
    private CertificadoTecnicoMecanicaService certificadoService;

    @Autowired
    private PDFGenerator pdfGenerator;

    @Autowired
    private EmailService emailService;

    public Revision createOrUpdateRevision(String vehiculoPlaca, Revision revision, String username)
            throws MessagingException, DocumentException, IOException {
        // Buscar el vehículo por su placa
        Vehiculo vehiculo = vehiculoRepository.findById(vehiculoPlaca)
                .orElseThrow(() -> new RuntimeException("Vehículo no encontrado con placa: " + vehiculoPlaca));

        // Buscar el empleado por el username
        Empleado empleado = empleadoRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado con username: " + username));

        // Asignar el vehículo y el empleado a la revisión
        revision.setVehiculo(vehiculo);
        revision.setEmpleadoEncargado(empleado);

        // Guardar la revisión
        Revision savedRevision = revisionRepository.save(revision);

        // Generar el certificado técnico mecánico independientemente del resultado de la revisión
        CertificadoTecnicoMecanica certificado = new CertificadoTecnicoMecanica();
        certificado.setFechaEmision(new Date());
        certificado.setFechaVencimiento(calcularFechaVencimiento(revision.isResultadoRevision()));
        certificado.setRevision(savedRevision);
        certificado.setEmpleadoEncargado(empleado); // Asignar el empleado al certificado

        // Guardar el certificado
        CertificadoTecnicoMecanica savedCertificado = certificadoService.createCertificado(certificado);

        // Generar el PDF del certificado (incluyendo el nombre del empleado)
        byte[] pdfBytes = pdfGenerator.generateCertificadoPDF(savedCertificado);

        // Enviar el correo al usuario asociado al vehículo
        String email = vehiculo.getUsuario().getCorreo();
        String subject = "Certificado Técnico Mecánica";
        emailService.sendNotificationWithAttachment(
                email,
                subject,
                "notificationTemplate",
                revision.isResultadoRevision(),
                revision.getFechaRevision(),
                pdfBytes
        );

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
