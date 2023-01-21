package design.alberton.controller.helper;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static java.lang.String.format;
import static java.util.Spliterators.spliteratorUnknownSize;
import static org.apache.commons.io.FilenameUtils.getExtension;

public class ExcelHelper {

    public static List<String> readFile(final Path path) {

        final File file = path.toFile();

        if (!getExtension(file.getName()).equals("xlsx")) {
            throw new IllegalArgumentException(format("File at: %s is not a valid excel file", path));
        }
        if (!file.exists()) {
            throw new IllegalArgumentException(format("Excel file does not exist at: %s", path));
        }

        try (final XSSFWorkbook workbook = new XSSFWorkbook(file)) {
            final XSSFSheet sheet = workbook.getSheetAt(0);
            return iteratorToStream(sheet.rowIterator()).skip(1).map(ExcelHelper::readRow).toList();
        } catch (final IOException | InvalidFormatException e) {
            throw new IllegalStateException(format("Error reading excel from file at: %s", path));
        }
    }

    private static String readRow(final Row row) {
        final List<String> data = iteratorToStream(row.cellIterator()).map(Cell::toString).toList();
        return String.valueOf(data);
    }

    private static int getLastCellNum(final Row row) {
        return row.getLastCellNum() == -1 ? 0 : row.getLastCellNum();
    }

    public static <T> Stream<T> iteratorToStream(final Iterator<T> iterator) {
        final Spliterator<T> spliterator = spliteratorUnknownSize(iterator, 0);
        return StreamSupport.stream(spliterator, false);
    }
}
