package rsupport.addressbook.util;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import rsupport.addressbook.exception.BaseException;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class FileUtilsTest {

    @Autowired private PropertyUtils propertyUtils;
    @Autowired private FileUtils fileUtils;

    @Test
    public void csv_파일읽기() {
        //given

        //when
        Stream<String> result = fileUtils.readCsvFile(propertyUtils.getAddressbookFilePath());

        //then
        if (result==null) {
            Assert.fail();
        }
    }

    @Test(expected = BaseException.class)
    public void csv_파일_notFound() {
        //given
        String notFoundPath = "./file/not/found/path/memeber.csv";
        Path filePath = Paths.get(notFoundPath);

        //when
        fileUtils.readCsvFile(notFoundPath);

        //then
        Assert.fail();
    }

}
