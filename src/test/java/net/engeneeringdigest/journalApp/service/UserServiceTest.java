package net.engeneeringdigest.journalApp.service;

import net.engeneeringdigest.journalApp.entity.User;
import net.engeneeringdigest.journalApp.repository.UserRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
public class UserServiceTest {
    @Autowired
    private UserRepository userRepository;

    @Disabled
    @Test
    public void testFindByUserName() {
        assertEquals(4,2+2);
        User user = userRepository.findByUserName("Ram");
        User user2 =  userRepository.findByUserName("ram");
        assertTrue(!user2.getJournalEntries().isEmpty());
        assertEquals("Ram",user.getUserName());
//        assertEquals("ram",userRepository.findByUserName("ram"));
        assertNotNull(userRepository.findByUserName("ram"));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "Ram",
            "ram",
            "Swapnil",
            "Swap",
            "Gangnam"
    })
    public void testUserName(String userName){
        assertEquals(userName,userRepository.findByUserName(userName).getUserName());
    }

    @Disabled
    @ParameterizedTest
    @CsvSource({
            "1,1,2",
            "2,10,12",
            "3,3,5"
    })
    public void test(int a, int b,int expected){
        assertEquals(expected,a+b);
    }



}
