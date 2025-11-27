package com.erp.studenterp.service;

import com.erp.studenterp.dto.StudentResponse;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;

@Service
public class PdfService {

    public byte[] generateIdCard(StudentResponse student) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try {
            PdfWriter writer = new PdfWriter(baos);
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document document = new Document(pdfDoc);

            // Title
            Paragraph title = new Paragraph("STUDENT IDENTIFICATION CARD")
                    .setFontSize(20)
                    .setBold()
                    .setTextAlignment(TextAlignment.CENTER)
                    .setFontColor(new DeviceRgb(0, 51, 102));
            document.add(title);

            // Add spacing
            document.add(new Paragraph("\n"));

            // Student details table
            Table table = new Table(UnitValue.createPercentArray(new float[]{30, 70}));
            table.setWidth(UnitValue.createPercentValue(100));

            // Add rows
            addTableRow(table, "Roll Number:", student.getRollNumber());
            addTableRow(table, "Name:", student.getFirstName() + " " + student.getLastName());
            addTableRow(table, "Email:", student.getEmail());
            addTableRow(table, "Phone:", student.getPhone() != null ? student.getPhone() : "N/A");
            addTableRow(table, "Department:", student.getDepartment());
            addTableRow(table, "Course:", student.getCourse());
            addTableRow(table, "Semester:", String.valueOf(student.getSemester()));
            addTableRow(table, "Date of Birth:", student.getDateOfBirth() != null ?
                    student.getDateOfBirth().toString() : "N/A");
            addTableRow(table, "Enrollment Date:", student.getEnrollmentDate() != null ?
                    student.getEnrollmentDate().toString() : "N/A");

            document.add(table);

            // Footer
            document.add(new Paragraph("\n\n"));
            Paragraph footer = new Paragraph("This is a digitally generated ID card.")
                    .setFontSize(10)
                    .setItalic()
                    .setTextAlignment(TextAlignment.CENTER)
                    .setFontColor(ColorConstants.GRAY);
            document.add(footer);

            document.close();

            return baos.toByteArray();

        } catch (Exception e) {
            throw new RuntimeException("Error generating PDF: " + e.getMessage());
        }
    }

    private void addTableRow(Table table, String label, String value) {
        table.addCell(new Paragraph(label).setBold());
        table.addCell(new Paragraph(value));
    }
}