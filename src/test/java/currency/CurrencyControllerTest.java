package currency;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//幣別對應表資料CRUD API,並顯示內容
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class CurrencyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getCurrencies() throws Exception {

        mockMvc.perform(get("/api/currencies"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].currencyCode").exists())
                .andExpect(jsonPath("$[0].currencyName").exists());
    }

    @Test
    void getCurrency() throws Exception {
        mockMvc.perform(get("/api/currencies/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.currencyCode").value("USD"))
                .andExpect(jsonPath("$.currencyName").value("美元"));
    }

    @Test
    void createCurrency() throws Exception {

        String json =
                "{"
                        + "\"currencyCode\":\"TWD\","
                        + "\"currencyName\":\"新台幣\""
                        + "}";

        mockMvc.perform(
                        post("/api/currencies")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json)
                )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.currencyCode").value("TWD"))
                .andExpect(jsonPath("$.currencyName").value("新台幣"));
    }

    @Test
    void deleteCurrency() throws Exception {
        mockMvc.perform(delete("/api/currencies/1"))
                .andDo(print())
                .andExpect(status().isNoContent());
        mockMvc.perform(get("/api/currencies/1"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}
