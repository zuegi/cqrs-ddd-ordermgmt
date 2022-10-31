package ch.zuegi.ordermgmt.feature.test;

import ch.zuegi.ordermgmt.feature.test.shared.DomainCommandHandler;

public class ArtikelDomainCommandHandler implements DomainCommandHandler<Article, CreateArticle> {

    Article article;

    public ArtikelDomainCommandHandler(Article article) {
        this.article = article;
    }

    @Override
    public void handle(CreateArticle command) {
        ArticleEntity entity = new ArticleEntity(this.article.id());
        entity.setTitle(command.getTitle());

        this.article.addEntity(entity);
    }
}
