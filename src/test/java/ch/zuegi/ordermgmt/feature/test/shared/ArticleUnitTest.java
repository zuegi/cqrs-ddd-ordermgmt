package ch.zuegi.ordermgmt.feature.test.shared;

import ch.zuegi.ordermgmt.feature.test.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class ArticleUnitTest {

    @Test
    void erstelle_mit_entity() {
        // given
        ArticleId articleId = new ArticleId();
        CreateAuthor createAuthor = CreateAuthor.commandOf("Max", "Muster");
        String title= "Herr der Ringe";
        CreateArticle createArticle = CreateArticle.commandOf(title,createAuthor);

        // when
        Article article = Article.create(articleId);
        article.handle(createArticle);

        // then
        Assertions.assertThat(article).isInstanceOf(Article.class).isNotNull();

        Assertions.assertThat(article.getArticleEntity())
                .isInstanceOf(ArticleEntity.class)
                .isNotNull()
                .extracting(ArticleEntity::id, ArticleEntity::getTitle)
                .contains(articleId, title);
    }

    @Test
    void erstelle() {
        // given
        ArticleId articleId = new ArticleId();
        // when
        Article article = Article.create(articleId);
        // then
        Assertions.assertThat(article)
                .isInstanceOf(Article.class).isNotNull()
                .extracting(Article::id)
                .isEqualTo(articleId);
    }

}
