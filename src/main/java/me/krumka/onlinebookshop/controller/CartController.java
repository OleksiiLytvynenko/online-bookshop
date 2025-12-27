package me.krumka.onlinebookshop.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import me.krumka.onlinebookshop.dto.cartitem.CartItemRequestDto;
import me.krumka.onlinebookshop.dto.cartitem.UpdateQuantityRequestDto;
import me.krumka.onlinebookshop.dto.shoppingcart.ShoppingCartDto;
import me.krumka.onlinebookshop.model.User;
import me.krumka.onlinebookshop.service.ShoppingCartService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cart")
@Tag(name = "Shopping cart API", description = "Endpoints for managing a shopping cart")
@RequiredArgsConstructor
public class CartController {
    private final ShoppingCartService shoppingCartService;

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Get a users shopping cart", description = "Returns a shopping cart")
    public ShoppingCartDto getCart(@AuthenticationPrincipal User user) {
        return shoppingCartService.getShoppingCartByUserId(user.getId());
    }

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    @Operation(
            summary = "Add book to the shopping cart",
            description = "Adds a book to the shopping cart"
    )
    public ShoppingCartDto addBook(
            @AuthenticationPrincipal User user,
            @RequestBody @Valid CartItemRequestDto cartItemRequestDto) {
        return shoppingCartService.addBookToShoppingCart(
                user.getId(),
                cartItemRequestDto.bookId(),
                cartItemRequestDto.quantity()
        );
    }

    @PutMapping("/items/{cartItemId}")
    @PreAuthorize("hasRole('USER')")
    @Operation(
            summary = "Update quantity of a book in a shopping cart",
            description = "Updates quantity of a book in a shopping cart"
    )
    public ShoppingCartDto updateQuantity(
            @AuthenticationPrincipal
            User user,
            @PathVariable
            @Min(value = 1, message = "ID must be greater than or equal to 1")
            Long cartItemId,
            @RequestBody UpdateQuantityRequestDto updateQuantityRequestDto
    ) {
        return shoppingCartService.updateBookQuantity(
                user.getId(),
                cartItemId,
                updateQuantityRequestDto.quantity()
        );
    }

    @DeleteMapping("/items/{cartItemId}")
    @PreAuthorize("hasRole('USER')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(
            summary = "Remove a book from a shopping cart",
            description = "Removes a book from a shopping cart"
    )
    public void removeBookFromShoppingCart(
            @AuthenticationPrincipal
            User user,
            @PathVariable
            @Min(value = 1, message = "ID must be greater than or equal to 1")
            Long cartItemId
    ) {
        shoppingCartService.removeBookFromShoppingCart(user.getId(), cartItemId);
    }
}
