package com.mikebryant.checkregister;

import com.mikebryant.checkregister.config.JsonMarshaller;
import com.mikebryant.checkregister.data.model.TransactionMethod;
import com.mikebryant.checkregister.data.service.TransactionMethodService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import javax.persistence.EntityNotFoundException;

import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CheckregisterBackendApplication.class)
@WebAppConfiguration
@Transactional
public class TransactionMethodTests {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private JsonMarshaller jsonMarshaller;

    @Autowired
    private TransactionMethodService service;


    @Before
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void addTransactionMethod() throws Exception {
        TransactionMethod transactionMethod = new TransactionMethod();
        transactionMethod.setDescription("New Test TransactionMethod");

        mockMvc.perform(post("/transactionMethod")
                .contentType("application/json;charset=UTF-8")
                .content(jsonMarshaller.marshal(transactionMethod))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.uuid").isNotEmpty())
                .andExpect(jsonPath("$.description").value(transactionMethod.getDescription()))
                .andDo(print())
                .andReturn();
    }


    @Test
    public void updateTransactionMethod() throws Exception {
        String description = "Update Test TransactionMethod";

        TransactionMethod transactionMethod = new TransactionMethod();
        transactionMethod.setDescription(description);
        transactionMethod = service.save(transactionMethod);

        assertNotNull(transactionMethod.getUuid());
        String originalUuid = transactionMethod.getUuid();

        Assert.assertEquals(transactionMethod.getDescription(), description);

        String newDescripton = "Changed Update Test TransactionMethod";
        transactionMethod.setDescription(newDescripton);

        mockMvc.perform(put("/transactionMethod")
                .contentType("application/json;charset=UTF-8")
                .content(jsonMarshaller.marshal(transactionMethod))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.uuid").value(originalUuid))
                .andExpect(jsonPath("$.description").value(newDescripton))
                .andDo(print())
                .andReturn();
    }

    @Test(expected = EntityNotFoundException.class)
    public void deleteTransactionMethod() throws Exception {
        String description = "Delete Test TransactionMethod";

        TransactionMethod transactionMethod = new TransactionMethod();
        transactionMethod.setDescription(description);
        transactionMethod = service.save(transactionMethod);

        mockMvc.perform(delete("/transactionMethod/" + transactionMethod.getUuid()))
                .andExpect(status().isOk())
                .andDo(print());

        service.get(transactionMethod.getUuid());
    }

    @Test
    public void listTransactionMethods() throws Exception {
        String description = "List Test TransactionMethod";

        int originalNumberTransactionMethods = service.getAll().size();
        int newNumberTransactionMethods = 10;
        for(int i=1; i<=newNumberTransactionMethods; i++) {
            TransactionMethod transactionMethod = new TransactionMethod();
            transactionMethod.setDescription(i + " " + description);
            service.save(transactionMethod);
        }

        int totalNumberTransactionMethods = originalNumberTransactionMethods + newNumberTransactionMethods;

        mockMvc.perform(get("/transactionMethod")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(totalNumberTransactionMethods))
                .andDo(print())
                .andReturn();
    }

}
