package com.challenge.book.catalog.service.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ResourceUtils;

import com.challenge.book.catalog.exception.ReportErrorException;
import com.challenge.book.catalog.repository.ReportRepository;
import com.challenge.book.catalog.service.BookService;
import com.challenge.book.catalog.service.ReportService;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.util.JRSaver;

@Service
public class ReportServiceImpl implements ReportService {
    private final ReportRepository viewRepository;

    public ReportServiceImpl(ReportRepository viewRepository) {
        this.viewRepository = viewRepository;
    }

    public byte[] getReport() {
        JasperReport jasperReport;
        try {
            jasperReport = (JasperReport) JRLoader.loadObject(ResourceUtils.getFile("classpath:report.jasper"));
        } catch (FileNotFoundException | JRException e) {
            try {
                File file = ResourceUtils.getFile("classpath:report.jrxml");
                jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
                JRSaver.saveObject(jasperReport, "classpath:report.jasper");
            } catch (FileNotFoundException | JRException ex) {
                throw new ReportErrorException();
            }
        }

        final Map<String, Object> parameters = new HashMap<>();
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(
                viewRepository.findAll(Sort.by(Sort.Direction.ASC, "author")));
        JasperPrint jasperPrint = null;
        byte[] reportContent;

        try {
            jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
            reportContent = JasperExportManager.exportReportToPdf(jasperPrint);
        } catch (JRException e) {
            throw new ReportErrorException();
        }
        return reportContent;
    }
}
