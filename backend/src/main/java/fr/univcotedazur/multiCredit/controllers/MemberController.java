package fr.univcotedazur.multiCredit.controllers;

import fr.univcotedazur.multiCredit.components.MemberManager;
import fr.univcotedazur.multiCredit.controllers.dto.*;
import fr.univcotedazur.multiCredit.entities.AccountStatus;
import fr.univcotedazur.multiCredit.entities.Form;
import fr.univcotedazur.multiCredit.entities.MemberAccount;
import fr.univcotedazur.multiCredit.exceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = MemberController.BASE_URI, produces = MediaType.APPLICATION_JSON_VALUE)
public class MemberController {

    public static final String BASE_URI = "/members";
    public static final String MEMBER_NOT_FOUND = "member not found";
    public static final String USER_NOT_FOUND = "user not found";
    public static final String ACCOUNT_NOT_FOUND = "account not found";
    @Autowired
    private MemberManager memberManager;

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    // The 422 (Unprocessable Entity) status code means the server understands the content type of the request entity
    // (hence a 415(Unsupported Media Type) status code is inappropriate), and the syntax of the request entity is
    // correct (thus a 400 (Bad Request) status code is inappropriate) but was unable to process the contained
    // instructions.
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ErrorDTO handleExceptions(MethodArgumentNotValidException e) {
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setError("Cannot process Member information");
        errorDTO.setDetails(e.getMessage());
        return errorDTO;
    }

    @PostMapping(path = "register", consumes = APPLICATION_JSON_VALUE) // path is a REST CONTROLLER NAME
    public ResponseEntity<AccountDTO> register(@RequestBody @Valid MemberDTO memberDTO) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
            return ResponseEntity.status(HttpStatus.CREATED).body(convertMemberAccountToDto(
                    memberManager.createAccount(memberDTO.getName(), memberDTO.getMail(),
                            memberDTO.getPassword(), LocalDate.parse(memberDTO.getBirthDate(), formatter))
            ));
        } catch (AlreadyExistingMemberException e) {
            // Note: Returning 409 (Conflict) can also be seen a security/privacy vulnerability, exposing a service for account enumeration
            return ResponseEntity.status(HttpStatus.OK).body(convertMemberAccountToDto(Objects.requireNonNull(memberManager.findByMail(memberDTO.getMail()).orElse(null))));
        } catch (MissingInformationException e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).contentType(MediaType.APPLICATION_JSON).body(new MemberDTO(0, "missing information", "", "", ""));
        } catch (UnderAgeException e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).contentType(MediaType.APPLICATION_JSON).body(new MemberDTO(0, "under age", "", "", ""));
        }
    }


    @DeleteMapping("delete")
    public ResponseEntity<String> deleteAccount(@RequestBody @Valid @Pattern(regexp = "^(.+)@(.+)$", message = "email should be valid") String mail) {
        mail = mail.replace("\"", "");
        MemberAccount memberAccount = memberManager.findByMail(mail).orElse(null);
        return getDeleteResponseEntity(memberAccount);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAccountWithId(@PathVariable("id") long id) {
        MemberAccount memberAccount = memberManager.findById(id).orElse(null);
        return getDeleteResponseEntity(memberAccount);
    }

    private ResponseEntity<String> getDeleteResponseEntity(MemberAccount memberAccount) {
        if (memberAccount == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body(MEMBER_NOT_FOUND);
        }
        try {
            memberManager.deleteAccount(memberAccount);
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body("member deleted");
        } catch (AccountNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body(MEMBER_NOT_FOUND);

        }
    }

    @PutMapping(path = "status", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<UpdateStatusDTO> updateStatus(@RequestBody @Valid UpdateStatusDTO updateStatusDTO) {
        MemberAccount memberAccount = memberManager.findByMail(updateStatusDTO.getMail()).orElse(null);
        if (memberAccount == null) {
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(new UpdateStatusDTO(" ", USER_NOT_FOUND));
        }
        try {
            AccountStatus newStatus = switch (updateStatusDTO.getStatus()) {
                case "VFP" -> AccountStatus.VFP;
                case "REGULAR" -> AccountStatus.REGULAR;
                case "EXPIRED" -> AccountStatus.EXPIRED;
                default -> throw new IllegalStateException("Unexpected value: " + updateStatusDTO.getStatus());
            };
            memberManager.updateAccountStatus(memberAccount, newStatus);
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(updateStatusDTO);
        } catch (AccountNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body(new UpdateStatusDTO(" ", USER_NOT_FOUND));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).contentType(MediaType.APPLICATION_JSON).body(new UpdateStatusDTO(" ", "status not valid"));
        }
    }

    @PutMapping(path = "status")
    public ResponseEntity<String> updateAllMembersStatus() {
        memberManager.updateAccountsStatus();
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body("all members status updated");
    }


    @PutMapping(path = "archive")
    public ResponseEntity<String> archiveMember(@RequestBody @Valid String mail) {
        mail = mail.replace("\"", "");
        MemberAccount memberAccount = memberManager.findByMail(mail).orElse(null);
        if (memberAccount == null) {
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(ACCOUNT_NOT_FOUND);
        }
        try {
            memberManager.archiveAccount(memberAccount);
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body("account archived");
        } catch (AccountNotFoundException e) {
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(ACCOUNT_NOT_FOUND);
        }
    }

    @PutMapping(path = "restore", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<String> restoreMember(@RequestBody @Valid String mail) {
        mail = mail.replace("\"", "");
        MemberAccount memberAccount = memberManager.findByMail(mail).orElse(null);
        if (memberAccount == null) {
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(ACCOUNT_NOT_FOUND);
        }
        try {
            memberManager.restoreAccount(memberAccount);
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body("account restored");
        } catch (AccountNotFoundException e) {
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(ACCOUNT_NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<MemberDTO> getMemberById(@PathVariable("id") Long id) {
        MemberAccount memberAccount = memberManager.findById(id).orElse(null);
        if (memberAccount == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body(new MemberDTO(0, " ", "", "", USER_NOT_FOUND));
        }
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(convertMemberAccountToDto(memberAccount));
    }

    @PostMapping(path = "/{id}", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<MemberDTO> updateMember(@PathVariable("id") Long id, @RequestBody @Valid FormDTO formDTO) {
        MemberAccount memberAccount = memberManager.findById(id).orElse(null);
        if (memberAccount == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body(new MemberDTO(0, " ", "", "", USER_NOT_FOUND));
        }
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
            String name = formDTO.getName().isEmpty() ? null : formDTO.getName();
            String mail = formDTO.getMail().isEmpty() ? null : formDTO.getMail();
            String password = formDTO.getPassword().isEmpty() ? null : formDTO.getPassword();
            LocalDate birthDate = formDTO.getBirthDate().isEmpty() ? null : LocalDate.parse(formDTO.getBirthDate(), formatter);
            Form form = new Form(name, mail, password, birthDate);
            memberManager.updateAccount(memberAccount, form);
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(convertMemberAccountToDto(memberAccount));
        } catch (AccountNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body(new MemberDTO(0, " ", "", "", USER_NOT_FOUND));
        }
    }

    @GetMapping("")
    public ResponseEntity<List<MemberDTO>> getAllMembers() {
        List<MemberAccount> memberAccounts = memberManager.findAll();
        List<MemberDTO> memberDTOs = new ArrayList<>();
        for (MemberAccount memberAccount : memberAccounts) {
            memberDTOs.add(convertMemberAccountToDto(memberAccount));
        }
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(memberDTOs);
    }


    private MemberDTO convertMemberAccountToDto(MemberAccount member) { // In more complex cases, we could use ModelMapper
        MemberDTO memberDTO = new MemberDTO(member.getId(), member.getName(), member.getMail(), member.getPassword(), member.getBirthDate().toString());
        memberDTO.setStatus(member.getStatus().toString());
        memberDTO.setBalance(member.getBalance());
        memberDTO.setPoints(member.getPoints());
        memberDTO.setMembershipCardNumber(member.getMembershipCard().getNumber());
        return memberDTO;
    }

    @PutMapping("/charge")
    public ResponseEntity<String> chargeMemberCard(@RequestBody @Valid ChargeCardDTO chargeCardDTO) {
        MemberAccount memberAccount = memberManager.findById(chargeCardDTO.getMemberId()).orElse(null);
        if (memberAccount == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body(MEMBER_NOT_FOUND);
        }
        try {
            memberManager.chargeMembershipCard(memberAccount, chargeCardDTO.getAmount(), chargeCardDTO.getCardNumber());
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body("card charged");
        } catch (AccountNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body(MEMBER_NOT_FOUND);
        } catch (PaymentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body("payement refused");
        }
    }

    @PostMapping(path = "/renew/{id}")
    public ResponseEntity<MemberDTO> renewAccount(@PathVariable("id") Long id) {
        MemberAccount memberAccount = memberManager.findById(id).orElse(null);
        if (memberAccount == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body(new MemberDTO(0, " ", "", "", USER_NOT_FOUND));
        }
        try {
            memberManager.renewMembership(memberAccount);
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(convertMemberAccountToDto(memberAccount));
        } catch (AccountNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body(new MemberDTO(0, " ", "", "", USER_NOT_FOUND));
        } catch (TooEarlyForRenewalException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body(new MemberDTO(0, " ", "", "", "too early to renew"));
        }
    }
}