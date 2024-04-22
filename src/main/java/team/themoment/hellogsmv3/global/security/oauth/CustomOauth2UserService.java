package team.themoment.hellogsmv3.global.security.oauth;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import team.themoment.hellogsmv3.domain.auth.entity.Authentication;
import team.themoment.hellogsmv3.domain.auth.repo.AuthenticationRepository;
import team.themoment.hellogsmv3.domain.auth.type.Role;
import team.themoment.hellogsmv3.global.exception.error.ExpectedException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
public class CustomOauth2UserService  implements OAuth2UserService {

    private final OAuth2UserService<OAuth2UserRequest, OAuth2User> delegateOauth2UserService;
    private final AuthenticationRepository authenticationRepository;

    public CustomOauth2UserService(AuthenticationRepository authenticationRepository) {
        this.delegateOauth2UserService = new DefaultOAuth2UserService();
        this.authenticationRepository = authenticationRepository;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = delegateOauth2UserService.loadUser(userRequest);
        Map<String, Object> oAuthAttributes = oAuth2User.getAttributes();

        String provider = userRequest.getClientRegistration().getRegistrationId();
        String providerId;

        if (provider == null) throw new ExpectedException("oauth provider가 존재하지 않습니다.", HttpStatus.BAD_REQUEST);

        switch (provider.toLowerCase()) {
            case "kakao": {
                providerId = ((Map<String, Object>) oAuthAttributes.get("kakao_account")).get("email").toString();
                break;
            }
            case "google": {
                providerId = oAuthAttributes.get("email").toString();
                break;
            }
            default: {
                throw new ExpectedException("올바르지 않은 oauth provider 입니다.", HttpStatus.BAD_REQUEST);
            }
        }

        Authentication user = getUser(provider, providerId);

        String nameAttribute = "id";
        Long id = user.getId();
        Role role = user.getRole();
        Map<String, Object> attributes = new HashMap<>(Map.of(
                nameAttribute, id,
                "role", role,
                "provider", provider,
                "provider_id", providerId,
                "last_login_time", LocalDateTime.now()
        ));
        Collection<GrantedAuthority> authorities = new ArrayList<>(oAuth2User.getAuthorities());
        authorities.add(new SimpleGrantedAuthority(role.name()));

        return new UserInfo(authorities, attributes, nameAttribute);
    }

    private Authentication getUser(String provider, String providerId) {
        Authentication savedAuthentication = authenticationRepository.findByProviderNameAndProviderId(provider, providerId)
                .orElse(null);
        if (savedAuthentication == null) {
            Authentication authentication = new Authentication(null, providerId, provider, null);
            return authenticationRepository.save(authentication);
        }
        return savedAuthentication;
    }
}

