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
import rsupport.addressbook.constant.UrlConstants;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class OrganizationChartControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void 조직도_확인() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(UrlConstants.ORGANIZATION_CHART))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}