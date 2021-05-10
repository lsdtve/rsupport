package rsupport.addressbook.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;
import rsupport.addressbook.constant.UrlConstants;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class AddressBookSynchronizeControllerTest {

    @Autowired private MockMvc mockMvc;

    @Test
    @DisplayName("주소록 동기화")
    void sync() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(UrlConstants.SYNCHRONIZE))
            .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
