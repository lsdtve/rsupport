package rsupport.addressbook.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;
import rsupport.addressbook.constant.UrlConstants;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class AddressBookSynchronizeControllerTest {

    @Autowired private MockMvc mockMvc;

    @Test
    public void 주소록_동기화() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(UrlConstants.SYNCHRONIZE))
            .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
