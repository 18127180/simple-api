package com.safetrust.simpleapi.contact;

import com.safetrust.simpleapi.base.SearchDTO;
import com.safetrust.simpleapi.feature.contact.ContactDTO;
import com.safetrust.simpleapi.feature.contact.ContactService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ContactUnitTest {

    @Autowired
    private ContactService contactService;

    @Test
    @Order(1)
    void testCreateOneDTO() {
        ContactDTO contactDTO = new ContactDTO();
        contactDTO.setName("Phuc Le");
        contactDTO.setEmailAddress("phucyugi@gmail.com");
        contactDTO.setPostalAddress("VietNam");
        contactDTO.setTelephoneNumber("0365306251");
        ContactDTO contactDTOResult = contactService.createOneDTO(contactDTO);
        Assertions.assertEquals(contactDTO.getName(), contactDTOResult.getName());
        Assertions.assertEquals(contactDTO.getEmailAddress(), contactDTOResult.getEmailAddress());
        Assertions.assertEquals(contactDTO.getPostalAddress(), contactDTOResult.getPostalAddress());
        Assertions.assertEquals(contactDTO.getTelephoneNumber(), contactDTOResult.getTelephoneNumber());
    }

    @Test
    @Order(2)
    void testGetOneDTO() {
        Object contactDTOResult = contactService.getOneDTO("1", "full");
        Assertions.assertNotEquals(null, contactDTOResult);
    }

    @Test
    @Order(3)
    void testFindAllByName() {
        PageRequest page = PageRequest.of(0, 100, Sort.Direction.fromString("asc"), "id");
        SearchDTO searchDTO = contactService.findAllByName("Phuc Le", page, "full");
        Assertions.assertEquals(1, searchDTO.getRecordsTotal());
    }

    @Test
    @Order(4)
    void testUpdateOneDTO() {
        ContactDTO contactDTO = new ContactDTO();
        contactDTO.setName("Phuc Le Update");
        contactDTO.setEmailAddress("phucyugi123@gmail.com");
        contactDTO.setPostalAddress("UK");
        contactDTO.setTelephoneNumber("0365306252");
        ContactDTO contactDTOResult = contactService.updateOneDTO("1", contactDTO);
        Assertions.assertEquals(contactDTO.getName(), contactDTOResult.getName());
        Assertions.assertEquals(contactDTO.getEmailAddress(), contactDTOResult.getEmailAddress());
        Assertions.assertEquals(contactDTO.getPostalAddress(), contactDTOResult.getPostalAddress());
        Assertions.assertEquals(contactDTO.getTelephoneNumber(), contactDTOResult.getTelephoneNumber());
    }

    @Test
    @Order(5)
    void testDeleteOne() {
        contactService.deleteOne("1");
        try {
            contactService.getOneDTO("1", "full");
        } catch (Exception e) {
            Assertions.assertNotEquals(null, e);
        }
    }

}
