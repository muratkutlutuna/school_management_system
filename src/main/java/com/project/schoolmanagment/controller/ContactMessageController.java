package com.project.schoolmanagment.controller;

import com.project.schoolmanagment.payload.request.ContactMessageRequest;
import com.project.schoolmanagment.payload.response.ContactMessageResponse;
import com.project.schoolmanagment.payload.response.ResponseMessage;
import com.project.schoolmanagment.service.ContactMessageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contactMessages")
@RequiredArgsConstructor
public class ContactMessageController {


    private final ContactMessageService contactMessageService;

    @PostMapping("/save")
    public ResponseMessage<ContactMessageResponse> save(@RequestBody @Valid ContactMessageRequest contactMessageRequest){
        return contactMessageService.save(contactMessageRequest);
    }

    /**
     *
     * @param page number of selected page
     * @param size size of the page
     * @param sort sort property
     * @param type DESC or ASC
     * @return ContactMessageResponse
     */
    @GetMapping("/getAll")
    public Page<ContactMessageResponse> getAll(
            @RequestParam(value="page", defaultValue="0") int page,
            @RequestParam(value = "size", defaultValue="10") int size,
            @RequestParam(value = "sort", defaultValue="date") String sort,
            @RequestParam(value = "type", defaultValue="desc") String type){
        return contactMessageService.getAll(page, size, sort, type);
    }

    @GetMapping("/searchByEmail")
    public ResponseMessage<ContactMessageResponse> searchByEmail(
            @RequestParam(value="email") String email){
        return contactMessageService.searchByEmail(email);
    }
    @GetMapping("/searchBySubject")
    public Page<ContactMessageResponse> searchBySubject(
            @RequestParam(value="subject") String subject,
            @RequestParam(value="page", defaultValue="0") int page,
            @RequestParam(value = "size", defaultValue="10") int size,
            @RequestParam(value = "sort", defaultValue="date") String sort,
            @RequestParam(value = "type", defaultValue="ASC") Sort.Direction type){
        return contactMessageService.searchBySubject(subject,page, size, sort, type);
    }

    @DeleteMapping("/{id}") //http://localhost:8080/contactMessages/1 + DELETE
    public ResponseMessage<ContactMessageResponse> deleteContactMessagetById(@PathVariable("id") Long id) {
        return contactMessageService.deleteById(id);
    }

    @PutMapping("/{id}")
    public ResponseMessage<ContactMessageResponse>updateContactMessage(
            @PathVariable("id") Long id,
            @RequestBody @Valid ContactMessageRequest contactMessageRequest){
        return contactMessageService.updateById(id,contactMessageRequest);
    }

    @GetMapping("/getAllContactMessagesAsList")
    public ResponseMessage<List<ContactMessageResponse>> getAllContactMessagesAsList(){
        return contactMessageService.getAllAsList();
    }



        /* TODO:please add more endpoints
    * 1 - Delete by id
    * 2 - update (first find the correct message according to its id)
    * 3 - get all messages as a list
     */

}
