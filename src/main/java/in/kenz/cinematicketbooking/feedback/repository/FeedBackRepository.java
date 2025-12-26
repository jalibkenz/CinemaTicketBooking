package in.kenz.cinematicketbooking.feedback.repository;

import in.kenz.cinematicketbooking.feedback.entity.FeedBack;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedBackRepository  extends JpaRepository<FeedBack, Long> {
}
