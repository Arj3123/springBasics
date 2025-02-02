package com.example.postgres.repositories;

import com.example.postgres.TestDataUtil;
import com.example.postgres.domain.Author;
import com.example.postgres.repository.AuthorRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AuthorREPIntTest {
    private AuthorRepo underTest;
    @Autowired
    public AuthorREPIntTest(AuthorRepo underTest){
        this.underTest=underTest;
    }
    @Test
    public void testThatAuthorCanBeCreatedAndRecalled(){
        Author author= TestDataUtil.createTestAuthor();
        underTest.save(author);
         Optional<Author> result = underTest.findById(author.getId());
         assertThat(result).isPresent();
         assertThat(result.get()).isEqualTo(author);
    }
      @Test
    public void testThatMultipleAuthorsCanBeCreatedAndRecalled() {
        Author authorA = TestDataUtil.createTestAuthorA();
        underTest.save(authorA);
        Author authorB = TestDataUtil.createTestAuthorB();
        underTest.save(authorB);
        Author authorC = TestDataUtil.createTestAuthorC();
        underTest.save(authorC);

        Iterable<Author> result = underTest.findAll();
        assertThat(result)
                .hasSize(3).
                containsExactly(authorA, authorB, authorC);
    }
   @Test
    public void testThatAuthorCanBeUpdated() {
        Author authorA = TestDataUtil.createTestAuthorA();
        underTest.save(authorA);
        authorA.setName("UPDATED");
        underTest.save(authorA);
        Optional<Author> result = underTest.findById(authorA.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(authorA);
    }
   @Test
    public void testThatAuthorCanBeDeleted() {
        Author authorA = TestDataUtil.createTestAuthorA();
        underTest.save(authorA);
        underTest.deleteById(authorA.getId());
        Optional<Author> result = underTest.findById(authorA.getId());
        assertThat(result).isEmpty();
    }
    @Test
    public void testToGetAuthorLessThanAge50(){
        Author tA=TestDataUtil.createTestAuthorA();
        underTest.save(tA);
        Author tB=TestDataUtil.createTestAuthorB();
        underTest.save(tB);
        Author tC=TestDataUtil.createTestAuthorC();
        underTest.save(tC);
        Iterable<Author> result=underTest.ageLessThan(50);
        assertThat(result).containsExactly(tB,tC);
    }
    @Test
    public void testToReturnAuthorAgeGreaterThanAge(){
        Author tA=TestDataUtil.createTestAuthorA();
        underTest.save(tA);
        Author tB=TestDataUtil.createTestAuthorB();
        underTest.save(tB);
        Author tC=TestDataUtil.createTestAuthorC();
        underTest.save(tC);
        Iterable<Author> result=underTest.GreaterThan(50);
        assertThat(result).containsExactly(tA);
    }
}
