package com.company.reports;

import com.company.database.Database;
import com.company.model.Course;
import com.company.model.Pupil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class GenerateFiles {
    public static File getAllPupilBuCourse() {
        File file = new File("src/main/resources/files/tableCourse.xls");
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            XSSFWorkbook workbook = new XSSFWorkbook();

            for (Course course : Database.COURSE_LIST) {
                Sheet sheet = workbook.createSheet(course.getName());

                Row header = sheet.createRow(0);
                header.createCell(0).setCellValue("Id");
                header.createCell(1).setCellValue("Firstname");
                header.createCell(2).setCellValue("Lastname");
                header.createCell(3).setCellValue("Age");

                int number = 0;
                for (Pupil pupil : Database.PUPIL_LIST) {
                    if (pupil.getCourseId().equals(course.getId())) {
                        Row row = sheet.createRow(++number);
                        row.createCell(0).setCellValue(number);
                        row.createCell(1).setCellValue(pupil.getFirstName());
                        row.createCell(2).setCellValue(pupil.getLastName());
                        row.createCell(3).setCellValue(pupil.getAge());
                    }
                }

                for (int i = 0; i < 4; i++) {
                    sheet.autoSizeColumn(i);
                }

            }
            workbook.write(new FileOutputStream(file));
            workbook.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return file;
    }

    public static File getAllPupil() {
        File file = new File("src/main/resources/files/tablePupil.xls");
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            XSSFWorkbook workbook = new XSSFWorkbook();

            Sheet sheet = workbook.createSheet("O'quvchilar ro'yhati");

            Row header = sheet.createRow(0);
            header.createCell(0).setCellValue("Id");
            header.createCell(1).setCellValue("Firstname");
            header.createCell(2).setCellValue("Lastname");
            header.createCell(3).setCellValue("Age");

            int number = 0;
            for (Pupil pupil : Database.PUPIL_LIST) {
                Row row = sheet.createRow(++number);
                row.createCell(0).setCellValue(pupil.getId());
                row.createCell(1).setCellValue(pupil.getFirstName());
                row.createCell(2).setCellValue(pupil.getLastName());
                row.createCell(3).setCellValue(pupil.getAge());
            }

            for (int i = 0; i < 4; i++) {
                sheet.autoSizeColumn(i);
            }
            workbook.write(new FileOutputStream(file));
            workbook.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return file;
    }
}
