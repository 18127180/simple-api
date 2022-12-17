package com.safetrust.simpleapi.feature.contact;

import com.safetrust.simpleapi.base.BaseRestController;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@RestController
@Getter
@RequestMapping(ContactController.BASE_ENDPOINT)
public class ContactController extends BaseRestController {
    static final String BASE_ENDPOINT = API_BASE + "/contact";

    private final ContactService service;

//    @GetMapping
//    public ResponseEntity<SearchDTO> find(HttpServletRequest request,
//                                          @RequestParam(value = "search", defaultValue = "") String search,
//                                          @RequestParam(value = "active", defaultValue = "true") boolean isActive,
//                                          @RequestParam(value = "start", defaultValue = "0") int page,
//                                          @RequestParam(value = "length", defaultValue = "100") int size,
//                                          @RequestParam(value = "sort", defaultValue = "code") List<String> sort,
//                                          @RequestParam(value = "direction", defaultValue = "asc") String direction,
//                                          @RequestParam(value = "resource", defaultValue = "name") String dtoType
//    ) {
//        return ResponseEntity.ok(service.findAllByName(getCurrentSbuId(request), search, isActive, pageRequest(page, size, sort, direction), dtoType));
//    }

    @GetMapping(ID)
    public ResponseEntity<?> getOne(HttpServletRequest request,
                                    @PathVariable String id,
                                    @RequestParam(value = "resource", defaultValue = "full") String dtoType) {
        return ResponseEntity.ok(service.getOneDTO(id, dtoType));
    }

    @PostMapping
    public ResponseEntity<?> createOne(HttpServletRequest request,
                                       final @RequestBody ContactDTO resource) {
        return ResponseEntity.ok(service.createOneDTO(resource));
    }

    @PutMapping(ID)
    public ResponseEntity<ContactDTO> updateOne(HttpServletRequest request,
                                                @PathVariable String id, final @RequestBody ContactDTO resource) {
        return ResponseEntity.ok(service.updateOneDTO(id, resource));
    }
}
