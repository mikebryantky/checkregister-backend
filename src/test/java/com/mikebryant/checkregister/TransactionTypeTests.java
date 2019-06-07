package com.mikebryant.checkregister;

import com.mikebryant.checkregister.config.JsonMarshaller;
import com.mikebryant.checkregister.data.model.TransactionType;
import com.mikebryant.checkregister.data.service.TransactionTypeService;
import org.junit.Assert;
import org.junit.Before;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CheckregisterBackendApplication.class)
@WebAppConfiguration
@Transactional
public class TransactionTypeTests {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private JsonMarshaller jsonMarshaller;

    @Autowired
    private TransactionTypeService service;


    @Before
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void addTransactionType() throws Exception {
        TransactionType transactionType = new TransactionType();
        transactionType.setDescription("New Test TransactionType");
        transactionType.setColor("Red");

        mockMvc.perform(post("/transactionType")
                .contentType("application/json;charset=UTF-8")
                .content(jsonMarshaller.marshal(transactionType))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.uuid").isNotEmpty())
                .andExpect(jsonPath("$.description").value(transactionType.getDescription()))
                .andExpect(jsonPath("$.color").value(transactionType.getColor()))
                .andDo(print())
                .andReturn();
    }


    @Test
    public void updateTransactionType() throws Exception {
        String description = "Update Test TransactionType";
        String color = "Red";

        TransactionType transactionType = new TransactionType();
        transactionType.setDescription(description);
        transactionType.setColor(color);
        transactionType = service.save(transactionType);

        assertNotNull(transactionType.getUuid());
        String originalUuid = transactionType.getUuid();

        Assert.assertEquals(transactionType.getDescription(), description);
        Assert.assertEquals(transactionType.getColor(), color);

        String newDescripton = "Changed Update Test TransactionType";
        transactionType.setDescription(newDescripton);

        mockMvc.perform(put("/transactionType")
                .contentType("application/json;charset=UTF-8")
                .content(jsonMarshaller.marshal(transactionType))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.uuid").value(originalUuid))
                .andExpect(jsonPath("$.description").value(newDescripton))
                .andExpect(jsonPath("$.color").value(color))
                .andDo(print())
                .andReturn();
    }

    @Test(expected = EntityNotFoundException.class)
    public void deleteTransactionType() throws Exception {
        String description = "Delete Test TransactionType";
        String color = "Red";

        TransactionType transactionType = new TransactionType();
        transactionType.setDescription(description);
        transactionType.setColor(color);
        transactionType = service.save(transactionType);

        mockMvc.perform(delete("/transactionType/" + transactionType.getUuid()))
                .andExpect(status().isOk())
                .andDo(print());

        service.get(transactionType.getUuid());
    }

    @Test
    public void listTransactionTypes() throws Exception {
        String description = "List Test TransactionType";
        String color = "Red";

        int originalNumberTransactionTypes = service.getAll().size();
        int newNumberTransactionTypes = 10;
        for(int i=1; i<=newNumberTransactionTypes; i++) {
            TransactionType transactionType = new TransactionType();
            transactionType.setDescription(i + " " + description);
            transactionType.setColor(i + " " + color);
            service.save(transactionType);
        }

        int totalNumberTransactionTypes = originalNumberTransactionTypes + newNumberTransactionTypes;

        mockMvc.perform(get("/transactionType")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(totalNumberTransactionTypes))
                .andDo(print())
                .andReturn();
    }

}
