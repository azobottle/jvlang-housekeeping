package com.example.application.controller;

import com.vaadin.flow.server.auth.AnonymousAllowed;
import dev.hilla.*;
import dev.hilla.auth.CsrfChecker;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.context.ApplicationContext;

import java.util.List;

@BrowserCallable
@AnonymousAllowed
public class ShopController {

    public List<ShopHomeItem> readHome() {
        return List.of(
                ShopHomeItem.builder()
                        .id(1L)
                        .name("aa")
                        .nickname("AA")
                        .build(),
                ShopHomeItem.builder()
                        .name("bb")
                        .build()
        );
    }
}

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@SuperBuilder
class ShopHomeItem {
    Long id;

    @NotNull
    String name;

    String nickname;

    List<Long> readChildren() {
        return List.of(2L, 3L);
    }
}
