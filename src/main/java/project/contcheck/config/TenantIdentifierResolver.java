package project.contcheck.config;

import org.checkerframework.checker.initialization.qual.Initialized;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.UnknownKeyFor;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.security.Principal;
import java.util.Optional;
import java.util.function.Predicate;

@Component
public class TenantIdentifierResolver implements CurrentTenantIdentifierResolver {
    static final String DEFAULT_TENANT = "padrao";

    @Override
    public @UnknownKeyFor @NonNull @Initialized String resolveCurrentTenantIdentifier() {
        return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
                .filter(Predicate.not(authentication -> authentication instanceof AnonymousAuthenticationToken))
                .map(Principal::getName)
                .orElse(DEFAULT_TENANT);
    }

    @Override
    public @UnknownKeyFor @NonNull @Initialized boolean validateExistingCurrentSessions() {
        return true;
    }
}
