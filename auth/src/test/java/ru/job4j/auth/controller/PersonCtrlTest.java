package ru.job4j.auth.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import ru.job4j.auth.domain.Person;
import ru.job4j.auth.repository.PersonRepository;

import static org.hamcrest.Matchers.containsString;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource("/application-test.properties")
@AutoConfigureMockMvc
@Sql(value = {"/create-person-table.sql", "/fill-person-table.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class PersonCtrlTest {

    @Autowired
    private PersonCtrl controller;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void tryToLoadAllPeopleAndShouldGetThreeRecordsAnd200Result() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/person/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String stringResult = result.getResponse().getContentAsString();
        Assert.assertEquals("[{\"id\":1,\"login\":\"parsentev\",\"password\":\"123\"},{\"id\":2,\"login\":\"ban\",\"password\":\"123\"},{\"id\":3,\"login\":\"ivan\",\"password\":\"123\"}]", stringResult);
    }

    @Test
    public void tryToGetAPersonWithIdAndGetARecordAboutHim() throws Exception {
        this.mockMvc.perform(get("/person/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.login").value("parsentev"))
                .andExpect(jsonPath("$.password").value("123"));

    }

    @Test
    public void tryToGetAPersonWithFictitiousIdAndGetTheStatus404() throws Exception {
        this.mockMvc.perform(get("/person/10000"))
                .andDo(print())
                .andExpect(status().is(404));
    }

    @Test
    public void tryToCreateANewPersonAndFindHimInTheDatabase() throws Exception {
        Person person = new Person();
        person.setLogin("newlogin");
        person.setPassword("newpassword");
        ObjectMapper om = new ObjectMapper();
        ObjectWriter ow = om.writer().withDefaultPrettyPrinter();
        String personJson = ow.writeValueAsString(person);
        String expected = "{\"id\":4,\"login\":\"newlogin\",\"password\":\"newpassword\"}";

        this.mockMvc.perform(post("/person/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(personJson))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().string(containsString(expected)));
    }

    @Test
    public void tryToUpdateAPersonAndFindHisNewDataInTheDatabase() throws Exception {
        Person person = personRepository.findById(1).get();
        person.setPassword("newpsw");
        ObjectMapper om = new ObjectMapper();
        ObjectWriter ow = om.writer().withDefaultPrettyPrinter();
        String personJson = ow.writeValueAsString(person);
        String expected = "{\"id\":1,\"login\":\"parsentev\",\"password\":\"newpsw\"}";

        this.mockMvc.perform(put("/person/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(personJson))
                .andDo(print())
                .andExpect(status().isOk());

        this.mockMvc.perform(get("/person/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.login").value("parsentev"))
                .andExpect(jsonPath("$.password").value("newpsw"));
    }

    @Test
    public void tryToDeleteAPersonAndFindNothingByHisIdTheDatabase() throws Exception {
        this.mockMvc.perform(delete("/person/3"))
                .andDo(print())
                .andExpect(status().isOk());

        this.mockMvc.perform(get("/person/3"))
                .andExpect(status().is(404));
    }
}
