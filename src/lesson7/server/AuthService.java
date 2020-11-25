package lesson7.server;

public interface AuthService {

    /**
     * @return - nickname если пользоатель есть и null если пользователя нет
     */
    String getNicknameByLoginAndPassword(String login, String password);
}
