package com.mikebryant.checkregister;

import com.mikebryant.checkregister.config.JsonMarshaller;
import com.mikebryant.checkregister.data.model.TransactionCategory;
import com.mikebryant.checkregister.data.model.TransactionType;
import com.mikebryant.checkregister.data.service.TransactionCategoryService;
import com.mikebryant.checkregister.data.service.TransactionTypeService;
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
public class TransactionCategoryTests {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private JsonMarshaller jsonMarshaller;

    @Autowired
    private TransactionCategoryService service;


    @Before
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void addTransactionCategory() throws Exception {
        TransactionCategory transactionCategory = new TransactionCategory();
        transactionCategory.setDescription("New Test TransactionCategory");

        mockMvc.perform(post("/transactionCategory")
                .contentType("application/json;charset=UTF-8")
                .content(jsonMarshaller.marshal(transactionCategory))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.uuid").isNotEmpty())
                .andExpect(jsonPath("$.description").value(transactionCategory.getDescription()))
                .andDo(print())
                .andReturn();
    }

    @Test
    public void updateTransactionCategory() throws Exception {
        String description = "Update Test TransactionCategory";

        TransactionCategory transactionCategory = new TransactionCategory();
        transactionCategory.setDescription(description);
        transactionCategory = service.save(transactionCategory);

        assertNotNull(transactionCategory.getUuid());
        String originalUuid = transactionCategory.getUuid();

        Assert.assertEquals(transactionCategory.getDescription(), description);

        String newDescripton = "Changed Update Test TransactionCategory";
        transactionCategory.setDescription(newDescripton);

        mockMvc.perform(put("/transactionCategory")
                .contentType("application/json;charset=UTF-8")
                .content(jsonMarshaller.marshal(transactionCategory))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.uuid").value(originalUuid))
                .andExpect(jsonPath("$.description").value(newDescripton))
                .andDo(print())
                .andReturn();
    }

    @Test(expected = EntityNotFoundException.class)
    public void deleteTransactionCategory() throws Exception {
        String description = "Delete Test TransactionCategory";

        TransactionCategory transactionCategory = new TransactionCategory();
        transactionCategory.setDescription(description);
        transactionCategory = service.save(transactionCategory);

        mockMvc.perform(delete("/transactionCategory/" + transactionCategory.getUuid()))
                .andExpect(status().isOk())
                .andDo(print());

        service.get(transactionCategory.getUuid());
    }

    @Test
    public void listTransactionCategories() throws Exception {
        String description = "List Test TransactionCategory";

        int originalNumberTransactionCategories = service.getAll().size();
        int newNumberTransactionCategories = 10;
        for(int i=1; i<=newNumberTransactionCategories; i++) {
            TransactionCategory transactionCategory = new TransactionCategory();
            transactionCategory.setDescription(i + " " + description);
            service.save(transactionCategory);
        }

        int totalNumberTransactionCategories = originalNumberTransactionCategories + newNumberTransactionCategories;

        mockMvc.perform(get("/transactionCategory")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(totalNumberTransactionCategories))
                .andDo(print())
                .andReturn();
    }

}
