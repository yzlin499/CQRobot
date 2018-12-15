package top.yzlin.sharedbanlist.cqrobotapi;

public interface OperationInterface {

    boolean check(String word);

    String reply(BanListOperation banListOperation);
}
