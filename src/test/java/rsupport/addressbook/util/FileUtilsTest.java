package rsupport.addressbook.util;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.stream.Stream;
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
    void csv_파일읽기() {
        //given

        //when
        Stream<String> result = FileUtils.readCsvFile(propertyUtils.getAddressbookFilePath());

        //then
        if (result==null) {
            fail();
        }
    }

    @Test
    void csv_파일_notFound() {
        //given

        //when
        assertThrows(BaseException.class, () -> {
            FileUtils.readCsvFile(null);
        });

        //then
    }

}
