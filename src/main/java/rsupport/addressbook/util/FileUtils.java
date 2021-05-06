package rsupport.addressbook.util;

import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.stream.Stream;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import rsupport.addressbook.exception.BaseException;
import rsupport.addressbook.exception.ExceptionCode;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FileUtils {

    public static Stream<String> readCsvFile(String path) {

        try {
            return Files.lines(Paths.get(path));
        } catch (NoSuchFileException e) {
            throw new BaseException(ExceptionCode.FILE_NOT_FOUND);
        } catch (Exception e) {
            throw new BaseException(ExceptionCode.RUNTIME_ERROR);
        }

    }
}
