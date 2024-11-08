

import org.springframework.context.annotation.Bean;
import org.springframework.retry.backoff.FixedBackOffPolicy;

@Bean
public org.springframework.retry.support.RetryTemplate retryTemplate() {
    org.springframework.retry.support.RetryTemplate retryTemplate = new org.springframework.retry.support.RetryTemplate();
    FixedBackOffPolicy backOffPolicy = new FixedBackOffPolicy();
    backOffPolicy.setBackOffPeriod(2000);  // 2 seconds delay between retries
    retryTemplate.setBackOffPolicy(backOffPolicy);
    return retryTemplate;
}

public void main() {
}
