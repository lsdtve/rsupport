package rsupport.addressbook.util;

import org.springframework.stereotype.Component;
import rsupport.addressbook.exception.BaseException;
import rsupport.addressbook.exception.EnumBaseException;

import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

@Component
public class FileUtils {

    public Stream<String> readCsvFile(String path) {

        Path filePath = Paths.get(path);
        Stream<String> result = Stream.empty();

        try {
            result =  Files.lines(filePath);
        } catch (NoSuchFileException e) {
            throw new BaseException(EnumBaseException.FILE_NOT_FOUND);
        } catch (Exception e) {
            throw new BaseException(EnumBaseException.RUNTIME_ERROR);
        }

        return result;
    }
}
