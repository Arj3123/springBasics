package com.example.postgres.controllers;

import com.example.postgres.TestDataUtil;
import com.example.postgres.domain.Author;
import com.example.postgres.domain.dto.AuthorDto;
import com.example.postgres.services.AuthorService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class AuthorControllerIntTest {
    private AuthorService authorService;
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    @Autowired
    public AuthorControllerIntTest(MockMvc mockMvck,ObjectMapper objectMapper,AuthorService authorService){
        this.mockMvc=mockMvck;
        this.objectMapper=objectMapper;
        this.authorService=authorService;
    }
    @Test
    public void testThatCraeteAuthorReturnsHttp201Created() throws Exception {
        Author tA=TestDataUtil.createTestAuthorA();
        tA.setId(null);
        String authorJson= objectMapper.writeValueAsString(tA);
        mockMvc.perform(
                MockMvcRequestBuilders.post("/authors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorJson)
        ).andExpect(MockMvcResultMatchers.status().isCreated()
        );
    }
    @Test
    public void testThatCraeteAuthorReturnsSAvedAuthor() throws Exception {
        Author tA = TestDataUtil.createTestAuthorA();
        tA.setId(null);
        String authorJson = objectMapper.writeValueAsString(tA);
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/authors")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(authorJson)
                ).andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Abigail Rose"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(80)

                );
    }
    @Test
    public void testThatListAuthorsReturnsHttpStatus200() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/authors")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatListAuthorRetursAuthor() throws Exception {
        Author tA=TestDataUtil.createTestAuthorA();
        authorService.createAuthor(tA);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/authors")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.jsonPath("$[0].id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Abigail Rose"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].age").value(80)

                );
    }
    @Test
    public void testThatGetAuthorReturnsHttpStatus200WhenAuthorExist() throws Exception {
        Author testAuthorEntityA = TestDataUtil.createTestAuthorA();
        authorService.createAuthor(testAuthorEntityA);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/authors/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatGetAuthorReturnsAuthorWhenAuthorExist() throws Exception {
        Author testAuthorEntityA = TestDataUtil.createTestAuthorA();
        authorService.createAuthor(testAuthorEntityA);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/authors/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(1)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value("Abigail Rose")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.age").value(80)
        );
    }

    @Test
    public void testThatGetAuthorReturnsHttpStatus404WhenNoAuthorExists() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/authors/99")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNotFound());
    }
    @Test
    public void testThatFullUpdateAuthorReturnsHttpStatus404WhenNoAuthorExists() throws Exception {
        AuthorDto testAuthorDtoA = TestDataUtil.createTestAuthorDto();
        String authorDtoJson = objectMapper.writeValueAsString(testAuthorDtoA);
        mockMvc.perform(
                MockMvcRequestBuilders.put("/authors/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorDtoJson)
        ).andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testThatFullUpdateAuthorReturnsHttpStatus4200WhenAuthorExists() throws Exception {
        Author testAuthorEntityA = TestDataUtil.createTestAuthorA();
        Author savedAuthor = authorService.createAuthor(testAuthorEntityA);

        AuthorDto testAuthorDtoA = TestDataUtil.createTestAuthorDto();
        String authorDtoJson = objectMapper.writeValueAsString(testAuthorDtoA);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/authors/" + savedAuthor.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorDtoJson)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }
    @Test
    public void testThatUpdateUpdatesExistingAuthor() throws Exception {
        Author tA=TestDataUtil.createTestAuthorA();
        Author savedAuthor=authorService.createAuthor(tA);
        Author dto=TestDataUtil.createTestAuthorB();
        dto.setId(tA.getId());
        String authorJson= objectMapper.writeValueAsString(dto);
        mockMvc.perform(
                MockMvcRequestBuilders.put("/authors/"+savedAuthor.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                               .content(authorJson)
                ).andExpect(MockMvcResultMatchers.jsonPath("$.id").value(savedAuthor.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(dto.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(dto.getAge()));
    }
    @Test
    public void testThatPartialUpdateExistingAuthorReturnsHttpStatus20Ok() throws Exception {
        Author testAuthorEntityA = TestDataUtil.createTestAuthorA();
        Author savedAuthor = authorService.createAuthor(testAuthorEntityA);

        AuthorDto testAuthorDtoA = TestDataUtil.createTestAuthorDto();
        testAuthorDtoA.setName("UPDATED");
        String authorDtoJson = objectMapper.writeValueAsString(testAuthorDtoA);

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/authors/" + savedAuthor.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorDtoJson)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatPartialUpdateExistingAuthorReturnsUpdatedAuthor() throws Exception {
        Author testAuthorEntityA = TestDataUtil.createTestAuthorA();
        Author savedAuthor = authorService.createAuthor(testAuthorEntityA);

        AuthorDto testAuthorDtoA = TestDataUtil.createTestAuthorDto();
        testAuthorDtoA.setName("UPDATED");
        String authorDtoJson = objectMapper.writeValueAsString(testAuthorDtoA);

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/authors/" + savedAuthor.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorDtoJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(savedAuthor.getId())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value("UPDATED")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.age").value(testAuthorDtoA.getAge())
        );
    }
    @Test
    public void testThatDeleteAuthorReturnsHttpStatus204ForNonExistingAuthor() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/authors/999")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void testThatDeleteAuthorReturnsHttpStatus204ForExistingAuthor() throws Exception {
        Author testAuthorEntityA = TestDataUtil.createTestAuthorA();
        Author savedAuthor = authorService.createAuthor(testAuthorEntityA);

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/authors/" + savedAuthor.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}
