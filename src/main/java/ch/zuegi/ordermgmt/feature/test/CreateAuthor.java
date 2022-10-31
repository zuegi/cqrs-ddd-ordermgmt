package ch.zuegi.ordermgmt.feature.test;


import ch.zuegi.ordermgmt.shared.Command;
import lombok.Value;

@Value(staticConstructor = "commandOf")
public class CreateAuthor implements Command {
    String vorname;
    String nachname;
}
