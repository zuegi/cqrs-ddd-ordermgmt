package ch.zuegi.ordermgmt.feature.test;

import ch.zuegi.ordermgmt.feature.test.shared.DomainCommandHandler;

public class ArtikelDomainCommandHandler implements DomainCommandHandler<Article, CreateArticle> {

    Article article;

    public ArtikelDomainCommandHandler(Article article) {
        this.article = article;
    }

    @Override
    public void handle(CreateArticle command) {
        // FIXME das kann es irgendwie auch nicht so richtig sein
        AuthorId authorId = new AuthorId();

        Author author = new Author(new AuthorId());
        author.handle(command.getAuthor());

        ArticleEntity articleEntity = new ArticleEntity(this.article.id());
        articleEntity.setTitle(command.getTitle());
        articleEntity.setAuthor(author.getAuthorEntity());

        // und dann alles zusammen
        this.article.addEntity(articleEntity);
        this.article.addAggregate(author);
    }
}
