package com.mikebryant.checkregister;

import com.mikebryant.checkregister.config.JsonMarshaller;
import com.mikebryant.checkregister.data.model.Transaction;
import com.mikebryant.checkregister.data.model.TransactionCategory;
import com.mikebryant.checkregister.data.model.TransactionMethod;
import com.mikebryant.checkregister.data.model.TransactionType;
import com.mikebryant.checkregister.data.service.TransactionCategoryService;
import com.mikebryant.checkregister.data.service.TransactionMethodService;
import com.mikebryant.checkregister.data.service.TransactionService;
import com.mikebryant.checkregister.data.service.TransactionTypeService;
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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CheckregisterBackendApplication.class)
@WebAppConfiguration
@Transactional
public class TransactionTests {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private JsonMarshaller jsonMarshaller;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private TransactionTypeService transactionTypeService;

    @Autowired
    private TransactionCategoryService transactionCategoryService;

    @Autowired
    private TransactionMethodService transactionMethodService;


    @Before
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void addTransaction() throws Exception {
        TransactionType transactionType = transactionTypeService.get(TransactionType.WITHDRAWAL);
        TransactionCategory transactionCategory = transactionCategoryService.getAll().get(0);
        TransactionMethod transactionMethod = transactionMethodService.getAll().get(0);

        Transaction transaction = new Transaction();
        transaction.setTransactionType(transactionType);
        transaction.setTransactionCategory(transactionCategory);
        transaction.setTransactionMethod(transactionMethod);
        transaction.setCheckNumber("101");
        transaction.setDescription("Test Description");
        transaction.setTxDate(LocalDate.of(2019, Month.AUGUST, 12));
        transaction.setAmount(-100.05);
        transaction.setNotes("Test Note");

        mockMvc.perform(post("/transaction")
                .contentType("application/json;charset=UTF-8")
                .content(jsonMarshaller.marshal(transaction))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.uuid").isNotEmpty())
                .andExpect(jsonPath("$.transactionCategory.uuid").value(transactionCategory.getUuid()))
                .andExpect(jsonPath("$.transactionType.uuid").value(transactionType.getUuid()))
                .andExpect(jsonPath("$.transactionMethod.uuid").value(transactionMethod.getUuid()))
                .andExpect(jsonPath("$.checkNumber").value(transaction.getCheckNumber()))
                .andExpect(jsonPath("$.description").value(transaction.getDescription()))
                .andExpect(jsonPath("$.txDate").value("2019-08-12"))
                .andExpect(jsonPath("$.withdrawalAmount").value(transaction.getAmount()))
                .andExpect(jsonPath("$.checkNumber").value(transaction.getCheckNumber()))
                .andExpect(jsonPath("$.notes").value(transaction.getNotes()))
                .andExpect(jsonPath("$.reconciledDate").isEmpty())
                .andExpect(jsonPath("$.depositAmount").isEmpty())
                .andDo(print())
                .andReturn();
}


//    @Test
//    public void listTransactionTypes() throws Exception {
//        String description = "List Test TransactionType";
//        String color = "Red";
//
//        int originalNumberTransactionTypes = service.getAll().size();
//        int newNumberTransactionTypes = 10;
//        for(int i=1; i<=newNumberTransactionTypes; i++) {
//            TransactionType transactionType = new TransactionType();
//            transactionType.setDescription(i + " " + description);
//            transactionType.setColor(i + " " + color);
//            service.save(transactionType);
//        }
//
//        int totalNumberTransactionTypes = originalNumberTransactionTypes + newNumberTransactionTypes;
//
//        mockMvc.perform(get("/transactionType")
//                .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$").isArray())
//                .andExpect(jsonPath("$.length()").value(totalNumberTransactionTypes))
//                .andDo(print())
//                .andReturn();
//    }

}
