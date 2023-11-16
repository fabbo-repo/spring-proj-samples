package com.mercadonatest.eanapi.modules.auth.domain.models.services;

import com.mercadonatest.eanapi.ReplaceUnderscoresAndCamelCase;
import com.mercadonatest.eanapi.modules.auth.domain.models.dtos.TokensDto;
import com.mercadonatest.eanapi.modules.auth.domain.models.dtos.TokensDtoFactory;
import com.mercadonatest.eanapi.modules.auth.domain.models.exceptions.UnauthorizedException;
import com.mercadonatest.eanapi.modules.auth.domain.models.requests.CredentialsRequest;
import com.mercadonatest.eanapi.modules.auth.domain.models.requests.CredentialsRequestFactory;
import com.mercadonatest.eanapi.modules.auth.domain.models.requests.RefreshTokenRequest;
import com.mercadonatest.eanapi.modules.auth.domain.models.requests.RefreshTokenRequestFactory;
import com.mercadonatest.eanapi.modules.auth.domain.services.AuthServiceImpl;
import com.mercadonatest.eanapi.modules.auth.domain.clients.AuthClient;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.mercadonatest.eanapi.TestUtils.randomText;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(ReplaceUnderscoresAndCamelCase.class)
@RunWith(MockitoJUnitRunner.class)
class AuthServiceTests {
    @Mock
    private AuthClient authClient;

    @InjectMocks
    private AuthServiceImpl authService;

    @Test
    void givenValidCredentialsRequest_whenAccess_shouldReturnValidTokensDto() {
        final CredentialsRequest request = CredentialsRequestFactory.credentialsRequest();
        final TokensDto tokensDto = TokensDtoFactory.tokensDto();

        when(authClient.access(request.username(), request.password()))
                .thenReturn(tokensDto);

        final TokensDto response = authService.access(request);

        assertThat(response)
                .usingRecursiveComparison()
                .isEqualTo(tokensDto);
    }

    @Test
    void givenInvalidCredentialsRequest_whenAccess_shouldThrowUnauthorizedException() {
        final CredentialsRequest request = CredentialsRequestFactory.credentialsRequest();
        final UnauthorizedException expectedException = new UnauthorizedException(
                randomText()
        );

        when(authClient.access(request.username(), request.password()))
                .thenThrow(expectedException);

        final Exception exception = assertThrows(
                expectedException.getClass(),
                () -> authService.access(request)

        );
        assertEquals(expectedException.getMessage(), exception.getMessage());
    }

    @Test
    void givenValidRefreshRequest_whenRefresh_shouldReturnValidTokensDto() {
        final RefreshTokenRequest request = RefreshTokenRequestFactory.refreshTokenRequest();
        final TokensDto tokensDto = TokensDtoFactory.tokensDto();

        when(authClient.refresh(request.refreshToken()))
                .thenReturn(tokensDto);

        final TokensDto response = authService.refresh(request.refreshToken());

        assertThat(response)
                .usingRecursiveComparison()
                .isEqualTo(tokensDto);
    }

    @Test
    void givenInvalidRefreshRequest_whenRefresh_shouldThrowUnauthorizedException() {
        final RefreshTokenRequest request = RefreshTokenRequestFactory.refreshTokenRequest();
        final UnauthorizedException expectedException = new UnauthorizedException(
                randomText()
        );

        when(authClient.refresh(request.refreshToken()))
                .thenThrow(expectedException);

        final Exception exception = assertThrows(
                expectedException.getClass(),
                () -> authService.refresh(request.refreshToken())

        );
        assertEquals(expectedException.getMessage(), exception.getMessage());
    }

    @Test
    void givenValidRefreshRequest_whenLogout_shouldLogoutAndReturnNone() {
        final RefreshTokenRequest request = RefreshTokenRequestFactory.refreshTokenRequest();

        final Object response = authService.logout(request.refreshToken());

        assertNull(response);
    }

    @Test
    void givenInvalidRefreshRequest_whenLogout_shouldThrowUnauthorizedException() {
        final RefreshTokenRequest request = RefreshTokenRequestFactory.refreshTokenRequest();
        final UnauthorizedException expectedException = new UnauthorizedException(
                randomText()
        );

        when(authClient.logout(request.refreshToken()))
                .thenThrow(expectedException);

        final Exception exception = assertThrows(
                expectedException.getClass(),
                () -> authService.logout(request.refreshToken())

        );
        assertEquals(expectedException.getMessage(), exception.getMessage());
    }
}
