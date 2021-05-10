package rsupport.addressbook.util;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import rsupport.addressbook.exception.BaseException;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class FileUtilsTest {

    @Autowired private PropertyUtils propertyUtils;

    @Test
    @DisplayName("csv 파일 읽기")
    void readFile() {
        //given

        //when
        Stream<String> result = FileUtils.readCsvFile(propertyUtils.getAddressbookFilePath());

        //then
        Assertions.assertThat(result)
            .isNotEmpty();
    }

    @Test
    @DisplayName("csv 파일 예외처리")
    void readFileException() {
        //given

        //when
        assertThrows(BaseException.class, () -> {
            FileUtils.readCsvFile(null);
        });

        //then
    }

}
