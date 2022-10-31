package ch.zuegi.ordermgmt.feature.test;

import ch.zuegi.ordermgmt.shared.Command;
import lombok.Value;


@Value(staticConstructor = "commandOf")
public class CreateArticle implements Command {
    String title;
    CreateAuthor author;
}
