package ch.zuegi.ordermgmt.feature.test;

import ch.zuegi.ordermgmt.feature.test.shared.AggregateId;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ArticleEntity extends AggregateId<ArticleId> {
    String title;
    AuthorEntity author;

    protected ArticleEntity(ArticleId aggregateId) {
        super(aggregateId);
    }

    @Override
    public ArticleId id() {
        return this.aggregateId;
    }
}
