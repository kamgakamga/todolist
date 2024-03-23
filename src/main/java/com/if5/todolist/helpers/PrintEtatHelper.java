package com.if5.todolist.helpers;

import com.if5.todolist.dtos.requets.systeme.ParamEtatDTO;
import com.if5.todolist.dtos.requets.systeme.ParamImpressionDTO;
import com.if5.todolist.models.entities.systeme.EtatImprimable;
import com.if5.todolist.utils.GeneralUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import org.springframework.core.io.FileSystemResource;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.if5.todolist.utils.GeneralUtils.LOGO_PAR_DEFAUT;
import static com.if5.todolist.utils.GeneralUtils.REPORTS_DIR_PATH;
import static com.if5.todolist.utils.StringsUtils.*;

/**
 * Copyright (c) 2024, Iforce5, All Right Reserved.
 * https://iforce5.com
 * <p>
 * When: @created 02/March/2024 -- 2:17 PM
 *
 * @author name :  Serges KAMGA on 3/2/2024
 * @author email : kamgakamga93@gmail.com /  serge.kamga@iforce5.com
 * Project : @project todolist
 * Package : @package com.if5.todolist.helpers
 **/
@Slf4j
@AllArgsConstructor
public class PrintEtatHelper {
    public static final String REPORTS_DIR = REPORTS_DIR_PATH;
    private static final String LOGO_PATH = LOGO_PAR_DEFAUT;

    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");
    private static final DateTimeFormatter shortDateFormatter = DateTimeFormatter.ofPattern("dd-MM-yy");


    /**
     * @param paramImpression parametres de l'impression
     * @param conn            Connexion a la BD
     * @param response        RAS
     * @throws IOException si le template du rapport n'est pas trouve
     * @throws JRException si le template n'a pas pu etre correctement rempli
     * @apiNote Constitue le rapport d'un etat
     */
    public static void imprimerEtatImprimable(EtatImprimable stored, ParamImpressionDTO paramImpression, Map<String, Object> params,
                                              Connection conn, HttpServletResponse response)
            throws IOException, JRException {
        log.info(stored.getChemin());
        String path = REPORTS_DIR_PATH + stored.getChemin();
        log.info(path);
        params.put(SUBREPORT_DIR, REPORTS_DIR_PATH);
      //  params.put(FILIGRANE_PAR_DEFAUT, FILLIGRANE_PAR_DEFAUT);
        FileSystemResource fsr = new FileSystemResource(path);
        InputStream jasperStream = fsr.getInputStream();
        String filename = "ETAT_" +GeneralUtils.replaceSpaceByUndescore(stored.getLibelle())+ "_" + LocalDateTime.now();
        // on elimine les cles n'ayant pas de valeur
        List<ParamEtatDTO> nonNulParams = paramImpression.getParamEtats().stream().filter(paramEtatDTO -> Objects.nonNull(paramEtatDTO.getValeur())).collect(Collectors.toList());
        nonNulParams.forEach(param -> params.put(param.getTexte(), param.getValeur()));
        // Ajout de l'en-tete si xa n'existe pas encore
        params.forEach(params::putIfAbsent);
        JasperReport jasperReport = (JasperReport) JRLoader.loadObject(jasperStream);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, conn);

        String prefix = GeneralUtils.replaceSpaceByUndescore(filename);
        if (paramImpression.getExporter()) {
            exportToExcel(jasperPrint, prefix, response);
        } else {
            String inline = INLINES + prefix + ".pdf";
            GeneralUtils.initHeaderDocumentPDF(response, inline);
            final OutputStream outStream = response.getOutputStream();
            JasperExportManager.exportReportToPdfStream(jasperPrint, outStream);
            outStream.flush();
        }
    }
    private static void exportToExcel(JasperPrint jasperPrint, String filename, HttpServletResponse response) throws IOException, JRException {
        response.setContentType(CONTENT_TYPE_XLSX);
        response.setHeader(CONTENT_DISPOSITION, ATTACHEMENTS + filename + ".xls");
        ServletOutputStream out = response.getOutputStream();
        JRXlsExporter exporter = new JRXlsExporter();
        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(out));
        exporter.exportReport();
        out.flush();
    }
}
