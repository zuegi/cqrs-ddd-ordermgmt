package ch.zuegi.ordermgmt.feature.test;


import ch.zuegi.ordermgmt.feature.test.shared.AggregateRoot;
import ch.zuegi.ordermgmt.feature.test.shared.DomainCommandHandler;
import ch.zuegi.ordermgmt.shared.Command;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class Article extends AggregateRoot<Article, ArticleId> {

    ArticleEntity articleEntity;

    private Author author;

    protected Article(ArticleId aggregateId) {
        super(aggregateId);

    }

    @Override
    public void initBehaviour() {
        if (behaviourMap == null) {
            behaviourMap = new HashMap<>();
        }
        behaviourMap.put(CreateArticle.class, new ArtikelDomainCommandHandler(this));
    }

    @Override
    public ArticleId id() {
        return this.aggregateId;
    }


    public static Article create(ArticleId articleId) {
        Article article = new Article(articleId);
        return article;
    }

    public void addEntity(ArticleEntity entity) {
        this.articleEntity = entity;
    }

    public void addAggregate(Author author) {
        this.author = author;
    }
}
