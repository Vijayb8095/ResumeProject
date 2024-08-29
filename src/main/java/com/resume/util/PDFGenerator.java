package com.resume.util;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.resume.model.Resume;

import java.io.FileOutputStream;
import java.io.IOException;

public class PDFGenerator {

	public static void generateResumePDF(Resume resume, String filePath) throws DocumentException, IOException {
		Document document = new Document();
		PdfWriter.getInstance(document, new FileOutputStream(filePath));
		document.open();
		document.add(new Paragraph("Name: " + resume.getName()));
		document.add(new Paragraph("Email: " + resume.getEmail()));
		document.add(new Paragraph("Phone: " + resume.getPhone()));
		document.add(new Paragraph("Skills: " + resume.getSkills()));
		document.add(new Paragraph("Experience: " + resume.getExperience()));
		document.add(new Paragraph("Education: " + resume.getEducation()));
		document.close();
	}

}
