package com.dreamsoftware.tcs.processor;

import com.dreamsoftware.tcs.model.events.OnUserRegisteredEvent;
import com.dreamsoftware.tcs.persistence.nosql.entity.UserStateEnum;
import com.dreamsoftware.tcs.persistence.nosql.repository.UserRepository;
import java.util.Date;
import java.util.function.Consumer;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * User Registered Processor
 *
 * @author ssanchez
 */
@Component("userRegisteredProcessor")
@RequiredArgsConstructor
public class UserRegisteredProcessor implements Consumer<OnUserRegisteredEvent> {

    private final Logger logger = LoggerFactory.getLogger(UserRegisteredProcessor.class);

    /**
     * User Repository
     */
    private final UserRepository userRepository;

    @Override
    public void accept(OnUserRegisteredEvent event) {
        logger.debug("UserRegisteredProcessor CALLED!");
        userRepository.findOneByWalletHash(event.getWalletHash()).ifPresent((userEntity) -> {
            userEntity.setActivationDate(new Date());
            userEntity.setState(UserStateEnum.VALIDATED);
            userRepository.save(userEntity);
        });
    }
}
