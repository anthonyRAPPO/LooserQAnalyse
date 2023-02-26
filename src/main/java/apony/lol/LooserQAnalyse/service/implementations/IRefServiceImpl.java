package apony.lol.LooserQAnalyse.service.implementations;

import java.time.Duration;

import org.springframework.stereotype.Service;
import apony.lol.LooserQAnalyse.service.interfaces.IRefService;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;

@Service
public class IRefServiceImpl implements IRefService {

    private Bucket bucket;

    @Override
    public void init() {

        bucket = Bucket.builder()
                .addLimit(Bandwidth.classic(4, Refill.intervally(4,
                        Duration.ofSeconds(5))))
                .build();

    }

    @Override
    public boolean canRequest() {
        return bucket.tryConsume(1);
    }

}
