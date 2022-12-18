package com.safetrust.simpleapi.contact;

import com.safetrust.simpleapi.base.SearchDTO;
import com.safetrust.simpleapi.feature.contact.ContactDTO;
import com.safetrust.simpleapi.feature.contact.ContactService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ContactControllerIntegrationTest {

    @LocalServerPort
    private int port;

    private static RestTemplate restTemplate;

    private String baseUrl = "http://localhost";

    @Autowired
    private ContactService contactService;

    @BeforeAll
    public static void init() {
        restTemplate = new RestTemplate();
    }

    @BeforeEach
    public void setUp() {
        baseUrl = baseUrl.concat(":").concat(port + "").concat("/api");
    }

    @Test
    @Order(1)
    void testCreateOneAPI() {
        ContactDTO contactDTO = new ContactDTO();
        contactDTO.setName("Phuc Le Update");
        contactDTO.setEmailAddress("phucyugi123@gmail.com");
        contactDTO.setPostalAddress("UK");
        contactDTO.setTelephoneNumber("0365306252");
        ContactDTO contactDTOResult = restTemplate.postForObject(baseUrl + "/contact", contactDTO, ContactDTO.class);
        Assertions.assertNotEquals(null, contactDTOResult);
        Assertions.assertEquals(contactDTO.getName(), contactDTOResult.getName());
        Assertions.assertEquals(contactDTO.getEmailAddress(), contactDTOResult.getEmailAddress());
        Assertions.assertEquals(contactDTO.getPostalAddress(), contactDTOResult.getPostalAddress());
        Assertions.assertEquals(contactDTO.getTelephoneNumber(), contactDTOResult.getTelephoneNumber());
    }

    @Test
    @Order(2)
    void testGetOneAPI() {
        ContactDTO contactDTOResult = restTemplate.getForObject(baseUrl + "/contact/1", ContactDTO.class);
        Assertions.assertNotEquals(null, contactDTOResult);
    }

    @Test
    @Order(3)
    void testUpdateOneAPI() {
        ContactDTO contactDTO = new ContactDTO();
        contactDTO.setName("Phuc Le Update");
        contactDTO.setEmailAddress("phucyugi123@gmail.com");
        contactDTO.setPostalAddress("UK");
        contactDTO.setTelephoneNumber("0365306252");
        restTemplate.put(baseUrl + "/contact/1", contactDTO, ContactDTO.class);
        ContactDTO contactDTOResult = restTemplate.getForObject(baseUrl + "/contact/1", ContactDTO.class);
        Assertions.assertEquals(contactDTO.getName(), contactDTOResult.getName());
        Assertions.assertEquals(contactDTO.getEmailAddress(), contactDTOResult.getEmailAddress());
        Assertions.assertEquals(contactDTO.getPostalAddress(), contactDTOResult.getPostalAddress());
        Assertions.assertEquals(contactDTO.getTelephoneNumber(), contactDTOResult.getTelephoneNumber());
    }

    @Test
    @Order(4)
    void testGetAPI() {
        SearchDTO searchDTO = restTemplate.getForObject(baseUrl + "/contact?search=Phuc Le", SearchDTO.class);
        Assertions.assertEquals(1, searchDTO.getRecordsTotal());
    }

    @Test
    @Order(5)
    void testDeleteAPI() {
        restTemplate.delete(baseUrl + "/contact/1");
        try {
            contactService.getOneDTO("1", "full");
        } catch (Exception e) {
            Assertions.assertNotEquals(null, e);
        }
    }
}
