package com.project.schoolmanagment.service;

import com.project.schoolmanagment.entity.concrets.ContactMessage;
import com.project.schoolmanagment.exception.ConflictException;
import com.project.schoolmanagment.exception.ResourceNotFoundException;
import com.project.schoolmanagment.payload.request.ContactMessageRequest;
import com.project.schoolmanagment.payload.response.ContactMessageResponse;
import com.project.schoolmanagment.payload.response.ResponseMessage;
import com.project.schoolmanagment.repository.ContactMessageRepository;
import com.project.schoolmanagment.utils.Messages;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ContactMessageService {
    private final ContactMessageRepository contactMessageRepository;

    public ResponseMessage<ContactMessageResponse> save(ContactMessageRequest contactMessageRequest) {

        //it is expected to create one message in day with the same email
        boolean isSameMessageWithSameEmailForToday = contactMessageRepository.existsByEmailEqualsAndDateEquals(contactMessageRequest.getEmail(), LocalDate.now());

        if (isSameMessageWithSameEmailForToday) {
            throw new ConflictException(Messages.ALREADY_SEND_A_MESSAGE_TODAY);
        }
        ContactMessage contactMessage = createContactMessage(contactMessageRequest);
        ContactMessage savedData = contactMessageRepository.save(contactMessage);
        return ResponseMessage.<ContactMessageResponse>builder()
                //this message should be moved to messages class and called from there.
                .message(Messages.SUCCESSFULL_CREATED)
                .httpStatus(HttpStatus.CREATED)
                .object(createResponse(savedData))
                .build();
    }

    public Page<ContactMessageResponse> getAll(int page, int size, String sort, String type/*Sort.Direction type*/){
        //int this solution type property should be instance of Direction
//        Pageable myPageable = PageRequest.of(page, size, Sort.by(type, sort));

        Pageable pageable = PageRequest.of(page, size, Sort.by(sort).ascending());
        if (Objects.equals(type, "desc")) {
            pageable = PageRequest.of(page, size, Sort.by(sort).descending());
        }
        return contactMessageRepository.findAll(pageable).map(this::createResponse);
    }

    public ResponseMessage<ContactMessageResponse> searchByEmail(String email){
        ContactMessage byEmail = contactMessageRepository.findByEmail(email);
        return ResponseMessage.<ContactMessageResponse>builder()
                //this message should be moved to messages class and called from there.
                .message(Messages.SUCCESSFULL_FOUND)
                .httpStatus(HttpStatus.FOUND)
                .object(createResponse(byEmail))
                .build();
    }

    private ContactMessageResponse createResponse(ContactMessage contactMessage) {
        return ContactMessageResponse.builder()
                .name(contactMessage.getName())
                .subject(contactMessage.getSubject())
                .message(contactMessage.getMessage())
                .email(contactMessage.getEmail())
                .date(LocalDate.now())
                .build();
    }

    //TODO: Please check builder design pattern
    //I would give this method a name like contactMessageRequestToContactMessage
    private ContactMessage createContactMessage(ContactMessageRequest contactMessageRequest) {
        return ContactMessage.builder()
                .name(contactMessageRequest.getName())
                .subject(contactMessageRequest.getSubject())
                .message(contactMessageRequest.getMessage())
                .email(contactMessageRequest.getEmail())
                .date(LocalDate.now())
                .build();
    }

    public Page<ContactMessageResponse> searchBySubject(String subject, int page, int size, String sort, Sort.Direction type) {

        Pageable myPageable = PageRequest.of(page, size, Sort.by(type, sort));

        return contactMessageRepository.findBySubjectEquals(subject,myPageable).map(this::createResponse);

    }

    public ResponseMessage<ContactMessageResponse> deleteById(Long id) {

        ContactMessage contactMessage = contactMessageRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(Messages.NO_MESSAGE_WITH_THIS_ID)
        );
        contactMessageRepository.deleteById(id);
        return ResponseMessage.<ContactMessageResponse>builder()
                .message(Messages.SUCCESSFULL_DELETED)
                .httpStatus(HttpStatus.FOUND)
                .object(createResponse(contactMessage))
                .build();
    }

    public ResponseMessage<List<ContactMessageResponse>> getAllAsList() {
        List<ContactMessage> contactMessageList = contactMessageRepository.findAll();
        return ResponseMessage.<List<ContactMessageResponse>>builder()
                .message(Messages.SUCCESSFULL_FOUND_ALL)
                .httpStatus(HttpStatus.FOUND)
                .object(contactMessageList.stream().map(this::createResponse).toList())
                .build();

    }

    public ResponseMessage<ContactMessageResponse> updateById(Long id, ContactMessageRequest contactMessageRequest) {
        ContactMessage existingContactMessage = contactMessageRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(Messages.NO_MESSAGE_WITH_THIS_ID)
        );
        boolean isSameMessageWithSameEmailForToday = contactMessageRepository.existsByEmailEqualsAndDateEquals(contactMessageRequest.getEmail(), LocalDate.now());

        if (isSameMessageWithSameEmailForToday) {
            throw new ConflictException(Messages.ALREADY_SEND_A_MESSAGE_TODAY);
        }

        existingContactMessage.toBuilder()
                .name(contactMessageRequest.getName())
                .email(contactMessageRequest.getEmail())
                .subject(contactMessageRequest.getSubject())
                .message(contactMessageRequest.getMessage())
                .build();
        contactMessageRepository.save(existingContactMessage);

        return ResponseMessage.<ContactMessageResponse>builder()
                .message(Messages.SUCCESSFULL_FOUND)
                .httpStatus(HttpStatus.FOUND)
                .object(createResponse(createContactMessage(contactMessageRequest)))
                .message(Messages.SUCCESSFULL_UPDATED)
                .httpStatus(HttpStatus.OK)
                .object(createResponse(existingContactMessage))
                .build();
    }
}
