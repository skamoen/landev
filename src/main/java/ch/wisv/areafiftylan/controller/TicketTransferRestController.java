package ch.wisv.areafiftylan.controller;

import ch.wisv.areafiftylan.exception.DuplicateTicketTransferTokenException;
import ch.wisv.areafiftylan.model.view.View;
import ch.wisv.areafiftylan.security.token.TicketTransferToken;
import ch.wisv.areafiftylan.service.TicketService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

import static ch.wisv.areafiftylan.util.ResponseEntityBuilder.createResponseEntity;

@RestController
public class TicketTransferRestController {
    private TicketService ticketService;

    @Autowired
    public TicketTransferRestController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @PreAuthorize("@currentUserServiceImpl.isTicketOwner(principal, #ticketId)")
    @RequestMapping(value = "/tickets/transfer/{ticketId}", method = RequestMethod.POST)
    public ResponseEntity<?> requestTicketTransfer(@PathVariable Long ticketId, @RequestBody String goalUsername){
        TicketTransferToken ttt = ticketService.setupForTransfer(ticketId, goalUsername);

        return createResponseEntity(HttpStatus.OK, "Ticket successfully set up for transfer", ttt);
    }

    @PreAuthorize("@currentUserServiceImpl.isTicketReceiver(principal, #token)")
    @RequestMapping(value = "/tickets/transfer", method = RequestMethod.PUT)
    public ResponseEntity<?> transferTicket(@RequestBody String token){
        ticketService.transferTicket(token);

        return createResponseEntity(HttpStatus.OK, "Ticket successfully transferred");
    }

    @PreAuthorize("@currentUserServiceImpl.isTicketSender(principal, #token)")
    @RequestMapping(value = "/tickets/transfer", method = RequestMethod.DELETE)
    public ResponseEntity<?> cancelTicketTransfer(@RequestBody String token){
        ticketService.cancelTicketTransfer(token);

        return createResponseEntity(HttpStatus.OK, "Ticket transfer successfully cancelled");
    }

    @PreAuthorize("isAuthenticated() and @currentUserServiceImpl.hasAnyTicket(principal)")
    @RequestMapping(value = "/tickets/tokens", method = RequestMethod.GET)
    public ResponseEntity<?> getTicketTokensOpenForTransfer(Authentication auth) {
        UserDetails currentUser = (UserDetails) auth.getPrincipal();
        Collection<TicketTransferToken> tokens = ticketService.getTicketTransferTokensByUser(currentUser.getUsername());

        return createResponseEntity(HttpStatus.OK, "Ticket transfer tokens successfully retrieved", tokens);
    }

    @ExceptionHandler(DuplicateTicketTransferTokenException.class)
    public ResponseEntity<?> handleDuplicateTicketTransFerException(DuplicateTicketTransferTokenException ex) {
        return createResponseEntity(HttpStatus.BAD_REQUEST, ex.getMessage());
    }
}
