package top.yzlin.sharedbanlist.cqrobotapi;

public class IsInBanList implements OperationInterface {
    @Override
    public boolean check(String word) {
        return false;
    }

    @Override
    public String reply(BanListOperation banListOperation) {
        return null;
    }
}
